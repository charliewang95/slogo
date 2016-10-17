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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * @author Charlie Wang
 *
 */
public class Console {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private TextField myTextField;
	private Button myButton;
	private Label myErrorLabel;
	private HBox hb;
	private GridPane gp;
	private ResourceBundle myResources;
	private SimpleObjectProperty<ObservableList<String>> myCommands;

	public Console() {
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(10);
		gp.setHgap(5);

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
		
		myButton = new Button(myResources.getString("ConsoleButton"));
		GridPane.setConstraints(myButton, 2, 0);
		gp.getChildren().add(myButton);
		myButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				checkInput();
			}
		});
	}

	private void checkInput() {
		if ((myTextField.getText() != null && !myTextField.getText().isEmpty())) {
			myCommands.getValue().add(myTextField.getText());
			myTextField.clear();
			myErrorLabel.setText("");
		} else {
			myErrorLabel.setText(myResources.getString("NoCommandError"));
		}
	}
	
	public GridPane getConsole() {
		return gp;
	}
	
	public SimpleObjectProperty<ObservableList<String>> getCommandList() {
		return myCommands;
	}
}
