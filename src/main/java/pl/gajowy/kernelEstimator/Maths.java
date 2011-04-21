package pl.gajowy.kernelEstimator;

public class Maths {
    public static int biggestPowerOfTwoWithin(int x) {
        int bigPowerOfTwo = 1 << 30;
        while (bigPowerOfTwo > x) {
            bigPowerOfTwo >>= 1;
        }
        return bigPowerOfTwo;
    }
}
