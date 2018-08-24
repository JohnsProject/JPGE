package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link Vector3Utils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class Vector3UtilsTest {
	
	@Test
	public void getTest() throws Exception {
//		int[] aold = new int[]{25, 35, 45};
//		int[] bold = new int[]{11, 12, 14};
//		int[] a = new int[]{25, 35, 45};
//		int[] b = new int[]{11, 12, 14};
//		assert(Vector3Utils.equals(a, aold));
//		assert(Vector3Utils.equals(bold, b));
//		Vector3Utils.swap(a, b);
//		assert(Vector3Utils.equals(a, bold));
//		assert(Vector3Utils.equals(aold, b));
	}
	
	@Test
	public void swapTest() throws Exception {
		int[] aold = new int[]{25, 35, 45};
		int[] bold = new int[]{11, 12, 14};
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(Vector3Utils.equals(a, aold));
		assert(Vector3Utils.equals(bold, b));
		Vector3Utils.swap(a, b);
		assert(Vector3Utils.equals(a, bold));
		assert(Vector3Utils.equals(aold, b));
	}
	
	@Test
	public void addTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f+d, f+d, f+d};
		int[] e = Vector3Utils.add(a, b);
		assert(Vector3Utils.equals(e, c));
		a = Vector3Utils.match(a, f);
		e = Vector3Utils.add(a, d);
		assert(Vector3Utils.equals(e, c));
	}
	
	@Test
	public void subtractTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f-d, f-d, f-d};
		int[] e = Vector3Utils.subtract(a, b);
		assert(Vector3Utils.equals(e, c));
		a = Vector3Utils.match(a, f);
		e = Vector3Utils.subtract(a, d);
		assert(Vector3Utils.equals(e, c));
	}
	
	@Test
	public void multiplicationTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f*d, f*d, f*d};
		int[] e = Vector3Utils.multiply(a, b);
		assert(Vector3Utils.equals(e, c));
		a = Vector3Utils.match(a, f);
		e = Vector3Utils.multiply(a, d);
		assert(Vector3Utils.equals(e, c));
	}
	
	@Test
	public void divisionTest() throws Exception {
		int d = 2, f = 10;
		int[] a = new int[]{f, f, f};
		int[] b = new int[]{d, d, d};
		int[] c = new int[]{f/d, f/d, f/d};
		int[] e = Vector3Utils.divide(a, b);
		assert(Vector3Utils.equals(e, c));
		a = Vector3Utils.match(a, f);
		e = Vector3Utils.divide(a, d);
		assert(Vector3Utils.equals(e, c));
	}
	
	@Test
	public void minTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(Vector3Utils.equals(Vector3Utils.min(a, b), b));
	}
	
	@Test
	public void maxTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		assert(Vector3Utils.equals(Vector3Utils.max(a, b), a));
	}
	
	@Test
	public void matchTest() throws Exception {
		int d = 5;
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{11, 12, 14};
		int[] c = new int[]{d, d, d};
		assert(!Vector3Utils.equals(a, b));
		Vector3Utils.match(a, b);
		assert(Vector3Utils.equals(a, b));
		assert(!Vector3Utils.equals(a, c));
		Vector3Utils.match(a, d);
		assert(Vector3Utils.equals(a, c));
	}
	
	@Test
	public void equalsTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] b = new int[]{25, 35, 45};
		int[] c = new int[]{11, 12, 14};
		assert(Vector3Utils.equals(a, b));
		assert(!Vector3Utils.equals(a, c));
	}
	
	@Test
	public void invertTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		int[] ap = new int[]{25, 35, 45};
		int[] an = new int[]{-25, -35, -45};
		Vector3Utils.invert(a);
		assert(Vector3Utils.equals(a, an));
		Vector3Utils.invert(a);
		assert(Vector3Utils.equals(a, ap));
	}
	
	@Test
	public void toStringTest() throws Exception {
		int[] a = new int[]{25, 35, 45};
		String s = Vector3Utils.toString(a);
		assert(s.contains(a[Vector3Utils.X] + ""));
		assert(s.contains(a[Vector3Utils.Y] + ""));
		assert(s.contains(a[Vector3Utils.Z] + ""));
	}
}
