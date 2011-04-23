package pl.gajowy.kernelEstimator;

import com.google.common.base.Preconditions;
import com.jogamp.opencl.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.FloatBuffer;

import static com.jogamp.opencl.CLCommandQueue.Mode.PROFILING_MODE;
import static com.jogamp.opencl.CLMemory.Mem.READ_ONLY;
import static com.jogamp.opencl.CLMemory.Mem.WRITE_ONLY;
import static java.lang.Math.PI;
import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.System.nanoTime;

public class OpenCLOneDEstimationEngineBiggerGroups implements EstimationEngine {
    private static final boolean ASYNCHRONOUS = false;
    private static final boolean SYNCHRONOUS = true;

    public CalculationOutcome estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {

        CLContext context = CLContext.create();
        System.out.println(context);

        try {
            CLDevice device = context.getMaxFlopsDevice();
            CLCommandQueue queue = device.createCommandQueue(PROFILING_MODE);

            //TODO program path
            CLProgram program = context.createProgram(new ClassPathResource("/pl/gajowy/kernelEstimator/kernels/OneDGroups.cl").getInputStream()).build();

            CLBuffer<FloatBuffer> dataPointsBuffer = context.createFloatBuffer(dataPoints.length, READ_ONLY);
            dataPointsBuffer.getBuffer().put(dataPoints);
            dataPointsBuffer.getBuffer().rewind();

            CLBuffer<FloatBuffer> estimatesBuffer = context.createFloatBuffer(samplingSettings.getSampleSize(), WRITE_ONLY);

            CLKernel kernel = program.createCLKernel("estimate"); //TODO unhardcode
            kernel.putArg(bandwidth)
                    .putArg(dataPointsBuffer).putArg(dataPointsBuffer.getCLCapacity())
                    .putArg(samplingSettings.getStartPoint())
                    .putArg(samplingSettings.getDensity())
                    .putArg(estimatesBuffer).putArg(estimatesBuffer.getCLCapacity())
                    .putArg(new Float(PI).floatValue())
            ;

            final CLEventList events = new CLEventList(1);

            int computeUnitsAvailable = 14; //TODO get from device
            int threadsPerWorkGroup = max((int) ceil(samplingSettings.getSampleSize() / (double) computeUnitsAvailable), 1);

            long time = nanoTime();
            queue.putWriteBuffer(dataPointsBuffer, ASYNCHRONOUS)
                .put1DRangeKernel(kernel, 0, computeUnitsAvailable * threadsPerWorkGroup, threadsPerWorkGroup, events)
                .putReadBuffer(estimatesBuffer, SYNCHRONOUS)
                .finish()
                .release();

            time = nanoTime() - time;

            CLEvent probe = events.getEvent(0);
            long timeFromProfiling = probe.getProfilingInfo(CLEvent.ProfilingCommand.END)
                      - probe.getProfilingInfo(CLEvent.ProfilingCommand.START);
            events.release();

            FloatBuffer resultBuffer = estimatesBuffer.getBuffer();
            resultBuffer.rewind();
            float[] result = new float[resultBuffer.capacity()];
            resultBuffer.get(result);


            dataPointsBuffer.release();
            estimatesBuffer.release();

            return new CalculationOutcome(result, time, timeFromProfiling);

        } catch (IOException e) {
            throw new CouldNotReadKernelSourceException(e);
        } catch (CLException e) {
            throw e; //TODO some common exception class for all failures of this method?
        } finally {
            // cleanup all resources associated with this context.
            context.release();
        }
    }
}
