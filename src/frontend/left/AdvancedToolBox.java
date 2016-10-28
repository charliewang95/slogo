package frontend.left;

import java.util.ResourceBundle;

import frontend.Display;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class AdvancedToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private ComboBox<String> cb;
	private Display myDisplay;
	private ResourceBundle myResources;
	
	public AdvancedToolBox (Display display) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		cb = new ComboBox<String>();
		cb.setPromptText(myResources.getString("AdvancedSettings"));
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
		
		addWorkspaceSettings();
		addChangePenProperties();
		
		cb.setOnAction(e -> {
			if (cb.getValue().equals("Save Pref")) {
//				myDisplay.
			}
		});
	}
	
	private void addWorkspaceSettings() {
		cb.getItems().add("Save Pref");
		cb.getItems().add("Load Pref");
	}

	private void addChangePenProperties() {
		cb.getItems().add("Pensize");
	}

	public ComboBox<String> getBox() {
		return cb;
	}
}
