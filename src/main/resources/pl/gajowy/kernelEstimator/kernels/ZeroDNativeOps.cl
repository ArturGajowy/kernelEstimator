
float gaussianKernel(float x, float PI) {
    return native_exp(- x * x / 2) / native_sqrt(2 * PI);
}

kernel void estimate(
    private const float bandwidth,
    global const float* dataPoints, private const int dataPointsSize,
    private const float startPoint,
    private const float density,
    private const int sampleSize,
    global float* estimationPoints,
    private float PI
) {
    float estimatedPoint;
    float estimate;
    for (int i = 0; i < sampleSize; i++) {
        estimatedPoint = startPoint + i * density;
        estimate = 0;
        for (int j = 0; j < dataPointsSize; j++) {
            estimate += (
                gaussianKernel((estimatedPoint - dataPoints[j]) / bandwidth, PI) / dataPointsSize
            );
        }
        estimationPoints[i] = estimate;
    }
}
