package com.johnsproject.jpge;

import com.johnsproject.jpge.graphics.PixelShader;
import com.johnsproject.jpge.utils.ColorUtils;

public class TestPixelShader extends PixelShader{
	
	@Override
	public void shadePixel(int x, int y, int color, int cameraWidth, int[] viewBuffer) {
		super.setPixel(x, y, ColorUtils.brighter(color, 15), cameraWidth, viewBuffer);
	}
	
}
