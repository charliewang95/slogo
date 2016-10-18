package frontend;

import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * @author Charlie Wang
 */
public class Console {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Display myDisplay;
	private TextField myTextField;
	private Label myErrorLabel;
	private GridPane gp;
	private ResourceBundle myResources;
	private SimpleObjectProperty<ObservableList<String>> myCommands;

	public Console(Display display) {
		myDisplay = display;
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(10);
		gp.setHgap(5);
		//gp.setStyle("-fx-background-color: #C0C0C0");

		Label label1 = new Label(myResources.getString("ConsoleText"));
		GridPane.setConstraints(label1, 0, 0);
		gp.getChildren().add(label1);

		myTextField = new TextField();
		myTextField.setPromptText(myResources.getString("ConsoleHint"));
		myTextField.setPrefColumnCount(Integer.parseInt(myResources.getString("ConsoleColumn")));
		myTextField.getText();
		GridPane.setConstraints(myTextField, 1, 0);
		gp.getChildren().add(myTextField);
		myTextField.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				checkInput();
			}
		});

		myErrorLabel = new Label();
		myErrorLabel.setTextFill(Color.RED);
		GridPane.setConstraints(myErrorLabel, 1, 1);
		gp.getChildren().add(myErrorLabel);
		
		Button button1 = new Button(myResources.getString("ConsoleButton"));
		GridPane.setConstraints(button1, 2, 0);
		gp.getChildren().add(button1);
		
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				checkInput();
			}
		});
		
		Button button2 = new Button(myResources.getString("ConsoleClear"));
		GridPane.setConstraints(button2, 3, 0);
		gp.getChildren().add(button2);
		
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myTextField.clear();
			}
		});
	}

	private void checkInput() {
		if ((myTextField.getText() != null && !myTextField.getText().isEmpty())) {
			String s = myTextField.getText();
			myCommands.getValue().add(s);
			myTextField.clear();
			myErrorLabel.setText("");
			myDisplay.getHistory().addHistory(s);
			
		} else {
			myErrorLabel.setText(myResources.getString("NoCommandError"));
		}
	}
	
	public GridPane getConsole() {
		return gp;
	}
	
	public void clear() {
		myTextField.clear();
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
	}
	
	public SimpleObjectProperty<ObservableList<String>> getCommandList() {
		return myCommands;
	}
}
