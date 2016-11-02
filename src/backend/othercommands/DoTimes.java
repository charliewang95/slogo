package backend.othercommands;

import java.util.ArrayList;

import main.Playground;
import backend.Command;
import backend.Interpreter;
import backend.Turtle;
import backend.VariableHouse;

public class DoTimes extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private Playground myPlayground;
	private final String ZERO = "0";
	private String inString;
	private final char SPACE = ' ';

	public DoTimes(Playground p, Turtle t, VariableHouse vh, String com) {
		super("Variables", 0);
		myPlayground = p;
		myTurtle = t;
		myVariableHouse = vh;
		inString = com;
	}

	public String compute(ArrayList<Command> inputs) {
		
		System.out.println("inString is " +inString);
		int counter = 0;
		int placeHolder = 0;
		inString = inString.substring(1, inString.length());
		for (int i = 0; i < inString.length(); i++){
			if (inString.charAt(i) == '['){
				counter++;
			}
			if (counter == 2){
				placeHolder = i;
				break;
			}
		}
		System.out.println(inString.indexOf('['));
		String repeatExpr = inString.substring(0, inString.indexOf('['));

		
		repeatExpr = repeatExpr.replace('[', ' ');
		repeatExpr = repeatExpr.replace(']', ' ');
		repeatExpr.trim();
		Interpreter iTemp = new Interpreter(myPlayground, myTurtle);
		int times = 0;
		System.out.println("ayy  lmao " +repeatExpr);
		repeatExpr = repeatExpr.replaceAll("\\s", "");
		try {
			String timesString = iTemp.interpretString(repeatExpr).root.value;
			times = Integer.parseInt(timesString);
			
		}
		catch (Exception e){
			if (myVariableHouse.isVariable(repeatExpr)){
				System.out.println("hello");
				times = Integer.parseInt(myVariableHouse.getVariable(repeatExpr));
			}
//			times = ;
		}
		System.out.println("times = " +times);
		
		String command = inString.substring(inString.indexOf('['), inString.length());
		
		command = command.replace('[', ' ');
		command = command.replace(']', ' ');
		
		System.out.println(command);
		String returnVal = ZERO;
		for (int j = 0; j < times; j++){
		
		Interpreter i = new Interpreter(myPlayground, myTurtle);
		
//		List<String> variables = myVariableHouse.getCommandVars(command);
//		String commands = myVariableHouse.getCommandActions(command);
		
//		VariableHouse newVariableHouse = makeNewVariableHouse(variables);
//		i.setVariableHouse(newVariableHouse);
		try {
			returnVal = i.interpretString(command).root.returnValue;
		} catch (SecurityException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		return returnVal;
		
	}
	

}
