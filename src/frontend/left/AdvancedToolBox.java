package frontend.left;

import java.io.File;
import java.util.ResourceBundle;

import frontend.Display;
import frontend.ErrorException;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class AdvancedToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private ComboBox<String> cb;
	private Display myDisplay;
	private ResourceBundle myResources;

	public AdvancedToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		cb = new ComboBox<String>();
		cb.setPromptText(myResources.getString("AdvancedSettings"));
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));

		cb.getItems().add(myResources.getString("SavePref"));
		cb.getItems().add(myResources.getString("LoadPref"));
		cb.getItems().add(myResources.getString("SaveCommands"));
		cb.getItems().add(myResources.getString("SaveImage"));
		cb.getItems().add(myResources.getString("DefineNew"));

		cb.setOnAction(t -> {
			if (cb.getValue().equals(myResources.getString("SavePref"))) {
				myDisplay.saveDefaultConfig();
			} else if (cb.getValue().equals(myResources.getString("LoadPref"))) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(myResources.getString("LoadPref"));
				File config = fileChooser.showOpenDialog(null);
				try {
					myDisplay.setDefaultConfig(config);
				} catch (Exception e) {
					ErrorException ee = new ErrorException(myResources.getString("FileNoGood"));
				}
			} else if (cb.getValue().equals(myResources.getString("SaveCommands"))) {
				setSaveCommandsEvent();
			} else if (cb.getValue().equals(myResources.getString("SaveImage"))) {
				setSaveImageEvent();
			} else if (cb.getValue().equals(myResources.getString("DefineNew"))) {
				setDefineNewEvent();
			}
		});
	}

	private void setSaveCommandsEvent() {
		myDisplay.printHistoryToFile();
	}

	private void setDefineNewEvent() {
		myDisplay.defineNewCommands("");
	}

	private void setSaveImageEvent() {
		myDisplay.printGround();
	}

	public ComboBox<String> getBox() {
		return cb;
	}
}
