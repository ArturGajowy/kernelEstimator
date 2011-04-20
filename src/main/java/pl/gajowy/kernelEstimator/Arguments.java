package pl.gajowy.kernelEstimator;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;

import static com.google.common.collect.Lists.newLinkedList;

public class Arguments {

    public static final String START_POINT = "startPoint";
    public static final String END_POINT = "endPoint";
    public static final String DENSITY = "density";
    public static final String BANDWIDTH = "bandwidth";
    public static final String VERIFY = "verify";
    public static final String SHOW_TIMES = "showTimes";

    private float[] dataPoints;
    private float startPoint;
    private float endPoint;
    private float density;
    private float bandwidth;
    private boolean verifyFlag;
    private boolean showTimes;

    public static String getUsageMessage() {
        return "Usage: java -jar kernelEstimator data.dat " +
                Joiner.on(' ').join(START_POINT, END_POINT, DENSITY, BANDWIDTH, optional(VERIFY), optional(SHOW_TIMES));
    }

    private static String optional(String argumentName) {
        return "[" + argumentName + "]";
    }

    public Arguments(String[] args) {
        verifyProperNumberOfArguments(args);
        LinkedList<String> argumentsList = toList(args);
        String dataPointsFilename = popString(argumentsList);
        this.dataPoints = readDataPoints(dataPointsFilename);
        this.startPoint = popFloat(START_POINT, argumentsList);
        this.endPoint = popFloat(END_POINT, argumentsList);
        this.density = popFloat(DENSITY, argumentsList);
        this.bandwidth = popFloat(BANDWIDTH, argumentsList);
        this.verifyFlag = popOptionalFlag(VERIFY, argumentsList);
        this.showTimes = popOptionalFlag(SHOW_TIMES, argumentsList);
        checkNoMoreFlags(argumentsList);
    }

    private void verifyProperNumberOfArguments(String[] args) {
        int minArguments = 5;
        int maxArguments = 7;
        if (args.length < minArguments || args.length > maxArguments) {
            throw new InvalidProgramArgumentsException("Expected from " + minArguments + " to " + maxArguments + " arguments");
        }
    }

    private String popString(LinkedList<String> argumentsList) {
        return argumentsList.pop();
    }

    private float popFloat(String argumentName, LinkedList<String> argsumentsList) {
        try {
            return Float.parseFloat(argsumentsList.pop());
        } catch (NumberFormatException e) {
            throw new InvalidProgramArgumentsException("Error reading float value for " + argumentName);
        }
    }

    private boolean popOptionalFlag(String flagName, LinkedList<String> argumentsList) {
        if (flagName.equals(argumentsList.peek())) {
            argumentsList.pop();
            return true;
        } else {
            return false;
        }
    }

    private void checkNoMoreFlags(LinkedList<String> argumentsList) {
        if (!argumentsList.isEmpty()) {
            throw new InvalidProgramArgumentsException("Unexpected flag: " + argumentsList.peek());
        }
    }

    private float[] readDataPoints(String dataPointsFilename) {
        try {
            File dataPointsFile = new File(dataPointsFilename);
            return Files.readLines(dataPointsFile, Charset.defaultCharset(), new DataPointsParsingLineProcessor());
        } catch (IOException e) {
            throw new InvalidProgramArgumentsException("Error reading file " + dataPointsFilename + ":\n" + e.getMessage());
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

    private LinkedList<String> toList(String[] args) {
        LinkedList<String> argumentsList = newLinkedList();
        for (int i = 0; i < args.length; i++) {
            argumentsList.add(args[i]);
        }
        return argumentsList;
    }

    public boolean verifyFlagDefined() {
        return verifyFlag;
    }

    public boolean showTimesDefined() {
        return showTimes;
    }
}
