package pl.gajowy.kernelEstimator;

import com.google.common.base.Joiner;

import java.util.EnumMap;

public class VerificationOutcome {

    private EnumMap<ErrorClass, Integer> errorCountsByClass;

    public VerificationOutcome(EnumMap<ErrorClass, Integer> errorCountsByClass) {
        this.errorCountsByClass = errorCountsByClass;
    }

    @Override
    public String toString() {
        return Joiner.on("\t").join(errorCountsByClass.values());
    }
}
