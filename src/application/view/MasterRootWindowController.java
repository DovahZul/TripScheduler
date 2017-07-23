package application.view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.util.MyDbAdapter;
import application.util.SqliteConnection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MasterRootWindowController implements Initializable{

	 @FXML PasswordField oldpwd;
	 @FXML PasswordField newpwd;
	 @FXML PasswordField newpwd2;
	 @FXML CheckBox showchar;

	 @FXML
		private Button closeBtn;
		@FXML
		private Button addBtn;

	 private Main mainApp;
	 public void setMainApp(Main main) {
			// TODO Auto-generated method stub
			this.mainApp=main;

			}
	@Override
	public void initialize(URL url, ResourceBundle rb) {



}
/*
	@FXML
	private void handleCheck(){
		//if(showchar.isSelected()){

		//	newpwd.set

		/}else{

		}

	}*/
	@FXML
	private void handleAdd(){

		boolean correctinput=true;
		String errorptomp="";
		String str="";
		Pattern r;
		Matcher m;




		str ="^[a-zA-Z][a-zA-Z0-9-_\\.]{4,20}$";//"(^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
		String pain=MyDbAdapter.getMasterPwd();

		if(!oldpwd.getText().equals(pain))
		{correctinput=false; errorptomp="не верный пароль";}
System.out.println(pain);
		  if(!newpwd.getText().equals(newpwd2.getText()))

			{correctinput=false; errorptomp="пароли не совпадают";}

		if(oldpwd.getText()==null||newpwd.getText()==null||newpwd2.getText()==null)

		{correctinput=false; errorptomp="поля не заполенены";}






		if(correctinput){

		Exception bool=MyDbAdapter.setMasterPwd(newpwd.getText());
		if(bool==null){

			Alert a=new Alert(AlertType.INFORMATION);
			a.setTitle("Сообщение");
			a.setHeaderText("Выполнено");
			a.setContentText("пароль изменён");
			a.showAndWait();
			handleClose();

		}else{
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("Пароль не изменён");
			a.setContentText("код:"+bool.getLocalizedMessage());
			a.showAndWait();
			handleClose();
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

