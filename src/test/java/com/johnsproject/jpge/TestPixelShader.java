package com.johnsproject.jpge;

import com.johnsproject.jpge.graphics.PixelShader;

public class TestPixelShader extends PixelShader{
	
	@Override
	public void shadePixel(int x, int y, int color, int cameraWidth, int[] viewBuffer) {
		super.setPixel(x, y, color, cameraWidth, viewBuffer);
	}
	
}
