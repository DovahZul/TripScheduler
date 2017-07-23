package application.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModelMooring {
		public StringProperty portName = new SimpleStringProperty();
		public IntegerProperty tripId = new SimpleIntegerProperty();
		public StringProperty startDatetime = new SimpleStringProperty();
		public StringProperty endDateTime = new SimpleStringProperty();
		public StringProperty responsible = new SimpleStringProperty();
		public StringProperty status = new SimpleStringProperty();
		public IntegerProperty mooringID = new SimpleIntegerProperty();


	    public ModelMooring() {
	        this("none",-1, "none","none","none", "none");
	    }

	    public void setMooringId(int id) {
	        this.mooringID.set(id);
	    }
	    public IntegerProperty getMooringID() {
	        return mooringID;
	    }

	    public void setTripId(int id) {
	        this.tripId.set(id);
	    }
	    public IntegerProperty getTripID() {
	        return tripId;
	    }

	    public void setStatus(String status) {
	        this.status.set(status);
	    }
	    public StringProperty getStatus() {
	        return status;
	    }

	    public void setPortName(String watchDate) {
	        this.portName.set(watchDate);
	    }
	    public StringProperty getPortName() {
	        return portName;
	    }

	    public void setStartDateTime(String watchDate) {
	        this.startDatetime.set(watchDate);
	    }
	    public StringProperty getStartDateTime() {
	        return startDatetime;
	    }
	    public void setEndDateTime(String watchDate) {
	        this.endDateTime.set(watchDate);
	    }
	    public StringProperty getEndDateTime() {
	        return endDateTime;
	    }

	    public void setResponsiblePerson(String watchDate) {
	        this.responsible.set(watchDate);
	    }
	    public StringProperty getResponsiblePerson() {
	        return responsible;
	    }


	    public ModelMooring(String portName,int id, String startDatetime,String endDateTime,String responsible,String status) {
	        this.portName = new SimpleStringProperty(portName);
	        this.tripId = new SimpleIntegerProperty(id);
	        this.startDatetime = new SimpleStringProperty(startDatetime);
	        this.endDateTime = new SimpleStringProperty(endDateTime);
	        this.responsible = new SimpleStringProperty(responsible);
	        this.status = new SimpleStringProperty(status);
	    }
}