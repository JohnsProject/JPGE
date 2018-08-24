package com.johnsproject.jpge.utils;

import java.awt.Color;

import org.junit.Test;

/**
 * Test class for {@link ColorUtils}.
 * 
 * @author John's Project - John Konrad Ferraz Salomon
 *
 */
public class ColorUtilsTest {
	@Test
	public void convertRgbaTest() throws Exception {
		int c = new Color(50,50,50,50).getRGB();
		int cc = ColorUtils.convert(50,50,50,50);
		assert(c == cc);
	}
	
	@Test
	public void convertRgbTest() throws Exception {
		int c = new Color(50,50,50).getRGB();
		int cc = ColorUtils.convert(50,50,50);
		assert(c == cc);
	}
	
	@Test
	public void getRedTest() throws Exception {
		Color c = new Color(50,50,50,50);
		int cc = ColorUtils.getRed(c.getRGB());
		assert(c.getRed() == cc);
	}
	
	@Test
	public void getGreenTest() throws Exception {
		Color c = new Color(50,50,50,50);
		int cc = ColorUtils.getGreen(c.getRGB());
		assert(c.getGreen() == cc);
	}
	
	@Test
	public void getBlueTest() throws Exception {
		Color c = new Color(50,50,50,50);
		int cc = ColorUtils.getBlue(c.getRGB());
		assert(c.getBlue() == cc);
	}
	
	@Test
	public void getAlphaTest() throws Exception {
		Color c = new Color(50,50,50,50);
		int cc = ColorUtils.getAlpha(c.getRGB());
		assert(c.getAlpha() == cc);
	}
	
	@Test
	public void setRedTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.setRed(cc, 20);
		cc = ColorUtils.getRed(cc);
		assert(20 == cc);
	}
	
	@Test
	public void setGreenTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.setGreen(cc, 20);
		cc = ColorUtils.getGreen(cc);
		assert(20 == cc);
	}
	
	@Test
	public void setBlueTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.setBlue(cc, 20);
		cc = ColorUtils.getBlue(cc);
		assert(20 == cc);
	}
	
	@Test
	public void setAlphaTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.setAlpha(cc, 20);
		cc = ColorUtils.getAlpha(cc);
		assert(20 == cc);
	}
	
	@Test
	public void changeRedTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.changeRed(cc, +20);
		assert(70 == ColorUtils.getRed(cc));
		cc = ColorUtils.changeRed(cc, -20);
		assert(50 == ColorUtils.getRed(cc));
	}
	
	@Test
	public void changeGreenTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.changeGreen(cc, +20);
		assert(70 == ColorUtils.getGreen(cc));
		cc = ColorUtils.changeGreen(cc, -20);
		assert(50 == ColorUtils.getGreen(cc));
	}
	
	@Test
	public void changeBlueTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.changeBlue(cc, +20);
		assert(70 == ColorUtils.getBlue(cc));
		cc = ColorUtils.changeBlue(cc, -20);
		assert(50 == ColorUtils.getBlue(cc));
	}
	
	@Test
	public void changeAlphaTest() throws Exception {
		int cc = ColorUtils.convert(50, 50, 50, 50);
		cc = ColorUtils.changeAlpha(cc, +20);
		assert(70 == ColorUtils.getAlpha(cc));
		cc = ColorUtils.changeAlpha(cc, -20);
		assert(50 == ColorUtils.getAlpha(cc));
	}
	
	@Test
	public void darkerTest() throws Exception {
		int c = ColorUtils.convert(50, 50, 50, 50);
		int cc = ColorUtils.darker(c, 2);
		assert(c > cc);
	}
	
	@Test
	public void brighterTest() throws Exception {
		int c = ColorUtils.convert(50, 50, 50, 50);
		int cc = ColorUtils.brighter(c, 2);
		assert(c < cc);
	}
}
