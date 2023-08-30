package controller;

import java.sql.SQLException;

import connect.Connect;
import model.User;

public class UserController {
	
	private Connect db = Connect.getInstance();
	private static UserController uc = null;
	
	private UserController() {}
	
	public static UserController getInstance() {
		if (uc == null) uc = new UserController();
		return uc;
	}
	
	public String validate(String username, String email, String password, String selectedGender) {
		String errorMessage = "";
		if (username.equals("") || email.equals("") || password.equals("") || selectedGender.equals("")) {
			errorMessage = "All fields must be filled";
		} else if (!uniqueUsername(username)) {
			errorMessage = "Username must be unique";
		} else if (username.length() < 5) {
			errorMessage = "Username must be more than 5 characters";
		} else if (!email.endsWith("@gmail.com")) {
			errorMessage = "Email must end with @gmail.com";
		} else if (password.length() < 8) {
			errorMessage = "Password must be more than 8 characters";
		}
		
		return errorMessage;
	}
	
	public boolean uniqueUsername(String username) {
		db.execQuery(String.format("SELECT * FROM user WHERE username = '%s'", username));
		
		try {
			if (db.rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void add(String username, String email, String password, String gender) {
		db.execUpdate(String.format("INSERT INTO user VALUE ('%s', '%s', '%s', '%s', '%s', 'customer')", generateId(), username, email, password, gender));
	}
	
	public User get(String username, String password) {
		db.execQuery(String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'", username, password));
		
		User user = null;
		try {
			while (db.rs.next()) {
				String id = db.rs.getString("id");
				String usernameDb = db.rs.getString("username");
				String email = db.rs.getString("email");
				String passwordDb = db.rs.getString("password");
				String gender = db.rs.getString("gender");
				String role = db.rs.getString("role");
				user = new User(id, usernameDb, email, passwordDb, gender, role);
			}
		} catch (SQLException e) {
			System.out.println("Error while loading user data");
			e.printStackTrace();
		}
		
		return user;
	}
	
	public String generateId() {
		db.execQuery("SELECT id FROM user ORDER BY id DESC LIMIT 1");
		
		Integer lastNumber = 0;
		try { 
			while (db.rs.next()) {
				String lastId = db.rs.getString("id");
				lastNumber = Integer.parseInt(lastId.substring(1));
			}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		
		return String.format("U%03d", lastNumber + 1);
	}

}
