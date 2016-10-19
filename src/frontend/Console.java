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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author Charlie Wang
 */
public class Console {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Display myDisplay;
	private History myHistory;
	private TextArea myTextArea;
	private GridPane myGridPane;
	private VBox myLeftArea;
	private VBox myMidArea;
	private ResourceBundle myResources;
	private int bracketCount = 0;
	private SimpleObjectProperty<ObservableList<String>> myCommands;

	public Console(Display display) {
		myDisplay = display;
		myLeftArea = new VBox();
		myMidArea = new VBox();
		myHistory = new History();
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");

		myGridPane = new GridPane();
		myGridPane.setPadding(new Insets(10, 10, 10, 10));
		myGridPane.setVgap(10);
		myGridPane.setHgap(5);
		myLeftArea.setSpacing(10);
		myMidArea.setSpacing(10);

		Label label1 = new Label(myResources.getString("ConsoleText"));
		myLeftArea.getChildren().add(label1);
		GridPane.setMargin(label1, new Insets(0, 0, 0, 5));

		Button button1 = new Button(myResources.getString("ConsoleButton"));
		myLeftArea.getChildren().add(button1);
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				checkInput();
			}
		});

		Button button2 = new Button(myResources.getString("ConsoleClear"));
		GridPane.setConstraints(button2, 0, 2);
		myLeftArea.getChildren().add(button2);
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myTextArea.clear();
			}
		});
		GridPane.setConstraints(myLeftArea, 0, 0);
		myGridPane.getChildren().add(myLeftArea);

		myTextArea = new TextArea();
		myTextArea.setPromptText(myResources.getString("ConsoleHint"));
		myTextArea.setPrefColumnCount(Integer.parseInt(myResources.getString("ConsoleColumn")));
		myTextArea.getText();
		myTextArea.setPrefSize(340, 150);
		GridPane.setConstraints(myTextArea, 1, 0);
		myGridPane.getChildren().add(myTextArea);
		myTextArea.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				if (myTextArea.getText().trim().charAt(myTextArea.getText().trim().length() - 1) == '[') {
					bracketCount++;
				} else if (myTextArea.getText().trim().charAt(myTextArea.getText().trim().length() - 1) == ']') {
					bracketCount--;
				} 
				if (bracketCount == 0) {
					checkInput();
					e.consume();
				}
			}
		});

		Label label2 = new Label(myResources.getString("HistoryTitle"));
		GridPane.setMargin(myMidArea, new Insets(0, 0, 0, 10));

		GridPane.setConstraints(myMidArea, 2, 0);
		myGridPane.getChildren().add(myMidArea);
		myMidArea.getChildren().add(label2);

		myHistory.getHistory().itemsProperty().bind(myCommands);
		GridPane.setConstraints(myHistory.getHistory(), 3, 0);
		myGridPane.getChildren().add(myHistory.getHistory());
	}

	private void checkInput() {
		if ((myTextArea.getText().trim() != null && !myTextArea.getText().trim().isEmpty())) {
			String s = myTextArea.getText();
			myCommands.getValue().add(s);
			myHistory.addString(s);
			myTextArea.clear();
		}
	}

	public GridPane getConsole() {
		return myGridPane;
	}

	public History getHistory() {
		return myHistory;
	}

	public void clear() {
		myTextArea.clear();
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myHistory.getHistory().itemsProperty().bind(myCommands);
	}

	public SimpleObjectProperty<ObservableList<String>> getCommandList() {
		return myCommands;
	}
}
