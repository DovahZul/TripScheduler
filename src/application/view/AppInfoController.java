package application.view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AppInfoController implements Initializable{

	 @FXML TextArea mainTextArea;
	 @FXML WebView mainTextAreaWeb;

	 private Main mainApp;
	 public void setMainApp(Main main) {
			// TODO Auto-generated method stub
			this.mainApp=main;
			mainTextArea.setEditable(false);

			}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	    try {
	    	//mainTextArea.set
	        Scanner s = new Scanner(new File(System.getProperty("user.dir")+"/resources/AppInfo.txt"));//.useDelimiter("\\s+");
	        while (s.hasNext()) {
	            if (s.hasNext()) { // check if next token is an int
	            	mainTextArea.appendText(s.nextLine() + "\n"); // display the found integer
	            } else {
	            	mainTextArea.appendText(s.next() + "\n"); // else read the next token
	            }



	        }
	    } catch (Exception ex) {
	    	Alert a2=new Alert(AlertType.ERROR);
			a2.setTitle("Ошибка");
			a2.setHeaderText("Внутренняя ошибка");
			a2.setContentText(ex.getLocalizedMessage()+"");
			a2.showAndWait();
			this.mainApp.InitSchedules();
	        System.err.println(ex);
	    }
	}
}


