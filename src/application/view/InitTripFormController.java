package application.view;

import java.net.URL;
import java.security.Provider.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.time.temporal.ChronoUnit;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InitTripFormController implements Initializable{



	private Main mainApp;
	private Stage primaryStage;
	private int currShipId=-1;
	private int currshipType=-1;

	@FXML
	private ProgressIndicator prog;

	 /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     * @param mainApp
     */

	@FXML
	private Label shipTypeLabel;

	@FXML
	private ComboBox<String> shipName;
	private int shipID=-1;

	@FXML
	private ComboBox<String> master;
	private int masterID=-1;

	@FXML
	private ComboBox<String> firstOfficer;
	private int firstOfficerID=-1;

	@FXML
	private ComboBox<String> secondOfficer;
	private int secondOfficerID=-1;

	@FXML
	private ComboBox<String> thirdOfficer;
	private int thirdOfficerID=-1;

	@FXML
	private ComboBox<String> ChiefEng;
	private int ChiefEngID=-1;


	@FXML
	public ComboBox<String> firstEng;
	private int firstEngID=-1;

	@FXML
	public ComboBox<String> secondEng;
	private int secondEngID=-1;

	@FXML
	public ComboBox<String> thirdEng;
	private int thirdEngID=-1;

	@FXML
	public ComboBox<String> fourthEng;
	private int fourthEngID=-1;

	@FXML
	public ComboBox<String> Boatswain;
	private int BoatswainID=-1;

	@FXML
	public ComboBox<String> Donkerman;
	private int DonkermanID=-1;

	@FXML
	public ComboBox<String> electroEngineer;
	private int electroEngineerID=-1;

	@FXML
	public ComboBox<String> motorMan;
	private int motorManID=-1;

	@FXML
	public ComboBox<String> radioOfficer;
	private int radioOfficerID=-1;

	@FXML
	public ComboBox<String> cook;
	private int cookID=-1;

	@FXML
	public ComboBox<String> medicalOfficer;
	private int medicalOfficerID=-1;

	@FXML
	public ComboBox<String> chiefSeamen;
	private int chiefSeamenID=-1;

	@FXML
	public ComboBox<String> secondSeamen;
	private int secondSeamenID=-1;

	@FXML
	public ComboBox<String> thirdSeamen;
	private int thirdSeamenID=-1;

	@FXML
	public ComboBox<String> fourthSeamen;
	private int fourthSeamenID=-1;

	@FXML
	public ComboBox<String> motormanWelder;
	private int motormanWelderID=-1;



	@FXML
	public Pane additionalPersonal;

	@FXML
	public Pane additionalPersonalLabel;


	///GAS TANKER PERSONAL
	@FXML
	public Label gasEngineerLabel;

	@FXML
	public ComboBox<String> gasEngineer;
	private int gasEngineerID=-1;

	@FXML
	public Label refrigeratorEngineerLabel;
	@FXML
	public ComboBox<String> refrigeratorEngineer;
	private int refrigeratorEngineerID=-1;

	///////////////
	///OIL TANKER PERSONAL
	@FXML
	public Label pumpmanLabel;

	@FXML
	public ComboBox<String> pumpman;
	private int PumpmanID=-1;


////////////////////////////////


	@FXML
	public DatePicker startDate;


	@FXML
	public DatePicker endDate;

	@FXML
	public Button closeBtn;






	Connection connection;
	HashMap<String,Integer> h=new HashMap<String, Integer>();


public void setMainApp(Main main) {
	// TODO Auto-generated method stub
	this.mainApp=main;

}



 ObservableList<String> comboString;
@Override
public void initialize(URL location, ResourceBundle resources) {


	additionalPersonal.setVisible(false);
	additionalPersonalLabel.setVisible(false);
	prog.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
	prog.setVisible(false);



	shipName.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
          //System.out.println(ov);
          //  System.out.println(t);
            System.out.println(t1);
            currShipId=MyDbAdapter.getShipIdFromFormattedName(t1);
            currshipType=MyDbAdapter.getShipTypeById(currShipId);
            System.out.println("Curr Ship ID:"+currShipId);

            System.out.println("Ship Type:"+currshipType);
            shipTypeLabel.setText("Тип судна: "+MyDbAdapter.getShipTypeNameById(currShipId));
          //  InitCrewPersonalForShipType(currshipType);
           // if(currshipType<1){additionalPersonal.setVisible(false);additionalPersonalLabel.setVisible(false);}else
         //   {additionalPersonal.setVisible(true);additionalPersonalLabel.setVisible(true);}
            switch(currshipType){
            case(0):
            {additionalPersonal.setVisible(false);additionalPersonalLabel.setVisible(false);break;}

            case(1):
            {additionalPersonal.setVisible(true);
            additionalPersonalLabel.setVisible(true);
            refrigeratorEngineer.setVisible(true);
        	refrigeratorEngineerLabel.setVisible(true);
        	pumpmanLabel.setVisible(false);
        	pumpman.setVisible(false);
        	gasEngineerLabel.setVisible(false);
        	gasEngineer.setVisible(false);
        	break;}

            case(2):{additionalPersonal.setVisible(false);additionalPersonalLabel.setVisible(false);break;}
            case(3):
            {
            	additionalPersonal.setVisible(true);
                additionalPersonalLabel.setVisible(true);
                refrigeratorEngineer.setVisible(false);
            	refrigeratorEngineerLabel.setVisible(false);
            	pumpmanLabel.setVisible(true);
            	pumpman.setVisible(true);
            	gasEngineerLabel.setVisible(false);
            	gasEngineer.setVisible(false);
            	break;}
            case(4):
            {
            	additionalPersonal.setVisible(true);
                additionalPersonalLabel.setVisible(true);
                refrigeratorEngineer.setVisible(true);
            	refrigeratorEngineerLabel.setVisible(true);
            	pumpmanLabel.setVisible(false);
            	pumpman.setVisible(false);
            	gasEngineerLabel.setVisible(true);
            	gasEngineer.setVisible(true);
            	break;}

            case(5):{additionalPersonal.setVisible(false);additionalPersonalLabel.setVisible(false);break;}



        }
        }

    });

	Calendar now = Calendar.getInstance();
	startDate.setValue(LocalDate.now());
	// TODO Auto-generated method stub
	connection =SqliteConnection.Connector();
	if (connection == null) System.out.print("NOT CONNECTED");
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
//	String query = "select FirstName,LastName,SurName from Employee ";
	String query = "select ShipID,Name from Ship where TripID=-1 ";
	try{
		preparedStatement = connection.prepareStatement(query);
		//preparedStatement.setInt(1,value);
		resultSet = preparedStatement.executeQuery();
		comboString = FXCollections.observableArrayList(); //Declared somewhere else
		while(resultSet.next()){

			comboString.add(resultSet.getInt("ShipID")+": "+resultSet.getString("Name"));//(resultSet.getString("LastName")+" "+resultSet.getString("FirstName")+" "+resultSet.getString("SurName"));

		}
		shipName.setItems(comboString);

		master.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Капитан"));
		firstOfficer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Старший помощник"));
		secondOfficer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Второй помощник"));
		thirdOfficer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Третий помощник"));
		ChiefEng.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Старший механик"));
		firstEng.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Второй механик"));
		secondEng.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Третий механик"));
		thirdEng.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Четвертый механик"));
		Boatswain.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Боцман"));
		Donkerman.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Донкерман"));
		electroEngineer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Электромеханик"));

		motorMan.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Моторист"));
		radioOfficer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Помощник капитана по радиоэлектронике"));

		cook.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Шеф повар"));
		medicalOfficer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Врач"));


		chiefSeamen.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Матрос 1-го класса"));
		secondSeamen.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Матрос 2-го класса"));
		thirdSeamen.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Матрос 3-го класса"));
		fourthSeamen.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Матрос 4-го класса"));

		motormanWelder.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Матрос-сварщик"));
		gasEngineer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Инженер-газовик"));
		refrigeratorEngineer.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Рефмеханик"));
		pumpman.setItems(MyDbAdapter.getFioOfFreeEmployeesByPost("Оператор насосной установки"));



		//preparedStatement.close();
	//	resultSet.close();
	}catch(Exception e){System.out.print("ERROR:+++ "+e.getMessage()+"(((");}


}
/*
final Service thread=new Service<Integer>(){
	@Override
	public Task createTask(){
		return new Task<Integer>(){
			@Ovveride
			public Integer call() throws InterruptedException{
				int i;
				for(i=0;i<1000;i++){
					updateProgress(i,10000);
					Thread.sleep(10);
				}
				return i;
			}
		};
	};

}

*/
	@FXML
	void handleConfirm(){

		boolean corectinput=true;
		String tempval = null;
		System.out.println(startDate.getValue());
		System.out.println(endDate.getValue());

		if(endDate.getValue()==null){ corectinput=false;tempval="введите дату окончания рейса";}else
		if(startDate.getValue()==null){ corectinput=false;tempval="введите дату окончания рейса";}else
		if(startDate.getValue().isAfter(endDate.getValue())){corectinput=false;tempval="Даты рейса некорректны";}

		if(thirdSeamen.getValue()==null){	tempval="Укажите третьего матроса";corectinput=false;}
		if(secondSeamen.getValue()==null){	tempval="Укажите второго матроса";corectinput=false;}
		if(chiefSeamen.getValue()==null){	tempval="Укажите старшего матроса";corectinput=false;}
		if(cook.getValue()==null){	tempval="Укажите повара";}
		if(motorMan.getValue()==null){	tempval="Укажите моториста";corectinput=false;}
		if(electroEngineer.getValue()==null){	tempval="Укажите электромеханика";corectinput=false;}
		if(Donkerman.getValue()==null){	tempval="Укажите донкермана";}
		if(Boatswain.getValue()==null){	tempval="Укажите боцмана";corectinput=false;}
		if(secondEng.getValue()==null){	tempval="Укажите третьего механика";corectinput=false;}
		if(firstEng.getValue()==null){	tempval="Укажите второго механика";corectinput=false;}
		if(ChiefEng.getValue()==null){	tempval="Укажите сташего механика";corectinput=false;}
		if(secondOfficer.getValue()==null){tempval="Укажите второго помошника";corectinput=false;}
		if(firstOfficer.getValue()==null){	tempval="Укажите старшего помошника";corectinput=false;}
		if(master.getValue()==null){	tempval="Укажите капитана";corectinput=false;}
		if(currshipType==-1){
			tempval="Судно не выбрано";
			corectinput=false;}

		switch(currshipType){case(1):
        {
			if(refrigeratorEngineer.getValue()==null){	tempval="Укажите рефмеханика";corectinput=false;}
        	break;}

        case(3):
        {
        	if(pumpman.getValue()==null){	tempval="Укажите оператора насосной установки";corectinput=false;}
        break;
        }

        case(4):{
        	if(gasEngineer.getValue()==null){	tempval="Укажите инжинера-газовика";corectinput=false;}
        	if(refrigeratorEngineer.getValue()==null){	tempval="Укажите рефмеханика";corectinput=false;}
        	break;}

    }


		if(corectinput)
		{
		masterID=MyDbAdapter.getIdByFormattedFIOString(master.getValue());

		System.out.println(masterID);
		h.put("masterID",MyDbAdapter.getIdByFormattedFIOString(master.getValue()));

		h.put("firstOfficerID",MyDbAdapter.getIdByFormattedFIOString(firstOfficer.getValue()));
		h.put("secondOfficerID",MyDbAdapter.getIdByFormattedFIOString(secondOfficer.getValue()));
		h.put("thirdOfficerID",MyDbAdapter.getIdByFormattedFIOString(thirdOfficer.getValue()));

		h.put("ChiefEngID",MyDbAdapter.getIdByFormattedFIOString(ChiefEng.getValue()));
		h.put("firstEngID",MyDbAdapter.getIdByFormattedFIOString(firstEng.getValue()));
		h.put("secondEngID",MyDbAdapter.getIdByFormattedFIOString(secondEng.getValue()));
		h.put("thirdEngID",MyDbAdapter.getIdByFormattedFIOString(thirdEng.getValue()));

		h.put("BoatswainID",MyDbAdapter.getIdByFormattedFIOString(Boatswain.getValue()));
		h.put("DonkermanID",MyDbAdapter.getIdByFormattedFIOString(Donkerman.getValue()));
		h.put("electroEngineerID",MyDbAdapter.getIdByFormattedFIOString(electroEngineer.getValue()));
		h.put("motorManID",MyDbAdapter.getIdByFormattedFIOString(motorMan.getValue()));
		h.put("radioOfficerID",MyDbAdapter.getIdByFormattedFIOString(radioOfficer.getValue()));

		h.put("cookID",MyDbAdapter.getIdByFormattedFIOString(cook.getValue()));
		h.put("medicalOfficerID",MyDbAdapter.getIdByFormattedFIOString(medicalOfficer.getValue()));

		h.put("chiefSeamenID",MyDbAdapter.getIdByFormattedFIOString(chiefSeamen.getValue()));
		h.put("secondSeamenID",MyDbAdapter.getIdByFormattedFIOString(secondSeamen.getValue()));
		h.put("thirdSeamenID",MyDbAdapter.getIdByFormattedFIOString(thirdSeamen.getValue()));
		h.put("fourthSeamenID",MyDbAdapter.getIdByFormattedFIOString(fourthSeamen.getValue()));
		h.put("motormanWelderID",MyDbAdapter.getIdByFormattedFIOString(motormanWelder.getValue()));


		h.put("gasEngineerID",MyDbAdapter.getIdByFormattedFIOString(gasEngineer.getValue()));
		h.put("pumpmanID",MyDbAdapter.getIdByFormattedFIOString(pumpman.getValue()));
		h.put("refrigeratorEngineerID",MyDbAdapter.getIdByFormattedFIOString(refrigeratorEngineer.getValue()));


		Exception b=MyDbAdapter.CreateTripWatchSchedule(h,startDate.getValue(),endDate.getValue(),MyDbAdapter.getIdByFormattedFIOString(shipName.getValue()),currshipType);
		//System.out.println("VALUE:"+startDate.getValue().format(DateTimeFormatter.ofPattern("dd.mm.yyyy")));
		//Alert a=new Alert(AlertType.INFORMATION);

		final SimpleIntegerProperty prop = new SimpleIntegerProperty(0);
		prog.progressProperty().bind(prop);

		prog.setVisible(true);
		new Thread(){
		     @Override
		     public void run(){
		          try {

		               for(int i=0; i<ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue()); i++){
		                    prop.set(i);
		                    Thread.sleep(100);
		               }

		          } catch (InterruptedException ex) {
		               System.err.println("Error on Thread Sleep");
		          }
		     }

		}.start();
		if(b==null){
			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("Рейс создан");
			a.showAndWait();
		}else{
			Alert a=new Alert(AlertType.WARNING);
			a.setTitle("Ошибка");
			a.setHeaderText("Рейс не создан");
			a.setContentText(b.getMessage());
			a.showAndWait();
		}
			handleClose();

		}else{
			Alert a=new Alert(AlertType.WARNING);
			a.setTitle("Ошибка");
			a.setHeaderText("Обязательные поля не заполнены");
			a.setContentText(tempval);
			a.showAndWait();
			corectinput=true;
		}


		}
	@FXML
	private void handleClose(){
	//this.close();
		Stage stage = (Stage) closeBtn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	    //System.out.println("IDQWQWEQWE:"+MyDbAdapter.getIdByFormattedFIOString(master.getValue()));
	}

	@FXML
	private void handleShipChanged(){
	//this.close();
		System.out.println(shipName.getValue());
	    //System.out.println("IDQWQWEQWE:"+MyDbAdapter.getIdByFormattedFIOString(master.getValue()));
	}


}
