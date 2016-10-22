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
	
	public boolean checkNoInputs(ArrayList<Command> inputs) {
		if(inputs.size() != 0) {
			//error length
			return false;
		}
		return true;
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
	
	public boolean checkTwoNumberInput(ArrayList<Command> inputs) {
		if(inputs.size() != 2) {
			//error length
			return false;
		} else if(! (inputs.get(0) instanceof CommandNumber) ) {
			//error not number
			return false;
		} else if(! (inputs.get(1) instanceof CommandNumber) ) {
			//error not number
			return false;
		}
		return true;
	}
	
	protected Double getNumCommandDouble(ArrayList<Command> inputs, int index) {
		return Double.parseDouble(inputs.get(index).compute(null));
	}
	
	protected Integer getNumCommand(ArrayList<Command> inputs, int index) {
		return (int) (double) Double.parseDouble(inputs.get(index).compute(null));
	}
	
	abstract public String compute(ArrayList<Command> inputs);
	
}
