package application.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Callback;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SchedulesController implements Initializable{
	@FXML
	private Label shipNameLabel;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Label postLabel;

	@FXML
	TableView<ModelMooring> mooringTable;

	@FXML
	TableColumn<ModelMooring,String> itemHiddenMooringID;
	@FXML
	TableColumn<ModelMooring,String> itemPortname;
	@FXML
	TableColumn<ModelMooring,String> itemStart;
	@FXML
	TableColumn<ModelMooring,String> itemEnd;
	@FXML
	TableColumn<ModelMooring,String> itemResponsible;
	@FXML
	TableColumn<ModelMooring,String> itemStatus;

	@FXML
	TableView<WatchStage> personalWatchTable;
	@FXML
	TableColumn<WatchStage,String> itemDate;
	@FXML
	TableColumn<WatchStage,String> itemHours;

	@FXML
	TableColumn<WatchStage,Boolean> selectColumn;

	@FXML
	TableView<WatchStage> commonWatchTable;
	@FXML
	TableColumn<WatchStage,String> itemCommonDate;
	@FXML
	TableColumn<WatchStage,String> itemEmployeeData;
	@FXML
	TableColumn<WatchStage,String> itemCommonHours;


	@FXML
	TableView<ModelEmployeeDay> commonWatchTableV2;

	@FXML TableColumn<ModelEmployeeDay,String> itemEmployeeFioV2;
	@FXML TableColumn<ModelEmployeeDay,String> itemEmployeePostV2;

	@FXML TableColumn<ModelEmployeeDay,String> itemHour00;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour01;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour02;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour03;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour04;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour05;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour06;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour07;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour08;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour09;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour10;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour11;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour12;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour13;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour14;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour15;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour16;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour17;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour18;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour19;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour20;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour21;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour22;
	@FXML TableColumn<ModelEmployeeDay,String> itemHour23;




	@FXML
	Tab personalScheduleTab;

	@FXML
	Tab commonScheduleTabV2;

	@FXML
	TabPane scheduleTabPane;

	////////////////////STATICTICS
	@FXML
	private Label startDateLabel;

	@FXML
	private Label endDateLabel;

	@FXML
	private Label totalCountDaysLabel;

	@FXML
	private Label goneDaysLabel;

	@FXML
	private Label checkedDaysLabel;

	@FXML
	private Label remainedDaysLabel;

////////////////////STATICTICS
	/*
	 * Administrative Controls NEED TO BE DISABLED!!!!!!!!
	 *    ///////////////////////////////////
	 */
	@FXML ComboBox<String> admSwitchUser;
	@FXML ComboBox<String> admSwitchShip;

	@FXML HBox admScheduleControls;
	@FXML Group admMooringControls;

	@FXML Button  admBtnRepealChecks;
	@FXML Button  admBtnCheckTilToday;
	@FXML Button  admBtnCheckAll;
	@FXML Button  admBtnSaveChecks;



	@FXML
	Label forNewbie;


	@FXML AnchorPane UserAnchorPane;
	@FXML Button exitBtn;
	//@FXML Button editMooringButton;


	private  int currId=13;
	Connection connection;
	private ObservableList<WatchStage> personalScheduleList;
	private ObservableList<ModelMooring> mooringScheduleList;
	private ObservableList<WatchStage> commonScheduleList;
	private ObservableList<ModelEmployeeDay> commonScheduleListV2;

	private Main mainApp;
	private Stage primaryStage;



	 /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     * @param mainApp
     */

	@FXML
	DatePicker commonScheduleV2Swicher;

	public int getCurrId(){
		return this.currId;
	}
	public void setCurrId(int id){
		this.currId=id;
	}
public void setMainApp(Main main) {
	// TODO Auto-generated method stub
	this.mainApp=main;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		mooringTable.setPlaceholder(new Label("Нет информации"));
		personalWatchTable.setPlaceholder(new Label("Нет информации"));
		commonWatchTableV2.setPlaceholder(new Label("Нет информации"));
		//itemHours.set



	FillPersWatch(Main.getCurrId());
	FillReport(Main.getCurrId());
	FillMooringSchedule(MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrId()));
	System.out.println("MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrShipId:"+MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrId()));

	FillCommonWatchV2(Main.dateFormat.format(Main.currentDate.getTime()),MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrId()));


	admSwitchUser.setItems(MyDbAdapter.ReadAllEmployeeAsFormattedStringOnShips());
	admSwitchShip.setItems(MyDbAdapter.readUsedShipsAsFormattedString());

	admSwitchUser.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {

        	System.out.println("LALALA:"+t1+" LAAAAAAA"+MyDbAdapter.getTripIDByEmployeeID(13));
        	FillPersWatch(MyDbAdapter.getIdByFormattedFIOString(t1));
        	FillReport(MyDbAdapter.getIdByFormattedFIOString(t1));

        	if(Main.getCurrId()==0){
    	    	FillCommonWatchV2(Main.dateFormat.format(Main.currentDate.getTime()),MyDbAdapter.getRelatedShipIdByEmployeeId(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue())));
    	    	Main.setCurrFakedId(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue()));

        }else{
    	    		FillCommonWatchV2(Main.dateFormat.format(Main.currentDate.getTime()),MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrId()));
    	    		Main.setCurrFakedId(-1);
        }

        }

    });
	System.out.println("qweqwe:"+MyDbAdapter.readAllShipsAsFormattedString());


	admSwitchShip.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {


        //	FillPersWatch(MyDbAdapter.getIdByFormattedFIOString(t1));
        //	FillReport(MyDbAdapter.getIdByFormattedFIOString(t1));

        //	Main.setCurrId(MyDbAdapter.getIdByFormattedFIOString(t1));
        	Main.setCurrShipId(MyDbAdapter.getShipIdFromFormattedName(t1));
        	//System.out.println("CUrrent SHip ID:"+  "     "+   MyDbAdapter.getIdByFormattedFIOString(t1)+" "+Main.getCurrShipId());
         	FillMooringSchedule(Main.getCurrShipId());
        }

    });


	commonScheduleV2Swicher.setValue((Main.currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

	commonScheduleV2Swicher.valueProperty().addListener(new ChangeListener<LocalDate>() {
	    @Override
	    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

	    	if(Main.getCurrId()==0)
	    	FillCommonWatchV2(newValue.toString(),MyDbAdapter.getRelatedShipIdByEmployeeId(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue())));else
	    		FillCommonWatchV2(newValue.toString(),MyDbAdapter.getRelatedShipIdByEmployeeId(Main.getCurrId()));
	    	System.out.println("FillCommonWatchV2(newValue.toSt"+  "     "+  MyDbAdapter.getRelatedShipIdByEmployeeId(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue())));

	    }
	});



		personalWatchTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		personalWatchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		commonWatchTableV2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		mooringTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//commonWatchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    personalWatchTable.setEditable(true);
	    scheduleTabPane.getSelectionModel().select(personalScheduleTab);

	    personalWatchTable.setRowFactory( new StyleRowFactory() );

	    if(Main.getCurrId()==0){ToggleAdmin(true);selectColumn.setEditable(true);}else{ ToggleAdmin(false);selectColumn.setEditable(false);}
	}



	private void ToggleAdmin(boolean b) {
		if(b){
			admScheduleControls.setDisable(false);
			admMooringControls.setDisable(false);

		}else{
			admScheduleControls.setDisable(true);
			admMooringControls.setDisable(true);
		}


	}
	private void FillReport(int par1currID) {
		String volsung="не определено";


		volsung=MyDbAdapter.getTripStartDateFromEmployeeID(par1currID);
		startDateLabel.setText(volsung);

		volsung=MyDbAdapter.getTripEndDateFromEmployeeID(par1currID);
		endDateLabel.setText(volsung);

		volsung=MyDbAdapter.getTotalCountTripDaysByEmployeeID(par1currID);
		totalCountDaysLabel.setText(volsung);

		volsung=MyDbAdapter.getGoneCountTripDaysByEmployeeID(par1currID);
		goneDaysLabel.setText(volsung);

		volsung=MyDbAdapter.getCheckedCountTripDaysByEmployeeID(par1currID);
		checkedDaysLabel.setText(volsung);

		volsung=MyDbAdapter.getRemainedCountTripDaysByEmployeeID(par1currID);
		remainedDaysLabel.setText(volsung);
		//Main.dateFormat.format(Main.currentDate.getTime())

	}
	public void setShipNameLabel(int currid){
		// connection =SqliteConnection.Connector();
			String str=MyDbAdapter.getRelatedShipNameByEmployeeId(currid);
			if(str!=null && Main.getCurrId()>0)
			shipNameLabel.setText("Судно: "+str);else{
				shipNameLabel.setText("Текущее судно не найдено");forNewbie.setVisible(true);}
	 }

	 public void setUserWelcomeLabel(int currid){
		// connection =SqliteConnection.Connector();
			String str=MyDbAdapter.getFirstNameByEmployeeId(currid);
			if(str!=null){
			welcomeLabel.setText("Добро пожаловать, "+str);}else
				welcomeLabel.setText("Пользователь не определён.");
	 }

	 public void setUserPostLabel(int currid){
			// connection =SqliteConnection.Connector();
				String str=MyDbAdapter.getPostNameByEmployeeID(currid);
				if(str!=null){
					postLabel.setText("Должность: "+str);}else
						postLabel.setText("Должность не определена.");
		 }

	 class StyleRowFactory implements Callback<TableView<WatchStage>, TableRow<WatchStage>> {
		    @Override
		    public TableRow<WatchStage> call(TableView<WatchStage> tableView) {
		        return new TableRow<WatchStage>() {
		            @Override
		            protected void updateItem( WatchStage person, boolean b ) {
		                super.updateItem( person, b );
		                if ( person == null )
		                    return;
		                if ( person.isHighlight() ) {
		                    setStyle( "-fx-background-color: gray;" );
		                } else {
		                    setStyle( null );
		                }
		            }
		        };
		    }
		}

	 private void FillPersWatch(int par1currID) {

         Callback<TableColumn<WatchStage, Boolean>, TableCell<WatchStage, Boolean>> cellFactory = CheckBoxTableCell.forTableColumn(selectColumn);
         selectColumn.setCellFactory(new Callback<TableColumn<WatchStage, Boolean>, TableCell<WatchStage, Boolean>>() {
             @Override
             public TableCell<WatchStage, Boolean> call(TableColumn<WatchStage, Boolean> column) {
                 TableCell<WatchStage, Boolean> cell = cellFactory.call(column);
                 cell.setAlignment(Pos.BASELINE_CENTER);
                 cell.editableProperty().setValue(true);
                 return cell ;
             }
         });
        // selectColumn.setCellFactory(cellFactory);
         selectColumn.setCellValueFactory(cellData -> cellData.getValue().getChecked());
         selectColumn.setEditable(true);


			itemDate.setCellValueFactory(cellData -> cellData.getValue().getWatchDate());
			itemHours.setCellValueFactory(cellData -> cellData.getValue().getWatchTime());
			personalScheduleList = FXCollections.observableArrayList();
			connection =SqliteConnection.Connector();
			if (connection == null) System.out.println("NOT CONNECTED");
			//List<String>dates=new ArrayList<String>();
			try{

		       // String SQL = "select Date,startTime,endTime,EmployeeID from testSchedule WHERE EmployeeID=? " ;
				String SQL = "select distinct Date,Completed FROM testSchedule WHERE EmployeeID=? " ;
				PreparedStatement ps = connection.prepareStatement(SQL);
				ps.setInt(1,par1currID);
				ResultSet rs=ps.executeQuery();
			//preparedStatement.cancel();
				String SQL2;
				PreparedStatement preparedStatement2 = null;
		        while(rs.next())
		        {
		        	String temp="";
		        	WatchStage cm = new WatchStage();
		            cm.watchDate.set(rs.getString("Date"));

		            if(cm.getWatchDate().get().equals(Main.dateFormat.format(Main.currentDate.getTime())))
		            {	cm.setHighlight(true);
		            }
		            cm.checked.set(rs.getBoolean("Completed"));
		        	 SQL2 = "select startTime,endTime FROM testSchedule WHERE EmployeeID=? AND Date like ?" ;
		        	  preparedStatement2 = connection.prepareStatement(SQL2);
		        	  if(Main.getCurrId()==0)
						preparedStatement2.setInt(1,Main.getCurrFakedId());else
							preparedStatement2.setInt(1,Main.getCurrId());

						preparedStatement2.setString(2,rs.getString("Date"));
						ResultSet rs2=preparedStatement2.executeQuery();
						  while(rs2.next()){
							  temp+=rs2.getString("startTime")+"-"+rs2.getString("endTime")+"|";
							  System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOorwhilers2.next: "+temp);

						  }
						  cm.watchTime.set(temp);
						  personalScheduleList.add(cm);

						  //preparedStatement2.cancel();
						 // rs2.close();
		        }


		        for(WatchStage w : personalScheduleList){
		        		System.out.println(w.getChecked());
		        }
		        personalWatchTable.setItems(personalScheduleList);

		      //  rs.close();
		    //    preparedStatement2.close();

		        connection.close();
		       // preparedStatement.cancel();

		    }
		    catch(Exception e){
		          e.printStackTrace();
		          System.out.println("Error on Building Data");

		    }

		}


	protected void FillMooringSchedule(int par1SHipID) {
		itemHiddenMooringID.setCellValueFactory(cellData -> cellData.getValue().getMooringID().asString());
		itemPortname.setCellValueFactory(cellData -> cellData.getValue().getPortName());
		itemStart.setCellValueFactory(cellData -> cellData.getValue().getStartDateTime());
		itemEnd.setCellValueFactory(cellData -> cellData.getValue().getEndDateTime());
		itemResponsible.setCellValueFactory(cellData -> cellData.getValue().getResponsiblePerson());
		itemStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus());
		mooringScheduleList = FXCollections.observableArrayList();

		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");
		try{
	        String SQL = "select "
	        		+ "MooringSchedule.MooringID,"
	        		+ "MooringSchedule.PortName,"
	        		+ "MooringSchedule.TripID,"
	        		+ "MooringSchedule.startDate,"
	        		+ "MooringSchedule.endDate,"
	        		+ "MooringSchedule.ResponsibleEmployeeID,"
	        		+ "MooringSchedule.StatusID"

	        		+ " from MooringSchedule,Ship WHERE "
	        		+ "MooringSchedule.TripID=Ship.TripID AND Ship.ShipID=?" ;


	        PreparedStatement preparedStatement =null;
	        preparedStatement=connection.prepareStatement(SQL);
			preparedStatement.setInt(1,par1SHipID);
			ResultSet rs=preparedStatement.executeQuery();
	        while(rs.next()){
	       // 	String temp1=MyDbAdapter.getFirstNameByEmployeeId(currid);
	        	ModelMooring cm = new ModelMooring();
	        	cm.mooringID.set(rs.getInt("MooringID"));
	            cm.portName.set(rs.getString("PortName"));
	            System.out.println("PORT NAME: "+cm.portName);
	            cm.tripId.set(rs.getInt("TripID"));
	            cm.startDatetime.set(rs.getString("startDate"));
	            cm.endDateTime.set(rs.getString("endDate"));
	            cm.responsible.set(MyDbAdapter.getFormattedFioOfEmployeeByEmployeeId(rs.getInt("ResponsibleEmployeeID")));
	            cm.status.set(MyDbAdapter.getTypeAbsNameById(rs.getInt("StatusID")));//(rs.getInt("StatusID"));
	                mooringScheduleList.add(cm);
	        }
	        mooringTable.setItems(mooringScheduleList);



	        connection.close();
	        preparedStatement.close();
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");

	    }


	}

	@FXML
	private void handleSaveCheckBoxes(){
		  for(WatchStage w : personalScheduleList){
      		System.out.println(w.getChecked().get());
		  }
		  if(Main.getCurrId()!=0)
		  {
		Exception t=MyDbAdapter.WriteCheckedDaysToEmployee(personalScheduleList,Main.getCurrId());
		if(t==null){
			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("Изменения сохранены");
			a.showAndWait();
		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Не сохранено,внутренняя ошибка");
			a.setContentText(t.getMessage());
			a.showAndWait();
		}
		  }else
		  {

			  Exception t=MyDbAdapter.WriteCheckedDaysToEmployee(personalScheduleList,MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue()));
				if(t==null){
					Alert a=new Alert(AlertType.INFORMATION);
					a.setTitle("Сообщение");
					a.setHeaderText("Выполнено");
					a.setContentText("Изменения сохранены");
					a.showAndWait();
				}else{
					Alert a=new Alert(AlertType.ERROR);
					a.setTitle("Ошибка");
					a.setHeaderText("Не сохранено,внутренняя ошибка");
					a.setContentText(t.getMessage());
					a.showAndWait();
				}



		  }
	}

	@FXML
	private void handleCheckAll(){
		  for(WatchStage w : personalScheduleList){
      		w.setChecked(true);
		  }
	}

	@FXML
	private void handleCheckTillToday() throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date1 = sdf.parse(Main.dateFormat.format(Main.currentDate.getTime()));
	        Date date2 ;//= sdf.parse("2010-01-31");

		  for(WatchStage w : personalScheduleList){
			  date2=sdf.parse(w.getWatchDate().get());
			//  if(sdf.format("2000-05-05").(sdf.format("2000-05-05"))>0)
			  if(date1.after(date2))
      		w.setChecked(true);
		  }
	}

	@FXML
	private void handleNewMooring()
	{
	//	mainApp.InitMooringWindow();
		if(Main.getCurrShipId()<0){
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Судно не выбрано");
			a.setContentText("укажите судно");
			a.showAndWait();


			return;
		}
		 try {
		        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(Main.class.getResource("/application/view/InitMooringWindow.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("Добавить швартовку");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        dialogStage.setResizable(false);

		        // Передаёт адресатов в контроллер.
		        InitMooringWindowController controller = loader.getController();
		     //   controller.setPersonData(personData);
		        controller.setMainApp(this);
		        dialogStage.show();
		     //  System.out.print("INITTRIP");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }




	}

	@FXML
	private void handleUpdatePersWatch()
	{
		if(Main.getCurrId()==0)
		{
		FillPersWatch(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue()));
		FillReport(MyDbAdapter.getIdByFormattedFIOString(admSwitchUser.getValue()));
		}else{
			FillPersWatch(Main.getCurrId());
			FillReport(Main.getCurrId());

		}

	}
	private void FillCommonWatchV2(String formatteddate,int shipID) {

		itemEmployeeFioV2.setCellValueFactory(cellData -> cellData.getValue().getFullName());
		itemEmployeePostV2.setCellValueFactory(cellData -> cellData.getValue().getPost());
		itemHour00.setCellValueFactory(cellData -> cellData.getValue().hour00);
		itemHour01.setCellValueFactory(cellData -> cellData.getValue().hour01);
		itemHour02.setCellValueFactory(cellData -> cellData.getValue().hour02);
		itemHour03.setCellValueFactory(cellData -> cellData.getValue().hour03);
		itemHour04.setCellValueFactory(cellData -> cellData.getValue().hour04);
		itemHour05.setCellValueFactory(cellData -> cellData.getValue().hour05);
		itemHour06.setCellValueFactory(cellData -> cellData.getValue().hour06);
		itemHour07.setCellValueFactory(cellData -> cellData.getValue().hour07);
		itemHour08.setCellValueFactory(cellData -> cellData.getValue().hour08);
		itemHour09.setCellValueFactory(cellData -> cellData.getValue().hour09);
		itemHour10.setCellValueFactory(cellData -> cellData.getValue().hour10);
		itemHour11.setCellValueFactory(cellData -> cellData.getValue().hour11);
		itemHour12.setCellValueFactory(cellData -> cellData.getValue().hour12);
		itemHour13.setCellValueFactory(cellData -> cellData.getValue().hour13);
		itemHour14.setCellValueFactory(cellData -> cellData.getValue().hour14);
		itemHour15.setCellValueFactory(cellData -> cellData.getValue().hour15);
		itemHour16.setCellValueFactory(cellData -> cellData.getValue().hour16);
		itemHour17.setCellValueFactory(cellData -> cellData.getValue().hour17);
		itemHour18.setCellValueFactory(cellData -> cellData.getValue().hour18);
		itemHour19.setCellValueFactory(cellData -> cellData.getValue().hour19);
		itemHour20.setCellValueFactory(cellData -> cellData.getValue().hour20);
		itemHour21.setCellValueFactory(cellData -> cellData.getValue().hour21);
		itemHour22.setCellValueFactory(cellData -> cellData.getValue().hour22);
		itemHour23.setCellValueFactory(cellData -> cellData.getValue().hour23);

		commonScheduleListV2 = FXCollections.observableArrayList();
		List<String> l=new ArrayList<String>();
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");
		try{
	        String SQL = "select DISTINCT EmployeeID from testSchedule,Employee WHERE Date like ? AND RelatedShipID=? AND RelatedShipID>=0 AND EmployeeID>0 " ;


	        PreparedStatement preparedStatement =null;
	        preparedStatement=connection.prepareStatement(SQL);
	        preparedStatement.setString(1,formatteddate);
	        preparedStatement.setInt(2,shipID);
			ResultSet rs=preparedStatement.executeQuery();
			System.out.println("System.out.println(+shipID);:"+shipID);


	        while(rs.next()){

	        	ModelEmployeeDay cm = new ModelEmployeeDay();
	            cm.setFullName(MyDbAdapter.getFormattedFioOfEmployeeByEmployeeId(rs.getInt("EmployeeID")));
	            cm.setPost(MyDbAdapter.getPostNameByEmployeeID(rs.getInt("EmployeeID")));
	            l=MyDbAdapter.getWorkHoursByEmployeeIdAndDate(rs.getInt("EmployeeID"),Main.dateFormat.format(Main.currentDate.getTime()));

	            cm.hour00.set(l.get(0));
	            cm.hour01.set(l.get(1));
	            cm.hour02.set(l.get(2));
	            cm.hour03.set(l.get(3));
	            cm.hour04.set(l.get(4));
	            cm.hour05.set(l.get(5));
	            cm.hour06.set(l.get(6));
	            cm.hour07.set(l.get(7));
	            cm.hour08.set(l.get(8));
	            cm.hour09.set(l.get(9));
	            cm.hour10.set(l.get(10));
	            cm.hour11.set(l.get(11));
	            cm.hour12.set(l.get(12));
	            cm.hour13.set(l.get(13));
	            cm.hour14.set(l.get(14));
	            cm.hour15.set(l.get(15));
	            cm.hour16.set(l.get(16));
	            cm.hour17.set(l.get(17));
	            cm.hour18.set(l.get(18));
	            cm.hour19.set(l.get(19));
	            cm.hour20.set(l.get(20));
	            cm.hour21.set(l.get(21));
	            cm.hour22.set(l.get(22));
	            cm.hour23.set(l.get(23));


	            commonScheduleListV2.add(cm);
	        }
	        commonWatchTableV2.setItems(commonScheduleListV2);



	        connection.close();
	        preparedStatement.close();
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");

	    }


	}

	@FXML
	public void handleNextDate(){
		try{
		commonScheduleV2Swicher.setValue(commonScheduleV2Swicher.getValue().plusDays(1));
		}catch(Exception e){System.out.println(e.getMessage());}
		}

	@FXML
	public void handlePreviousDate(){
		try{
		commonScheduleV2Swicher.setValue(commonScheduleV2Swicher.getValue().minusDays(1));
		}catch(Exception e){System.out.println(e.getMessage());}
		}


	@FXML
	private void handleApply(){

	}

	@FXML
	private void handleDeleteMooring(){
		int selectedIndex = mooringTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	Alert a=new Alert(AlertType.CONFIRMATION, "Удалить? " + mooringTable.getItems().get(selectedIndex).getPortName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			a.setTitle("Удаление");
			a.setHeaderText("Удалить елемент?");
		//	a.setContentText("Внимание!Если судно находся в рейсе"+System.lineSeparator()+
		//			 " информация о рейсе будет удалена!");
			a.showAndWait();
			if (a.getResult() == ButtonType.YES) {
				Exception t=MyDbAdapter.DeleteMooringById(mooringTable.getItems().get(selectedIndex).getMooringID().get());
				if(t==null){
					FillMooringSchedule(Main.getCurrShipId());
						Alert a2=new Alert(AlertType.INFORMATION);
					a2.setTitle("Сообщение");
					a2.setHeaderText("Выполнено");
					a2.setContentText("Запись удалена");
					a2.showAndWait();
				}else{
					Alert a2=new Alert(AlertType.ERROR);
					a2.setTitle("Ошибка");
					a2.setHeaderText("Удаление не выполнено,внутренняя ошибка");
					a2.setContentText(t.getMessage());
					a2.showAndWait();
					}

	    }

	    }else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Выделение отсутствует");
	        alert.setHeaderText("Ни один елемент не выбран");
	        alert.setContentText("Пожалуйста выберите елемент для удаления.");

	        alert.showAndWait();
	    }

	}

	@FXML
	private void handleEditMooring(){
		int selectedIndex = mooringTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {



	    	 try {
			        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(Main.class.getResource("/application/view/EditMooringWindow.fxml"));
			        AnchorPane page = (AnchorPane) loader.load();
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("Швартовка - редактирвоание");
			        dialogStage.initModality(Modality.WINDOW_MODAL);
			        dialogStage.initOwner(primaryStage);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);
			        dialogStage.setResizable(false);

			        // Передаёт адресатов в контроллер.
			        EditMooringWindowController controller = loader.getController();
			     //   controller.setPersonData(personData);
			        controller.setMainApp(this);
			        controller.setElement(mooringTable.getItems().get(selectedIndex));
			        controller.fillNode(mooringTable.getItems().get(selectedIndex));
			        dialogStage.showAndWait();
			     //  System.out.print("INITTRIP");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }







	    }else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Выделение отсутствует");
	        alert.setHeaderText("Ни один елемент не выбран");
	        alert.setContentText("Пожалуйста выберите елемент для редактирования.");

	        alert.showAndWait();
	    }


	}


	@FXML
	public void handleUpdateMooring(){
	//	FillMooringSchedule(MyDbAdapter.readAllShipsAsFormattedString(MyDbAdapter.getIdByFormattedFIOString(admSwitchShip.getValue())));
	//	if(Main.getCurrId()==0)
	//	{
//
		FillMooringSchedule(Main.getCurrShipId());

		//}else{
//
	//		FillMooringSchedule(Main.getCurrId());

		//}
	}

	@FXML
	public void handleLogOut(){
		Main.setCurrId(-1);
		try{
			Stage stage = (Stage) exitBtn.getScene().getWindow();
		    // do what you have to do
		    stage.close();
			mainApp.initLogin();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}

			}

	@FXML
	public void handForNewbieHover(){
		forNewbie.setUnderline(true);
	}
	@FXML
	public void handForNewbieLeave(){
		forNewbie.setUnderline(false);
	}
	@FXML
	public void hadleShowSalary(){
		mainApp.InitSalary();
	}

	@FXML
	public void hadleNewbie(){
		mainApp.InitAppInfo();
	}







}
