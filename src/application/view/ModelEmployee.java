package application.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelEmployee {

		public SimpleIntegerProperty employeeId = new SimpleIntegerProperty();
		public SimpleIntegerProperty relatedSHipId = new SimpleIntegerProperty();

		public StringProperty firstName = new SimpleStringProperty();
		public StringProperty lastName = new SimpleStringProperty();
		public StringProperty surName = new SimpleStringProperty();
		public StringProperty post = new SimpleStringProperty();

		public StringProperty bdate = new SimpleStringProperty();
		public StringProperty country = new SimpleStringProperty();
		public StringProperty language = new SimpleStringProperty();
		public DoubleProperty salary = new SimpleDoubleProperty();

		public StringProperty login = new SimpleStringProperty();
		public StringProperty password = new SimpleStringProperty();




		//ublic StringProperty sndDate = new SimpleStringProperty();

	    public ModelEmployee() {
	        this(-1,-1,"none", "none","none");
	    }


	    public void setSalary(double shipName) {
	        this.salary.set(shipName);
	    }
	    public DoubleProperty getSalary() {
	        return salary;
	    }



	    public void setID(int shipName) {
	        this.employeeId.set(shipName);
	    }
	    public IntegerProperty getId() {
	        return employeeId;
	    }

	    public void setFirstName(String shipName) {
	        this.firstName.set(shipName);
	    }
	    public StringProperty getFirstName() {
	        return firstName;
	    }

	    public void setLastName(String shipName) {
	        this.lastName.set(shipName);
	    }
	    public StringProperty getLastName() {
	        return lastName;
	    }

	    public void setSurName(String shipName) {
	        this.surName.set(shipName);
	    }
	    public StringProperty getSurName() {
	        return surName;
	    }




	    public void setPost(String startDate) {
	    	 this.post.set(startDate);
	    }
	    public StringProperty getPost() {
	        return post;
	    }

	    public void setCountry(String endDate) {
	    	 this.country.set(endDate);
	    }
	    public StringProperty getCountry() {
	        return country;
	    }

	    public void setLanguage(String endDate) {
	    	 this.language.set(endDate);
	    }
	    public StringProperty getLanguage() {
	        return language;
	    }

	    public ModelEmployee(int employeeId, String firstName, String lastName,String post,String country,String language,double salary,
	    		String login,String password) {
	    	this.employeeId = new SimpleIntegerProperty(employeeId);
	        this.firstName = new SimpleStringProperty(firstName);
	        this.lastName = new SimpleStringProperty(lastName);
	        this.post = new SimpleStringProperty(post);
	        this.country = new SimpleStringProperty(country);
	        this.language = new SimpleStringProperty(language);
	        this.salary = new SimpleDoubleProperty(salary);

	        this.login = new SimpleStringProperty(language);
	        this.password = new SimpleStringProperty(language);


	    }


		public ModelEmployee(int i, int j, String string, String string2, String string3) {
			// TODO Auto-generated constructor stub
		}
}