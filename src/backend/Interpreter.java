package backend;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {
	
	private ProgramParser parse = new ProgramParser();
	
	public Command interpretString(String input){
		
		//parse.addPatterns(command);
		separateStrings(input);
		
		return null;
		
	}
	
	private List<String> separateStrings(String input){
		List<String> stringList = new ArrayList<String>();
		StringBuilder currentString = new StringBuilder();
		input = input + " ";
		for (int i = 0; i < input.length(); i++){
			char currentChar = input.charAt(i);
			
			//refactor this later
			if (currentChar != ' '){
				currentString.append(currentChar);
			}
			else{
				if (!currentString.toString().isEmpty()){
					stringList.add(currentString.toString());
				}
				currentString = new StringBuilder();
			}
			
		}
		System.out.println(stringList);
		return stringList;
		
	}
	

}
