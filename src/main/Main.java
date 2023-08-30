package main;

import controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.RegisterView;

public class Main extends Application {
	
	private static String userId = "";
	
	@Override
	public void start(Stage stage) throws Exception {
		ViewController.setStage(stage);
		new RegisterView();
	}

	public static void main(String[] args) {
		launch();
	}

	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		Main.userId = userId;
	}

}
