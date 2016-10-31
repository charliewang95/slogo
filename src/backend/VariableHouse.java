package backend;

import java.util.HashMap;
import java.util.List;

public class VariableHouse {
	
	private HashMap<String, String> variables; //value String will be a number in String form
	private HashMap<String, List<String>> commandVars;
	private HashMap<String, List<String>> commandActions;
	
	public VariableHouse() {
		variables = new HashMap<String, String>();
		commandVars = new HashMap<String, List<String>>();
		commandActions = new HashMap<String, List<String>>();
	}
	
	public String getVariable(String var) {
		if( ! variables.containsKey(var) ) {
			return null;
		}
		return variables.get(var);
	}
	
	public void makeVariable(String var, String val) {
		variables.put(var, val);
	}
	
	public List<String> getCommandVars(String com) {
		if( ! commandVars.containsKey(com) ) {
			return null;
		}
		return commandVars.get(com);
	}
	
	public List<String> getCommandActions(String com) {
		if( ! commandActions.containsKey(com) ) {
			return null;
		}
		return commandActions.get(com);
	}
	
	public void makeCommands(String var, List<String> vars, List<String> comms) {
		commandVars.put(var, vars);
		commandActions.put(var, comms);
	}
	

}
