package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import controller.InvoiceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Invoice;

public class HistoryView extends CustomerView {
	
	private InvoiceController ic = InvoiceController.getInstance();
	
	private Scene scene;
	
	private ScrollPane scrollContainer;
	private BorderPane borderContainer;
	private GridPane detailContainer;
	private VBox historyContainer;
	
	private Label title, idLbl, modelLbl, brandLbl, colorLbl, priceLbl, quantityLbl, paymentLbl, 
			totalLbl, changeLbl, idValue, modelValue, brandValue, colorValue, priceValue, quantityValue, 
			totalValue, paymentValue, changeValue;
	private Button printBtn;
	
	private TableView<Invoice> invoiceTable;
	
	public HistoryView() {
		initialize();
		setPosition();
		setStyle();
		setEventHandler();
		changeScene(scene);
	}
	
	@Override
	public void initialize() {
		scrollContainer = new ScrollPane();
		borderContainer = new BorderPane();
		detailContainer = new GridPane();
		historyContainer = new VBox();
		
		scene = new Scene(scrollContainer, 1000, 600);
		
		title = new Label("Transaction History");
		idLbl = new Label("");
		modelLbl = new Label("");
		brandLbl = new Label("");
		colorLbl = new Label("");
		priceLbl = new Label("");
		quantityLbl = new Label("");
		paymentLbl = new Label("");
		totalLbl = new Label("");
		changeLbl = new Label("");
		
		idValue = new Label(""); 
		modelValue = new Label("");
		brandValue = new Label("");
		colorValue = new Label("");
		priceValue = new Label(""); 
		quantityValue = new Label("");
		totalValue = new Label("");
		paymentValue = new Label("");
		changeValue = new Label("");
		
		printBtn = new Button("Print Invoice");
		
		invoiceTable = new TableView<>();
		setInvoiceTableColumns();
		refreshInvoiceTable();
	}

	@Override
	public void setPosition() {
		scrollContainer.setContent(borderContainer);
		scrollContainer.setFitToWidth(true);
		
		borderContainer.setTop(navBar);
		borderContainer.setBottom(historyContainer);
		
		historyContainer.getChildren().addAll(title, invoiceTable, detailContainer);
		
		detailContainer.getChildren().clear();
		detailContainer.add(idLbl, 0, 0);
		detailContainer.add(idValue, 1, 0);
		detailContainer.add(modelLbl, 0, 1);
		detailContainer.add(modelValue, 1, 1);
		detailContainer.add(brandLbl, 0, 2);
		detailContainer.add(brandValue, 1, 2);
		detailContainer.add(colorLbl, 0, 3);
		detailContainer.add(colorValue, 1, 3);
		detailContainer.add(priceLbl, 0, 4);
		detailContainer.add(priceValue, 1, 4);
		detailContainer.add(quantityLbl, 0, 5);
		detailContainer.add(quantityValue, 1, 5);
		detailContainer.add(totalLbl, 0, 6);
		detailContainer.add(totalValue, 1, 6);
		detailContainer.add(paymentLbl, 0, 7);
		detailContainer.add(paymentValue, 1, 7);
		detailContainer.add(changeLbl, 0, 8);
		detailContainer.add(changeValue, 1, 8);
		detailContainer.add(printBtn, 0, 9, 2, 1);
	}

	@Override
	public void setStyle() {
		invoiceTable.setMaxWidth(900);
		invoiceTable.setMaxHeight(250);
		
		historyContainer.setAlignment(Pos.CENTER);
		historyContainer.setPadding(new Insets(50));
		historyContainer.setSpacing(20);
		
		detailContainer.setAlignment(Pos.CENTER);
		detailContainer.setVgap(10);
		detailContainer.setHgap(20);
		
		printBtn.setPrefWidth(260);
		
		setTitleFont(title);
		setBoldFont(idLbl);
		setBoldFont(modelLbl);
		setBoldFont(brandLbl);
		setBoldFont(colorLbl);
		setBoldFont(paymentLbl);
		setBoldFont(priceLbl);
		setBoldFont(quantityLbl);
		setBoldFont(totalLbl);
		setBoldFont(changeLbl);
	}

