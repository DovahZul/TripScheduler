package application.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ShipModel {

		public SimpleIntegerProperty shipId = new SimpleIntegerProperty();
		public StringProperty shipName = new SimpleStringProperty();
		public StringProperty shipType = new SimpleStringProperty();
		public StringProperty shipStatus = new SimpleStringProperty();
		//ublic StringProperty sndDate = new SimpleStringProperty();

	    public ShipModel() {
	        this(-1,"none", "none","none");
	    }


	    public void setShipID(int shipName) {
	        this.shipId.set(shipName);
	    }
	    public IntegerProperty getShipId() {
	        return shipId;
	    }

	    public void setShipName(String shipName) {
	        this.shipName.set(shipName);
	    }
	    public StringProperty getShipName() {
	        return shipName;
	    }

	    public void setSatus(String startDate) {
	    	 this.shipStatus.set(startDate);
	    }
	    public StringProperty getSatus() {
	        return shipStatus;
	    }

	    public void setShipType(String endDate) {
	    	 this.shipType.set(endDate);
	    }
	    public StringProperty getShipType() {
	        return shipType;
	    }

	    public ShipModel(int id, String name, String type,String status) {
	    	this.shipId = new SimpleIntegerProperty(id);
	        this.shipName = new SimpleStringProperty(name);
	        this.shipStatus = new SimpleStringProperty(status);
	        this.shipType = new SimpleStringProperty(type);

	    }
}
