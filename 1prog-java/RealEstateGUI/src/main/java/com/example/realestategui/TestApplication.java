package com.example.realestategui;

import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;

public class TestApplication {
	public RealEstateController controller;

	@Before
	public void testFactory() {
		controller = new RealEstateController();
		//controller.welcomeText = new Label("test");
		controller.initialize(null, null);
	}

	@Test
	public void testApp() {
		controller.onHelloButtonClick();
	}
}
