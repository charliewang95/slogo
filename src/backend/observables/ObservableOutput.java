package backend.observables;

import java.util.Observable;

public class ObservableOutput extends Observable{
	private String output;

	public ObservableOutput(String out) {
		output = out;
	}

	public void setOutput(String out) {
		output = out;
	}

	public String getOutput() {
		return output;
	}
}
