package com.johnsproject.jpge.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.junit.Test;

/**
 * Test class for {@link MathUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class MathUtilsTest {
	
	@Test
	public void test() throws Exception {
		int a = 50000;
		for (int i = 0; i < 10; i++) {
			System.out.println("start value : \t" + (a));
			System.out.println("shift >> " + i + " : \t" + (a >> i));
			System.out.println("div value : \t" + (a/(a >> i)));
			System.out.println("shift << " + i + " : \t" + (a << i));
			System.out.println("mult value : \t" + ((a << i)/a));
			System.out.println("-------------------------------");
		}
	}
}
