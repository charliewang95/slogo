package frontend.bottom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * @author Charlie Wang
 *
 */
public class History {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";

	private StringBuilder myBuilder;
	private Console myConsole;
	private ListView<String> lv;
	private ResourceBundle myResources;

	public History(Console console) {
		myConsole = console;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
		lv = new ListView<>();
		lv.itemsProperty().bind(myConsole.getCommandList());
		lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2 && lv.getSelectionModel().getSelectedItem()!=null) {
					myConsole.interpretInput(lv.getSelectionModel().getSelectedItem());
					myConsole.updateText();
				}
			}
		});

		lv.setPrefSize(Integer.parseInt(myResources.getString("HistoryWidth")),
				Integer.parseInt(myResources.getString("HistoryHeight")));

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
