package com.johnsproject.jpge.utils;

import org.junit.Test;

/**
 * Test class for {@link MathUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class MathUtilsTest {
	
//	@Test
//	public void test() throws Exception {
//		int a = 50000;
//		for (int i = 0; i < 10; i++) {
//			System.out.println("start value : \t" + (a));
//			System.out.println("shift >> " + i + " : \t" + (a >> i));
//			System.out.println("div value : \t" + (a/(a >> i)));
//			System.out.println("shift << " + i + " : \t" + (a << i));
//			System.out.println("mult value : \t" + ((a << i)/a));
//			System.out.println("-------------------------------");
//		}
//	}
//	
//	@Test
//	public void genLookupTableTest() throws Exception {
//		int langle = 0;
//		System.out.print("private static short[] valuesSin = {");
//		for (int angle = 0; angle < 91; angle++) {
//			System.out.print((int)Math.round((Math.sin(Math.toRadians(angle)) * 256)) + ", ");
//			if (angle >= langle + 15) {
//				System.out.println("");
//				langle = angle;
//			}
//		}
//		System.out.println("};");
//	}
	
	@Test
	public void sinTest() throws Exception {
		//System.out.println(1<< 15);
		int value = 200, precision = 1;
		for (int angle = 0; angle < 360; angle++) {
			int mi = ((value * MathUtils.sin(angle)) >> MathUtils.SHIFT);
			int mf = (int)Math.round(((value * Math.sin(Math.toRadians(angle)))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void cosTest() throws Exception {
		int value = 200, precision = 1;
		for (int angle = 0; angle < 360; angle++) {
			int mi = (value * MathUtils.cos(angle)) >> MathUtils.SHIFT;
			int mf = (int)Math.round(((value * Math.cos(Math.toRadians(angle)))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void tanTest() throws Exception {
		int value = 200, precision = 700;
		// until 90 because 90 is infinity
		for (int angle = 0; angle < 90; angle++) {
			int mi = (value * MathUtils.tan(angle)) >> MathUtils.SHIFT;
			int mf = (int)Math.round(((value * Math.tan(Math.toRadians(angle)))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void cotTest() throws Exception {
		int value = 200, precision = 700;
		for (int angle = 0; angle < 90; angle++) {
			int mi = (value * MathUtils.cot(angle)) >> MathUtils.SHIFT;
			int mf = (int)Math.round(((value * (1/Math.tan(Math.toRadians(angle))))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void secTest() throws Exception {
		int value = 200, precision = 2000;
		for (int angle = 0; angle < 90; angle++) {
			int mi = (value * MathUtils.sec(angle)) >> MathUtils.SHIFT;
			int mf = (int)Math.round(((value * (1/Math.cos(Math.toRadians(angle))))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void cosecTest() throws Exception {
		int value = 200, precision = 700;
		for (int angle = 0; angle < 90; angle++) {
			int mi = (value * MathUtils.cosec(angle)) >> MathUtils.SHIFT;
			int mf = (int)Math.round(((value * (1/Math.sin(Math.toRadians(angle))))));
			//System.out.println("angle " + angle + ", mi " + mi + ", mf " + mf);
			assert(mi <= mf + precision);
			assert(mi >= mf - precision);
		}
	}
	
	@Test
	public void getQuadrantTest() throws Exception {
		// 1 Quadrant // 2 Quadrant // 3 Quadrant // 4 Quadrant
		int angle1 = 45, angle2 = 120, angle3 = 200, angle4 = 320;
		assert(MathUtils.getQuadrant(angle1) == 1);
		assert(MathUtils.getQuadrant(angle2) == 2);
		assert(MathUtils.getQuadrant(angle3) == 3);
		assert(MathUtils.getQuadrant(angle4) == 4);
	}
	
	@Test
	public void clamp0to360Test() throws Exception {
		int angle = 560;
		assert(MathUtils.clamp0to360(angle) >= 0);
		assert(MathUtils.clamp0to360(angle) <= 360);
	}
	
	@Test
	public void clamp0to270Test() throws Exception {
		int angle = 560;
		assert(MathUtils.clamp0to270(angle) >= 0);
		assert(MathUtils.clamp0to270(angle) <= 270);
	}
	
	@Test
	public void clamp0to180Test() throws Exception {
		int angle = 560;
		assert(MathUtils.clamp0to180(angle) >= 0);
		assert(MathUtils.clamp0to180(angle) <= 180);
	}
	
	@Test
	public void clamp0to90Test() throws Exception {
		int angle = 560;
		assert(MathUtils.clamp0to90(angle) >= 0);
		assert(MathUtils.clamp0to90(angle) <= 90);
	}
	
	@Test
	public void randomTest() throws Exception {
		int[] values = new int[100000];
		for (int i = 0; i < values.length; i++) {
			values[i] = MathUtils.random();
		}
		// shows that this random value ins't equals to any other of the array
		int random = MathUtils.random();
		for (int i = 0; i < values.length; i++) {
			assert(random != values[i]);
		}
	}
	
	@Test
	public void sqrtTest() throws Exception {
		assert(MathUtils.sqrt(25) == 5);
		assert(MathUtils.sqrt(100) == 10);
	}
	
	@Test
	public void pow2Test() throws Exception {
		assert(MathUtils.sqrt(MathUtils.pow(5,2)) == 5);
		assert(MathUtils.sqrt(MathUtils.pow(50,2)) == 50);
	}
}
