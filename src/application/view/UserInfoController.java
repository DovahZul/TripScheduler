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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserInfoController implements Initializable{


	 @FXML
	TableView<ModelUser> userInfoTable;

	@FXML
	TableColumn<ModelUser,String> itemUserId;
	@FXML
	TableColumn<ModelUser,String> itemUserFullName;

	@FXML
	TableColumn<ModelUser,String> itemUserBirthday;

	@FXML
	TableColumn<ModelUser,String> itemUserPost;
	@FXML
	TableColumn<ModelUser,String> itemUserCountry;
	@FXML
	TableColumn<ModelUser,String> itemUserLanguage;
	@FXML
	TableColumn<ModelUser,String> itemUserCurrentShip;
	@FXML
	TableColumn<ModelUser,String> itemUserSalary;

	@FXML
	HBox admControlsUserInfo;

	Connection connection;
	private ObservableList<ModelUser> data;
	private Stage primaryStage;
	private Main mainApp;

	public void setMainApp(Main mainApp) {
		// TODO Auto-generated method stub
		this.mainApp=mainApp;

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub
		userInfoTable.setPlaceholder(new Label("Нет информации"));
		Fill();
		userInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//userInfoTable.setEditable(true);
		//itemUserCountry.setEditable(true);
		if(Main.getCurrId()==0)admControlsUserInfo.setDisable(false);else admControlsUserInfo.setDisable(true);















	}
	public void Fill(){
		userInfoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//System.out.println("CURRID from Root ="+currId);
		itemUserId.setCellValueFactory(cellData -> cellData.getValue().getId().asString());
		itemUserFullName.setCellValueFactory(cellData -> cellData.getValue().getFullName());
		itemUserBirthday.setCellValueFactory(cellData -> cellData.getValue().getBirthday());
		itemUserPost.setCellValueFactory(cellData -> cellData.getValue().getPost());
		itemUserCountry.setCellValueFactory(cellData -> cellData.getValue().getCountry());
		itemUserLanguage.setCellValueFactory(cellData -> cellData.getValue().getLanguage());
		itemUserCurrentShip.setCellValueFactory(cellData -> cellData.getValue().getCurrentShip());
		itemUserSalary.setCellValueFactory(cellData -> cellData.getValue().getSalary().asString());

		 data = FXCollections.observableArrayList();
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");else System.out.print("DB IS OK");
		try{
	        String SQL = "select * from Employee where ID<>0";
	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			ResultSet rs=preparedStatement.executeQuery();
	        while(rs.next()){
	        	ModelUser cm = new ModelUser();
	            cm.userId.set(rs.getInt("ID"));
	            cm.userFullName.set(rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0));
	            cm.userFirstName.set(rs.getString("FirstName"));
	            cm.userLastName.set(rs.getString("LastName"));
	            cm.userSurName.set(rs.getString("SurName"));


	            cm.userPost.set(rs.getString("Post"));
	            cm.userBirthday.set(rs.getString("bdate"));
	            cm.userCountry.set(rs.getString("Country"));
	            cm.userLanguage.set(rs.getString("Language"));
	            cm.userCurrentShip.set( MyDbAdapter.getShipNameById(rs.getInt("RelatedShipId")));
	            cm.salary.set(rs.getDouble("Salary"));
	            cm.login.set(rs.getString("login"));
	            cm.password.set(rs.getString("password"));


	            data.add(cm);
	           // data.add(new WatchStage("qwe1","qwe2","qwe3"));
	           // System.out.println(data.get(0).ShipName);
	        }
	        userInfoTable.setItems(data);

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
	private void handleAddUser(){
		mainApp.InitUserWindow();
	    }

	@FXML
	private void handleDeleteUser(){
		int selectedIndex = userInfoTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	Alert a=new Alert(AlertType.CONFIRMATION, "Удалить? " + userInfoTable.getItems().get(selectedIndex).getFullName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			a.setTitle("Удаление");
			a.setHeaderText("Удалить елемент?");
			a.setContentText("Внимание!не удаляйте пользователя"+System.lineSeparator()+
					 " который находится в рейсе!");
			a.showAndWait();
			if (a.getResult() == ButtonType.YES) {
				Exception t=MyDbAdapter.DeleteUserById(userInfoTable.getItems().get(selectedIndex).userId.get());
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
	public void handleUpdateUsers(){
		Fill();
	}

	@FXML
	private void handleEditUser(){
		int selectedIndex =userInfoTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {



	    	 try {
			        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(Main.class.getResource("/application/view/EditUserWindow.fxml"));
			        AnchorPane page = (AnchorPane) loader.load();
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("Сотрудник - редактирование");
			        dialogStage.initModality(Modality.WINDOW_MODAL);
			        dialogStage.initOwner(primaryStage);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);
			        dialogStage.setResizable(false);

			        // Передаёт адресатов в контроллер.
			        EditUserWindowController controller = loader.getController();
			     //   controller.setPersonData(personData);
			        controller.setMainApp(this);
			        controller.setElement(userInfoTable.getItems().get(selectedIndex));
			        controller.fillNode(userInfoTable.getItems().get(selectedIndex));
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