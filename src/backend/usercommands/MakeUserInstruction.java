package backend.usercommands;

import java.util.ArrayList;
import java.util.List;

import backend.Command;
import backend.CommandOperator;
import backend.Turtle;
import backend.VariableHouse;

public class MakeUserInstruction extends Command {
	
	private Turtle myTurtle;
	private VariableHouse myVariableHouse;
	private final String ONE = "1";
	private final char SPACE = ' ';
	private final char LEFT_BRACKET = '[';
	private final char RIGHT_BRACKET = ']';

	public MakeUserInstruction(Turtle t, VariableHouse vh, String val) {
		super("Variables", 0);
		myTurtle = t;
		myVariableHouse = vh;
		createCommand(val);
	}

	//assumes inputs is formatted correctly because error has already been thrown
	public String compute(ArrayList<Command> inputs) {
		return ONE;
	}
	
	private void createCommand(String s) {
		
		List<String> commandVars = new ArrayList<String>();
		s = s.trim();
		String commandName = getCommandName(s);
		System.out.println("1");
		int helperIndex = commandName.length(); //goes past the space
		helperIndex = pastNextChar(helperIndex, s, LEFT_BRACKET);
		System.out.println("2");
		helperIndex = makeCommandObjects(commandVars, s, helperIndex);
		System.out.println("3");
		helperIndex = pastNextChar(helperIndex, s, LEFT_BRACKET);
		System.out.println("4");
		String commandActions = makeCommandActions(s, helperIndex);
		
		myVariableHouse.makeCommands(commandName, commandVars, commandActions);
		
	}
	
	private String getCommandName(String s) {
		int helperIndex = s.indexOf(SPACE);
		if(! isSafeIndex(s,helperIndex) ) {
			return "";
		}
		return s.substring(0,helperIndex);
	}
	
	private int pastNextChar(int helperIndex, String s, char c) {
		while(helperIndex < s.length() && s.charAt(helperIndex) != c) {helperIndex++;}
		return ++helperIndex;
	}
	
	private int makeCommandObjects(List<String> commandObjects, String s, int index) {
		
		StringBuilder currentString = new StringBuilder();
		
		while(index < s.length() && s.charAt(index) != RIGHT_BRACKET) {
			char currentChar = s.charAt(index);
			if (currentChar != ' '){
				currentString.append(currentChar);
			} else {
				if (!currentString.toString().isEmpty()) {
					commandObjects.add(currentString.toString());
				}
				currentString = new StringBuilder();
			}
			index++;
		}
		
		if (!currentString.toString().isEmpty()) {
			commandObjects.add(currentString.toString());
		}
		
		if(! isSafeIndex(s,index) ) {
			return -1;
		}
		
		return index;
		
	}
	
	private String makeCommandActions(String s, int index) {
		int rightIndex = pastNextChar(index, s, RIGHT_BRACKET) - 1;
		String ans = s.substring(index, rightIndex);
		ans = ans.trim();
		return ans;
	}
	
	private boolean isSafeIndex(String s, int index) {
		if( index >= s.length() ) {
			//error
			return false;
		}
		return true;
	}

}

