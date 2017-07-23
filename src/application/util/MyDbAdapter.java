package application.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.view.InitTripFormController;
import application.view.ModelEmployee;
import application.view.ShipModel;
import application.view.WatchStage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class MyDbAdapter {

 static Connection connection;
	public static boolean WriteShip(String shipFullName, int typeID) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "INSERT INTO Ship (Name,Type) VALUES (?,?)";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1,shipFullName);
			preparedStatement.setInt(2,typeID);
			preparedStatement.executeUpdate();
			//preparedStatement.close();
			return true;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");
	          return false;
	    }

	}

		public static Exception WriteUser(String fname, String lname, String sname, String country, String language,
				LocalDate bdate, String Post,String salary, String login, String passwd) {
			connection =SqliteConnection.Connector();
			if (connection == null) System.out.println("NOT CONNECTED");
			else System.out.println("DB IS OK");
			try{
		        String SQL = "INSERT INTO Employee (FirstName,LastName,SurName,Post,bdate,Country,Language,login,password,Salary) "
		        		+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

		        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				preparedStatement.setString(1,fname);
				preparedStatement.setString(2,lname);
				preparedStatement.setString(3,sname);
				preparedStatement.setString(4,Post);
				preparedStatement.setString(5,bdate.toString());
				preparedStatement.setString(6,country);
				preparedStatement.setString(7,language);

				preparedStatement.setString(8,login);
				preparedStatement.setString(9,passwd);

				preparedStatement.setDouble(10,Double.parseDouble(salary));


				preparedStatement.executeUpdate();
				return null;
		    }
		    catch(Exception e){
		          e.printStackTrace();
		          System.out.println("Error on Building Data");
		          return e;
		    }

		}


	public static boolean ReadEmployee(List<ModelEmployee> l) {
		// TODO Auto-generated method stub
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.print("NOT CONNECTED");
		else System.out.print("DB IS OK");
	//	data = FXCollections.ObservableList<>();
		try{
	        String SQL = "Select * from Employee";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);


			ResultSet rs=preparedStatement.executeQuery();
			ModelEmployee m=new ModelEmployee();
			while(rs.next()){
				m.setID(rs.getInt("ID"));
				m.setFirstName(rs.getString("FirstName"));
				m.setLastName(rs.getString("LastName"));
				m.setSurName(rs.getString("SurName"));
				m.setPost(rs.getString("Post"));
				m.setCountry(rs.getString("Country"));
				m.setLanguage(rs.getString("Language"));
				l.add(m);
			}
			return true;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return false;
	    }
	}



	public static ObservableList<String> getFioOfFreeEmployeesByPost(String post){
		// TODO Auto-generated method stub
				connection =SqliteConnection.Connector();
				if (connection == null) System.out.println("NOT CONNECTED");
				else System.out.println("DB IS OK");
				try{
			        String SQL = "Select * from Employee where Post like ? AND RelatedShipID=-1";

			        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			        ObservableList<String> list =FXCollections.observableArrayList();
			        preparedStatement.setString(1, post);
					ResultSet rs=preparedStatement.executeQuery();
					//ModelEmployee m=new ModelEmployee();

				//	DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
					DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
					SimpleDateFormat df = new SimpleDateFormat("yyyy");
					Date date;
					while(rs.next()){
						date =format.parse(rs.getString("bdate"));
						list.add(rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+". "+
					df.format(date)+" г. " );
					}
					connection.close();
					preparedStatement.close();
					return  list;
			    }
			    catch(Exception e){
			          e.printStackTrace();
			          System.out.println("Error on Building Data :"+e.getMessage());
			          return null;
			    }


	}

	public static int getIdByFormattedFIOString(String str){
		if(str!=null)
		{
		String s=str.substring(0, str.indexOf(':',0));
			return Integer.parseInt(s);
		}else
			return -1;

	}
	public static int getShipIdFromFormattedName(String str) {
		return Integer.parseInt(str.substring(0, str.indexOf(':')));
	}



