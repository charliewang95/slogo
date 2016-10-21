package backend;

import java.util.ArrayList;

public abstract class Command {
	private String commandType;
	private int numInputs;
	
	public Command(String type, int inputs){
		commandType = type;
		numInputs = inputs;
	}
	
	
	public String getType(){
		return commandType;
	}
	
	public int getNumInputs() {
		return numInputs;
	}
	
	abstract public String compute(ArrayList<Command> inputs);
	
}
