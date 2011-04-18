package pl.gajowy.kernelEstimator;

import com.google.common.io.LineProcessor;

import java.io.IOException;

class DataPointsParsingLineProcessor implements LineProcessor<float[]> {

    float[] points;
    int pointsRead = 0;

    public boolean processLine(String line) throws IOException {
        line = line.trim();
        if (isNotEmptyOrComment(line)) {
            if (dataSizeKnown()) {
                readPoint(line);
            } else {
                readDataSize(line);
            }
        }
        return true;
    }

    private void readPoint(String line) {
        points[pointsRead++] = Float.parseFloat(line); //TODO handle NFE
    }

    private void readDataSize(String line) {
        points = new float[Integer.parseInt(line)]; //TODO handle NFE
    }

    private boolean dataSizeKnown() {
        return points != null;
    }

    private boolean isNotEmptyOrComment(String trimmedLine) {
        return !trimmedLine.isEmpty() && !trimmedLine.startsWith("#");
    }

    public float[] getResult() {
        return points;
    }
}
