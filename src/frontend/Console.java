package frontend;

import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Console {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	
	private TextField myTextField; 
	private HBox hb;
	private ResourceBundle myResources;
	
	public Console() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myTextField = new TextField();
		myTextField.setPromptText(myResources.getString("CommandHint"));
		myTextField.setPrefColumnCount(10);
		myTextField.getText();
	}
	
	public TextField getTextField() {
		return myTextField;
	}
}
