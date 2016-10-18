package frontend;

import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
 */
public class ToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private static final Color DEFAULTPENCOLOR = Color.PINK;
	private static final Color DEFAULTBACKGROUNDCOLOR = Color.LIGHTGREY;

	private GridPane gp;
	private WebView myPage;
	private ComboBox<String> cb;
	SimpleObjectProperty<ObservableList<String>> myTurtleTypes = new SimpleObjectProperty<>(
			FXCollections.observableArrayList());
	private Display myDisplay;
	private ResourceBundle myResources;
	private int count = 0;

	public ToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		gp = new GridPane();
		gp.setPrefSize(300, 50);
		gp.setHgap(0);

		// tool bax title
		Label title = new Label("      " + myResources.getString("ToolTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font("Verdana", 14));
		GridPane.setConstraints(title, 0, ++count);
		gp.getChildren().add(title);
		GridPane.setMargin(title, new Insets(10, 10, 15, 10));

		// reset button (reset console, command, and history)
		addButton("Reset");

		// save commands in the command history window into data/output.txt
		addButton("SaveCommands");

		// save current image that the turtle draws
		addButton("SaveImage");

		// set pen's color
		addToolLabel("SetPenColor");
		addPalette("SetPenColor");

		// set background's color
		addToolLabel("SetBackground");
		addPalette("SetBackground");

		// set turtle image
		addToolLabel("SetTurtle");
		addTurtleList();
		
		//set command language
		addToolLabel("SetLanguage");
		addLanguageList();
		
		// set online help
		addButton("OnlineHelp");
		
		//gp.getChildren().add(myPage);
	}

	public void addTurtleList() {
		myTurtleTypes = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myTurtleTypes.getValue().add("Turtle");
		cb = new ComboBox<>();
		cb.setPromptText(myTurtleTypes.get().get(0));
		cb.itemsProperty().bind(myTurtleTypes);
		GridPane.setConstraints(cb, 0, ++count);
		gp.getChildren().add(cb);
		
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
	}
	
	public void addLanguageList() {
		
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

	private void setResetEvent() {
		myDisplay.getConsole().clear();
		myDisplay.getHistory().clear();
	}

	private void setSaveCommandsEvent() {
		myDisplay.getHistory().printHistoryToFile();
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
	
	private void addPaletteEvent(ColorPicker cp, String function) {
		cp.setOnAction(new EventHandler() {
			@Override
			public void handle(Event t) {
				if (function.equals("PenColor")) {
					setPenEvent();
				} else if (function.equals("BackgroundColor")) {
					setBackgroundEvent();
				}
			}
		});
	}

	private void setPenEvent() {
		// TODO change pen color
	}

	private void setBackgroundEvent() {
		// TODO change background color
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
