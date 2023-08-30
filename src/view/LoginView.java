package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.User;

public class LoginView extends View {
	
	private UserController uc = UserController.getInstance();
	
	Scene scene;
	
	VBox container, bottomContainer;
	GridPane formContainer;
	HBox gotoRegisterContainer;
	
	Label title, usernameLbl, passwordLbl, noAccLbl, registerLbl;
	TextField usernameTf;
	PasswordField passwordTf;
	Button loginBtn;
	
	public LoginView() {
		initialize();
		setPosition();
		setStyle();
		setEventHandler();
		changeScene(scene);
	}

	@Override
	public void initialize() {
		container = new VBox();
		bottomContainer = new VBox();
		formContainer = new GridPane();
		gotoRegisterContainer = new HBox();
		
		scene = new Scene(container, 1000, 600);
		
		title = new Label("Welcome Back!");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		noAccLbl = new Label("Doesn't have an account?");
		registerLbl = new Label("Register");
		
		usernameTf = new TextField();
		
		passwordTf = new PasswordField();
		
		loginBtn = new Button("Login");
	}

	@Override
	public void setPosition() {
		container.getChildren().addAll(title, formContainer, bottomContainer);
		
		bottomContainer.getChildren().addAll(loginBtn, gotoRegisterContainer);
		
		gotoRegisterContainer.getChildren().addAll(noAccLbl, registerLbl);
		
		formContainer.add(usernameLbl, 0, 0);
		formContainer.add(usernameTf, 1, 0);
		
		formContainer.add(passwordLbl, 0, 1);
		formContainer.add(passwordTf, 1, 1);
	}

	@Override
	public void setStyle() {
		usernameTf.setPrefWidth(400);
		passwordTf.setPrefWidth(400);
		
		formContainer.setVgap(10);
		formContainer.setHgap(20);
		formContainer.setAlignment(Pos.CENTER);
		
		gotoRegisterContainer.setAlignment(Pos.CENTER);
		gotoRegisterContainer.setSpacing(5);
		
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.setSpacing(5);
		
		container.setPadding(new Insets(30));
		container.setSpacing(50);
		container.setAlignment(Pos.CENTER);
		
		setTitleFont(title);
		setBoldFont(registerLbl);
		setBoldFont(usernameLbl);
		setBoldFont(passwordLbl);
		
		loginBtn.setPrefWidth(250);
	}

	@Override
	public void setEventHandler() {
		loginBtn.setOnAction(e -> {
			String username = usernameTf.getText();
			String password = passwordTf.getText();
			
			User user = uc.get(username, password);
			if (user.getId().equals("")) {
				showErrorAlert("User not found");
			} else {
				if (user.getRole().equals("customer")) {
					Main.setUserId(user.getId());
					new ShopView();
				} else {
					Main.setUserId(user.getId());
					new ShoeManagementView();
				}
			}
		});
		
		registerLbl.setOnMouseClicked(e -> {
			new RegisterView();
		});
	}

}
