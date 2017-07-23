package application.view;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShipInfoController implements Initializable{


	 @FXML
	TableView<ShipModel> shipInfoTable;

	@FXML
	TableColumn<ShipModel,String> itemShipId;
	@FXML
	TableColumn<ShipModel,String> itemShipName;

	@FXML
	TableColumn<ShipModel,String> itemShipType;

	@FXML
	TableColumn<ShipModel,String> itemShipStatus;
	@FXML
	HBox admControlsSHipInfo;


	Connection connection;
	private ObservableList<ShipModel> data;
	private Stage primaryStage;
	private Main mainApp;

	public void setMainApp(Main mainApp) {
		// TODO Auto-generated method stub
		this.mainApp=mainApp;

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		shipInfoTable.setPlaceholder(new Label("Нет информации"));

		shipInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		shipInfoTable.setEditable(true);

		Fill();
		if(Main.getCurrId()==0)admControlsSHipInfo.setDisable(false);else admControlsSHipInfo.setDisable(true);
	}
	public void Fill(){
		//System.out.println("CURRID from Root ="+currId);
		itemShipId.setCellValueFactory(cellData -> cellData.getValue().getShipId().asString());
		itemShipName.setCellValueFactory(cellData -> cellData.getValue().getShipName());
		itemShipType.setCellValueFactory(cellData -> cellData.getValue().getShipType());
		itemShipStatus.setCellValueFactory(cellData -> cellData.getValue().getSatus());

		 data = FXCollections.observableArrayList();
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");
		else System.out.print("DB IS OK");
	//	data = FXCollections.ObservableList<>();
		try{
	        String SQL = "select * from Ship";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
		//	preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();
	        while(rs.next()){
	        	ShipModel cm = new ShipModel();
	            cm.shipId.set(rs.getInt("ShipID"));
	            cm.shipName.set(rs.getString("Name"));

	            cm.shipType.set(MyDbAdapter.getShipTypeAbsNameById(rs.getInt("Type")));
	            cm.shipStatus.set(MyDbAdapter.getShipStatusAbsNameByStatusId(rs.getInt("StatusID")));



	         //   System.out.println( cm.ShipName);
	         //out.println( cm.StartDate);
	         //   System.out.println( cm.EndDate);
	            //this.l.setText("QWEQWEQWEQWEWEQWEQWEQWQWEQWEQW");

	            data.add(cm);
	           // data.add(new WatchStage("qwe1","qwe2","qwe3"));
	           // System.out.println(data.get(0).ShipName);
	        }
	        shipInfoTable.setItems(data);

	      //watchTable.getItems().setAll(data);
	       // System.out.println(watchTable.getRowFactory());
	       // itemShipname.setCellValueFactory(new PropertyValueFactory<WatchStage, String>("id"));
	      //  watchTable.setItems(null);
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");

	    }
	}

	@FXML
	private void handleAddShip(){
	//	mainApp.InitShipWindow();
		try {
	        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("/application/view/InitShipWindow.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Регистрация судна");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);

	        InitShipWindowController controller = loader.getController();
	        controller.setConn(this.connection);
	        controller.setMainApp(this);


	        dialogStage.show();
	        System.out.print("Ship window inited");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    }


	@FXML
	private void handleApply(){

	}

	@FXML
	private void handleDeleteShip(){
		int selectedIndex = shipInfoTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	Alert a=new Alert(AlertType.CONFIRMATION, "Удалить? " + shipInfoTable.getItems().get(selectedIndex).getShipName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			a.setTitle("Удаление");
			a.setHeaderText("Удалить елемент?");
			a.setContentText("Внимание!Если судно находся в рейсе"+System.lineSeparator()+
					 " информация о рейсе будет удалена!");
			a.showAndWait();
			if (a.getResult() == ButtonType.YES) {
				Exception t=MyDbAdapter.DeleteShipById(shipInfoTable.getItems().get(selectedIndex).shipId.get());
				if(t==null){
					Fill();//shipInfoTable.getItems().remove(selectedIndex);
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
	public void handleUpdate(){
		Fill();
	}

	@FXML
	private void handleEditShip(){
		int selectedIndex =shipInfoTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {



	    	 try {
			        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(Main.class.getResource("/application/view/EditShipWindow.fxml"));
			        AnchorPane page = (AnchorPane) loader.load();
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("Судно - редактирвоание");
			        dialogStage.initModality(Modality.WINDOW_MODAL);
			        dialogStage.initOwner(primaryStage);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);
			        dialogStage.setResizable(false);

			        // Передаёт адресатов в контроллер.
			        EditShipWindowController controller = loader.getController();
			     //   controller.setPersonData(personData);
			        controller.setMainApp(this);
			        controller.setElement(shipInfoTable.getItems().get(selectedIndex));
			        controller.FillNode(shipInfoTable.getItems().get(selectedIndex));
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
}




