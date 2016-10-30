package frontend.left;

import java.io.File;
import java.util.ResourceBundle;

import frontend.Display;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class AdvancedToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private ComboBox<String> cb;
	private Display myDisplay;
	private ResourceBundle myResources;
	
	public AdvancedToolBox (Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		cb = new ComboBox<String>();
		cb.setPromptText(myResources.getString("AdvancedSettings"));
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
		
		addWorkspaceSettings();
		addChangePenProperties();
		
		cb.setOnAction(t -> {
			if (cb.getValue().equals("Save Pref")) {
				myDisplay.saveDefaultConfig();
			} else if (cb.getValue().equals("Load Pref")) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(myResources.getString("LoadPref"));
				File config = fileChooser.showOpenDialog(null);
				try {
					myDisplay.setDefaultConfig(config);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
