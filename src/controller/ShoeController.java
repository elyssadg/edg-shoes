package controller;

import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;
import model.Shoes;

public class ShoeController {
	
	private Connect db = Connect.getInstance();
	private static ShoeController sc = null;
	
	private ShoeController() {}
	
	public static ShoeController getInstance() {
		if (sc == null) sc = new ShoeController();
		return sc;
	}
	
	public String validate(String model, String brand, String color, String price) {
		String errorMessage = "";
		if (model.equals("") || brand.equals("") || color.equals("") || price.equals("")) {
			errorMessage = "All fields must be filled";
		} else if (brand.length() < 4 || brand.length() > 20) {
			errorMessage = "Model must be between 5 and 20 characters";
		} else if (!price.matches("\\d+")) {
			errorMessage = "Price must be numeric";
		}
		
		return errorMessage;
	}
	
	public void add(int price, String model, String brand, String color) {
		db.execUpdate(String.format("INSERT INTO shoes VALUE ('%s', '%s', '%s', '%s', %d)", generateId(brand), model, brand, color, price));
	}
	
	public Vector<Shoes> get() {
		db.execQuery("SELECT * FROM shoes");
		
		Vector<Shoes> shoes = new Vector<>();
		try {
			while (db.rs.next()) {
				String id = db.rs.getString("id");
				int price = db.rs.getInt("price");
				String model = db.rs.getString("model");
				String brand = db.rs.getString("brand");
				String color = db.rs.getString("color");
				shoes.add(new Shoes(price, id, model, brand, color));
			}
		} catch (SQLException e) {
			System.out.println("Error while loading shoe data");
			e.printStackTrace();
		}
		
		return shoes;
	}
	
	public void update(String id, int price, String model, String brand, String color) {
		db.execUpdate(String.format("UPDATE shoes "
								  + "SET model = '%s', brand = '%s', color ='%s', price = %d "
								  + "WHERE id = '%s'", model, brand, color, price, id));
	}
	
	public void delete(String id) {
		db.execUpdate(String.format("DELETE FROM shoes WHERE id = '%s'", id));
	}
	
	public String generateId(String brand) {
		db.execQuery("SELECT id FROM shoes WHERE brand = '" + brand + "' ORDER BY id DESC LIMIT 1");
		
		Integer lastNumber = 0;
		try { 
			while (db.rs.next()) {
				String lastId = db.rs.getString("id");
				lastNumber = Integer.parseInt(lastId.substring(1));
			}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		char firstChar = brand.toUpperCase().charAt(0);
		return String.format("%c%03d", firstChar, lastNumber + 1);
	}
	
}
