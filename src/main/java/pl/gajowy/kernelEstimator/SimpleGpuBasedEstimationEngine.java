package pl.gajowy.kernelEstimator;

import com.google.common.base.Preconditions;
import com.jogamp.opencl.*;
import com.jogamp.opencl.util.Filter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.min;
import static java.lang.System.nanoTime;
import static java.lang.System.out;

import static com.jogamp.opencl.CLMemory.Mem.*;

public class SimpleGpuBasedEstimationEngine implements EstimationEngine {
    private static final boolean ASYNCHRONOUS = false;
    private static final boolean SYNCHRONOUS = true;

    public float[] estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {


//        CLPlatform platform = CLPlatform.getDefault(hasGpuDeviceFilter());
//        verifyPlatformAvailable(platform);
//        CLContext context = CLContext.create(platform, CLDevice.Type.GPU);

        CLContext context = CLContext.create();
        System.out.println(context);

        // always make sure to release the context under all circumstances
        // not needed for this particular sample but recommented
        try {
            CLDevice device = context.getMaxFlopsDevice();
            CLCommandQueue queue = device.createCommandQueue();

            //TODO program path
            CLProgram program = context.createProgram(new ClassPathResource("/pl/gajowy/kernelEstimator/kernels/Simple.cl").getInputStream()).build();

            CLBuffer<FloatBuffer> dataPointsBuffer = context.createFloatBuffer(dataPoints.length, READ_ONLY);
            dataPointsBuffer.getBuffer().put(dataPoints);
            dataPointsBuffer.getBuffer().rewind();

            CLBuffer<FloatBuffer> estimatesBuffer = context.createFloatBuffer(samplingSettings.getSampleSize(), WRITE_ONLY);

            CLKernel kernel = program.createCLKernel("estimate"); //TODO unhardcode
            kernel.putArg(bandwidth)
                    .putArg(dataPointsBuffer).putArg(dataPointsBuffer.getCLCapacity())
                    .putArg(samplingSettings.getStartPoint())
                    .putArg(samplingSettings.getDensity())
                    .putArg(samplingSettings.getSampleSize())
                    .putArg(estimatesBuffer)
                    .putArg(new Float(PI).floatValue())
            ;

            // asynchronous write of data to GPU device,
            // followed by blocking read to get the computed results back.
            long time = nanoTime();
            queue.putWriteBuffer(dataPointsBuffer, ASYNCHRONOUS)
                .putTask(kernel)
                .putReadBuffer(estimatesBuffer, ASYNCHRONOUS)
                .putBarrier()
            ;
            time = nanoTime() - time;

            FloatBuffer resultBuffer = estimatesBuffer.getBuffer();
            resultBuffer.rewind();
            float[] result = new float[resultBuffer.capacity()];
            resultBuffer.get(result);
            return result;

        } catch (IOException e) {
            throw new CouldNotReadKernelSourceException(e);
        } catch (CLException e) {
            throw e; //TODO some common exception class for all failures of this method?
        } finally {
            // cleanup all resources associated with this context.
            context.release();
        }
    }

    private void verifyPlatformAvailable(CLPlatform platform) {
        if (platform == null) {
            throw new RuntimeException("No platforms offering GPU devices found, can't perform computations.");
        }
    }

    private Filter<CLPlatform> hasGpuDeviceFilter() {
        return new Filter<CLPlatform>() {
            public boolean accept(CLPlatform item) {
                return item.listCLDevices(CLDevice.Type.GPU).length > 0;
            }
        };
    }
}
