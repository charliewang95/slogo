package frontend.right;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

	public Variable() {
		myVBox = new VBox();
		myVBox.setSpacing(10);

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

		Label title1 = new Label("        " + myResources.getString("VariableTitle"));
		title1.setTextFill(Color.BLUE);
		title1.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("VariableFontSize"))));
		VBox.setMargin(title1, new Insets(20, 10, 0, 10));
		
		myVBox.getChildren().add(title1);
		VBox.setMargin(lvVariable, new Insets(0, 10, 0, 10));
		myVBox.getChildren().add(lvVariable);

		Label title2 = new Label("    " + myResources.getString("NewCommandsTitle"));
		title2.setTextFill(Color.BLUE);
		title2.setFont(Font.font(myResources.getString("TitleFont"),
				Integer.parseInt(myResources.getString("NewCommandsFontSize"))));

		VBox.setMargin(title2, new Insets(5, 10, 0, 10));
		myVBox.getChildren().add(title2);
		VBox.setMargin(lvCommands, new Insets(0, 10, 0, 10));
		myVBox.getChildren().add(lvCommands);
	}

	public VBox getVariable() {
		return myVBox;
	}
	
	public void updateVariable(ArrayList<String> map) {
		myNewVariables = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		myNewVariables.getValue().addAll(map);
		lvVariable.itemsProperty().bind(myNewVariables);
	}
	
	public void bindNewCommands(String user, String existed) {
		myNewCommands.getValue().add(user+" = "+existed);
	}
	
	public void clear() {
		myNewCommands = new SimpleObjectProperty<>(FXCollections.observableArrayList());
		lvCommands.itemsProperty().bind(myNewCommands);
	}
}
