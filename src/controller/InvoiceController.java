package controller;

import java.sql.SQLException;
import java.util.Vector;

import connect.Connect;
import model.Invoice;

public class InvoiceController {
	
	private Connect db = Connect.getInstance();
	private static InvoiceController ic = null;
	
	private InvoiceController() {}
	
	public static InvoiceController getInstance() {
		if (ic == null) ic = new InvoiceController();
		return ic;
	}
	
	public String validate(int totalPrice, int payment) {
		String errorMessage = "";
		if (payment < totalPrice) {
			errorMessage = "Your balance is not enough";
		} else if (!String.valueOf(payment).matches("\\d+")) {
			errorMessage = "Balance must be numeric";
		}
		
		return errorMessage;
	}
	
	public Vector<Invoice> get() {
		db.execQuery("SELECT * FROM invoice");
		
		Vector<Invoice> invoices = new Vector<>();
		try {
			while (db.rs.next()) {
				String id = db.rs.getString("id");
				String shoesId = db.rs.getString("shoes_id");
				String userId = db.rs.getString("user_id");
				int price = db.rs.getInt("price");
				String model = db.rs.getString("model");
				String brand = db.rs.getString("brand");
				String color = db.rs.getString("color");
				int quantity = db.rs.getInt("quantity");
				int payment = db.rs.getInt("payment");
				invoices.add(new Invoice(price, quantity, payment, id, shoesId, userId, model, brand, color));
			}
		} catch (SQLException e) {
			System.out.println("Error while loading transaction data");
			e.printStackTrace();
		}
		
		return invoices;
	}
	
	public void add(String shoesId, String userId, String model, String brand, String color, int price, int quantity, 
			int payment) {
		db.execUpdate(String.format("INSERT INTO invoice VALUE ('%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d)", 
				generateId(), shoesId, userId, model, brand, color, price, quantity, payment));
	}
	
	public String generateId() {
		db.execQuery("SELECT id FROM invoice ORDER BY id DESC LIMIT 1");
		
		Integer lastNumber = 0;
		try { 
			while (db.rs.next()) {
				String lastId = db.rs.getString("id");
				lastNumber = Integer.parseInt(lastId.substring(1));
			}
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
		
		return String.format("T%03d", lastNumber + 1);
	}

}
