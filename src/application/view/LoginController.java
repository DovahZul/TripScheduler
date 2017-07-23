package application.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import application.util.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	public LoginModel loginModel = new LoginModel();
	  private Main mainApp;
	  @FXML
	  private TextField login;
	  @FXML
	  private PasswordField password;
	  @FXML
	  private Button okbtn;

	  @FXML
	  private Button closeBtn;

	  private int currentUserId;
	private Stage primaryStage;
	  /**
	     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
	     *
	     * @param mainApp
	     */
	public void setMainApp(Main main) {
		// TODO Auto-generated method stub
		this.mainApp=main;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//closeBtn.defaultButtonProperty().bind(closeBtn.focusedProperty());
		okbtn.setDefaultButton(true);

		//if(loginModel.isDbConnected()){
		//	System.out.println("DB CONNECTED");
	//	}else{
		//	System.out.println("DB NOT CONNECTED");
		//	Alert a=new Alert(AlertType.ERROR);
		//	a.setTitle("Ошибка");
		//	a.setHeaderText("Ошибка базы данных");
		//	a.setContentText("Оиибка базы данных");
		//	a.showAndWait();
		//}
	}

	@FXML
	public void tryLogin(ActionEvent event){
		if(login.getText()=="")login.requestFocus();else
			if(password.getText()=="")password.requestFocus();else
		try{
			if(loginModel.isLogin(login.getText(), password.getText())!=-1){
				//currentUserId=loginModel.isLogin(login.getText(), password.getText());


				Main.setCurrId(loginModel.isLogin(login.getText(), password.getText()));
				System.out.println("username and password is correct  currentUserId:"+Main.getCurrId());

				mainApp.initRootWindow();
				mainApp.InitSchedules();
			//	this.primaryStage.getIcons().add(new Image("file:resources/images/dogeicon.png"));
			//	mainApp.InitMooringWindow();
				/*
				FXMLLoader loader=new FXMLLoader();
				loader.setLocation(getClass().getResource("RootWindow.fxml"));
				Parent parent = loader.load();

				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();//new Stage();

				stage.setScene(scene);
				RootController rootController =     loader.<RootController>getController();

				rootController.setCurrId(this.currentUserId);

				System.out.println(" CONTROLLER is null="+(null==rootController)+rootController.getCurrId());
				rootController.setMainApp(mainApp);
				if(currentUserId==0){
					stage.setTitle("Расписание - Администратор");
				}else{
				stage.setTitle("Расписание - "+ loginModel.getFioByID(currentUserId));
				}
				stage.show();
				((Node) (event.getSource())).getScene().getWindow().hide();
				*/

			}else{
				System.out.print("username and password is NOT correct ");
				 Alert alert = new Alert(AlertType.WARNING);
			        alert.initOwner(mainApp.getPrimaryStage());
			        alert.setTitle("Авторизация");
			        alert.setHeaderText("Неверные данные");
			        alert.setContentText("неправильный логин или пароль");
			        alert.showAndWait();
			}
		}catch(SQLException  e){
			System.out.print("username and password is NOT correct(catch)");
			e.printStackTrace();
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("NUll POINTER ");
			e.printStackTrace();
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
