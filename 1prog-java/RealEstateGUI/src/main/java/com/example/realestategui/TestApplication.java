package com.example.realestategui;

import javafx.scene.control.Label;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestApplication {
	public RealEstateController controller;

	@BeforeClass
	public static void testBeforeAll() {
		RealEstateController.isRunningTest = true;
	}

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

	@AfterClass
	public static void testAfterAll() {
		RealEstateController.isRunningTest = false;
	}
}
