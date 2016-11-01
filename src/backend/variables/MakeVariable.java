package backend.variables;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;
import backend.VariableHouse;

public class MakeVariable extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private String varValue;
	private final char SPACE = ' ';

	public MakeVariable(Turtle t, VariableHouse vh, String val) {
		super("Variables", 0);
		myTurtle = t;
		myVariableHouse = vh;
		createVariable(val);
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		return varValue;
	}
	
	private void createVariable(String s) {
		s = s.trim();
		int indexOfFirstSpace = s.indexOf(SPACE);
		String varName = s.substring(0, indexOfFirstSpace);
		
		int indexOfLastSpace = s.lastIndexOf(SPACE);
		String varVal = s.substring(indexOfLastSpace+1);
		System.out.println("var val is " +varVal);
		
		varValue = new String(varVal);
		myVariableHouse.makeVariable(varName, varVal);
		
	}

}
