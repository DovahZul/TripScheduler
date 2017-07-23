package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MyXontactController implements Initializable{

	@FXML
	private Button closeBtn;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	@FXML
	private void handleClose(){
	//this.close();
		Stage stage = (Stage) closeBtn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
}
