package frontend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class History {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
	
	private StringBuilder myBuilder;
	private Label myHistory;
	private Display myDisplay;
	private ScrollPane sp;
	private VBox vb;
	private ResourceBundle myResources;
	
	public History (Display display) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		myDisplay = display;
		myHistory = new Label();
		Label title = new Label("  "+myResources.getString("HistoryTitle"));
		title.setTextFill(Color.BLUE);
		title.setFont(Font.font("Verdana", 14));
		
		myBuilder = new StringBuilder();
		sp = new ScrollPane(); 
		sp.setContent(myHistory);
		//sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setPrefSize(150, 500);
		sp.setVvalue(1.0); 
		
		vb = new VBox(title, sp);
		VBox.setMargin(sp, new Insets(10, 10, 10, 10));
		VBox.setMargin(title, new Insets(10, 0, 10, 10));
		//myHistory.textProperty().bind(myDisplay.getConsole().getCommandList());
	}
	
	public VBox getHistory() {
		return vb;
	}
	
	public void addHistory(String s) {
		myBuilder.append(s);
		myBuilder.append("\n");
		myHistory.setText(myBuilder.toString());
	}
	
	public void clear() {
		myBuilder = new StringBuilder();
		myHistory.setText("");
	}
	
	public void printHistoryToFile() {
		File file = new File("data/output.txt");
		String toWrite = myHistory.getText();

		try (FileOutputStream fos = new FileOutputStream(file)) {
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] byteArray = toWrite.getBytes();
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
