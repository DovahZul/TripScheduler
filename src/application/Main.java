package application;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import application.util.MyDbAdapter;
import application.view.AppInfoController;
import application.view.InitMooringWindowController;
import application.view.InitShipWindowController;
import application.view.InitTripFormController;
import application.view.InitUserWindowController;
import application.view.LoginController;
import application.view.MasterRootWindowController;
import application.view.MoneyWindowController;
import application.view.MyXontactController;
import application.view.RootController;
import application.view.SchedulesController;
import application.view.ShipInfoController;
import application.view.UserInfoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootWindow;
	private AnchorPane loginWindow;
	private static int currId=-1;
	private static int currTripID=-1;
	private static int currShipID=-1;
	private static int currFackedId=-1;
	//public Connection connection;
	private Connection connection;
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static Calendar currentDate=Calendar.getInstance();




	public static int getCurrId(){
		return currId;
	}
	public static void setCurrId( int id){
		currId=id;
		//System.out.println("АЙДИ ЮЗЕРА ПОСТАВЛЕНО АЙАЙАЙАЙАЙ " + id+ " а НАДА " + currId);
	}

	public static int getCurrFakedId(){
		return currFackedId;
	}
	public static void setCurrFakedId( int id){
		currFackedId=id;
		//System.out.println("АЙДИ ЮЗЕРА ПОСТАВЛЕНО АЙАЙАЙАЙАЙ " + id+ " а НАДА " + currId);
	}





	public void setCurrTripId(int i){
		this.currTripID=i;
	}




	public static int getCurrTripId(){
		return currTripID;
	}


	public static int getCurrShipId(){
		return currShipID;
	}
	public static void setCurrShipId( int id){
		currShipID=id;
		System.out.println("АЙДИ КОРАБЛЯ ПОСТАВЛЕНО АЙАЙАЙАЙАЙ " + id+ " а НАДА " + currShipID);
	}

	public static void main(String[] args) {launch(args);}
	public Stage getPrimaryStage() {return primaryStage;}


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		initLogin();

		System.out.println("YEAH:!+"+currId);

		}

	public void initLogin() {
		try {
	        // Загружаем корневой макет из fxml файла.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class
	                .getResource("/application/view/Login.fxml"));
	        loginWindow = (AnchorPane) loader.load();

	        // Отображаем сцену, содержащую корневой макет.
	        Scene scene = new Scene(loginWindow);
	        primaryStage.setScene(scene);

	        // Даём контроллеру доступ к главному приложению.
	        LoginController controller = loader.getController();
	        //if(this!=null)
	        controller.setMainApp(this);
	       // if(currId==0)primaryStage.setTitle("Главное окно - Администратор");
	        primaryStage.setWidth(360);
	        primaryStage.setHeight(320);
	        primaryStage.setResizable(false);
	        primaryStage.setMinHeight(320);
	        primaryStage.setMinWidth(360);
	        primaryStage.setTitle("Авторизация");
	        primaryStage.show();
	    } catch (NullPointerException | IOException e ) {
	        e.printStackTrace();
	    }

	}

	public void initRootWindow(){
		try {
	        // Загружаем корневой макет из fxml файла.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class
	                .getResource("/application/view/RootWindow.fxml"));
	        rootWindow = (BorderPane) loader.load();

	        // Отображаем сцену, содержащую корневой макет.
	        Scene scene = new Scene(rootWindow);
	        primaryStage.setScene(scene);

	        // Даём контроллеру доступ к главному приложению.
	        RootController controller = loader.getController();
	        //if(this!=null)
	        controller.setMainApp(this);
	        if(Main.getCurrId()==0)primaryStage.setTitle("Главное окно - Администратор");
	        else primaryStage.setTitle("Главное окно - "+MyDbAdapter.getFullNameByEmployeeId(Main.getCurrId()));
	        primaryStage.setWidth(783);
	        primaryStage.setHeight(593);
	        primaryStage.setResizable(true);
	        primaryStage.show();
	        primaryStage.setMinHeight(593);
	        primaryStage.setMinWidth(783);
	    } catch (NullPointerException | IOException e ) {
	        e.printStackTrace();
	    }
	}
	public void InitSchedules() {

		try {
			FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(Main.class.getResource("/application/view/Schedules.fxml"));
	         BorderPane scheduleOverview = (BorderPane) loader.load();

			// Помещаем сведения об адресатах в центр корневого макета.
	         rootWindow.setCenter(scheduleOverview);

	         SchedulesController controller = loader.getController();
	         controller.setMainApp(this);
	         controller.setShipNameLabel(currId);
	         controller.setUserWelcomeLabel(currId);
	         controller.setUserPostLabel(currId);



		} catch (IOException e) {
			e.printStackTrace();
        }

	}
	public void ShowShipInfo() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("/application/view/ShipInfo.fxml"));
       AnchorPane ShipInfo = (AnchorPane) loader.load();
       //rootWindow.centerProperty().set(null);
       ShipInfoController controller = loader.getController();
       controller.setMainApp(this);

		// Помещаем сведения об адресатах в центр корневого макета.
       rootWindow.setCenter(ShipInfo);
	}

	public void ShowUserInfo() throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(Main.class.getResource("/application/view/UserInfo.fxml"));
      AnchorPane ShipInfo = (AnchorPane) loader.load();
      //rootWindow.centerProperty().set(null);
      UserInfoController controller = loader.getController();
      controller.setMainApp(this);

		// Помещаем сведения об адресатах в центр корневого макета.
      rootWindow.setCenter(ShipInfo);

	}



	public void InitShipWindow() {
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


		        dialogStage.show();
		        System.out.print("Ship window inited");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

	}

	public void InitUserWindow() {
		 try {

		        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(Main.class.getResource("/application/view/InitUserWindow.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("Регистрация сотрудника");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        dialogStage.setResizable(false);

		        // Передаёт адресатов в контроллер.
		        InitUserWindowController controller = loader.getController();
		     //   controller.setPersonData(personData);

		        dialogStage.show();
		        System.out.print("User window inited");
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

	}
		 public void InitTripWindow() {
				// TODO Auto-generated method stub

				 try {
				        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
				        FXMLLoader loader = new FXMLLoader();
				        loader.setLocation(Main.class.getResource("/application/view/InitTripForm.fxml"));
				        BorderPane page = (BorderPane) loader.load();
				        Stage dialogStage = new Stage();
				        dialogStage.setTitle("Новый рейс");
				        dialogStage.initModality(Modality.WINDOW_MODAL);
				        dialogStage.initOwner(primaryStage);
				        Scene scene = new Scene(page);
				        dialogStage.setScene(scene);
				        dialogStage.setResizable(false);

				        // Передаёт адресатов в контроллер.
				        InitTripFormController controller = loader.getController();
				     //   controller.setPersonData(personData);

				        dialogStage.show();
				        System.out.print("INITTRIP");
				    } catch (IOException e) {
				        e.printStackTrace();
				    }

	}
		public void ClearTempData() {
			Exception t=MyDbAdapter.deleteAllTripsWithSchedules();
			if(t==null){
				Alert a=new Alert(AlertType.INFORMATION);
				a.setTitle("Сообщение");
				a.setHeaderText("Выполнено");
				a.setContentText("Все рейсы и расписания успешно удалены");
				a.showAndWait();
			}else{
				Alert a=new Alert(AlertType.ERROR);
				a.setTitle("Ошибка");
				a.setHeaderText("Удалить не удалось");
				a.setContentText(t.getMessage());
				a.showAndWait();
			}

		}
		public void InitMooringWindow() {
			// TODO Auto-generated method stub
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

			        dialogStage.show();
			        System.out.print("INITTRIP");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

		}
		public void InitAppInfo() {
			try {
				FXMLLoader loader = new FXMLLoader();
				 loader.setLocation(Main.class.getResource("/application/view/AppInfo.fxml"));
		         AnchorPane appInfo = (AnchorPane) loader.load();

				// Помещаем сведения об адресатах в центр корневого макета.
		         rootWindow.setCenter(appInfo);

		         AppInfoController controller = loader.getController();
		         controller.setMainApp(this);
		         rootWindow.setCenter(appInfo);



			} catch (IOException e) {
				e.printStackTrace();
	        }
		}
		public void ToggleAdmin(boolean b) {
			if(b){

			}else{


			}

		}
		public void InitSalary() {
			// TODO Auto-generated method stub
			if(Main.getCurrId()==0)

				if(Main.getCurrFakedId()<0)
				{

					Alert a=new Alert(AlertType.INFORMATION);
					a.setTitle("Сообщение");
					a.setHeaderText("Сотрудник не установлен");
					a.setContentText("выберите работника внизу панели");

					a.showAndWait();
					return;


				}

			 try {
			        // Загружает fxml-файл и создаёт новую сцену для всплывающего окна.
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(Main.class.getResource("/application/view/MoneyWindow.fxml"));
			        BorderPane page = (BorderPane) loader.load();
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("Табелирование");
			        dialogStage.initModality(Modality.WINDOW_MODAL);
			        dialogStage.initOwner(primaryStage);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);
			        dialogStage.setResizable(false);

			        // Передаёт адресатов в контроллер.
			        MoneyWindowController controller = loader.getController();
			     //   controller.setPersonData(personData);

			        dialogStage.showAndWait();
			      //  System.out.print("INITTRIP");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

		}
		public void HandleMasterRoot() {
			 try {
			   FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(Main.class.getResource("/application/view/MasterRootWindow.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("Смена пароля");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        dialogStage.setResizable(false);

		        // Передаёт адресатов в контроллер.
		        MasterRootWindowController controller = loader.getController();

		        dialogStage.showAndWait();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		}
		public void showContacts() {
			 try {
				   FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(Main.class.getResource("/application/view/MyContacts.fxml"));
			        AnchorPane page = (AnchorPane) loader.load();
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("О программе");
			        dialogStage.initModality(Modality.WINDOW_MODAL);
			        dialogStage.initOwner(primaryStage);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);
			        dialogStage.setResizable(false);

			        // Передаёт адресатов в контроллер.
			        MyXontactController controller = loader.getController();

			        dialogStage.showAndWait();


			    } catch (IOException e) {
			        e.printStackTrace();
			    }

		}






}
