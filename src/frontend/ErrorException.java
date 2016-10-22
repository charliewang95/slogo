package frontend;

import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 * @author Charlie Wang
 */
public class ErrorException{
	
	private Alert myAlert;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");;
	
	public ErrorException(String message) {
		myAlert = new Alert(AlertType.ERROR);
		myAlert.setTitle(myResources.getString("ErrorTitle"));
		myAlert.setHeaderText(null);
		myAlert.setContentText(message);
		myAlert.showAndWait();
	}
	
	public ErrorException(String message, String option1) {
		myAlert = new Alert(AlertType.CONFIRMATION);
		myAlert.setTitle(myResources.getString("ErrorTitle"));
		myAlert.setHeaderText(null);
		myAlert.setContentText(message);
		Optional<ButtonType> result = myAlert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	public ErrorException(String message, String option1, String option2) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("ErrorTitle");
	alert.setHeaderText(null);
	alert.setContentText(message);

	ButtonType buttonTypeOne = new ButtonType(option1);
	ButtonType buttonTypeTwo = new ButtonType(option2);
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
}
