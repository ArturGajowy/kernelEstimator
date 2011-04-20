package pl.gajowy.kernelEstimator;

public class CalculationOutcome {
    private float[] estimationPoints;
    private long elapsedTime;
    private Long profiledTime;

    public CalculationOutcome(float[] estimationPoints, long elapsedTime, Long profiledTime) {
        this.estimationPoints = estimationPoints;
        this.elapsedTime = elapsedTime;
        this.profiledTime = profiledTime;
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

    public Long getProfiledTime() {
        return profiledTime;
    }
}
