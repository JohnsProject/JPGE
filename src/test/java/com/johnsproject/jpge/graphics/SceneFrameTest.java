package com.johnsproject.jpge.graphics;

import org.junit.Test;

public class SceneFrameTest {
	
	@Test
	public void sceneFrameInitialzingTest() throws Exception {
		SceneFrame frame = new SceneFrame(640, 480);
		assert(frame.getScene() != null);
		assert(frame.getSceneRenderer() != null);
	}
}
