package application.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
	public static Connection Connector(){

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "/ScheduleDB");

			System.out.println(System.getProperty("user.dir") + "/cheduleDB FFFFFFFFFFFFFFFFF");
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}


	}
}
