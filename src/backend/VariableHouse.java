package backend;

import java.util.HashMap;
import java.util.List;

public class VariableHouse {
	
	private HashMap<String, String> variables; //value String will be a number in String form
	private HashMap<String, List<String>> commandVars;
	private HashMap<String, String> commandActions;
	
	public VariableHouse() {
		variables = new HashMap<String, String>();
		commandVars = new HashMap<String, List<String>>();
		commandActions = new HashMap<String, String>();
	}
	
	public boolean isVariable(String var) {
		return variables.containsKey(var);
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
	
	public boolean isCommand(String com) {
		return commandVars.containsKey(com) && commandActions.containsKey(com);
	}
	
	public List<String> getCommandVars(String com) {
		if( ! commandVars.containsKey(com) ) {
			return null;
		}
		return commandVars.get(com);
	}
	
	public String getCommandActions(String com) {
		if( ! commandActions.containsKey(com) ) {
			return null;
		}
		return commandActions.get(com);
	}
	
	public void makeCommands(String var, List<String> vars, String comms) {
		commandVars.put(var, vars);
		commandActions.put(var, comms);
	}
	

}
