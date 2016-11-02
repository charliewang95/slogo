package frontend.right;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import frontend.Display;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Charlie Wang
 *
 */
public class Variable {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private ListView<String> lvVariable;
	private ListView<String> lvCommands;
	private SimpleObjectProperty<ObservableList<String>> myNewCommands;
	private SimpleObjectProperty<ObservableList<String>> myNewVariables;
	private VBox myVBox;
	private ResourceBundle myResources;
	private Display myDisplay;

	public Variable(Display display) {
		myVBox = new VBox();
		myVBox.setSpacing(10);
		myDisplay = display;

		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		lvVariable = new ListView<>();
		lvCommands = new ListView<>();
		myNewCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myNewVariables = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		lvCommands.itemsProperty().bind(myNewCommands);
		lvVariable.itemsProperty().bind(myNewVariables);

		lvVariable.setPrefSize(Integer.parseInt(myResources.getString("VariableWidth")),
				Integer.parseInt(myResources.getString("VariableHeight")));
		lvCommands.setPrefSize(Integer.parseInt(myResources.getString("VariableWidth")),
				Integer.parseInt(myResources.getString("VariableHeight")));

		setTitle1();
		setVariableBoxEvent();
		setTitle2();
		setCommandsBoxEvent();
	}

	public void setVariableBoxEvent() {
		VBox.setMargin(lvVariable, new Insets(0, 10, 0, 10));
		myVBox.getChildren().add(lvVariable);

		lvVariable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2 && lvVariable.getSelectionModel().getSelectedItem() != null) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle(myResources.getString("ChangeVariableTitle"));
					dialog.setHeaderText(lvVariable.getSelectionModel().getSelectedItem().split(" ")[0]);
					dialog.setContentText(myResources.getString("NewVariableHint"));
					Optional<String> newFunc = dialog.showAndWait();
					if (newFunc.isPresent()) {
						myDisplay.changeVariable(lvVariable.getSelectionModel().getSelectedItem().split(" ")[0],
								newFunc.get());
					}
				}
			}
		});
	}

	private void setCommandsBoxEvent() {
		VBox.setMargin(lvCommands, new Insets(0, 10, 0, 10));
		myVBox.getChildren().add(lvCommands);

		lvCommands.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2 && lvCommands.getSelectionModel().getSelectedItem() != null) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle(myResources.getString("ChangeFunctionTitle"));
					dialog.setHeaderText(lvCommands.getSelectionModel().getSelectedItem().split(" ")[0]);
					dialog.setContentText(myResources.getString("NewFunctionHint"));
					Optional<String> newFunc = dialog.showAndWait();
					if (newFunc.isPresent()) {
						myDisplay.saveNewCommands(lvCommands.getSelectionModel().getSelectedItem().split(" ")[0],
								newFunc.get());
						myDisplay.bindNewCommands(lvCommands.getSelectionModel().getSelectedItem().split(" ")[0],
								newFunc.get());
					}
				}
			}
		});
	}

	private void setTitle1() {
		Label title1 = new Label("        " + myResources.getString("VariableTitle"));
		title1.setTextFill(Color.BLUE);
		title1.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("VariableFontSize"))));
		VBox.setMargin(title1, new Insets(20, 10, 0, 10));
		myVBox.getChildren().add(title1);
	}

	private void setTitle2() {
		Label title2 = new Label("    " + myResources.getString("NewCommandsTitle"));
		title2.setTextFill(Color.BLUE);
		title2.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("NewCommandsFontSize"))));
		VBox.setMargin(title2, new Insets(5, 10, 0, 10));
		myVBox.getChildren().add(title2);
	}

	public VBox getVariable() {
		return myVBox;
	}

	public void updateVariable(ArrayList<String> map) {
		myNewVariables = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myNewVariables.getValue().addAll(map);
		lvVariable.itemsProperty().bind(myNewVariables);
	}

	public void bindNewCommands(ArrayList<String> arr) {
		myNewCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myNewCommands.getValue().addAll(arr);
		lvCommands.itemsProperty().bind(myNewCommands);
	}

	public void clear() {
		myNewCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		lvCommands.itemsProperty().bind(myNewCommands);
	}
}
