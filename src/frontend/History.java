package frontend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class History {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private StringBuilder myBuilder;
	private ListView<String> lv;
	private VBox vb;
	private ResourceBundle myResources;

	public History() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		lv = new ListView<>();
		lv.setPrefSize(340, 150);
		myBuilder = new StringBuilder();
	}
	
	public ListView<String> getHistory() {
		return lv;
	}
	
	public void addString(String str) {
		myBuilder.append("\n");
		myBuilder.append(str);
	}
	
	public void clear() {
		myBuilder = new StringBuilder();
	}

	public void printHistoryToFile() {
		File file = new File("data/output.txt");
		String toWrite = myBuilder.toString();

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
