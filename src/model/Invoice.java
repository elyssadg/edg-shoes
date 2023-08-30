package model;

public class Invoice {
	
	private int price, quantity, payment;
	private String id, shoesId, userId, model, brand, color;
	
	public Invoice(int price, int quantity, int payment, String id, String shoesId, String userId, String model, String brand,
			String color) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.payment = payment;
		this.id = id;
		this.shoesId = shoesId;
		this.userId = userId;
		this.model = model;
		this.brand = brand;
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShoesId() {
		return shoesId;
	}

	public void setShoesId(String shoesId) {
		this.shoesId = shoesId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
