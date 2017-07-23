package application.view;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.util.MyDbAdapter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitShipWindowController  implements Initializable{

	Connection connection;
	private Main mainApp;
	private Stage primaryStage;
	private ShipInfoController parentController;

	@FXML
	private TextField shipFullName;

	@FXML
	private ComboBox<String> shipType;

	@FXML
	private Button closeBtn;

	@FXML
	private Button addBtn;




	public void setMainApp(ShipInfoController shipInfoController) {
		// TODO Auto-generated method stub
		this.parentController=shipInfoController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		shipType.setItems(MyDbAdapter.ReadAllShipTypes());

	}

	@FXML
	private void handleAdd(){

		boolean correctinput=true;
		String errorptomp="";
		String str="";
		Pattern r;
		Matcher m;



		if(shipType.getValue()==null)
		{correctinput=false; errorptomp="Укажите тип судна";}

		 str ="^[a-zА-ЯёЁa-zA-Z][а-яА-Яё Ёa-zA-Z0-9-_]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(shipFullName.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное название судна";}

		if(correctinput){

			int typeid=MyDbAdapter.getTypeIdByAbsName(shipType.getValue());
		boolean bool=MyDbAdapter.WriteShip(shipFullName.getText(),typeid);
		if(bool){

			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Судно зарегистрировано");
			a.setHeaderText("Выполнено");
			a.setContentText("Запись добавлена.");
			a.showAndWait();
			parentController.Fill();
			handleClose();


		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("не вышло");
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


}
