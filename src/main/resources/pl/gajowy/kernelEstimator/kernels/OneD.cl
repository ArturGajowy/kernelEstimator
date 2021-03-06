
float gaussianKernel(float x, float PI) {
    return exp(- x * x / 2) / sqrt(2 * PI);
}

kernel void estimate(
    private const float bandwidth,
    global const float* dataPoints, private const int dataPointsSize,
    private const float startPoint,
    private const float density,
    global float* estimationPoints,
    private float PI
) {
    float estimatedPoint;
    float estimate;
    int i = get_global_id(0);
    estimatedPoint = startPoint + i * density;
    estimate = 0;
    for (int j = 0; j < dataPointsSize; j++) {
        estimate += (
            gaussianKernel((estimatedPoint - dataPoints[j]) / bandwidth, PI) / dataPointsSize
        );
    }
    estimationPoints[i] = estimate;
}
