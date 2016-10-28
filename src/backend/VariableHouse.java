package backend;

import java.util.HashMap;

public class VariableHouse {
	
	private HashMap<String, String> variables; //value String will be a number in String form
	private HashMap<String, Command> commands; //value type might change soon
	
	public VariableHouse() {
		variables = new HashMap<String, String>();
		commands = new HashMap<String, Command>(); 
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
	
	public Command getCommands(String com) {
		if( ! commands.containsKey(com) ) {
			return null;
		}
		return commands.get(com);
	}
	
	public void makeCommands(String var, Command com) {
		commands.put(var, com);
	}
	

}
