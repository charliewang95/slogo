package backend.othercommands;

import java.util.ArrayList;

import main.Playground;
import backend.Command;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class IfElse extends Command{

	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	private final String ONE = "1";
	private String inString;
	private final String ZERO = "0";

	public IfElse(Playground p, Turtle t, VariableHouse vh, String com) {
		super("Variables", 0);
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
		inString = com;
	}

	public String compute(ArrayList<Command> inputs) {
		
		System.out.println("in String is this :" + inString);
		String repeatExp = inString.substring(0, inString.indexOf('[')).trim();
		Interpreter conditionalInterp = new Interpreter(myPlayground, myTurtle);
		conditionalInterp.interpretString(repeatExp);
		int repeat = Integer.parseInt(conditionalInterp.getOutput().substring(0, conditionalInterp.getOutput().indexOf('.')));
		
		String ifCommand = inString.substring(inString.indexOf('['), inString.indexOf(']'));

		ifCommand = ifCommand.replace('[', ' ');
		ifCommand = ifCommand.replace(']', ' ');
		
		String elseCommand = inString.substring(inString.indexOf(']')+1, inString.length());
		
		elseCommand = elseCommand.replace('[', ' ');
		elseCommand = elseCommand.replace(']', ' ');
		System.out.println("if command is this :"+ifCommand);
		System.out.println("else command is this :"+elseCommand);
		Interpreter i = new Interpreter(myPlayground, myTurtle);
		if (repeat!= 0){


			try {
				return i.interpretString(ifCommand).root.returnValue;
			} catch (SecurityException
					| IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else {
			try {
				return i.interpretString(elseCommand).root.returnValue;
			} catch (SecurityException
					| IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return ZERO;

	}

}
