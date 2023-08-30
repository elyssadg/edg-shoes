package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewController {
	
	private static Stage stage;
	
	public static void setScene(Scene newScene) {
		stage.setScene(newScene);
		stage.show();
	}

	public static void setStage(Stage stage) {
		ViewController.stage = stage;
	}
	
}
