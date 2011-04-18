package pl.gajowy.kernelEstimator;

public interface EstimationEngine {
    float[] estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings);
}
