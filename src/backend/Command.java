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
	
	public String getString() {
		return this.getClass().toString();
	}
	
	public boolean checkOneNumberInput(ArrayList<Command> inputs) {
		if(inputs.size() != 1) {
			//error length
			return false;
		} else if(! (inputs.get(0) instanceof CommandNumber) ) {
			//error not number
			return false;
		}
		return true;
	}
	
	abstract public String compute(ArrayList<Command> inputs);
	
}
