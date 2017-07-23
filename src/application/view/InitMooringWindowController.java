package application.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InitMooringWindowController  implements Initializable{

	Connection connection;
	private Main mainApp;
	private SchedulesController parentController;
	private Stage primaryStage;

	@FXML
	private TextField portName;

	@FXML
	private TextField startTime;
	@FXML
	private TextField endTime;

	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;

	@FXML
	private ComboBox<String> responsibleEmployee;
	@FXML
	private ComboBox<String> statusBox;

	@FXML
	private Button closeBtn;
	@FXML
	private Button addBtn;

	ObservableList<String> responsibleComboString;
	ObservableList<String> statusComboString;



	public void setMainApp(SchedulesController main) {
		this.parentController=main;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		responsibleComboString = FXCollections.observableArrayList();
		statusComboString = FXCollections.observableArrayList();
		Calendar now = Calendar.getInstance();
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		FillStatusBox();
		FillResponsiblePersonBox();
	}

	private void FillResponsiblePersonBox() {

		responsibleComboString.addAll(MyDbAdapter.getFomattedEmployeeStringsByShipID(Main.getCurrShipId()));
//		System.out.println(MyDbAdapter.getFomattedEmployeeStringsByTripID(13));
		responsibleEmployee.setItems(responsibleComboString);
		for(String s:responsibleComboString){
			System.out.println("qwe:"+s);
			System.out.println("qwe:");
		}
	}

	private void FillStatusBox() {
		statusComboString.addAll(MyDbAdapter.ReadAllMooringStatuses());
		statusBox.setItems(statusComboString);

	}

	@FXML
	private void handleAdd(){

		boolean correctinput=true;
		String errorptomp="";
		String str="";
		Pattern r;
		Matcher m;



		if(statusBox.getValue()==null)
		{correctinput=false; errorptomp="Укажите статус швартовки";}
		if(responsibleEmployee.getValue()==null)
		{correctinput=false; errorptomp="Укажите ответственного";}



		  str ="^^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$";
		  r = Pattern.compile(str);
		  m = r.matcher(endTime.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное время конца рейса";}

		  str ="^^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$";
		  r = Pattern.compile(str);
		  m = r.matcher(startTime.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное время начала рейса";}

		  if(Integer.parseInt(startTime.getText().substring(0, startTime.getText().indexOf(':')))
				  >Integer.parseInt(endTime.getText().substring(0, endTime.getText().indexOf(':'))))
		  {correctinput=false; errorptomp="дата конца позже даты начала";}


		  if(Integer.parseInt(startTime.getText().substring(0, startTime.getText().indexOf(':')))
				  ==Integer.parseInt(endTime.getText().substring(0, endTime.getText().indexOf(':')))){

			  if(Integer.parseInt(startTime.getText().substring(startTime.getText().indexOf(':')+1,startTime.getText().length() ))
					  >=Integer.parseInt(startTime.getText().substring(endTime.getText().indexOf(':')+1,endTime.getText().length())))
			  {correctinput=false; errorptomp="некорректное время стоянки";}



		  }


		  str ="^[а-яА-Яё Ёa-zA-Z]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(portName.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное название порта";}

		  if(startDate.getValue().isAfter(endDate.getValue()))
		  { correctinput=false; errorptomp="Некорректные даты стоянки";}

		if(correctinput){

	int temp=MyDbAdapter.getIdByFormattedFIOString(responsibleEmployee.getValue());
	int temp2=MyDbAdapter.getTypeIdByAbsName(statusBox.getValue());
	int temp3=MyDbAdapter.getTripIDByShipID(Main.getCurrShipId());
		Exception bool=MyDbAdapter.WriteMooring(portName.getText(),temp3,startDate.getValue(),startTime.getText(),
				endDate.getValue(),endTime.getText(),temp,temp2);
		if(bool==null){

			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("Запись добавлена");
			a.showAndWait();
			parentController.FillMooringSchedule(Main.getCurrShipId());
			handleClose();

		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Запись не доблена");
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




}