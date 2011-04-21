package pl.gajowy.kernelEstimator;

public class Maths {
    public static int firstPowerOfTwoBeingAtLeast(int x) {
        int powerOfTwo = 1;
        while (powerOfTwo < x) {
            powerOfTwo <<= 1;
        }
        return powerOfTwo;
    }
}
