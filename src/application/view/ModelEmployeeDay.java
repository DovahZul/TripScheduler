package application.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelEmployeeDay {

	public SimpleIntegerProperty userId = new SimpleIntegerProperty();
	public StringProperty userFullName = new SimpleStringProperty();
	public StringProperty userPost = new SimpleStringProperty();

	public StringProperty hour00 = new SimpleStringProperty();
	public StringProperty hour01 = new SimpleStringProperty();
	public StringProperty hour02 = new SimpleStringProperty();
	public StringProperty hour03 = new SimpleStringProperty();
	public StringProperty hour04 = new SimpleStringProperty();
	public StringProperty hour05 = new SimpleStringProperty();
	public StringProperty hour06 = new SimpleStringProperty();
	public StringProperty hour07 = new SimpleStringProperty();
	public StringProperty hour08 = new SimpleStringProperty();
	public StringProperty hour09 = new SimpleStringProperty();
	public StringProperty hour10 = new SimpleStringProperty();
	public StringProperty hour11 = new SimpleStringProperty();
	public StringProperty hour12 = new SimpleStringProperty();
	public StringProperty hour13 = new SimpleStringProperty();
	public StringProperty hour14 = new SimpleStringProperty();
	public StringProperty hour15 = new SimpleStringProperty();
	public StringProperty hour16 = new SimpleStringProperty();
	public StringProperty hour17 = new SimpleStringProperty();
	public StringProperty hour18 = new SimpleStringProperty();
	public StringProperty hour19 = new SimpleStringProperty();
	public StringProperty hour20 = new SimpleStringProperty();
	public StringProperty hour21 = new SimpleStringProperty();
	public StringProperty hour22 = new SimpleStringProperty();
	public StringProperty hour23 = new SimpleStringProperty();



    public ModelEmployeeDay() {
        this(-1,"none", null);
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

    public void setPost(String userFullName) {
        this.userPost.set(userFullName);
    }
    public StringProperty getPost() {
        return userPost;
    }



    public ModelEmployeeDay(int id, String userFullName,String post)
    {
    	 this.userId = new SimpleIntegerProperty(id);
        this.userFullName = new SimpleStringProperty();
        this.userPost=new SimpleStringProperty(post);

    }
}