package pl.gajowy.kernelEstimator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class MathsTest {

    @Test
    public void shouldAnswer4for3() {
        assertThat(Maths.firstPowerOfTwoBeingAtLeast(3), is(4));
    }

    @Test
    public void shouldAnswer16for16() {
        assertThat(Maths.firstPowerOfTwoBeingAtLeast(16), is(16));
    }

    @Test
    public void shouldAnswer1for1() {
        assertThat(Maths.firstPowerOfTwoBeingAtLeast(1), is(1));
    }

    @Test
    public void shouldAnswer2048for2000() {
        assertThat(Maths.firstPowerOfTwoBeingAtLeast(2000), is(2048));
    }
}
