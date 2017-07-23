package application.view;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.util.MyDbAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditShipWindowController  implements Initializable{

	Connection connection;
	private Main mainApp;
	private Stage primaryStage;
	private ShipInfoController parentController;
	private ShipModel node;

	@FXML
	private TextField shipFullName;

	@FXML
	private ComboBox<String> shipType;

//	@FXML
	//private ComboBox<String> shipStatus;

	@FXML
	private Button closeBtn;

	@FXML
	private Button addBtn;




	public void setMainApp(ShipInfoController main) {
		// TODO Auto-generated method stub
		this.parentController=main;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		shipType.setItems(MyDbAdapter.ReadAllShipTypes());
		//shipStatus.setItems(MyDbAdapter.ReadAllShipStatuses());

	}

	void FillNode(ShipModel sh){
		//node=sh;
		shipFullName.setText(sh.shipName.get());
		shipType.setValue(sh.shipType.get());
		//shipStatus.setValue(sh.shipStatus.get());

	}


	@FXML
	private void handleApply(){

		boolean correctinput=true;
		String errorptomp="";
		String str="";
		Pattern r;
		Matcher m;

		//if(shipStatus.getValue()==null)
		//{correctinput=false; errorptomp="Укажите состояние судна";}

		if(shipType.getValue()==null)
		{correctinput=false; errorptomp="Укажите тип судна";}

		 str ="^[a-zА-ЯёЁa-zA-Z][а-яА-Яё Ёa-zA-Z0-9-_]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(shipFullName.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное название судна";}

		if(correctinput){
			int typeid=MyDbAdapter.getShipTypeIdByAbsName(shipType.getValue());
		//	Alert aa=new Alert(AlertType.INFORMATION);
		//	aa.setTitle(typeid+"");

		//	aa.showAndWait();
		//	int statusid=MyDbAdapter.getStatusIdByAbsName(shipStatus.getValue());
		Exception bool=MyDbAdapter.EditShip(node.shipId.get(),shipFullName.getText(),typeid);
		if(bool==null){

			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("Запись изменена");
			a.showAndWait();
			parentController.Fill();
			handleClose();


		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Редактирование не удалось");
			a.setContentText("код:"+bool.getLocalizedMessage());
			a.showAndWait();
		}
		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Некорректные данные");
			a.setContentText(errorptomp);
			a.showAndWait();
		}


	}
	@FXML
	private void handleClose(){
	//this.close();
		Stage stage = (Stage) closeBtn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}

	public void setConn(Connection connection2) {
		// TODO Auto-generated method stub
		//this.connection=connection2;
	}

	public void setElement(ShipModel shipModel) {
		node=shipModel;

	}


}
