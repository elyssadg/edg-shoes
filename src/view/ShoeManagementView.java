package view;

import java.util.Vector;

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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Main;
import model.Shoes;

public class ShoeManagementView extends View {
	
	private ShoeController sc = ShoeController.getInstance();
	
	private Scene scene;
	
	private ScrollPane scrollContainer;
	private BorderPane borderContainer;
	private VBox innerContainer;
	private GridPane formContainer;
	
	protected MenuBar navBar;
	protected Menu manageShoesMenu, logoutMenu;
	protected MenuItem insertMenu, updateMenu, deleteMenu;
	protected Label logoutLbl;
	
	private Label title, idLbl, modelLbl, brandLbl, colorLbl, priceLbl;
	private TextField idTf, brandTf, colorTf, priceTf;
	private ComboBox<String> modelBox;
	private Button submitBtn;
	
	private TableView<Shoes> table;
	
	private String action;
	
	public ShoeManagementView() {
		this.action = "insert";
		initialize();
		setPosition();
		setStyle();
		setEventHandler();
		setForm();
		resetComponent();
		changeScene(scene);
	}

	@Override
	public void initialize() {
		scrollContainer = new ScrollPane();
		borderContainer = new BorderPane();
		innerContainer = new VBox();
		formContainer = new GridPane();
		
		scene = new Scene(scrollContainer, 1000, 600);
		
		navBar = new MenuBar();
		
		manageShoesMenu = new Menu("Manage Shoes");
		insertMenu = new MenuItem("Insert");
		updateMenu = new MenuItem("Update");
		deleteMenu = new MenuItem("Delete");
		manageShoesMenu.getItems().addAll(insertMenu, updateMenu, deleteMenu);
		
		logoutMenu = new Menu();
		logoutLbl = new Label("Logout");
		logoutMenu.setGraphic(logoutLbl);
		
		navBar.getMenus().addAll(manageShoesMenu, logoutMenu);
		
		title = new Label("Insert New Pair of Shoes");
		idLbl = new Label("ID");
		modelLbl = new Label("Model");
		brandLbl = new Label("Brand");
		colorLbl = new Label("Color");
		priceLbl = new Label("Price");
		
		idTf = new TextField("Auto-Generated");
		brandTf = new TextField();
		brandTf.setPromptText("Between 4 and 20 characters");
		colorTf = new TextField();
		priceTf = new TextField();
		priceTf.setPromptText("Only consist of numbers");
		
		modelBox = new ComboBox<>();
		modelBox.getItems().add("Sneakers");
		modelBox.getItems().add("Heels");
		modelBox.getItems().add("Flat Shoes");
		modelBox.getItems().add("Sandals");
		modelBox.getItems().add("Boots");
		modelBox.getSelectionModel().selectFirst();
		
		submitBtn = new Button("Insert");
		
		table = new TableView<>();
		setTableColumns();
		refreshTable();
	}

	@Override
	public void setPosition() {
		scrollContainer.setContent(borderContainer);
		scrollContainer.setFitToWidth(true);
		
		borderContainer.setTop(navBar);
		borderContainer.setBottom(innerContainer);
		
		innerContainer.getChildren().addAll(title, table, formContainer);
		
		formContainer.add(idLbl, 0, 0);
		formContainer.add(idTf, 1, 0);
		idTf.setDisable(true);
		
		formContainer.add(modelLbl, 0, 1);
		formContainer.add(modelBox, 1, 1);
		
		formContainer.add(brandLbl, 0, 2);
		formContainer.add(brandTf, 1, 2);
		
		formContainer.add(colorLbl, 0, 3);
		formContainer.add(colorTf, 1, 3);
		
		formContainer.add(priceLbl, 0, 4);
		formContainer.add(priceTf, 1, 4);
		
		formContainer.add(submitBtn, 0, 5, 2, 1);
	}

