package com.johnsproject.jpge.graphics;

import org.junit.Test;

public class SceneWindowTest {
	
	@Test
	public void SceneWindowInitialzingTest() throws Exception {
		SceneWindow frame = new SceneWindow(640, 480);
		assert(frame.getDepthBuffer() != null);
	}
}
