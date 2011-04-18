import com.google.common.base.Joiner;
import com.google.common.io.Files;
import pl.gajowy.kernelEstimator.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = new Arguments(args);
            computeAndShowResults(arguments);
        } catch (InvalidProgramArgumentsException e) {
            System.err.println("Usage: java -jar kernelEstimator data.dat xmin xmax density bandwidth");
            //TODO handle errors other than argument counts and types (file io)
        }
    }

    private static void computeAndShowResults(Arguments arguments) {
        float[] dataPoints = arguments.getDataPoints();
        SamplingSettings samplingSettings = arguments.getSampling();
        float h = arguments.getBandwidth();

        KernelEstimatorSampling kernelEstimatorSampling = new KernelEstimatorSampling(h, dataPoints, samplingSettings);
        float[] estimationPoints = kernelEstimatorSampling.calculateUsing(new SimpleGpuBasedEstimationEngine());
        VerificationOutcome verificationOutcome = new Verifier().verify(kernelEstimatorSampling, estimationPoints);

        showResults(estimationPoints, verificationOutcome);
    }

    private static void showResults(float[] estimationPoints, VerificationOutcome verificationOutcome) {
        String dataLines = "";
        for (int i = 0; i < estimationPoints.length; i++) {
            dataLines += estimationPoints[i] + "\n";
        }
        try {
            File to = new File("data.txt.out");
            to.createNewFile();
            Files.write(dataLines, to, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        System.out.println("size " + estimationPoints.length);
        System.out.println(verificationOutcome);
    }
}
