package backend.observables;

import java.util.Observable;

public class Communication extends Observable{
	private String output;

	public Communication() {
		output = null;
	}
	
	public Communication(String str) {
	        output = str;
	}

	public void setOutput(String out) {
		output = out;
	        setChanged();
	        notifyObservers();
	}

	public String getOutput() {
		return output;
	}
}
