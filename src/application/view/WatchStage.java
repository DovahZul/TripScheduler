package application.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WatchStage {
		public StringProperty watchDate = new SimpleStringProperty();
		public StringProperty watchTime = new SimpleStringProperty();
		public StringProperty fio = new SimpleStringProperty();
		public BooleanProperty checked = new SimpleBooleanProperty();
		public BooleanProperty highlightProperty = new SimpleBooleanProperty();


	    public WatchStage() {
	        this("none", "none","none",false);
	    }
	    public void setHighlight( boolean value ) {
	        highlightProperty.set( value );
	    }
	    public boolean isHighlight() {
	        return highlightProperty.get();
	    }
	    public void setChecked(Boolean b) {
	    	getChecked().set(b);
	    }
	    public BooleanProperty getChecked() {
	        return checked;
	    }
	    public void setWatchDate(String watchDate) {
	        this.watchDate.set(watchDate);
	    }
	    public StringProperty getWatchDate() {
	        return watchDate;
	    }
	    public void setWatchTime(String watchDate) {
	        this.watchTime.set(watchDate);
	    }
	    public StringProperty getWatchTime() {
	        return watchTime;
	    }

	    public void setFio(String watchDate) {
	        this.fio.set(watchDate);
	    }
	    public StringProperty getFio() {
	        return fio;
	    }


	    public WatchStage(String date, String time, String fio,Boolean b) {
	        this.watchDate = new SimpleStringProperty(date);
	        this.watchTime = new SimpleStringProperty(time);;
	        this.fio = new SimpleStringProperty(fio);
	        this.checked = new SimpleBooleanProperty(b);

	    }
}
