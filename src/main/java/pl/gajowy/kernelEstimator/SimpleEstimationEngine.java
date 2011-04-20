package pl.gajowy.kernelEstimator;

import static java.lang.Math.*;
import static java.lang.System.nanoTime;

public class SimpleEstimationEngine implements EstimationEngine {

    public CalculationOutcome estimate(float bandwidth, float[] dataPoints, SamplingSettings samplingSettings) {
        float[] estimationPoints = new float[samplingSettings.getSampleSize()];
        long time = nanoTime();
        float estimatedPoint = samplingSettings.getStartPoint();
        for (int i = 0; i < samplingSettings.getSampleSize(); i++) {
            estimationPoints[i] = estimateForPoint(estimatedPoint, dataPoints, bandwidth);
            estimatedPoint += samplingSettings.getDensity();
        }
        time = nanoTime() - time;
        return new CalculationOutcome(estimationPoints, time);
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

