package backend.othercommands;

import java.util.ArrayList;

import main.Playground;
import backend.Command;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class If extends Command{

	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	private final String ONE = "1";
	private String inString;
	private final String ZERO = "0";

	public If(Playground p, Turtle t, VariableHouse vh, String com) {
		super("Variables", 0);
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
		inString = com;
	}

	public String compute(ArrayList<Command> inputs) {
		String repeatExp = inString.substring(0, inString.indexOf('[')).trim();
		Interpreter i2 = new Interpreter(myPlayground, myTurtle, myPlayground.getDisplay());
		i2.interpretString(repeatExp);
		int repeat = Integer.parseInt(i2.getOutput().substring(0, i2.getOutput().indexOf('.')));
//		int repeat = 0;
		String command = inString.substring(inString.indexOf('['), inString.indexOf(']'));
		
		command = command.replace('[', ' ');
		command = command.replace(']', ' ');
		
		if (repeat!= 0){
		
		Interpreter i = new Interpreter(myPlayground, myTurtle, myPlayground.getDisplay());
		
		try {
			return i.interpretString(command).root.returnValue;
		} catch (SecurityException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return ZERO;
		
	}
	
}
