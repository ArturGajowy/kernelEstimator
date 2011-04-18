package pl.gajowy.kernelEstimator;

import com.google.common.base.Preconditions;

public class KernelEstimatorSampling {
    private float  bandwidth;
    private float[] dataPoints;
    private SamplingSettings samplingSettings;

    public KernelEstimatorSampling(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {
        verifyArguments(bandwidth, dataPoints, samplingSettings);
        this.bandwidth = bandwidth;
        this.dataPoints = dataPoints;
        this.samplingSettings = samplingSettings;
    }

    private void verifyArguments(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {
        Preconditions.checkArgument(bandwidth > 0, "bandwidth must be a positive number");
        Preconditions.checkArgument(dataPoints.length > 0, "dataPoints must have positive length");
    }

    public float[] calculateUsing(EstimationEngine estimationEngine) {
        return estimationEngine.estimate(bandwidth, dataPoints, samplingSettings);
    }
}
