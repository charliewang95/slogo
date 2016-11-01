package backend.variables;

import java.util.List;

import main.Playground;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class RunUserInstruction {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	
	public RunUserInstruction(Playground p, Turtle t, VariableHouse vh) {
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
	}
	
	public void compute(String command) {
		
		if( ! myVariableHouse.isCommand(command)) {
			//error
			return;
		}
		
		Interpreter i = new Interpreter(myPlayground, myTurtle);
		
		List<String> variables = myVariableHouse.getCommandVars(command);
		String commands = myVariableHouse.getCommandActions(command);
		
		VariableHouse newVariableHouse = makeNewVariableHouse(variables);
		i.setVariableHouse(newVariableHouse);
		i.interpretString(commands);
		
	}
	
	private VariableHouse makeNewVariableHouse(List<String> variables) {
		VariableHouse ans = new VariableHouse();
		for(String s : variables) {
			ans.makeVariable( new String(s), new String( myVariableHouse.getVariable(s) ) );
		}
		return ans;
	}

}
