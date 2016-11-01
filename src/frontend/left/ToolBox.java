package frontend.left;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import backend.Interpreter;
import frontend.Display;
import frontend.ErrorException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
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
	private String[] styleList = { "Pen Down", "Pen Up", "Dash", "Solid" };
	private String[] languageList;
	private SimpleObjectProperty<ObservableList<String>> myTurtleList;
	private SimpleObjectProperty<ObservableList<String>> myLanguageList;
	private SimpleObjectProperty<ObservableList<String>> myStyleList;
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
		myStyleList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myStyleList.getValue().addAll(styleList);

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
		GridPane.setMargin(title, new Insets(20, 10, 10, 10));

		HBox firstLine = new HBox(10);
		GridPane.setConstraints(firstLine, 0, ++count);
		GridPane.setMargin(firstLine, new Insets(0, 10, 10, 15));
		gp.getChildren().add(firstLine);

		// create new workspace
		addButton(firstLine, "New");

		// reset button (reset console, command, and history)
		addButton(firstLine, "Reset");

		// save commands in the command history window into data/output.txt
		addComboBox(myStyleList, "SetPenStyle");

		// save current image that the turtle draws
		addToolLabel("SetPenSize");
		addSlider(1, 5, 1);

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

		// add advanced toolbox
		AdvancedToolBox atb = new AdvancedToolBox(myDisplay);
		GridPane.setConstraints(atb.getBox(), 0, ++count);
		gp.getChildren().add(atb.getBox());

		// set online help
		addButton(gp, "OnlineHelp");
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

	public void addSlider(int min, int max, int value) {
		Slider slider = new Slider(min, max, value);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		GridPane.setConstraints(slider, 0, ++count);
		addSliderEvent(slider);
		GridPane.setMargin(slider, new Insets(0, 0, 0, 15));
		gp.getChildren().add(slider);
	}

	public void addComboBox(SimpleObjectProperty<ObservableList<String>> namelist, String refer, String defaultVal) {
		ComboBox<String> cb = addComboBox(namelist, refer);
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

	private void addSliderEvent(Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				myDisplay.changePenSize(newValue.doubleValue());
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
		if (refer.equals("SetPenStyle")) {
			box.setPromptText(myResources.getString("SetPenStyle"));
		}
		box.setOnAction(t -> {
			if (refer.equals("SetTurtle")) {
				try {
					setTurtleEvent(box, box.getValue());
				} catch (Exception e) {
					ErrorException ee = new ErrorException(myResources.getString("NoOptionError"));
				}
			} else if (refer.equals("SetLanguage")) {
				try {
					box.setPromptText("English");
					setLanguageEvent(box.getValue());
				} catch (Exception e) {
					ErrorException ee = new ErrorException(myResources.getString("NoLanguageError"));
				}
			} else if (refer.equals("SetPenStyle")) {
				try {
					setPenStyleEvent(box.getValue());
				} catch (Exception e) {
					ErrorException ee = new ErrorException(myResources.getString("NoOptionError"));
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
			myTurtleList.getValue().set(myTurtleList.getValue().size() - 2, newName);
			myTurtleList.getValue().set(myTurtleList.getValue().size() - 1, "AddAnother");
			myDisplay.changeTurtle(newName, newImage);
		} else {
			try {
				myDisplay.changeTurtle(value);
			} catch (Exception e) {

			}
		}
	}

	private void setNewScreenEvent() {
		myDisplay.newStage();
	}

	private void setLanguageEvent(String value) {
		myDisplay.setLanguage(value);
		myLanguage = value;
	}

	private void setResetEvent() {
		myDisplay.reset();
	}

	private void setPenStyleEvent(String value) {
		myDisplay.setPenStyleEvent(value);
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
		myDisplay.setPenColor(color);
	}

	private void setBackgroundEvent(Color c) {
		myDisplay.changeBackground(c);
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
			languageList[0] = languageList[1];
			languageList[1] = tmp;
		}
		return languageList;
	}

	public String getLanguage() {
		return myLanguage;
	}

	public void setLanguage(String s) {
		for (String l : languageList) {
			if (l.equals(s)) {
				myLanguage = s;
				return;
			}
		}
		ErrorException ee = new ErrorException("Language Not Found");
	}
}
