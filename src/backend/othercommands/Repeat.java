package backend.othercommands;

import java.util.ArrayList;
import java.util.List;

import main.Playground;
import backend.Command;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class Repeat extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	private final String ONE = "1";
	private String inString;
	private final char SPACE = ' ';

	public Repeat(Playground p, Turtle t, VariableHouse vh, String com) {
		super("Variables", 0);
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
		inString = com;
	}

	public String compute(ArrayList<Command> inputs) {
		
		int repeat = Integer.parseInt(inString.substring(0, inString.indexOf(" ")));
		int spaceIndex = inString.indexOf(" ");
		String command = inString.substring(inString.indexOf(" "), inString.length() - 1);
		
		command = command.replace('[', ' ');
		command = command.replace(']', ' ');
		
		System.out.println(command);
		for (int j = 0; j < repeat; j++){
//		if( ! myVariableHouse.isCommand(command)) {
			//error
//			return "0";
//		}
		System.out.println("help");
		
		Interpreter i = new Interpreter(myPlayground, myTurtle);
		
//		List<String> variables = myVariableHouse.getCommandVars(command);
//		String commands = myVariableHouse.getCommandActions(command);
		
//		VariableHouse newVariableHouse = makeNewVariableHouse(variables);
//		i.setVariableHouse(newVariableHouse);
		try {
			i.interpretString(command);
		} catch (SecurityException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return ONE;
		
	}
	
	private VariableHouse makeNewVariableHouse(List<String> variables) {
		VariableHouse ans = new VariableHouse();
		for(String s : variables) {
			ans.makeVariable( new String(s), new String( myVariableHouse.getVariable(s) ) );
		}
		return ans;
	}

}