	@Override
	public void setEventHandler() {
		invoiceTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Invoice selectedRow = invoiceTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                	String id = selectedRow.getId();
                    String model = selectedRow.getModel();
                    String brand = selectedRow.getBrand();
                    String color = selectedRow.getColor();
                    int price = selectedRow.getPrice();
                    int quantity = selectedRow.getQuantity();
                    int payment = selectedRow.getPayment();
                    
                    if (idLbl.getText().equals("")) {
                    	idLbl.setText("Invoice ID");
                    	modelLbl.setText("Model");
                    	brandLbl.setText("Brand");
                    	colorLbl.setText("Color");
                    	priceLbl.setText("Price");
                    	quantityLbl.setText("Quantity");
                    	paymentLbl.setText("Total Payment");
                    	totalLbl.setText("Total");
                    	changeLbl.setText("Change");
                    }
                    
                    idValue.setText(id); 
            		modelValue.setText(model); 
            		brandValue.setText(brand); 
            		colorValue.setText(color); 
            		priceValue.setText(String.valueOf(price)); 
            		quantityValue.setText(String.valueOf(quantity)); 
            		totalValue.setText(String.valueOf(quantity * price));
            		paymentValue.setText(String.valueOf(payment));
            		changeValue.setText(String.valueOf(payment - price * quantity));
                }
            }
        });
		
		printBtn.setOnAction(e -> {
			Invoice selectedRow = invoiceTable.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
            	String id = selectedRow.getId();
            	String shoesId = selectedRow.getShoesId();
            	String userId = selectedRow.getUserId();
                String model = selectedRow.getModel();
                String brand = selectedRow.getBrand();
                String color = selectedRow.getColor();
                int price = selectedRow.getPrice();
                int quantity = selectedRow.getQuantity();
                int payment = selectedRow.getPayment();
                
        		String invoice = "src/invoice/" + id + ".txt";
        		try (FileWriter fileWriter = new FileWriter(invoice);
        			PrintWriter printWriter = new PrintWriter(fileWriter)) {
        			printWriter.println("Invoice ID       : " + id);
        			printWriter.println("===================================");
        			printWriter.println("Shoes ID         : " + shoesId);
        			printWriter.println("User ID          : " + userId);
        			printWriter.println("Shoes Model      : " + model);
        			printWriter.println("Shoes Brand      : " + brand);
        			printWriter.println("Shoes Color      : " + color);
        			printWriter.println("Shoes Price      : " + price);
        			printWriter.println("Shoes Quantity   : " + quantity);
        			
        			printWriter.println("\n\nTotal Price      : " + (price * quantity));
        			printWriter.println("Payment          : " + payment);
        			printWriter.println("Change           : " + (payment - price * quantity));
        			
        			fileWriter.close();
        			
        			Alert alert = new Alert(AlertType.INFORMATION, "Successfully print invoice " + id);
        			alert.show();
        		} catch (IOException e1) {
        			System.out.println("An error occured while printing the invoice");
        			e1.printStackTrace();
        		}
            }
		});
	}
	
	public void setInvoiceTableColumns() {
		TableColumn<Invoice, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setPrefWidth(900 / 4);
		
		TableColumn<Invoice, String> shoesIdColumn = new TableColumn<>("Shoes ID");
		shoesIdColumn.setCellValueFactory(new PropertyValueFactory<>("shoesId"));
		shoesIdColumn.setPrefWidth(900 / 4);
		
		TableColumn<Invoice, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityColumn.setPrefWidth(900 / 4);
		
		TableColumn<Invoice, Integer> paymentColumn = new TableColumn<>("Payment");
		paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
		paymentColumn.setPrefWidth(900 / 4);
		
		invoiceTable.getColumns().add(idColumn);
		invoiceTable.getColumns().add(shoesIdColumn);
		invoiceTable.getColumns().add(quantityColumn);
		invoiceTable.getColumns().add(paymentColumn);
	}
	
	public void refreshInvoiceTable() {
		Vector<Invoice> invoices = ic.get();
		ObservableList<Invoice> invoiceObs = FXCollections.observableArrayList(invoices);
		invoiceTable.setItems(invoiceObs);
	}

}
