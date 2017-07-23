package application.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelUser {

	public SimpleIntegerProperty userId = new SimpleIntegerProperty();
	public StringProperty userFullName = new SimpleStringProperty();

	public StringProperty userFirstName = new SimpleStringProperty();
	public StringProperty userLastName = new SimpleStringProperty();
	public StringProperty userSurName = new SimpleStringProperty();


	public StringProperty userBirthday = new SimpleStringProperty();
	public StringProperty userPost = new SimpleStringProperty();
	public StringProperty userCountry = new SimpleStringProperty();
	public StringProperty userLanguage = new SimpleStringProperty();
	public StringProperty userCurrentShip = new SimpleStringProperty();

	public DoubleProperty salary = new SimpleDoubleProperty();

	public StringProperty login = new SimpleStringProperty();
	public StringProperty password = new SimpleStringProperty();



    public ModelUser() {
        this(-1,"none", "none","none", "none", "none", "none", "none", 0, null, null);
    }

    public void setSalary(double shipName) {
        this.salary.set(shipName);
    }
    public DoubleProperty getSalary() {
        return salary;
    }

    public void setId(int userId) {
        this.userId.set(userId);
    }
    public IntegerProperty getId() {
        return userId;
    }

    public void setFullName(String userFullName) {
        this.userFullName.set(userFullName);
    }
    public StringProperty getFullName() {
        return userFullName;
    }

    public void setFirstName(String userFirstName) {
        this.userFirstName.set(userFirstName);
    }
    public StringProperty getFirstName() {
        return userFirstName;
    }

    public void setLastName(String userLastName) {
        this.userLastName.set(userLastName);
    }
    public StringProperty getLastName() {
        return userLastName;
    }

    public void setSurName(String userSurName) {
        this.userSurName.set(userSurName);
    }
    public StringProperty getSurName() {
        return userSurName;
    }

    public void setBirthday(String userBirthday) {
    	 this.userBirthday.set(userBirthday);
    }
    public StringProperty getBirthday() {
        return userBirthday;
    }

    public void setPost(String userPost) {
    	 this.userPost.set(userPost);
    }
    public StringProperty getPost() {
        return userPost;
    }

    public void setCountry(String userCountry) {
   	 this.userCountry.set(userCountry);
   }
   public StringProperty getCountry() {
       return userCountry;
   }

   public void setLanguage(String userLanguage) {
	  this.userLanguage.set(userLanguage);
   }
   		public StringProperty getLanguage() {
	  return userLanguage;
   }

   		public void setCurrentShip(String userCurrentShip) {
   		  this.userCurrentShip.set(userCurrentShip);
   	   }
   	   		public StringProperty getCurrentShip() {
   		  return userCurrentShip;
   	   }


    public ModelUser(int id, String userFirstName, String userLastName, String userSurName,String userBirthday,String userCountry,String userLanguage,String userCurrentShip,double salary,
    		String login,String password) {
    	this.userId = new SimpleIntegerProperty(id);

        this.userFirstName = new SimpleStringProperty(userFirstName);
        this.userLastName = new SimpleStringProperty(userLastName);
        this.userSurName = new SimpleStringProperty(userSurName);

        this.userBirthday = new SimpleStringProperty(userBirthday);
        this.userCountry = new SimpleStringProperty(userCountry);
        this.userLanguage = new SimpleStringProperty(userLanguage);
        this.userCurrentShip = new SimpleStringProperty(userCurrentShip);

        this.salary = new SimpleDoubleProperty(salary);

        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);

    }
}
