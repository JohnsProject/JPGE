package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link VectorUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class VectorUtilsTest {
	@Test
	public void swapTest() throws Exception {
		int[] aold = new int[]{25, 35, 45};
		int[] bold = new int[]{11, 12, 14};
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(VectorUtils.equals(a, aold));
		assert(VectorUtils.equals(bold, b));
		VectorUtils.swap(a, b);
		assert(VectorUtils.equals(a, bold));
		assert(VectorUtils.equals(aold, b));
	}
	
	@Test
	public void addTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f+d, f+d, f+d};
		int[] e = VectorUtils.add(a, b);
		assert(VectorUtils.equals(e, c));
		a = VectorUtils.match(a, f);
		e = VectorUtils.add(a, d);
		assert(VectorUtils.equals(e, c));
	}
	
	@Test
	public void subtractTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f-d, f-d, f-d};
		int[] e = VectorUtils.subtract(a, b);
		assert(VectorUtils.equals(e, c));
		a = VectorUtils.match(a, f);
		e = VectorUtils.subtract(a, d);
		assert(VectorUtils.equals(e, c));
	}
	
	@Test
	public void multiplicationTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f*d, f*d, f*d};
		int[] e = VectorUtils.multiply(a, b);
		assert(VectorUtils.equals(e, c));
		a = VectorUtils.match(a, f);
		e = VectorUtils.multiply(a, d);
		assert(VectorUtils.equals(e, c));
	}
	
	@Test
	public void divisionTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f/d, f/d, f/d};
		int[] e = VectorUtils.divide(a, b);
		assert(VectorUtils.equals(e, c));
		a = VectorUtils.match(a, f);
		e = VectorUtils.divide(a, d);
		assert(VectorUtils.equals(e, c));
	}
	
	@Test
	public void minTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(VectorUtils.equals(VectorUtils.min(a, b), b));
	}
	
	@Test
	public void maxTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(VectorUtils.equals(VectorUtils.max(a, b), a));
	}
	
	@Test
	public void matchTest() throws Exception {
		int d = 5;
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		int[] c = new int[]{d, d, d};
		assert(!VectorUtils.equals(a, b));
		VectorUtils.match(a, b);
		assert(VectorUtils.equals(a, b));
		assert(!VectorUtils.equals(a, c));
		VectorUtils.match(a, d);
		assert(VectorUtils.equals(a, c));
	}
	
	@Test
	public void equalsTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{25, 35, 45};
		int[] c = new int[]{11, 12, 14};
		assert(VectorUtils.equals(a, b));
		assert(!VectorUtils.equals(a, c));
	}
	
	@Test
	public void invertTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] ap = new int[]{25, 35, 45};
		int[] an = new int[]{-25, -35, -45};
		VectorUtils.invert(a);
		assert(VectorUtils.equals(a, an));
		VectorUtils.invert(a);
		assert(VectorUtils.equals(a, ap));
	}
	
	@Test
	public void toStringTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		String s = VectorUtils.toString(a);
		assert(s.contains(a[VectorUtils.X] + ""));
		assert(s.contains(a[VectorUtils.Y] + ""));
		assert(s.contains(a[VectorUtils.Z] + ""));
	}
}
