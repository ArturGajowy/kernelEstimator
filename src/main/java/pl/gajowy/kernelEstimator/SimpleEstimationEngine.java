package pl.gajowy.kernelEstimator;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

public class SimpleEstimationEngine implements EstimationEngine {

    public float[] estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {
        float[] estimationPoints = new float[samplingSettings.getSampleSize()];
        float estimatedPoint = samplingSettings.getStartPoint();
        for (int i = 0; i < samplingSettings.getSampleSize(); i++) {
            estimationPoints[i] = estimateForPoint(estimatedPoint, dataPoints, bandwidth);
            estimatedPoint += samplingSettings.getDensity();
        }
        return estimationPoints;
    }

    private float estimateForPoint(float estimatedPoint, float[] dataPoints, float bandwidth) {
        float estimate = 0;
        for (float dataPoint : dataPoints) {
            estimate += kernel((estimatedPoint - dataPoint) / bandwidth) / dataPoints.length;
        }
        return estimate ;
    }

    private float kernel(float x) {
        return new Float(exp(- x * x / 2) / sqrt(2 * PI));
    }
}

