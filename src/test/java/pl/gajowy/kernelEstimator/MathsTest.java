package pl.gajowy.kernelEstimator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class MathsTest {

    @Test
    public void shouldAnswer2for3() {
        assertThat(Maths.biggestPowerOfTwoWithin(3), is(2));
    }

    @Test
    public void shouldAnswer16for16() {
        assertThat(Maths.biggestPowerOfTwoWithin(16), is(16));
    }

    @Test
    public void shouldAnswer1for1() {
        assertThat(Maths.biggestPowerOfTwoWithin(1), is(1));
    }

    @Test
    public void shouldAnswer1024for2000() {
        assertThat(Maths.biggestPowerOfTwoWithin(2000), is(1024));
    }
}
