package frontend.bottom;

import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.javafx.collections.MappingChange.Map;

import backend.Interpreter;
import backend.observables.Communication;
import frontend.Display;
import frontend.ErrorException;
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
import javafx.stage.FileChooser;

/**
 * @author Charlie Wang
 */
public class Console {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	private Display myDisplay;
	private Communication myCommunicator;
	private Interpreter myInterpreter;
	private History myHistory;
	private TextArea myTextArea;
	private ListView<String> myOutputArea;
	private HBox myHBox;
	private VBox myLeftArea;
	private VBox myRightArea;
	private ResourceBundle myResources;
	private HashMap<String, String> newCommandsMap;
	private int bracketCount = 0;
	private SimpleObjectProperty<ObservableList<String>> myCommands;
	private SimpleObjectProperty<ObservableList<String>> myOutputs;
	private String savedString;

	public Console(Display display, Interpreter inter) {
		myInterpreter = inter;
		myDisplay = display;
		myLeftArea = new VBox();
		myRightArea = new VBox();
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myOutputs = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myHistory = new History(this);
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		newCommandsMap = new HashMap<String, String>();

		myHBox = new HBox();
		myHBox.setPadding(new Insets(10, 10, 10, 10));
		myHBox.setSpacing(10);
		myLeftArea.setSpacing(10);
		myRightArea.setSpacing(10);

		Label label1 = new Label(myResources.getString("ConsoleText"));
		myLeftArea.getChildren().add(label1);
		GridPane.setMargin(label1, new Insets(0, 0, 0, 5));

		Button button1 = new Button(myResources.getString("ConsoleButton"));
		myLeftArea.getChildren().add(button1);
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				checkInput(e);
				myOutputs.getValue().add(myInterpreter.getOutput());
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

		Button button3 = new Button(myResources.getString("ConsoleLoad"));
		GridPane.setConstraints(button3, 0, 3);
		myLeftArea.getChildren().add(button3);
		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(myResources.getString("LoadTitle"));
				FileChooser.ExtensionFilter filterTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().addAll(filterTXT);
				File preset = fileChooser.showOpenDialog(null);
				System.out.println(preset.getAbsolutePath());
				String[] inputStrings = myInterpreter.convertFileToString(preset.getAbsolutePath());
				StringBuilder sBuild = new StringBuilder();
				for (int i = 0; i < inputStrings.length; i++){
					if (inputStrings[i].contains("#")){
						int commentIndex = inputStrings[i].indexOf("#");
						inputStrings[i] = inputStrings[i].substring(0, commentIndex);
					}
				sBuild.append(" " + inputStrings[i]);
				}
				System.out.println(sBuild.toString());
				myInterpreter.interpretString(sBuild.toString());
//				myInterpreter.loadCommands(preset);
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
				myOutputs.getValue().add(myInterpreter.getOutput());
			}
		});

		Label label2 = new Label(myResources.getString("HistoryTitle"));
		GridPane.setMargin(myRightArea, new Insets(0, 0, 0, 10));

		myHBox.getChildren().add(myRightArea);
		myRightArea.getChildren().add(label2);
		myHBox.getChildren().add(myHistory.getHistory());

		Label label3 = new Label(myResources.getString("OutputText"));
		myHBox.getChildren().add(label3);
		GridPane.setMargin(label3, new Insets(0, 0, 0, 5));
		myOutputArea = new ListView<>();
		myOutputArea.itemsProperty().bind(myOutputs);
		myOutputArea.setPrefSize(Integer.parseInt(myResources.getString("OutputWidth")),
				Integer.parseInt(myResources.getString("OutputHeight")));
		myHBox.getChildren().add(myOutputArea);

	}

	private void checkInput(Event e) {
		bracketCount = 0;
		if ((myTextArea.getText().trim() != null && !myTextArea.getText().trim().isEmpty())) {
			String str = myTextArea.getText().trim().replace(">> ", "").replace("\t", "").replace("\n", " ")
					.replaceAll(" +", " ");
			String[] words = str.split(" ");
			for (int i=0; i<words.length; i++) {
				if (words[i].equals("[")) {
					bracketCount++;
				} else if (words[i].equals("]")) {
					bracketCount--;
					if (bracketCount < 0) {
						ErrorException ee = new ErrorException(myResources.getString("UnbalancedCommand"));
					}
				}
				if (newCommandsMap.containsKey(words[i])) {
					words[i] = newCommandsMap.get(words[i]);
				}
			}
			StringBuilder sb = new StringBuilder();
			for (String word : words) {
				sb.append(word+" ");
			}
			if (bracketCount == 0) {
				str = sb.toString().trim();
				System.out.println("checking: " + str);
				myTextArea.clear();
				e.consume();
				try {
					interpretInput(str);
					myCommands.getValue().add(str);
					myDisplay.updateText();
				} catch (Exception exception) {
					ErrorException ee = new ErrorException(myDisplay, myResources.getString("WrongCommand"),
							myResources.getString("OnlineHelp"), myResources.getString("DefineNew"), str);
				}
			}
		}
	}

	public void interpretInput(String input) {
		myInterpreter.interpretString(input);
	}

	public HBox getConsole() {
		return myHBox;
	}

	public void setLanguage(String language) {
		myInterpreter.setLanguage(language);
	}

	public History getHistory() {
		return myHistory;
	}

	public void clear() {
		myTextArea.clear();
		myCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myHistory.getHistory().itemsProperty().bind(myCommands);
		newCommandsMap = new HashMap<String, String>();
		myOutputs = new SimpleObjectProperty<ObservableList<String>>();
		myOutputArea.itemsProperty().bind(myOutputs);
	}

	public void updateOutput(String out) {
		myOutputs.getValue().add(out);
	}

	public SimpleObjectProperty<ObservableList<String>> getCommandList() {
		return myCommands;
	}

	public void saveNewCommands(String user, String existed) {
		newCommandsMap.put(user, existed);
	}

	public void updateText() {
		myDisplay.updateText();
	}
}
