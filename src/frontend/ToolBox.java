package frontend;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ToolBox {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	
	private GridPane gp;
	private Display myDisplay;
	private ResourceBundle myResources;

	public ToolBox(Display display) {
		myDisplay = display;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		
		gp = new GridPane();
		gp.setPrefSize(300, 50);
		//gp.setStyle("-fx-background-color: #C0C0C0");
		
		//gp.setVgap(10);
		gp.setHgap(0);
		
		Label title = new Label("  "+myResources.getString("ToolTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font("Verdana", 14));
		GridPane.setConstraints(title, 0, 0);
		gp.getChildren().add(title);
		GridPane.setMargin(title, new Insets(10, 10, 10, 10));
		
		addButton("Reset", 1);
		addButton("SaveCommands", 2);
		addButton("SaveImage", 3);
		addButton("SetPenColor", 4);
		addButton("SetBackground", 5);
	}

	public void addButton(String refer, int index) {
		Button button = new Button(myResources.getString(refer));
		addShadow(button);
		addButtonEvent(button, refer);
		GridPane.setConstraints(button, 0, index);
		gp.getChildren().add(button);
		GridPane.setMargin(button, new Insets(0, 0, 15, 15));
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