/*
	public static void setTripIdToShipByShipId(int shipid,int tripid){



		}



			 catch(Exception e){
		          e.printStackTrace();
		          System.out.println("Error on Building Data :"+e.getMessage());

		    }

	}
*/
	public static void resetEmployeeRelatedShip(int shipid){
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="UPDATE Employee SET RelatedShipID=-1 where RelatedShipID=?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(SQL);
			preparedStatement2.executeUpdate();

			connection.close();
			preparedStatement2.close();

		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());}

  }


	public static Exception CreateTripWatchSchedule(HashMap<String,Integer> hashmap,LocalDate start,LocalDate end,int shipID,int typeID) {

		LocalDate startdate=start;
		LocalDate startdateMechs=start;
		LocalDate enddate=end;

		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{

			String SQLtrip="INSERT INTO Trip (ShipID,StartDate,EndDate,"
					+ "MasterID,"
					+ "ChiefOfficerID,"
					+ "SecondOfficerID,"
					+ "ThirdOfficerID,"
					+ "ChiefEngineerID,"
					+ "SecondEngineerID,"
					+ "ThirdEngineerID,"
					+ "FourthEngineerID,"
					+ "BoatswainID,"
					+ "DonkermanID,"
					+ "ElectroEngineerID,"
					+ "MotormanID,"
					+ "RadioOfficerID,"
					+ "CookID,"
					+ "MedicalOfficerID,"
					+ "ChiefSeamanID,"
					+ "SecondSeamenID,"
					+ "thirdSeamenID,"
					+ "FourthSeamenID,"
					+ "MotormanWelderID,"
					+ "GasEngineerID,"
					+ "PumpmanID,"
					+ "RefrigeratorEngineerID"


					+ ")VALUES "+
					"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement preparedStatement3;// = null;

							preparedStatement3 = connection.prepareStatement(SQLtrip);
							preparedStatement3.setInt(1,shipID);
							preparedStatement3.setString(2,startdate.toString());
							preparedStatement3.setString(3,end.toString());

							preparedStatement3.setInt(4,hashmap.get("masterID"));

							preparedStatement3.setInt(5,hashmap.get("firstOfficerID"));
							preparedStatement3.setInt(6,hashmap.get("secondOfficerID"));
							preparedStatement3.setInt(7,hashmap.get("thirdOfficerID"));

							preparedStatement3.setInt(8,hashmap.get("ChiefEngID"));
							preparedStatement3.setInt(9,hashmap.get("firstEngID"));
							preparedStatement3.setInt(10,hashmap.get("secondEngID"));
							preparedStatement3.setInt(11,hashmap.get("thirdEngID"));

							preparedStatement3.setInt(12,hashmap.get("BoatswainID"));
							preparedStatement3.setInt(13,hashmap.get("DonkermanID"));
							preparedStatement3.setInt(14,hashmap.get("electroEngineerID"));
							preparedStatement3.setInt(15,hashmap.get("motorManID"));
							preparedStatement3.setInt(16,hashmap.get("radioOfficerID"));

							preparedStatement3.setInt(17,hashmap.get("cookID"));
							preparedStatement3.setInt(18,hashmap.get("medicalOfficerID"));

							preparedStatement3.setInt(19,hashmap.get("chiefSeamenID"));
							preparedStatement3.setInt(20,hashmap.get("secondSeamenID"));
							preparedStatement3.setInt(21,hashmap.get("thirdSeamenID"));
							preparedStatement3.setInt(22,hashmap.get("fourthSeamenID"));
							preparedStatement3.setInt(23,hashmap.get("motormanWelderID"));


							preparedStatement3.setInt(24,hashmap.get("gasEngineerID"));
							preparedStatement3.setInt(25,hashmap.get("pumpmanID"));
							preparedStatement3.setInt(26,hashmap.get("refrigeratorEngineerID"));
							preparedStatement3.executeUpdate();

							preparedStatement3.close();

							PreparedStatement preparedStatement2;
							String SQL2=null;
							for(Map.Entry<String, Integer> entry : hashmap.entrySet()) {
								Integer value = entry.getValue();
								SQL2 ="UPDATE Employee SET RelatedShipID=? where ID=?";
								preparedStatement2 = connection.prepareStatement(SQL2);
								preparedStatement2.setInt(1,shipID);
								preparedStatement2.setInt(2,value);
								preparedStatement2.executeUpdate();
							}
							//////////Вывод номера рейса в консоль
							SQL2 ="SELECT ID FROM Trip  ORDER By ID DESC  ";
							preparedStatement2 = connection.prepareStatement(SQL2);

							int tripID=-1;
							ResultSet rs=preparedStatement2.executeQuery();
							if(rs.next()){
								tripID=rs.getInt("ID");

							}



							System.out.println("TRIPID:"+tripID);
							//////////////////////Вывод номера рейса в консоль////////////////////////


				String SQL=null;
				PreparedStatement preparedStatement = null;


				String SQLtemp=null;
				PreparedStatement preparedStatementtemp;
				SQLtemp ="UPDATE Ship SET TripID=? Where ShipID=?";
				preparedStatementtemp = connection.prepareStatement(SQLtemp);
				System.out.println(tripID+"|||||||||||||||||||||"+ shipID);
				preparedStatementtemp.setInt(1,tripID);
				preparedStatementtemp.setInt(2,shipID);
				preparedStatementtemp.executeUpdate();


				/*
				 * Формирование вахт для СУДОВОДОВ(2 или 3 человека\4 через 8 или 6 через 6 часов)
				 */
				if(hashmap.get("thirdOfficerID")!=-1)
				{
				int step=4;
				int[] sudovodi=new int[3];
				sudovodi[0]=hashmap.get("firstOfficerID");
				sudovodi[1]=hashmap.get("secondOfficerID");
				sudovodi[2]=hashmap.get("thirdOfficerID");
				for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
			{

				for(int i=0,j=0;i<24;i+=step)
				{
				SQL ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
				preparedStatement = connection.prepareStatement(SQL);
				preparedStatement.setInt(1,shipID);
				preparedStatement.setInt(2,sudovodi[j]);
				preparedStatement.setInt(3,tripID);
				preparedStatement.setString(4, i +":00");
				preparedStatement.setString(5,(i+4)+":00");
				preparedStatement.setString(6,startdate.toString());

				preparedStatement.executeUpdate();
				if(j>=2)j=0;else j++;
				}

			   System.out.println(startdate);
			}
				startdate=start;
				}else{
					int step=6;
					int[] sudovodi=new int[2];
					sudovodi[0]=hashmap.get("firstOfficerID");
					sudovodi[1]=hashmap.get("secondOfficerID");

					for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
				{

					for(int i=0,j=0;i<24;i+=step)
					{
					SQL ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
					preparedStatement = connection.prepareStatement(SQL);
					preparedStatement.setInt(1,shipID);
					preparedStatement.setInt(2,sudovodi[j]);
					preparedStatement.setInt(3,tripID);
					preparedStatement.setString(4, i +":00");
					preparedStatement.setString(5,(i+6)+":00");
					preparedStatement.setString(6,startdate.toString());

					preparedStatement.executeUpdate();
					if(j>=1)j=0;else j++;
					}

				   System.out.println(startdate);
				}

					startdate=start;
			}


				/*
				 * Формирование вахт для МЕХАНИКОВ(2 или 3 человека\4 через 8 или 6 через 6 часов)
				 */
				String SQLmech=null;
				if(hashmap.get("thirdEngID")!=-1)
				{
				int step=4;
				int[] mechs=new int[3];
				mechs[0]=hashmap.get("firstEngID");
				mechs[1]=hashmap.get("secondEngID");
				mechs[2]=hashmap.get("thirdEngID");
				for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
			{

				for(int i=0,j=0;i<24;i+=step)
				{
					SQLmech ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
				preparedStatement = connection.prepareStatement(SQLmech);
				preparedStatement.setInt(1,shipID);
				preparedStatement.setInt(2,mechs[j]);
				preparedStatement.setInt(3,tripID);
				preparedStatement.setString(4, i +":00");
				preparedStatement.setString(5,(i+4)+":00");
				preparedStatement.setString(6,startdate.toString());

				preparedStatement.executeUpdate();
				if(j>=2)j=0;else j++;
				}


			   System.out.println(startdate);
			}
				startdate=start;
				}else{
					int step=6;
					int[] mechs=new int[2];
					mechs[0]=hashmap.get("firstEngID");
					mechs[1]=hashmap.get("secondEngID");

					for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
				{

					for(int i=0,j=0;i<24;i+=step)
					{
					SQLmech ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
					preparedStatement = connection.prepareStatement(SQLmech);
					preparedStatement.setInt(1,shipID);
					preparedStatement.setInt(2,mechs[j]);
					preparedStatement.setInt(3,tripID);
					preparedStatement.setString(4, i +":00");
					preparedStatement.setString(5,(i+6)+":00");
					preparedStatement.setString(6,startdate.toString());

					preparedStatement.executeUpdate();
					if(j>=1)j=0;else j++;
					}

				   System.out.println(startdate);
				}
					startdate=start;


			}	//preparedStatement.close();

				if(hashmap.get("fourthSeamenID")!=-1)
				{
				int step=4;
				int[] mechs=new int[3];
				mechs[0]=hashmap.get("secondSeamenID");
				mechs[1]=hashmap.get("thirdSeamenID");
				mechs[2]=hashmap.get("fourthSeamenID");
				for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
			{

				for(int i=0,j=0;i<24;i+=step)
				{
					SQLmech ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
				preparedStatement = connection.prepareStatement(SQLmech);
				preparedStatement.setInt(1,shipID);
				preparedStatement.setInt(2,mechs[j]);
				preparedStatement.setInt(3,tripID);
				preparedStatement.setString(4, i +":00");
				preparedStatement.setString(5,(i+4)+":00");
				preparedStatement.setString(6,startdate.toString());

				preparedStatement.executeUpdate();
				if(j>=2)j=0;else j++;
				}


			   System.out.println(startdate);
			}
				startdate=start;
				}else{
					int step=6;
					int[] mechs=new int[2];
					mechs[0]=hashmap.get("secondSeamenID");
					mechs[1]=hashmap.get("thirdSeamenID");

					for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
				{

					for(int i=0,j=0;i<24;i+=step)
					{
					SQLmech ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
					preparedStatement = connection.prepareStatement(SQLmech);
					preparedStatement.setInt(1,shipID);
					preparedStatement.setInt(2,mechs[j]);
					preparedStatement.setInt(3,tripID);
					preparedStatement.setString(4, i +":00");
					preparedStatement.setString(5,(i+6)+":00");
					preparedStatement.setString(6,startdate.toString());

					preparedStatement.executeUpdate();
					if(j>=1)j=0;else j++;
					}

				   System.out.println(startdate);
				}
					startdate=start;


			}	//preparedStatement.close();



				/*
				 *
				 * Графики для тех кто не несет вахт (8:00-20:00)
				 *
				 *
				 *
				 */
				startdate=start;
				String SQLsmall ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
				PreparedStatement statementsmall=null;

					for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1))
				{


System.out.println("NO WATCH:startdate "+startdate+" start:"+start+" enddate:"+enddate);
					SQLsmall ="Insert INTO testSchedule (ShipId,EmployeeID,TripID,startTime,endTime,Date) VALUES (?,?,?,?,?,?) ";
					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("masterID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
				//	statementsmall.close();


					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("ChiefEngID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("BoatswainID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("DonkermanID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("electroEngineerID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("motorManID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					if(hashmap.get("radioOfficerID")!=-1){
					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("radioOfficerID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();
					}

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("cookID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					if(hashmap.get("medicalOfficerID")!=-1){
					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("medicalOfficerID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
				//	statementsmall.close();
					}

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("chiefSeamenID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					if(hashmap.get("motormanWelderID")!=-1){
					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("motormanWelderID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();
					}

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("gasEngineerID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();

					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("pumpmanID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
					//statementsmall.close();


					if(hashmap.get("refrigeratorEngineerID")!=-1){
					statementsmall = connection.prepareStatement(SQLsmall);
					statementsmall.setInt(1,shipID);
					statementsmall.setInt(2,hashmap.get("refrigeratorEngineerID"));
					statementsmall.setInt(3,tripID);
					statementsmall.setString(4, "8" +":00");
					statementsmall.setString(5,"20"+":00");
					statementsmall.setString(6,startdate.toString());
					statementsmall.executeUpdate();
				//	statementsmall.close();
					}

				}


			//	statementsmall.close();
				startdate=start;









				connection.close();
				statementsmall.close();
			return null;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return e;

	    }
	}

	public static int getRelatedShipIdByEmployeeId(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select ShipID from Employee,Ship WHERE ShipID=Employee.RelatedShipID and ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("ShipID");
				connection.close();
				preparedStatement.close();
				return temp;


			}else return -1;

	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return -1;
	    }

}

	public static String getRelatedShipNameByEmployeeId(int currId) {
				connection =SqliteConnection.Connector();
				if (connection == null) System.out.println("NOT CONNECTED");
				else System.out.println("DB IS OK");
				try{
			        String SQL = "Select Ship.Name from Employee,Ship WHERE ShipID=Employee.RelatedShipID and ID=?";

			        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			        //connection.close();
			        preparedStatement.setInt(1,currId);
					ResultSet rs=preparedStatement.executeQuery();
					if(rs.next()){
						String t=rs.getString("Name");
						connection.close();
						return t;


					}else return null;

			    }
			    catch(Exception e){
			          e.printStackTrace();
			          System.out.println("Error on Building Data :"+e.getMessage());
			          return null;
			    }

	}
	public static String getFirstNameByEmployeeId(int currid) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select FirstName from Employee WHERE ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currid);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("FirstName");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return null;


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }finally{}
	}
	public static void deleteTripById(int tripid){
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="DELETE FROM Trip WHERE ID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.executeUpdate();
			preparedStatement.setInt(1,tripid);

			SQL ="UPDATE Employee SET RelatedShipID=-1 where RelatedShipID=? ";
			PreparedStatement preparedStatement2 = connection.prepareStatement(SQL);
			preparedStatement2.executeUpdate();
			preparedStatement2.setInt(1,tripid);

			SQL ="UPDATE Ship SET TripID=-1 where TripID=? ";
			PreparedStatement preparedStatement3 = connection.prepareStatement(SQL);
			preparedStatement3.executeUpdate();
			preparedStatement3.setInt(1,tripid);


		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());}

	}
	public static Exception deleteAllTripsWithSchedules(){

		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="DELETE  FROM Trip";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.executeUpdate();

			SQL ="DELETE FROM testSchedule";
			PreparedStatement preparedStatement2 = connection.prepareStatement(SQL);
			preparedStatement2.executeUpdate();

			SQL ="DELETE FROM MooringSchedule";
			PreparedStatement preparedStatement3 = connection.prepareStatement(SQL);
			preparedStatement3.executeUpdate();

			SQL ="UPDATE Employee SET RelatedShipID=-1";
			PreparedStatement preparedStatement4 = connection.prepareStatement(SQL);
			preparedStatement4.executeUpdate();

			SQL ="UPDATE Ship SET TripID=-1";
			PreparedStatement preparedStatement5 = connection.prepareStatement(SQL);
			preparedStatement5.executeUpdate();

			connection.close();
			return null;

		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());return e;}

	}

	public static int getShipTypeById(int currShipId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Type from Ship WHERE ShipID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currShipId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				int t=rs.getInt("Type");
				connection.close();
				return  t;

			}else return -1;

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return -1;
  }



}

	public static String getShipTypeNameById(int currShipId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName from ShipTypes,Ship WHERE ShipID=? AND TypeId=Type";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currShipId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("AbsoluteName");
				connection.close();
				preparedStatement.close();
				return  t;

			}else{connection.close(); return "не определёно";}

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определен";
		}
	}

	public static String getTripStartDateFromEmployeeID(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Trip.StartDate from Trip,testSchedule WHERE EmployeeID=? AND TripID=Trip.ID";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("StartDate");
				connection.close();
				preparedStatement.close();
				return  t;

			}else{connection.close(); return "не определёно";}

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static String getTripEndDateFromEmployeeID(int currId) {

		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Trip.EndDate from Trip,testSchedule WHERE EmployeeID=? AND TripID=Trip.ID";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("EndDate");
				connection.close();
				preparedStatement.close();
				return  t;

			}else{connection.close(); return "не определёно";}

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static String getTotalCountTripDaysByEmployeeID(int currId) {

		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select  COUNT( DISTINCT testSchedule.Date) from testSchedule,Trip WHERE EmployeeID=? " ;

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("COUNT( DISTINCT testSchedule.Date)");
				connection.close();
				return  t;

			}else{connection.close();preparedStatement.close(); return "не определёно";}

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static String getGoneCountTripDaysByEmployeeID(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select  COUNT( DISTINCT testSchedule.Date) from testSchedule,Trip WHERE EmployeeID=? AND "
	        		+ "testSchedule.Date <= ? ORDER BY testSchedule.Date DESC" ;

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
	        //java.sql.Date date = new java.sql.Date(date.getTime());
	        preparedStatement.setString(2,Main.dateFormat.format(Main.currentDate.getTime()));
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("COUNT( DISTINCT testSchedule.Date)");
				connection.close();
				preparedStatement.close();
				return  t;

			}else{connection.close(); return "не определёно";}

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static String getRemainedCountTripDaysByEmployeeID(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select  COUNT( DISTINCT testSchedule.Date) from testSchedule,Trip WHERE EmployeeID=? AND "
	        		+ "testSchedule.Date > ? ORDER BY testSchedule.Date DESC" ;

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
	        //java.sql.Date date = new java.sql.Date(date.getTime());
	        preparedStatement.setString(2,Main.dateFormat.format(Main.currentDate.getTime()));
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("COUNT( DISTINCT testSchedule.Date)");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return "не определёно";

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static String getCheckedCountTripDaysByEmployeeID(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select  COUNT( DISTINCT testSchedule.Date) from testSchedule,Trip WHERE EmployeeID=? AND "
	        		+ "Completed=1 ORDER BY testSchedule.Date DESC" ;

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
	        //java.sql.Date date = new java.sql.Date(date.getTime());
	     //   preparedStatement.setString(2,Main.dateFormat.format(Main.currentDate.getTime()));
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("COUNT( DISTINCT testSchedule.Date)");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return "не определёно";

	} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
		}
	}

	public static Exception WriteCheckedDaysToEmployee(ObservableList<WatchStage> commonScheduleList, int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
			String SQL;
			PreparedStatement preparedStatement = null;
			for(WatchStage w : commonScheduleList)
			{
			//"INSERT INTO Ship (Name,Type) VALUES (?,?)";
			SQL ="UPDATE testSchedule SET Completed=? where EmployeeID=? AND Date LIKE ?";
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setBoolean(1,w.getChecked().get());
			preparedStatement.setInt(2,currId);
			preparedStatement.setString(3,w.getWatchDate().get());
			preparedStatement.executeUpdate();
			}
			connection.close();
			preparedStatement.close();
			return null;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");
	          return e;
	    }
	}

	public static ObservableList<String> getFomattedEmployeeStringsByShipID(int relatedShipId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Employee.ID,FirstName,LastName,SurName,bdate,Post from Employee,Trip where  RelatedShipID=? AND ShipId=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
	        preparedStatement.setInt(1, relatedShipId);
	        preparedStatement.setInt(2, relatedShipId);
			ResultSet rs=preparedStatement.executeQuery();


			//DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			//SimpleDateFormat df = new SimpleDateFormat("yyyy");
			//Date date;
			while(rs.next()){
				//date =format.parse(rs.getString("bdate"));
				list.add(rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
						" , "+rs.getString("Post"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }



	}

	public static ObservableList<String> ReadAllMooringStatuses() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM MooringStatus";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()){

				list.add(rs.getString("AbsoluteName"));
			}
			connection.close();
			preparedStatement.close();

			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static int getTypeIdByAbsName(String value) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select StatusID FROM MooringStatus where AbsoluteName like ?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setString(1, value);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("StatusID");
				connection.close();
				preparedStatement.close();
				return temp;
			}


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return -1;
	    }
		return -1;
	}

	public static int getShipTypeIdByAbsName(String value) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select TypeId FROM ShipTypes where AbsoluteName like ?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setString(1, value);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("TypeId");
				connection.close();
				preparedStatement.close();
				return temp;
			}


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return -1;
	    }
		return -1;
	}

	public static Exception WriteMooring(String portName,int tripID, LocalDate start, String starttime, LocalDate end, String endtime,
			int responcibleID, int typeID) {
		connection =SqliteConnection.Connector();

		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "INSERT INTO MooringSchedule (Portname,TripID,startDate,endDate,ResponsibleEmployeeID,StatusID)"
	        		+ " VALUES (?,?,?,?,?,?)";
	        PreparedStatement preparedStatement=null;
	        preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1,portName);
			preparedStatement.setInt(2,tripID);
			preparedStatement.setString(3,start.toString()+" "+starttime);
			preparedStatement.setString(4,end.toString()+" "+endtime);
			preparedStatement.setInt(5,responcibleID);
			preparedStatement.setInt(6,typeID);
			preparedStatement.executeUpdate();
			connection.close();
			preparedStatement.close();
			return null;

	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data");
	          return e;
	    }
	}

	public static int getTripIDByEmployeeID(int employeeId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Trip.ID from Employee,Trip WHERE Trip.ShipID=RelatedShipID AND Employee.ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,employeeId);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("ID");
				connection.close();
				preparedStatement.close();
				return temp;


			}else {connection.close();return -1;}
	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data");

        return -1;

		}
	}


	public static String getTypeAbsNameById(int int1) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM MooringStatus where StatusID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setInt(1, int1);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				String temp=rs.getString("AbsoluteName");
				connection.close();
				preparedStatement.close();
				return temp;
			}else return "не определено";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
  }
}

	public static String getFormattedFioOfEmployeeByEmployeeId(int int1) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select ID,FirstName,LastName,SurName from Employee WHERE ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,int1);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0);
				connection.close();
				preparedStatement.close();
				return  t;

			}else return "не определено";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
					}

	}


	public static Exception DeleteShipById(int shipId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="DELETE FROM Ship WHERE ShipID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1,shipId);
			preparedStatement.executeUpdate();


			SQL ="UPDATE Employee SET RelatedShipID=-1 where RelatedShipID=? ";
			PreparedStatement preparedStatement2 = connection.prepareStatement(SQL);
			preparedStatement2.setInt(1,shipId);
			preparedStatement2.executeUpdate();


			SQL ="Delete FROM Trip where ShipID=?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(SQL);
			preparedStatement3.setInt(1,shipId);
			preparedStatement3.executeUpdate();

			connection.close();
			preparedStatement.close();
			preparedStatement2.close();
			preparedStatement3.close();
			return null;

		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());return e;}
	}

	public static String getShipTypeAbsNameById(int int1) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM ShipTypes where TypeId=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setInt(1, int1);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				String temp=rs.getString("AbsoluteName");
				connection.close();
				preparedStatement.close();
				return temp;
			}else return "не определено";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
  }
}

	public static String getShipStatusAbsNameByStatusId(int int1) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM ShipStatus where StatusID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setInt(1, int1);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				String temp=rs.getString("AbsoluteName");
				connection.close();
				preparedStatement.close();
				return temp;
			}else return "не определено";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
	}


	}

	public static ObservableList<String> ReadAllShipStatuses() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM ShipStatus";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()){

				list.add(rs.getString("AbsoluteName"));
			}
			connection.close();
			preparedStatement.close();

			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static ObservableList<String> ReadAllShipTypes() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select AbsoluteName FROM ShipTypes";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()){

				list.add(rs.getString("AbsoluteName"));
			}
			connection.close();
			preparedStatement.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static String getShipNameById(int shipID) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Name FROM Ship where ShipID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setInt(1, shipID);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				String temp=rs.getString("Name");
				connection.close();
				preparedStatement.close();
				return temp;
			}else return "отсутствует";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
  }
	}

	public static String getPostNameByEmployeeID(int currid) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Post from Employee WHERE ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currid);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("Post");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return null;


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static List<String> getWorkHoursByEmployeeIdAndDate(int currId, String formattedCurrDate) {
		List<String> list=new ArrayList<String>();

		connection =SqliteConnection.Connector();

		try{

			String SQL2;
			PreparedStatement preparedStatement2 = null;
				SQL2 = "select startTime,endTime FROM testSchedule WHERE EmployeeID=? AND Date like ?" ;
	        	  preparedStatement2 = connection.prepareStatement(SQL2);
	        	  preparedStatement2.setInt(1,currId);
	        	  preparedStatement2.setString(2,formattedCurrDate);
	        	  ResultSet rs2=preparedStatement2.executeQuery();
	        	  List<String> temp1=new ArrayList<String>();
	        	  List<String> temp2=new ArrayList<String>();
	        	 // List<String> temp2= new ArrayList<String>();
				  while(rs2.next()){
					  temp1.add(rs2.getString("startTime"));
					  temp1.add(rs2.getString("endTime"));

					  temp2.add(rs2.getString("startTime")+"-"+rs2.getString("endTime"));

					  }//temp1.clear();

				 for (int j = 0; j < temp1.size(); j++)
				 {
				//	System.out.println(temp1.get(j));
				//	System.out.println(temp1.get(j+1));
try{
					System.out.println(temp2.get(j).substring(0,temp2.get(j).indexOf('-')));
					System.out.println(temp2.get(j).substring(temp2.get(j).indexOf('-')+1,temp2.get(j).length()));

}catch(Exception e){}










					}

				 boolean tempbool=false;
				  for(int i=0;i<24;i++)
				  {
					  tempbool=false;
					  for (int j = 0; j < temp2.size(); j++)
					  {

						  try{
						if( MyProverka(i,temp2.get(j).substring(0,temp2.get(j).indexOf('-')),
								temp2.get(j).substring(temp2.get(j).indexOf('-')+1,temp2.get(j).length()))){
							list.add("✔");
						//	System.out.println(temp1.get(j));
								 // System.out.println("MyProverka "+i+":"+list.get(i)+"TEMP SIZE:"+temp1.size()+"CURRID:"+currId);
							// System.out.println("i:"+i+"  j: "+j+"  TEMP SIZE:"+temp1.size()+" CURRID:"+currId+"    temp1.get(j)"+temp1.get(j));
							tempbool=true;
								break;

						}//else{list.add(" ");}


						  }catch(Exception e){System.out.println(e.getMessage());}
						//else continue;//list.add(" ");
					//	  System.out.println("MyProverka "+i+"  CURRID:"+currId);


						//r if(j==temp1.size())
						//  break;
					 }

					  if(!tempbool)
					  list.add(" ");
					}

				  connection.close();
				  preparedStatement2.close();

			}catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	    }

		return list;
	}

	public static boolean MyProverka(int val, String str,String str2){

		int a1=Integer.parseInt(str.substring(0, str.indexOf(':')));
		int a2=Integer.parseInt(str2.substring(0, str2.indexOf(':')));
		//System.out.println("a1=Integer.parseInt:"+a1+"  a2=Integer.parseInt:"+a2+" ");

		if(val>=a1 && val<a2){return true;}else return false;

	}

	public static Exception DeleteMooringById(int i) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="DELETE FROM MooringSchedule WHERE MooringID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1,i);
			preparedStatement.executeUpdate();

			return null;

		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());return e;}
	}

	public static ObservableList<String> ReadAllPosts() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select DISTINCT Post FROM Employee";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()){

				list.add(rs.getString("Post"));
			}
			connection.close();
			preparedStatement.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static Exception DeleteUserById(int i) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		try{
			String SQL ="DELETE FROM Employee WHERE ID=?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1,i);
			preparedStatement.executeUpdate();


			connection.close();
			preparedStatement.close();

			return null;

		} catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());return e;}
	}

	public static ObservableList<String> ReadAllEmployeeAsFormattedString() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select * from Employee";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();


			DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
		//	Date date;
			while(rs.next()){
				//date =format.parse(rs.getString("bdate"));
				list.add(rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
						" , "+rs.getString("Post"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static ObservableList<String> ReadAllTripsAsFormattedString() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Trip.ID,MasterID,Name,FirstName,LastName,SurName,StartDate,EndDate from Trip,Employee,Ship where Employee.ID=MasterID AND "
	        		+ "Ship.ShipID=Trip.ShipID";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();


			DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
		//	Date date;
			while(rs.next()){
				//date =format.parse(rs.getString("bdate"));
				list.add(rs.getInt("ID")+": "+rs.getString("StartDate")+"|"+rs.getString("EndDate")+", Капитан:"
				+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
				", судно:"+rs.getString("Name"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static ObservableList<String> getFomattedEmployeeStringsByTripID(int i) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Employee.ID,FirstName,LastName,SurName,"
	        		+ "bdate,Post from Employee,Trip,Ship WHERE  "
	        		+ "Trip.ID=? AND Employee.RelatedShipID=Ship.ShipID AND Trip.ShipID=Ship.ShipID AND ( Employee.ID=MasterID ||"
	        		+ "Employee.ID=ChiefOfficerID ||"
	        		+ "Employee.ID=SecondOfficerID ||"
	        		+ "Employee.ID=ThirdOfficerID ||"
	        		+ "Employee.ID=ChiefEngineerID ||"
	        		+ "Employee.ID=SecondEngineerID ||"
	        		+ "Employee.ID=ThirdEngineerID ||"
	        		+ "Employee.ID=FourthEngineerID ||"
	        		+ "Employee.ID=BoatswainID ||"
	        		+ "Employee.ID=DonkermanID ||"
	        		+ "Employee.ID=ElectroEngineerID ||"
	        		+ "Employee.ID=MotormanID ||"
	        		+ "Employee.ID=RadioOfficerID ||"
	        		+ "Employee.ID=CookID ||"
	        		+ "Employee.ID=MedicalOfficerID ||"
	        		+ "Employee.ID=ChiefSeamanID ||"
	        		+ "Employee.ID=SecondSeamenID ||"
	        		+ "Employee.ID=ThirdSeamenID ||"
	        		+ "Employee.ID=FourthSeamenID ||"
	        		+ "Employee.ID=MotormanWelderID ||"
	        		+ "Employee.ID=GasEngineerID ||"
	        		+ "Employee.ID=PumpmanID ||"
	        		+ "Employee.ID=GasEngineerID ||"
	        		+ "Employee.ID=RefrigeratorEngineerID "
	        		+ " )";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
	        preparedStatement.setInt(1, i);
	   //     preparedStatement.setInt(2, relatedShipId);
			ResultSet rs=preparedStatement.executeQuery();


			DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			//Date date;
			while(rs.next()){
				//date =format.parse(rs.getString("bdate"));
				list.add(rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
						" , "+rs.getString("Post"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static ObservableList<String> readAllShipsAsFormattedString() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select * from Ship";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();


			//DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			//SimpleDateFormat df = new SimpleDateFormat("yyyy");
		//	Date date;
			while(rs.next()){
				//date =format.parse(rs.getString("bdate"));
				list.add(rs.getInt("ShipID")+": "+rs.getString("Name"));//;+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
					//	" , "+rs.getString("Post"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static int getTripIDByShipID(int currShipId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Trip.ID from Ship,Trip WHERE Trip.ShipID=Ship.ShipID AND Ship.ShipID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currShipId);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("ID");
				connection.close();
				preparedStatement.close();
				return temp;


			}else {connection.close();return -1;}
	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data");

        return -1;

	}
}

	public static ObservableList<String> readUsedShipsAsFormattedString() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select * from Ship Where TripID >=0";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();

			while(rs.next()){
				list.add(rs.getInt("ShipID")+": "+rs.getString("Name"));//;+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+

			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static String getFullNameByEmployeeId(int currId) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select FirstName,LastName,SurName from Employee WHERE ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				String t=rs.getString("LastName")+" "+rs.getString("FirstName")+" "+rs.getString("SurName");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return "не определено";

	}catch(Exception e){
        e.printStackTrace();
        System.out.println("Error on Building Data :"+e.getMessage());
        return "не определено";
					}

	}

	public static ObservableList<String> ReadAllEmployeeAsFormattedStringOnShips() {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select * from Employee WHERE ID<>0 AND RelatedShipID>=0";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        ObservableList<String> list =FXCollections.observableArrayList();
			ResultSet rs=preparedStatement.executeQuery();


			while(rs.next()){
				list.add(rs.getInt("ID")+": "+rs.getString("LastName")+" "+rs.getString("FirstName").charAt(0)+"."+rs.getString("SurName").charAt(0)+
						" , "+rs.getString("Post"));
			}
			preparedStatement.close();
			connection.close();
			return  list;
	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return null;
	    }
	}

	public static int getStavkaByEmployeeId(int currId) {
		connection =SqliteConnection.Connector();
		//if (connection == null) System.out.println("NOT CONNECTED");
	//	else System.out.println("DB IS OK");
		try{
	        String SQL = "Select Salary from Employee WHERE ID=?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

	        preparedStatement.setInt(1,currId);
			ResultSet rs=preparedStatement.executeQuery();

			if(rs.next()){
				int t=rs.getInt("Salary");
				connection.close();
				preparedStatement.close();
				return  t;

			}else return 0;


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return 0;
	    }
	}

	public static Exception EditMooringById(IntegerProperty mooringID, String portName, int tripID, LocalDate startDate,
			String startTime, LocalDate endDate, String endTime, int responsibleID, int statusID) {
		connection =SqliteConnection.Connector();
		try{
			String SQLtemp=null;

			SQLtemp ="UPDATE MooringSchedule SET PortName=?,TripID=?,StartDate=?,endDate=?,ResponsibleEmployeeID=?,"
					+ "StatusID=? "
					+ "Where MooringID=?";
			PreparedStatement preparedStatementtemp = connection.prepareStatement(SQLtemp);
		//	System.out.println(tripID+"|||||||||||||||||||||"+ shipID);
			preparedStatementtemp.setString(1,portName);
			preparedStatementtemp.setInt(2,tripID);
			preparedStatementtemp.setString(3,startDate.toString()+" "+startTime);
			preparedStatementtemp.setString(4,endDate.toString()+" "+endTime);
			preparedStatementtemp.setInt(5,responsibleID);
			preparedStatementtemp.setInt(6,statusID);
			preparedStatementtemp.setInt(7,mooringID.get());
			preparedStatementtemp.executeUpdate();


			//  Alert alert = new Alert(AlertType.WARNING);
		     //   alert.initOwner(mainApp.getPrimaryStage());
		     //   alert.setTitle("MurID:"+mooringID);
		       // alert.setHeaderText("Ни один елемент не выбран");
		      //  alert.setContentText("Пожалуйста выберите елемент для редактирования.");
		    //    alert.show();
			preparedStatementtemp.close();
			connection.close();
			return null;

		}
		  catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());

	          return e;
	    }
	}

	public static Exception EditShip(int i, String text, int typeID) {
		connection =SqliteConnection.Connector();
		try{
			String SQLtemp=null;

			SQLtemp ="UPDATE Ship SET Name=?,Type=? "
					+ "Where ShipID=?";
			PreparedStatement preparedStatementtemp = connection.prepareStatement(SQLtemp);
		//	System.out.println(tripID+"|||||||||||||||||||||"+ shipID);
			preparedStatementtemp.setString(1,text);
			preparedStatementtemp.setInt(2,typeID);

			preparedStatementtemp.setInt(3,i);

			preparedStatementtemp.executeUpdate();


			//  Alert alert = new Alert(AlertType.WARNING);
		     //   alert.initOwner(mainApp.getPrimaryStage());
		     //   alert.setTitle("MurID:"+mooringID);
		       // alert.setHeaderText("Ни один елемент не выбран");
		      //  alert.setContentText("Пожалуйста выберите елемент для редактирования.");
		    //    alert.show();
			preparedStatementtemp.close();
			connection.close();
			return null;

		}
		  catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());

	          return e;
	    }
	}

	public static int getStatusIdByAbsName(String value) {
		connection =SqliteConnection.Connector();
		if (connection == null) System.out.println("NOT CONNECTED");
		else System.out.println("DB IS OK");
		try{
	        String SQL = "Select StatusID FROM ShipStatus where AbsoluteName like ?";

	        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
	        preparedStatement.setString(1, value);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()){
				int temp=rs.getInt("StatusID");
				connection.close();
				preparedStatement.close();
				return temp;
			}


	    }
	    catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());
	          return -1;
	    }
		return -1;
	}

	public static Exception EditUser(int id, String fname, String lname, String sname, String country, String lang,
			LocalDate bdate, String post, String salary, String log, String passwd) {

			connection =SqliteConnection.Connector();
			try{
				String SQLtemp=null;

				SQLtemp ="UPDATE Employee SET FirstName=?,LastName=?,SurName=?,Country=?,Language=?,"
						+ "bdate=? ,Post=?,Salary=?,login=?,password=?"
						+ "Where ID=?";
				PreparedStatement preparedStatementtemp = connection.prepareStatement(SQLtemp);

				preparedStatementtemp.setString(1,fname);
				preparedStatementtemp.setString(2,lname);
				preparedStatementtemp.setString(3,sname);
				preparedStatementtemp.setString(4,country);
				preparedStatementtemp.setString(5,lang);
				preparedStatementtemp.setString(6,bdate.toString());
				preparedStatementtemp.setString(7,post);
				preparedStatementtemp.setDouble(8,Double.parseDouble(salary));
				preparedStatementtemp.setString(9,log);
				preparedStatementtemp.setString(10,passwd);
				preparedStatementtemp.setInt(11,id);
				preparedStatementtemp.executeUpdate();


				//  Alert alert = new Alert(AlertType.WARNING);
			     //   alert.initOwner(mainApp.getPrimaryStage());
			     //   alert.setTitle("MurID:"+mooringID);
			       // alert.setHeaderText("Ни один елемент не выбран");
			      //  alert.setContentText("Пожалуйста выберите елемент для редактирования.");
			    //    alert.show();
				preparedStatementtemp.close();
				connection.close();
				return null;

			}
			  catch(Exception e){
		          e.printStackTrace();
		          System.out.println("Error on Building Data :"+e.getMessage());

		          return e;
		    }
	}

	public static Exception setMasterPwd(String pwd) {
			connection =SqliteConnection.Connector();
			try{
				String SQLtemp=null;

				SQLtemp ="UPDATE Employee SET password=?"
						+ "Where ID=0";
				PreparedStatement preparedStatementtemp = connection.prepareStatement(SQLtemp);

				preparedStatementtemp.setString(1,pwd);
				preparedStatementtemp.executeUpdate();
				preparedStatementtemp.close();
				connection.close();
				return null;

			}
			  catch(Exception e){
		          e.printStackTrace();
		          System.out.println("Error on Building Data :"+e.getMessage());

		          return e;
		    }
	}

	public static String getMasterPwd() {
		connection =SqliteConnection.Connector();
		String temp=null;
		try{
			String SQLtemp=null;

			SQLtemp ="Select password from Employee Where ID=0";
			PreparedStatement preparedStatementtemp = connection.prepareStatement(SQLtemp);
			ResultSet rs=preparedStatementtemp.executeQuery();
			if(rs.next()){
				 temp=rs.getString("password");

			}
			preparedStatementtemp.close();
			connection.close();
			return temp;

		}
		  catch(Exception e){
	          e.printStackTrace();
	          System.out.println("Error on Building Data :"+e.getMessage());

	          return temp;
	    }
	}
}


