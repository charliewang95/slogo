package backend.variables;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import main.Playground;
import backend.Command;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class RunUserInstruction extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	private final String ONE = "1";
	private String command;
	
	public RunUserInstruction(Playground p, Turtle t, VariableHouse vh, String com) {
		super("Variables", 0);
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
		command = com;
	}
	
	public String compute(ArrayList<Command> inputs) {
		
		if( ! myVariableHouse.isCommand(command)) {
			//error
			return "0";
		}
		
		Interpreter i = new Interpreter(myPlayground, myTurtle, myPlayground.getDisplay());
		
		List<String> variables = myVariableHouse.getCommandVars(command);
		String commands = myVariableHouse.getCommandActions(command);
		
		VariableHouse newVariableHouse = makeNewVariableHouse(variables);
		i.setVariableHouse(newVariableHouse);
		try {
			i.interpretString(commands);
		} catch (SecurityException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ONE;
		
	}
	
	private VariableHouse makeNewVariableHouse(List<String> variables) {
		VariableHouse ans = new VariableHouse(myPlayground.getDisplay());
		for(String s : variables) {
			ans.makeVariable( new String(s), new String( myVariableHouse.getVariable(s) ) );
		}
		return ans;
	}

}
