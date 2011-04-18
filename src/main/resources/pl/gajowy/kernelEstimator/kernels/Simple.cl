//#pragma opencl cl_khr_byte_addressable_store : enable


/*inline*/ float gaussianKernel(float x, float PI) {
    return exp(- x * x / 2) / sqrt(2 * PI);
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
    for (int i = 0; i < sampleSize; i++) {
        estimatedPoint = startPoint + i * density;
        for (int j = 0; j < dataPointsSize; j++) {
            estimationPoints[i] += (
                gaussianKernel((estimatedPoint - dataPoints[j]) / bandwidth, PI) / dataPointsSize
            );
        }
    }
}
