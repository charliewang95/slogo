package frontend.bottom;

import java.util.ResourceBundle;

import backend.Interpreter;
import frontend.Display;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private HBox myHBox;
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
		
		myHBox = new HBox();
		myHBox.setPadding(new Insets(10, 10, 10, 10));
		myHBox.setSpacing(10);
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
				checkInput(e);
			}
		});

		Button button2 = new Button(myResources.getString("ConsoleClear"));
		GridPane.setConstraints(button2, 0, 2);
		myLeftArea.getChildren().add(button2);
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myTextArea.clear();
				e.consume();
			}
		});
		myHBox.getChildren().add(myLeftArea);
		
		myTextArea = new TextArea();
		myTextArea.setPromptText(myResources.getString("ConsoleHint"));
		myTextArea.setPrefColumnCount(Integer.parseInt(myResources.getString("ConsoleColumn")));
		myTextArea.getText();
		myTextArea.setPrefSize(Integer.parseInt(myResources.getString("ConsoleWidth")),
				Integer.parseInt(myResources.getString("ConsoleHeight")));
		myHBox.getChildren().add(myTextArea);
		myTextArea.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				checkInput(e);
			}
		});
		
		Label label2 = new Label(myResources.getString("HistoryTitle"));
		GridPane.setMargin(myMidArea, new Insets(0, 0, 0, 10));

		myHBox.getChildren().add(myMidArea);
		myMidArea.getChildren().add(label2);

		myHistory.getHistory().itemsProperty().bind(myCommands);
		myHBox.getChildren().add(myHistory.getHistory());
	}

	private void checkInput(Event e) {
		if ((myTextArea.getText().trim() != null && !myTextArea.getText().trim().isEmpty())) {
			int words = myTextArea.getText().trim().split(" ").length;
			if (myTextArea.getText().trim().split(" ")[words-1].equals("[")) {
				bracketCount++;
			} else if (myTextArea.getText().trim().split(" ")[words-1].equals("]")) {
				bracketCount--;
			}
			if (bracketCount == 0) {
				String s = myTextArea.getText().replace("\n", " ");
				myCommands.getValue().add(s);
				myHistory.addString(s);
				interpretInput(s);
				myTextArea.clear();
				e.consume();
			}
		}
	}
	
	public void interpretInput(String input){
		try {
			//System.out.println(input);
			Interpreter.class.newInstance().interpretString(input);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HBox getConsole() {
		return myHBox;
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