package view;

import java.util.Vector;

import controller.InvoiceController;
import controller.ShoeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Main;
import model.Shoes;

public class ShopView extends CustomerView {
	
	private ShoeController sc = ShoeController.getInstance();
	private InvoiceController ic = InvoiceController.getInstance();
	
	private Scene scene;
	
	private ScrollPane scrollContainer;
	private BorderPane borderContainer;
	private GridPane formContainer;
	private VBox shopContainer;
	
	private Label title, modelLbl, brandLbl, colorLbl, priceLbl, quantityLbl, paymentLbl, totalLbl, totalValue;
	private TextField brandTf, colorTf, priceTf, paymentTf;
	private Spinner<Integer> quantitySpinner;
	private ComboBox<String> modelBox;
	private Button buyBtn;
	
	private TableView<Shoes> shoesTable;

	public ShopView() {
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
		formContainer = new GridPane();
		shopContainer = new VBox();
		
		scene = new Scene(scrollContainer, 1000, 600);
		
		title = new Label("Welcome to EDG Shoes");
		modelLbl = new Label("Model");
		brandLbl = new Label("Brand");
		colorLbl = new Label("Color");
		priceLbl = new Label("Price");
		quantityLbl = new Label("Quantity");
		totalLbl = new Label("Total Price");
		paymentLbl = new Label("Input Balance");
		totalValue = new Label("0");
		
		brandTf = new TextField();
		brandTf.setDisable(true);
		colorTf = new TextField();
		colorTf.setDisable(true);
		priceTf = new TextField();
		priceTf.setDisable(true);
		paymentTf = new TextField();
		paymentTf.setPromptText("Only consist of numbers");
		
		modelBox = new ComboBox<>();
		modelBox.getItems().add("Sneakers");
		modelBox.getItems().add("Heels");
		modelBox.getItems().add("Flat Shoes");
		modelBox.getItems().add("Sandals");
		modelBox.getItems().add("Boots");
		modelBox.setDisable(true);
		
		quantitySpinner = new Spinner<>(1, 100, 1);
		
		buyBtn = new Button("Buy");
		
		shoesTable = new TableView<>();
		setShoesTableColumns();
		refreshShoesTable();
	}

	@Override
	public void setPosition() {
		scrollContainer.setContent(borderContainer);
		scrollContainer.setFitToWidth(true);
		
		borderContainer.setTop(navBar);
		borderContainer.setBottom(shopContainer);
		
		shopContainer.getChildren().addAll(title, shoesTable, formContainer);
		
		formContainer.add(modelLbl, 0, 0);
		formContainer.add(modelBox, 1, 0);
		formContainer.add(brandLbl, 0, 1);
		formContainer.add(brandTf, 1, 1);
		formContainer.add(colorLbl, 0, 2);
		formContainer.add(colorTf, 1, 2);
		formContainer.add(priceLbl, 0, 3);
		formContainer.add(priceTf, 1, 3);
		formContainer.add(quantityLbl, 0, 4);
		formContainer.add(quantitySpinner, 1, 4);
		formContainer.add(totalLbl, 0, 5);
		formContainer.add(totalValue, 1, 5);
		formContainer.add(paymentLbl, 0, 6);
		formContainer.add(paymentTf, 1, 6);
		formContainer.add(buyBtn, 0, 7, 2, 1);
	}

	@Override
	public void setStyle() {
		shoesTable.setMaxWidth(900);
		shoesTable.setMaxHeight(250);
		
		shopContainer.setAlignment(Pos.CENTER);
		shopContainer.setPadding(new Insets(50));
		shopContainer.setSpacing(20);
		
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setVgap(10);
		formContainer.setHgap(20);
		GridPane.setHalignment(buyBtn, HPos.CENTER);
		
		buyBtn.setPrefWidth(280);
		
		setTitleFont(title);
		setBoldFont(modelLbl);
		setBoldFont(brandLbl);
		setBoldFont(colorLbl);
		setBoldFont(paymentLbl);
		setBoldFont(priceLbl);
		setBoldFont(quantityLbl);
		setBoldFont(totalLbl);
	}

	@Override
	public void setEventHandler() {
		shoesTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Shoes selectedRow = shoesTable.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    modelBox.getSelectionModel().select(selectedRow.getModel());
                    brandTf.setText(selectedRow.getBrand());
                    colorTf.setText(selectedRow.getColor());
                    priceTf.setText(String.valueOf(selectedRow.getPrice()));
                    totalValue.setText(String.valueOf(selectedRow.getPrice() * quantitySpinner.getValue()));
                }
            }
        });
		
		quantitySpinner.setOnMouseClicked(e -> {
			Shoes selectedRow = shoesTable.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                totalValue.setText(String.valueOf(selectedRow.getPrice() * quantitySpinner.getValue()));
            }
		});
		
		buyBtn.setOnAction(e -> {
			Shoes selectedRow = shoesTable.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
            	String userId = Main.getUserId();
                String shoesId = selectedRow.getId();
                String model = selectedRow.getModel();
                String brand = selectedRow.getBrand();
                String color = selectedRow.getColor();
                int price = selectedRow.getPrice();
                int quantity = quantitySpinner.getValue();
                int payment = Integer.parseInt(paymentTf.getText());
                
                String errorMessage = ic.validate(price * quantity, payment);
                if (errorMessage.equals("")) {
                	ic.add(shoesId, userId, model, brand, color, price, quantity, payment);
                	resetComponent();
                } else {
                	showErrorAlert(errorMessage);
                }
            }
		});
	}
	
	public void setShoesTableColumns() {
		TableColumn<Shoes, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setPrefWidth(900 / 5);
		
		TableColumn<Shoes, String> modelColumn = new TableColumn<>("Model");
		modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
		modelColumn.setPrefWidth(900 / 5);
		
		TableColumn<Shoes, String> brandColumn = new TableColumn<>("Brand");
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
		brandColumn.setPrefWidth(900 / 5);
		
		TableColumn<Shoes, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
		colorColumn.setPrefWidth(900 / 5);
		
		TableColumn<Shoes, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceColumn.setPrefWidth(900 / 5);
		
		shoesTable.getColumns().add(idColumn);
		shoesTable.getColumns().add(modelColumn);
		shoesTable.getColumns().add(brandColumn);
		shoesTable.getColumns().add(colorColumn);
		shoesTable.getColumns().add(priceColumn);
	}
	
	public void refreshShoesTable() {
		Vector<Shoes> shoes = sc.get();
		ObservableList<Shoes> shoesObs = FXCollections.observableArrayList(shoes);
		shoesTable.setItems(shoesObs);
	}
	
	public void resetComponent() {
		modelBox.getSelectionModel().clearSelection();
		brandTf.clear();
		colorTf.clear();
		priceTf.clear();
		quantitySpinner.getValueFactory().setValue(1);
		totalValue.setText("0");
		paymentTf.clear();
	}

}
