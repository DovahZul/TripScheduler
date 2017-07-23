package application.util;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginModel {
	Connection connection;
	public LoginModel ()  {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");
	}
	public boolean isDbConnected(){
		try{
			return !connection.isClosed();
		}catch(Exception e){
			e.printStackTrace();
			Alert a=new Alert(AlertType.ERROR);
			a.setTitle("Ошибка");
			a.setHeaderText("База данных отсутствует?");
			a.setContentText(System.getProperty("user.dir") + "/ScheduleDB");
			a.setResizable(true);
			a.showAndWait();
			return false;
		}

	}
	public int isLogin(String user,String pass)throws SQLException{
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query = "select * from Employee where login=? and password=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,user);
			preparedStatement.setString(2,pass);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return resultSet.getInt("ID");
			}else{
				return -1;
			}

		}catch(Exception e){
			return -1;
		}finally{
			preparedStatement.close();
			resultSet.close();
		}
	}
	public String getFioByID(int value){
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query = "select FirstName,SurName from Employee where ID=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,value);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return new String(resultSet.getString("FirstName")+" "+resultSet.getString("SurName"));
			}else{
				return null;
			}


			//preparedStatement.close();
		//	resultSet.close();
		}catch(Exception e){System.out.print(e.getMessage());return null;}
	}

}
