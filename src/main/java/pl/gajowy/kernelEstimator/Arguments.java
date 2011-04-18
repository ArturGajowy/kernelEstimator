package pl.gajowy.kernelEstimator;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;

public class Arguments {

    public static final int EXPECTED_ARGUMENTS_COUNT = 5;

    private float[] dataPoints;
    private float startPoint;
    private float endPoint;
    private float density;
    private float bandwidth;

    public Arguments(String[] args) {
        verifyProperNumberOfArguments(args);
        String dataPointsFilename = args[0];
        this.dataPoints = readDataPoints(dataPointsFilename);
        parseFloatArguments(args);
    }

    private void verifyProperNumberOfArguments(String[] args) {
        if (args.length != EXPECTED_ARGUMENTS_COUNT) {
            throw new InvalidProgramArgumentsException();
        }
    }

    private float[] readDataPoints(String dataPointsFilename) {
        try {
            File dataPointsFile = new File(dataPointsFilename);
            return Files.readLines(dataPointsFile, Charset.defaultCharset(), new DataPointsParsingLineProcessor());
        } catch (IOException e) {
            throw new InvalidProgramArgumentsException(); //TODO some message?
        }
    }

    private void parseFloatArguments(String[] args) {
        try {
            this.startPoint = Float.parseFloat(args[1]);
            this.endPoint = Float.parseFloat(args[2]);
            this.density = Float.parseFloat(args[3]);
            this.bandwidth = Float.parseFloat(args[4]);
        } catch (NumberFormatException e) {
            throw new InvalidProgramArgumentsException();
        }
    }

    public float[] getDataPoints() {
        return dataPoints;
    }

    public SamplingSettings getSampling() {
        return new SamplingSettings(startPoint, endPoint, density);
    }

    public float getBandwidth() {
        return bandwidth;
    }

}
