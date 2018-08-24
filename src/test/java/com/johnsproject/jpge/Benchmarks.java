package com.johnsproject.jpge;

import org.junit.Test;

public class Benchmarks {

	@Test
	public void intVSfloatTest() throws Exception {
		//just test method, not working at all.
		int i = 0;
		float f = 0;
		int repetitions = 1000000;
		long initTime = 0, intTime = 0, floatTime = 0;
		for (int j = 0; j < repetitions; j++) {
			initTime = System.currentTimeMillis();
			intOperations(i);
			initTime = System.currentTimeMillis();
			floatOperations(f);
		}
		System.gc();
		for (int j = 0; j < repetitions; j++) {
			initTime = System.currentTimeMillis();
			floatOperations(f);
			initTime = System.currentTimeMillis();
			intOperations(i);
		}
		System.gc();
		for (int j = 0; j < repetitions; j++) {
			initTime = System.currentTimeMillis();
			intOperations(i);
			intTime += System.currentTimeMillis() - initTime;
			initTime = System.currentTimeMillis();
			floatOperations(f);
			floatTime += System.currentTimeMillis() - initTime;
		}
		System.gc();
		for (int j = 0; j < repetitions; j++) {
			initTime = System.currentTimeMillis();
			floatOperations(f);
			floatTime += System.currentTimeMillis() - initTime;
			initTime = System.currentTimeMillis();
			intOperations(i);
			intTime += System.currentTimeMillis() - initTime;
		}
		System.out.println("----------------- int vs float -----------------");
		System.out.println(" Repetitions :\t" + repetitions);
		System.out.println(" int time :\t" + (intTime) + " ns");
		System.out.println(" float time :\t" + (floatTime) + " ns");
	}
	
	public void intOperations(int i) {
		i += 1000;
		i -= 1000;
		i /= 1000;
		i *= 1000;
	}
	
	public void floatOperations(float f) {
		f += 1000;
		f -= 1000;
		f /= 1000;
		f *= 1000;
	}
	
}
