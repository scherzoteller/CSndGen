package edu.princeton.fft;

import junit.framework.TestCase;

import org.junit.Test;

public class ComplexTest extends TestCase {
	@Test
	public void testComplex() throws Exception {
        Complex a = new Complex(5.0, 6.0);
        Complex b = new Complex(-3.0, 4.0);

        assertEquals("a is ko", "5.0 + 6.0i", a.toString());
        assertEquals("b is ko", "-3.0 + 4.0i", b.toString());
        assertEquals("Re(a) is ko", 5.0, a.re());
        assertEquals("Im(a) is ko", 6.0, a.im());
        assertEquals("b + a is ko", new Complex(2.0, 10.0), b.plus(a));
        assertEquals("a - b is ko", new Complex(8.0, 2.0), a.minus(b));
        assertEquals("a * b is ko", new Complex(-39.0, 2.0), a.times(b));
        assertEquals("b * a is ko", new Complex(-39.0, 2.0), b.times(a));
        assertEquals("a / b is ko", new Complex(0.36, -1.52), a.divides(b));
        assertEquals("(a / b) * b is ko", new Complex(5.0, 6.0), a.divides(b).times(b));
        assertEquals("conj(a) is ko", new Complex(5.0, -6.0), a.conjugate());
        assertEquals("|a| is ko", 7.810249675906654, a.abs());
        assertEquals("tan(a) is ko", new Complex(-6.685231390246571E-6, 1.0000103108981198), a.tan());
        
	}
}