	@Override
	public void setStyle() {
		BorderPane.setAlignment(innerContainer, Pos.CENTER);
		
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setVgap(10);
		formContainer.setHgap(20);
		GridPane.setHalignment(submitBtn, HPos.CENTER);
		
		innerContainer.setPadding(new Insets(50));
		innerContainer.setSpacing(20);
		innerContainer.setAlignment(Pos.CENTER);
		
		table.setMaxWidth(900);
		table.setMaxHeight(250);
		
		setTitleFont(title);
		setBoldFont(idLbl);
		setBoldFont(modelLbl);
		setBoldFont(brandLbl);
		setBoldFont(colorLbl);
		setBoldFont(priceLbl);
	
		submitBtn.setPrefWidth(250);
	}

	@Override
	public void setEventHandler() {
		submitBtn.setOnAction(e -> {
			if (action.equals("insert")) {
				String model = modelBox.getSelectionModel().getSelectedItem();
				String brand = brandTf.getText();
				String color = colorTf.getText();
				String price = priceTf.getText();
				
				String errorMessage = sc.validate(model, brand, color, price);
				
				if (!errorMessage.equals("")) {
					showErrorAlert(errorMessage);
				} else {
					sc.add(Integer.parseInt(price), model, brand, color);
					refreshTable();
					resetComponent();
				}
			} else if (action.equals("update")) {
				String model = modelBox.getSelectionModel().getSelectedItem();
				String brand = brandTf.getText();
				String color = colorTf.getText();
				String price = priceTf.getText();
				
				String errorMessage = sc.validate(model, brand, color, price);
				
				if (!errorMessage.equals("")) {
					showErrorAlert(errorMessage);
				} else {
					sc.update(idTf.getText(), Integer.parseInt(price), model, brand, color);
					refreshTable();
					resetComponent();
				}
			} else if (action == "delete") {
				boolean confirm = showConfirmationAlert("Are you sure you want to delete the selected shoes?", e);
				if (confirm) {
					sc.delete(idTf.getText());
					refreshTable();
					resetComponent();
				}
			}
		});
		
		table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1 && (action.equals("update") || action.equals("delete"))) {
                Shoes selectedRow = table.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                	idTf.setText(selectedRow.getId());
                    modelBox.getSelectionModel().select(selectedRow.getModel());
                    brandTf.setText(selectedRow.getBrand());
                    colorTf.setText(selectedRow.getColor());
                    priceTf.setText(String.valueOf(selectedRow.getPrice()));
                }
            }
        });
		
		insertMenu.setOnAction(e -> {
			action = "insert";
			setForm();
		});
		
		updateMenu.setOnAction(e -> {
			action = "update";
			setForm();
		});
		
		deleteMenu.setOnAction(e -> {
			action = "delete";
			setForm();
		});
		
		logoutLbl.setOnMouseClicked(e -> {
			Main.setUserId("");
			new RegisterView();
		});
	}
	
	public void setTableColumns() {
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
		
		table.getColumns().add(idColumn);
		table.getColumns().add(modelColumn);
		table.getColumns().add(brandColumn);
		table.getColumns().add(colorColumn);
		table.getColumns().add(priceColumn);
	}
	
	public void refreshTable() {
		Vector<Shoes> shoes = sc.get();
		ObservableList<Shoes> shoesObs = FXCollections.observableArrayList(shoes);
		table.setItems(shoesObs);
	}
	
	public void resetComponent() {
		idTf.setText("Auto-Generated");
		modelBox.getSelectionModel().selectFirst();
		brandTf.clear();
		colorTf.clear();
		priceTf.clear();
	}
	
	public void setForm() {
		if (action.equals("insert")) {
			title.setText("Insert New Pair of Shoes");
			submitBtn.setText("Insert");
		} else if (action.equals("update")) {
			title.setText("Update Shoes");
			submitBtn.setText("Update");
		} else if (action.equals("delete")) {
			title.setText("Delete Shoes");
			submitBtn.setText("Delete");
		}
	}

}
