package frontend.left;

import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class AdvancedToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private ComboBox<String> cb;
	private ResourceBundle myResources;
	
	public AdvancedToolBox () {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		cb = new ComboBox<String>();
		cb.setPromptText(myResources.getString("AdvancedSettings"));
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
		
		addChangePenProperties();
		
		addWorkspaceSettings();
		
	}
	
	private void addWorkspaceSettings() {
		
	}

	private void addChangePenProperties() {
		
	}

	public ComboBox<String> getBox() {
		return cb;
	}
}
