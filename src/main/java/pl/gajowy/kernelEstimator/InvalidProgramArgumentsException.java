package pl.gajowy.kernelEstimator;

public class InvalidProgramArgumentsException extends RuntimeException {
    public InvalidProgramArgumentsException(String message) {
        super(message);
    }

    public InvalidProgramArgumentsException() {
        super("");
    }
}
