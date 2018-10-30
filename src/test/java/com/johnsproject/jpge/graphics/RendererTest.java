package com.johnsproject.jpge.graphics;

import org.junit.Test;

public class RendererTest {
	
	@Test
	public void ySortTest() throws Exception {
		int y1 = 20, y2 = 15, y3 = 60;
		int a = 0, b = 0, c = 0;
		if (y1 > c) c = y1; if (y1 < c && y1 > b) b = y1; if (y1 < b && y1 > a) a = y1;
		if (y2 > c) c = y2; if (y2 < c && y2 > b) b = y2; if (y2 < b && y2 > a) a = y2;
		if (y3 > c) c = y3; if (y3 < c && y3 > b) b = y3; if (y3 < b && y3 > a) a = y3;
		assert(a < b);
		assert(b < c);
		System.out.println(a + ", " + b + ", " + c);
	}
}
