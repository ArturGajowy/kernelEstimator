package pl.gajowy.kernelEstimator;

import java.io.IOException;

public class CouldNotReadKernelSourceException extends RuntimeException {
    public CouldNotReadKernelSourceException(IOException e) {
        super(e);
    }
}
