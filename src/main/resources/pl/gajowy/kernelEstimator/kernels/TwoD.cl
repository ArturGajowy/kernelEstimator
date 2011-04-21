
float gaussianKernel(float x, float PI) {
    return exp(- x * x / 2) / sqrt(2 * PI);
}

kernel void estimate(
    private const float bandwidth,
    global const float* dataPoints, private const int dataPointsSize,
    private const float startPoint,
    private const float density,
    local float* partialEstimates,
    global float* estimationPoints,
    private float PI
) {
    int i = get_global_id(0);
    int j = get_global_id(1);
    int partialEstimatesSize = get_local_size(1);
    int segmentSize = dataPointsSize / partialEstimatesSize; //host has to assure that dataPointsSize >= get_local_size(1)
    int segmentStart = j * segmentSize;
    int segmentEnd = min(dataPointsSize, (j + 1) * segmentSize);

    float estimatedPoint = startPoint + i * density;
    float estimate = 0;
    for (int k = segmentStart; k < segmentEnd; k++) {
        estimate += (
            gaussianKernel((estimatedPoint - dataPoints[k]) / bandwidth, PI) / dataPointsSize
        );
    }
    partialEstimates[j] = estimate;

    barrier(CLK_LOCAL_MEM_FENCE);
    for (int m = 1; m < partialEstimatesSize; m <<= 1) {
        if ((j % (2 * m)) == 0) {
            partialEstimates[j] += partialEstimates[j + m];
        }
        barrier(CLK_LOCAL_MEM_FENCE);
    }

    if (j == 0) {
        estimationPoints[i] = partialEstimates[j];
    }
}
