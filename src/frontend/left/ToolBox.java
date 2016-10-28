package frontend.left;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import backend.Interpreter;
import frontend.Display;
import frontend.ErrorException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Playground;

/**
 * @author Charlie Wang
 * @author Tripp Whaley
 * 
 */
public class ToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private static final Color DEFAULTPENCOLOR = Color.BLACK;
	private static final Color DEFAULTBACKGROUNDCOLOR = Color.LIGHTGREEN;
	private static final String DEFAULT_LANGUAGE = "English";
	private String[] turtleList = { "Turtle", "Elephant", "Rocket" };

	private String[] languageList;
	private SimpleObjectProperty<ObservableList<String>> myTurtleList;
	private SimpleObjectProperty<ObservableList<String>> myLanguageList;

	private GridPane gp;
	private WebView myPage;
	private Display myDisplay;
	private ResourceBundle myResources;
	private int count = 0;
	private String myLanguage;

	public ToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myTurtleList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myTurtleList.getValue().addAll(turtleList);
		myTurtleList.getValue().add(myResources.getString("AddAnother"));
		myLanguageList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myLanguageList.getValue().addAll(getLanguages());

		gp = new GridPane();
		gp.setPrefSize(Integer.parseInt(myResources.getString("ToolBoxWidth")),
				Integer.parseInt(myResources.getString("ToolBoxHeight")));
		gp.setHgap(0);

		// tool box title
		Label title = new Label("      " + myResources.getString("ToolTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("ToolBoxFontSize"))));
		GridPane.setConstraints(title, 0, ++count);
		gp.getChildren().add(title);
		GridPane.setMargin(title, new Insets(20, 10, 15, 10));

		// reset button (reset console, command, and history)
		HBox firstLine = new HBox(10);
		addButton(firstLine, "New");
		addButton(firstLine, "Reset");
		GridPane.setConstraints(firstLine, 0, ++count);
		GridPane.setMargin(firstLine, new Insets(0, 10, 15, 15));
		gp.getChildren().add(firstLine);

		// save commands in the command history window into data/output.txt
		addButton(gp, "SaveCommands");

		// save current image that the turtle draws
		addButton(gp, "SaveImage");

		// set online help
		addButton(gp, "OnlineHelp");

		// set pen's color
		addToolLabel("SetPenColor");
		addPalette("SetPenColor");

		// set background's color
		addToolLabel("SetBackground");
		addPalette("SetBackground");

		// set turtle image
		addToolLabel("SetTurtle");
		addComboBox(myTurtleList, "SetTurtle");

		// set command language
		addToolLabel("SetLanguage");
		addComboBox(myLanguageList, "SetLanguage", DEFAULT_LANGUAGE);
	}

	public ComboBox<String> addComboBox(SimpleObjectProperty<ObservableList<String>> namelist, String refer) {
		ComboBox<String> cb = new ComboBox<>();
		cb.itemsProperty().bind(namelist);
		cb.setPromptText(namelist.getValue().get(0));
		GridPane.setConstraints(cb, 0, ++count);
		gp.getChildren().add(cb);
		addComboBoxEvent(cb, refer);
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
		return cb;
	}
	
	public void addComboBox(SimpleObjectProperty<ObservableList<String>> namelist, String refer, String defaultVal) {
	        ComboBox<String> cb = addComboBox(namelist,refer);
	        cb.setValue(defaultVal);
	}

	public void addToolLabel(String refer) {
		Label label = new Label(myResources.getString(refer));
		GridPane.setConstraints(label, 0, ++count);
		gp.getChildren().add(label);
		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
	}

	public void addButton(Pane pane, String refer) {
		Button button = new Button(myResources.getString(refer));
		addShadow(button);
		addButtonEvent(button, refer);
		GridPane.setConstraints(button, 0, ++count);
		pane.getChildren().add(button);
		GridPane.setMargin(button, new Insets(0, 0, 15, 15));
	}

	public void addPalette(String refer) {
		ColorPicker colorPicker = new ColorPicker();
		if (refer.equals("SetPenColor")) {
			colorPicker.setValue(DEFAULTPENCOLOR);
		} else if (refer.equals("SetBackground")) {
			colorPicker.setValue(DEFAULTBACKGROUNDCOLOR);
		}
		addPaletteEvent(colorPicker, refer);

		GridPane.setConstraints(colorPicker, 0, ++count);
		gp.getChildren().add(colorPicker);
		GridPane.setMargin(colorPicker, new Insets(0, 0, 15, 15));
	}

	public void addButtonEvent(Button button, String function) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (function.equals("Reset")) {
					setResetEvent();
				} else if (function.equals("SaveCommands")) {
					setSaveCommandsEvent();
				} else if (function.equals("SaveImage")) {
					setSaveImageEvent();
				} else if (function.equals("OnlineHelp")) {
					setOnlineHelpEvent();
				} else if (function.equals("New")) {
					setNewScreenEvent();
				} else {
					ErrorException ee = new ErrorException(myResources.getString("NoButtonTitleError"));
				}
			}
		});
	}

	private void addPaletteEvent(ColorPicker cp, String function) {
		cp.setOnAction(t -> {
			if (function.equals("SetPenColor")) {
				setPenEvent(cp.getValue());
			} else if (function.equals("SetBackground")) {
				setBackgroundEvent(cp.getValue());
			}
		});
	}

	private void addComboBoxEvent(ComboBox<String> box, String refer) {
		box.setOnAction(t -> {
			if (refer.equals("SetTurtle")) {
				try {
					setTurtleEvent(box, box.getValue());
				} catch (Exception e) {
					//ErrorException ee = new ErrorException(myResources.getString("NoOptionError"));
					ErrorException ee = new ErrorException(myDisplay, "aha", "Seek Help Online", "Define New Command", "fr 50");
					
				}
			} else if (refer.equals("SetLanguage")) {
				try {
					box.setPromptText("English");
					setLanguageEvent(box.getValue());
				} catch (Exception e) {
					ErrorException ee = new ErrorException(myResources.getString("NoLanguageError"));
				}
			}
		});
	}

	private void setTurtleEvent(ComboBox<String> box, String value) throws IOException {
		if (value.equals(myResources.getString("AddAnother"))) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(myResources.getString("AddAnotherTitle"));
			FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			fileChooser.getExtensionFilters().addAll(filterJPG, filterPNG);

			File newImage = fileChooser.showOpenDialog(null);
			String newName = newImage.getName().replace(".png", "").replace(".jpg", "");
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle(null);
			dialog.setHeaderText(null);
			dialog.setContentText("Please name the image:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				newName = result.get();
			}

			myTurtleList.getValue().add(newName);
			myTurtleList.getValue().set(myTurtleList.getValue().size()-2, newName);
			myTurtleList.getValue().set(myTurtleList.getValue().size()-1, "AddAnother");
			myDisplay.getTurtleLand().changeTurtle(newName, newImage);
		} else {
			try {
				myDisplay.getTurtleLand().changeTurtle(value);
			} catch (Exception e) {
				ErrorException ee = new ErrorException(myDisplay, "aha", "Seek Help Online", "Define New Command", "fr 50");
			}
		}
	}

	private void setNewScreenEvent() {
		Stage newStage = new Stage();
		Playground newPlayGround = new Playground(newStage);
		newPlayGround.init();
	}
	
	private void setLanguageEvent(String value) {
		myDisplay.getConsole().setLanguage(value);
		myLanguage = value;
	}

	private void setResetEvent() {
		myDisplay.getConsole().interpretInput("clearscreen");
		myDisplay.getTurtleLand().updateText();
	}

	private void setSaveCommandsEvent() {
		myDisplay.getConsole().getHistory().printHistoryToFile();
	}

	private void setSaveImageEvent() {
		myDisplay.getTurtleLand().printGround();
	}

	public void setOnlineHelpEvent() {
		myPage = new WebView();
		myPage.getEngine().load(myResources.getString("URLAdress"));
		Group root = new Group();
		root.getChildren().add(myPage);
		myPage.autosize();
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(myResources.getString("URLTitle"));
		stage.show();
	}

	private void setPenEvent(Color color) {
		myDisplay.getTurtleLand().setPenColor(color);
	}

	private void setBackgroundEvent(Color c) {
		myDisplay.getTurtleLand().changeBackground(c);
	}

	private void addShadow(Button button) {
		DropShadow shadow = new DropShadow();
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(shadow);
			}
		});
		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(null);
			}
		});
	}

	public GridPane getTool() {
		return gp;
	}

	public String[] getLanguages() {
		File dir = new File("src/resources/languages/");
		if (dir.isDirectory()) {
			languageList = dir.list();
			for (int i = 0; i < languageList.length; i++) {
				languageList[i] = languageList[i].replace(".properties", "");
			}
			String tmp = languageList[0];
			languageList[0]=languageList[1];
			languageList[1]=tmp;
		}
		return languageList;
	}
}
