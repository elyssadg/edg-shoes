package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegisterView extends View {
	
	private UserController uc = UserController.getInstance();
	
	private Scene scene;
	
	private VBox container, bottomContainer;
	private GridPane formContainer;
	private FlowPane genderContainer;
	private HBox gotoLoginContainer;
	
	private Label title, usernameLbl, emailLbl, passwordLbl, genderLbl, haveAccLbl, loginLbl;
	private TextField usernameTf, emailTf;
	private PasswordField passwordTf;
	private ToggleGroup gender;
	private RadioButton maleRb, femaleRb;
	private Button registerBtn;
	
	public RegisterView() {
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
		genderContainer = new FlowPane();
		gotoLoginContainer = new HBox();
		
		scene = new Scene(container, 1000, 600);
		
		title = new Label("Hello!");
		usernameLbl = new Label("Username");
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		genderLbl = new Label("Gender");
		haveAccLbl = new Label("Already have an account?");
		loginLbl = new Label("Login");
		
		usernameTf = new TextField();
		usernameTf.setPromptText("Must be unique and more than 5 characters");
		emailTf = new TextField();
		emailTf.setPromptText("Must end with @gmail.com");
		
		passwordTf = new PasswordField();
		passwordTf.setPromptText("Must be more than 8 characters");
		
		gender = new ToggleGroup();
		maleRb = new RadioButton("Male");
		femaleRb = new RadioButton("Female");
		gender.getToggles().add(maleRb);
		gender.getToggles().add(femaleRb);
		
		registerBtn = new Button("Register");
	}

	@Override
	public void setPosition() {
		container.getChildren().addAll(title, formContainer, bottomContainer);
		
		bottomContainer.getChildren().addAll(registerBtn, gotoLoginContainer);
		
		gotoLoginContainer.getChildren().addAll(haveAccLbl, loginLbl);
		
		genderContainer.getChildren().addAll(maleRb, femaleRb);
		
		formContainer.add(usernameLbl, 0, 0);
		formContainer.add(usernameTf, 1, 0);
		
		formContainer.add(emailLbl, 0, 1);
		formContainer.add(emailTf, 1, 1);
		
		formContainer.add(passwordLbl, 0, 2);
		formContainer.add(passwordTf, 1, 2);
		
		formContainer.add(genderLbl, 0, 3);
		formContainer.add(genderContainer, 1, 3);
	}

	@Override
	public void setStyle() {
		formContainer.setVgap(10);
		formContainer.setHgap(20);
		formContainer.setAlignment(Pos.CENTER);
		
		gotoLoginContainer.setAlignment(Pos.CENTER);
		gotoLoginContainer.setSpacing(5);
		
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.setSpacing(5);
		
		container.setPadding(new Insets(30));
		container.setSpacing(50);
		container.setAlignment(Pos.CENTER);
		
		genderContainer.setHgap(10);
		
		setTitleFont(title);
		setBoldFont(loginLbl);
		setBoldFont(usernameLbl);
		setBoldFont(emailLbl);
		setBoldFont(genderLbl);
		setBoldFont(passwordLbl);
		
		registerBtn.setPrefWidth(250);
	}

	@Override
	public void setEventHandler() {
		registerBtn.setOnAction(e -> {
			String username = usernameTf.getText();
			String email = emailTf.getText();
			String password = passwordTf.getText();
			String selectedGender = "";
			if (maleRb.isSelected()) selectedGender = "Male";
			else if (femaleRb.isSelected()) selectedGender = "Female";
			
			String errorMessage = uc.validate(username, email, password, selectedGender);
			if (errorMessage.equals("")) {
				uc.add(username, email, password, selectedGender);
				new LoginView();
			} else {
				showErrorAlert(errorMessage);
				resetComponent();
			}
		});
		
		loginLbl.setOnMouseClicked(e -> {
			new LoginView();
		});
	}
	
	public void resetComponent() {
		usernameTf.clear();
		emailTf.clear();
		passwordTf.clear();
		gender.selectToggle(null);
	}

}
