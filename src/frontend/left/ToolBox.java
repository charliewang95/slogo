package frontend.left;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import frontend.Display;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Charlie Wang
 * 
 */
public class ToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private static final Color DEFAULTPENCOLOR = Color.BLACK;
	private static final Color DEFAULTBACKGROUNDCOLOR = Color.LIGHTGREEN;
	private String[] turtleList = { "Turtle", "Elephant", "Rocket" };
	private String[] languageList = { "English", "Chinese", "French", "German", "Italian", "Portuguese", "Russian",
			"Spanish", "System"};
	private SimpleObjectProperty<ObservableList<String>> myTurtleList;
	private SimpleObjectProperty<ObservableList<String>> myLanguageList;

	private GridPane gp;
	private WebView myPage;
	private Display myDisplay;
	private ResourceBundle myResources;
	private int count = 0;

	public ToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myTurtleList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myTurtleList.getValue().addAll(turtleList);
		myTurtleList.getValue().add(myResources.getString("AddAnother"));
		myLanguageList = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myLanguageList.getValue().addAll(languageList);

		gp = new GridPane();
		gp.setPrefSize(Integer.parseInt(myResources.getString("ToolBoxWidth")),
				Integer.parseInt(myResources.getString("ToolBoxHeight")));
		gp.setHgap(0);

		// tool box title
		Label title = new Label("         " + myResources.getString("ToolTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("ToolBoxFontSize"))));
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
		addComboBox(myTurtleList, "SetTurtle");

		// set command language
		addToolLabel("SetLanguage");
		addComboBox(myLanguageList, "SetLanguage");

	}

	public void addComboBox(SimpleObjectProperty<ObservableList<String>> namelist, String refer) {
		ComboBox<String> cb = new ComboBox<>();
		cb.setPromptText(namelist.get().get(0));
		cb.itemsProperty().bind(namelist);
		GridPane.setConstraints(cb, 0, ++count);
		gp.getChildren().add(cb);
		addComboBoxEvent(cb, refer);
		GridPane.setMargin(cb, new Insets(0, 0, 15, 15));
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

	private void addComboBoxEvent(ComboBox<String> box, String refer) {
		box.setOnAction(t -> {
			if (refer.equals("SetTurtle")) {
				try {
					setTurtleEvent(box, box.getValue());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (refer.equals("SetLanguage")) {

			}
		});
	}

	private void setTurtleEvent(ComboBox<String> box, String value) throws IOException {
		if (value.equals(myResources.getString("AddAnother"))) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(myResources.getString("AddAnotherTitle"));
			FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(filterJPG, filterPNG);
            
            
			File newImage = fileChooser.showOpenDialog(null);
			String newName = newImage.getName().replace(".png", "").replace(".jpg", "");
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle(null);
			dialog.setHeaderText(null);
			dialog.setContentText("Please name the image:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
			    newName = result.get();
			}
			
			try {
				myTurtleList.getValue().remove(myResources.getString("AddAnother"));
				myTurtleList.getValue().add(newName);
				myTurtleList.getValue().add(myResources.getString("AddAnother"));
				myDisplay.getTurtleLand().changeTurtle(newName, newImage);
			} catch (Exception e) {
				//TODO 
			}
		} else {
			try {
				myDisplay.getTurtleLand().changeTurtle(value);
			} catch (Exception e) {
				//TODO 
			}
		}
	}

	private void setLanguageEvent(String value) {

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
