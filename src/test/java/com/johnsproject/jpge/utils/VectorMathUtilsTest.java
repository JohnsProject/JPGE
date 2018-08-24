package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link VectorMathUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class VectorMathUtilsTest {
	
	@Test
	public void movePointTest() throws Exception {
		int[] sp = new int[] {10, 10, 10};
		int[] p = new int[] {10, 10, 10};
		VectorMathUtils.movePointByAngleZ(p, 90);
		System.out.print(sp[0] + ", " + sp[1] + ", " + sp[2] + ", ");
		System.out.print(p[0] + ", " + p[1] + ", " + p[2] + ", ");
		VectorMathUtils.movePointByAngleZ(p, 0);
		System.out.print(p[0] + ", " + p[1] + ", " + p[2] + ", ");
	}
}
