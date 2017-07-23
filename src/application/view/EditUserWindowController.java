package application.view;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.util.MyDbAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserWindowController  implements Initializable{

	Connection connection;
	private Main mainApp;
	private Stage primaryStage;
	private UserInfoController parentController;
	private ModelUser node;

	@FXML
	private TextField userFirstName;

	@FXML
	private TextField userLastName;

	@FXML
	private TextField userSurName;

	@FXML
	private TextField userContry;

	@FXML
	private TextField userLanguage;

	@FXML
	private DatePicker userBdate;

	@FXML
	private ComboBox<String> userPost;

	@FXML
	private TextField userSalary;

	@FXML
	private TextField userLogin;
	@FXML
	private TextField userPasswd;

	@FXML
	private Button addBtn;

	@FXML
	private Button closeBtn;

	public void setElement(ModelUser modelUser){
	node=modelUser;
	}


	public void setMainApp(UserInfoController main) {
		// TODO Auto-generated method stub
		this.parentController=main;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		userBdate.setValue(LocalDate.now());//setdMain.dateFormat.format(Main.currentDate.getTime());
		userPost.getItems().addAll(MyDbAdapter.ReadAllPosts());
		System.out.println(userPost.getValue());


	}

	protected void fillNode(ModelUser modelUser){

		userFirstName.setText(modelUser.userFirstName.get());
		userLastName.setText(modelUser.userLastName.get());
		userSurName.setText(modelUser.userSurName.get());
		userContry.setText(modelUser.userCountry.get());
		userLanguage.setText(modelUser.userLanguage.get());
		userBdate.setValue( LocalDate.parse(modelUser.userBirthday.get()));
		userPost.setValue(modelUser.userPost.get());
		userSalary.setText(Double.toString(modelUser.getSalary().get()));

		userLogin.setText(modelUser.login.get());
		userPasswd.setText(modelUser.password.get());

	}


	@FXML
	private void handleAdd(){
		//Alert aa=new Alert(AlertType.ERROR);
		//aa.setTitle(node.userId.get()+"");
		//aa.show();
		boolean correctinput=true;
		String errorptomp="";
		String str="";
		Pattern r;
		Matcher m;

		  str ="^[a-zA-Z][a-zA-Z0-9-_\\.]{4,20}$";//"(^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
		  r = Pattern.compile(str);
		  m = r.matcher(userPasswd.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректный пароль";}

		  str ="^[a-zA-Z][a-zA-Z0-9-_\\.]{4,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(userLogin.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректный логин";}

		  str ="^[1-9][0-9]{1,5}$";
		  r = Pattern.compile(str);
		  m = r.matcher(userSalary.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректная оплата";}

		  if(userPost.getValue()==null)
			{correctinput=false; errorptomp="Укажите должность";}

			//if(userBdate.getValue()==null);
			//{correctinput=false; errorptomp="укажите дату рождения";}


		  str ="^[а-яА-ЯёЁa-zA-Z]+$";
		  r = Pattern.compile(str);
		  m = r.matcher(userLanguage.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректный язык";}

		  str ="^[а-яА-ЯёЁa-zA-Z]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(userContry.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректная страна";}

		  str ="^[а-яА-ЯёЁa-zA-Z]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(userSurName.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное отчество";}

		  str ="^[а-яА-ЯёЁa-zA-Z]{1,20}$";
		  r = Pattern.compile(str);
		  m = r.matcher(userFirstName.getText());
		  if (!m.find( )){ correctinput=false; errorptomp="Некорректное имя";}


		  str ="^[а-яА-ЯёЁa-zA-Z]+$";
		  r = Pattern.compile(str);
		  m = r.matcher(userLastName.getText());
	      if (!m.find( ))
	      { correctinput=false; errorptomp="Некорректная фамилия";}


	//	if(!userLastName.getText().matches("^[^\\d].*"));//("@^[а-яА-ЯёЁa-zA-Z]+$"));
	//	{correctinput=false; errorptomp="Неккоректная фамилия";}


		if(correctinput){


		Exception bool=MyDbAdapter.EditUser(node.userId.get(),userFirstName.getText(),userLastName.getText(),userSurName.getText(),userContry.getText(),
				userLanguage.getText(),userBdate.getValue(),userPost.getValue() ,userSalary.getText(),userLogin.getText(),userPasswd.getText());
		if(bool==null){

			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("запись изменена.");
			a.showAndWait();
			parentController.Fill();
			handleClose();

		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("изменение не удалось");
			a.setContentText(bool.getMessage());
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


//if(!userPasswd.getText().matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"));
	//{correctinput=false; errorptomp="Неккоректный пароль";}

//	if(!userLogin.getText().matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{4,20}$"));
//	{correctinput=false; errorptomp="Неккоректный логин";}



//	if(!userLanguage.getText().matches("^[а-яА-ЯёЁa-zA-Z]+$"));
//	{correctinput=false; errorptomp="Неккоректный язык";}

//	if(!userContry.getText().matches("^[а-яА-ЯёЁa-zA-Z]{1,20}$"));
//	{correctinput=false; errorptomp="Неккоректная страна";}

//	if(!userSurName.getText().matches("^[а-яА-ЯёЁa-zA-Z]{1,20}$"));
//	{correctinput=false; errorptomp="Неккоректное отчество";}

//	if(!userFirstName.getText().matches("^[а-яА-ЯёЁa-zA-Z]{1,20}$"));
//	{correctinput=false; errorptomp="Неккоректное имя";}
