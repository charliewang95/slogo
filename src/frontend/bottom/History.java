package frontend.bottom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;

/**
 * @author Charlie Wang
 *
 */
public class History {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private StringBuilder myBuilder;
	private ListView<String> lv;
	private ResourceBundle myResources;

	public History() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		lv = new ListView<>();
		lv.setPrefSize(Integer.parseInt(myResources.getString("HistoryWidth")),
				Integer.parseInt(myResources.getString("HistoryHeight")));
		//lv.setStyle("-fx-background-color: #8FB9EA");
		myBuilder = new StringBuilder();
	}

	public ListView<String> getHistory() {
		return lv;
	}

	public void addString(String str) {
		myBuilder.append(str);
		myBuilder.append("\n");
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
