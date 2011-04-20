package pl.gajowy.kernelEstimator;

public interface EstimationEngine {
    CalculationOutcome estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings);
}
