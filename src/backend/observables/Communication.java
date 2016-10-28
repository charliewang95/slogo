package backend.observables;

import java.util.Observable;

public class Communication extends Observable{
	private String output;

	public Communication() {
		output = null;
	}

	public void setOutput(String out) {
		output = out;
	}

	public String getOutput() {
		return output;
	}
}
