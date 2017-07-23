package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MoneyWindowController implements Initializable{


	 @FXML Label myShipLabel;
	 @FXML Label myNameLabel;
	 @FXML Label myProfLabel;
	 @FXML Label myStavkaLabel;
	 @FXML Label myTripLong;
	 @FXML Label myMaxSalary;
	 @FXML Label myRealSalary;

	 @FXML Button closeBtn;


	Connection connection;
	private Stage primaryStage;
	private Main mainApp;

	public void setMainApp(Main mainApp) {
		// TODO Auto-generated method stub
		this.mainApp=mainApp;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int cid;
		if(Main.getCurrId()==0)cid=Main.getCurrFakedId();else
			cid=Main.getCurrId();


		float r=Integer.parseInt(MyDbAdapter.getTotalCountTripDaysByEmployeeID(cid)) *
				(MyDbAdapter.getStavkaByEmployeeId(cid)/30);

		float r2=Integer.parseInt(MyDbAdapter.getCheckedCountTripDaysByEmployeeID(cid)) *
				(MyDbAdapter.getStavkaByEmployeeId(cid)/30   );
		myNameLabel.setText(" ФИО Работника: "+MyDbAdapter.getFullNameByEmployeeId(cid));
		myShipLabel.setText(" Должность: "+MyDbAdapter.getPostNameByEmployeeID(cid)   );
		myProfLabel.setText(" Текущее судно: "+MyDbAdapter.getShipNameById(MyDbAdapter.getRelatedShipIdByEmployeeId(cid)));
		myStavkaLabel.setText(" Оклад($/мес): "+MyDbAdapter.getStavkaByEmployeeId(cid)+"$");
		myTripLong.setText(" Продолжительность рейса(суток): "+MyDbAdapter.getTotalCountTripDaysByEmployeeID(cid));
		myMaxSalary.setText(" Полная сумма: "+String.valueOf(r)+"$");
		myRealSalary.setText(" Начислено: "+String.valueOf(r2)+"$");
	}
	@FXML
	private void handleClose(){
	//this.close();
		Stage stage = (Stage) closeBtn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
}
