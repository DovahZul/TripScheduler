package application.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RootController implements Initializable{
	@FXML
	private Label l;

	@FXML Menu admMenuItem;


	@FXML
	Label currentDateLabel;
	@FXML MenuButton adminSwitchUserBox;
	@FXML MenuItem logOutItem;
	@FXML MenuItem exitItem;


	private  int currId=2;
	Connection connection;
	private ObservableList<WatchStage> data;
	private Main mainApp;
	private SchedulesController scheduleController;

	private ShipInfoController shipController;
	private UserInfoController userController;
	private Stage primaryStage;

	 /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     * @param mainApp
     */
public void setMainApp(Main main) {
	this.mainApp=main;
}

public void setScheduleController(SchedulesController main) {
	this.scheduleController=main;
}
public void setShipsController(ShipInfoController main) {
	this.shipController=main;
}
public void setuserInfoController(UserInfoController main) {
	this.userController=main;
}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//admMenuItem.setDisable(false);else admMenuItem.setDisable(true);
		currentDateLabel.setText("Сегодня "+Main.dateFormat.format(Main.currentDate.getTime()));
		//adminSwitchUserBox.getItems().addAll(new MenuItem());//MyDbAdapter.ReadAllEmployeeAsFormattedString());
	}

	public void setCurrId(int val){
		this.currId=val;
	}
	public int getCurrId(){
		return this.currId;
	}

	@FXML
	public void handleShipInfo(){
		try {
			mainApp.ShowShipInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	public void handleUserInfo(){
		try {
			mainApp.ShowUserInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSchedules(){
		mainApp.InitSchedules();
	}

	@FXML
	public void handleNewShip(){
		mainApp.InitShipWindow();
	}

	@FXML
	public void handleNewUser(){
		mainApp.InitUserWindow();
	}

	@FXML
	public void handleNewTrip(){
		mainApp.InitTripWindow();
	}
	@FXML
	public void handleClearAllTempData(){
		mainApp.ClearTempData();

	}
	@FXML
	public void handleGeneralInfo(){
		mainApp.InitAppInfo();

	}
	@FXML
	public void handleLogOut(){
		Main.setCurrId(-1);
		try{
	//		mainApp.getPrimaryStage().setWidth(300);
		//	mainApp.getPrimaryStage().setHeight(300);
			mainApp.getPrimaryStage().close();

		    // do what you have to do
		   // stage.close();
			mainApp.initLogin();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

			}
	@FXML
	public void handleQuite(){
		Main.setCurrId(-1);
		System.exit(0);
		try{


		}catch(Exception e){
			System.out.println(e.getMessage());
		}

}
	@FXML
	private void handleManageRootPwd(){
		mainApp.HandleMasterRoot();

	}
	@FXML
	private void handleShowContact(){
		mainApp.showContacts();

	}

}
