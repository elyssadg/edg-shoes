package view;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import main.Main;

public abstract class CustomerView extends View {
	
	protected MenuBar navBar;
	protected Menu shopMenu, historyMenu, logoutMenu;
	protected Label shopLbl, historyLbl, logoutLbl;
	
	public CustomerView() {
		initNavigationBar();
		setNavigation();
	}
	
	public void initNavigationBar() {
		navBar = new MenuBar();
		
		shopMenu = new Menu();
		shopLbl = new Label("Shop");
		shopMenu.setGraphic(shopLbl);
		
		historyMenu = new Menu();
		historyLbl = new Label("History");
		historyMenu.setGraphic(historyLbl);
		
		logoutMenu = new Menu();
		logoutLbl = new Label("Logout");
		logoutMenu.setGraphic(logoutLbl);
		
		navBar.getMenus().addAll(shopMenu, historyMenu, logoutMenu);
	}
	
	public void setNavigation() {
		shopLbl.setOnMouseClicked(e -> {
			new ShopView();
		});
		
		historyLbl.setOnMouseClicked(e -> {
			new HistoryView();
		});
		
		logoutLbl.setOnMouseClicked(e -> {
			Main.setUserId("");
			new LoginView();
		});
	}

}
