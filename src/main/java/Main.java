import pl.gajowy.kernelEstimator.*;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = new Arguments(args);
            computeAndShowResults(arguments);
        } catch (InvalidProgramArgumentsException e) {
            System.err.println(e.getMessage());
            System.err.println(Arguments.getUsageMessage());
        }
    }

    private static void computeAndShowResults(Arguments arguments) {
        float[] dataPoints = arguments.getDataPoints();
        SamplingSettings samplingSettings = arguments.getSampling();
        float h = arguments.getBandwidth();

        KernelEstimatorSampling kernelEstimatorSampling = new KernelEstimatorSampling(h, dataPoints, samplingSettings);
        CalculationOutcome calculationOutcome = kernelEstimatorSampling.calculateUsing(new SimpleGpuBasedEstimationEngine());
        writeOut(calculationOutcome.getEstimationPoints());
        if (arguments.showTimesDefined()) {
            System.out.println(calculationOutcome.getElapsedTime());
        }
        if (arguments.verifyFlagDefined()) {
            VerificationOutcome verificationOutcome = new Verifier().verify(kernelEstimatorSampling, calculationOutcome);
            System.out.println(verificationOutcome);
        }
    }

    private static void writeOut(float[] estimationPoints) {
        for (int i = 0; i < estimationPoints.length; i++) {
            float estimate = estimationPoints[i];
            System.out.println(estimate);
        }
    }
}
