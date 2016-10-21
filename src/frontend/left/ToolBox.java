package frontend.left;

import java.util.ResourceBundle;

import frontend.Display;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 * 
 */
public class ToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private static final Color DEFAULTPENCOLOR = Color.BLACK;
	private static final Color DEFAULTBACKGROUNDCOLOR = Color.LIGHTGREEN;
	private String[] turtleList = { "Turtle", "Elephant" };
	private String[] languageList = { "English", "Chinese", "French", "German", "Italian", "Portuguese", "Russian",
			"Spanish", "System" };

	private GridPane gp;
	private WebView myPage;
	private Display myDisplay;
	private ResourceBundle myResources;
	private int count = 0;

	public ToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		gp = new GridPane();
		gp.setPrefSize(Integer.parseInt(myResources.getString("ToolBoxWidth")),
				Integer.parseInt(myResources.getString("ToolBoxHeight")));
		gp.setHgap(0);

		// tool box title
		Label title = new Label("         " + myResources.getString("ToolTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font(myResources.getString("TitleFont"), Integer.parseInt(myResources.getString("ToolBoxFontSize"))));
		GridPane.setConstraints(title, 0, ++count);
		gp.getChildren().add(title);
		GridPane.setMargin(title, new Insets(20, 10, 15, 10));

		// reset button (reset console, command, and history)
		addButton("Reset");

		// save commands in the command history window into data/output.txt
		addButton("SaveCommands");

		// save current image that the turtle draws
		addButton("SaveImage");

		// set online help
		addButton("OnlineHelp");

		// set pen's color
		addToolLabel("SetPenColor");
		addPalette("SetPenColor");

		// set background's color
		addToolLabel("SetBackground");
		addPalette("SetBackground");

		// set turtle image
		addToolLabel("SetTurtle");
		addComboBox(turtleList, "SetTurtle");

		// set command language
		addToolLabel("SetLanguage");
		addComboBox(languageList, "SetLanguage");

	}

	public void addComboBox(String[] namelist, String refer) {
		SimpleObjectProperty<ObservableList<String>> list = new SimpleObjectProperty<>(
				FXCollections.observableArrayList());
		list.getValue().addAll(namelist);
		ComboBox<String> cb = new ComboBox<>();
		cb.setPromptText(list.get().get(0));
		cb.itemsProperty().bind(list);
		GridPane.setConstraints(cb, 0, ++count);
		gp.getChildren().add(cb);

		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
	}

	public void addLanguageList() {
		SimpleObjectProperty<ObservableList<String>> myTypes = new SimpleObjectProperty<>(
				FXCollections.observableArrayList());
		myTypes.getValue().add("Turtle");
		ComboBox<String> cbTurtle = new ComboBox<>();
		cbTurtle.setPromptText(myTypes.get().get(0));
		cbTurtle.itemsProperty().bind(myTypes);
		GridPane.setConstraints(cbTurtle, 0, ++count);
		gp.getChildren().add(cbTurtle);
	}

	public void addToolLabel(String refer) {
		Label label = new Label(myResources.getString(refer));
		GridPane.setConstraints(label, 0, ++count);
		gp.getChildren().add(label);
		GridPane.setMargin(label, new Insets(0, 0, 0, 15));
	}

	public void addButton(String refer) {
		Button button = new Button(myResources.getString(refer));
		addShadow(button);
		addButtonEvent(button, refer);
		GridPane.setConstraints(button, 0, ++count);
		gp.getChildren().add(button);
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
				} else {

				}
			}
		});
	}

	private void addPaletteEvent(ColorPicker cp, String function) {
		cp.setOnAction(t -> {
			if (function.equals("SetPenColor")) {
				setPenEvent();
			} else if (function.equals("SetBackground")) {
				setBackgroundEvent(cp.getValue());
				System.out.print(1);
			}
		});
	}

	private void setResetEvent() {
		myDisplay.getConsole().clear();
	}

	private void setSaveCommandsEvent() {
		myDisplay.getConsole().getHistory().printHistoryToFile();
	}

	private void setSaveImageEvent() {
		// TODO save image
	}

	private void setOnlineHelpEvent() {
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

	private void setPenEvent() {
		// TODO change pen color
	}

	private void setBackgroundEvent(Color c) {
		// TODO change background color
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
}
