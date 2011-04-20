package pl.gajowy.kernelEstimator;

public class CalculationOutcome {
    private float[] estimationPoints;
    private Long timeFromProfiling;
    private long elapsedTime;

    public CalculationOutcome(float[] estimationPoints, long elapsedTime, Long timeFromProfiling) {
        this.estimationPoints = estimationPoints;
        this.elapsedTime = elapsedTime;
        this.timeFromProfiling = timeFromProfiling;
    }

    public CalculationOutcome(float[] estimationPoints, long elapsedTime) {
        this(estimationPoints, elapsedTime, null);
    }

    public float[] getEstimationPoints() {
        return estimationPoints;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
