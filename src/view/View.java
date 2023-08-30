package view;

import java.util.Optional;

import controller.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class View {
	
	public View() {}
	
	public abstract void initialize();
	public abstract void setPosition();
	public abstract void setStyle();
	public abstract void setEventHandler();
	
	public void changeScene(Scene newScene) {
		ViewController.setScene(newScene);
	}
	
	public void setTitleFont(Label label) {
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	}
	
	public void setBoldFont(Label label) {
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	}
	
	public void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setTitle("Error Message");
		alert.show();
	}
	
	public boolean showConfirmationAlert(String message, ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION, message);
		alert.setTitle("Confirm Action");
		Optional<ButtonType> choice =  alert.showAndWait();
		if (choice.get() == ButtonType.CANCEL) {
			e.consume();
			return false;
		}
		return true;
	}
	
}
