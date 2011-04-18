package pl.gajowy.kernelEstimator;

import com.google.common.base.Preconditions;

public class SamplingSettings {

    private final float endPoint;
    private final float startPoint;
    private final float density;

    public SamplingSettings(float startPoint, float endPoint, float density) {
        Preconditions.checkArgument(startPoint < endPoint, "startPoint must be less than endPoint");
        Preconditions.checkArgument(density > 0, "density must be a positive number");
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.density = density;
    }

    public int getSampleSize() {
        int size = oneFor(startPoint) + oneFor(endPoint);
        for (float point = startPoint + density; point < endPoint; point += density) {
            size += oneFor(point);
        }
        return size;
    }

    private int oneFor(float elementToCount) {
        return 1;
    }

    public float getStartPoint() {
        return startPoint;
    }

    public float getDensity() {
        return density;
    }
}
