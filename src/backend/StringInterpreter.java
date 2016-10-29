package backend;

import java.util.ArrayList;
import java.util.List;

public class StringInterpreter {
	
	private ProgramParser parse;
	private String myLanguage;
	private List<String> stringList;
	
	public List<String> interpretString(String input){
		parse = new ProgramParser();
		System.out.println("HEY");
		List<String> stringList = separateStrings(input);
		List<String> parsedList = new ArrayList<String>();
		if (myLanguage == null){
			myLanguage = "English";
		}
		System.out.println("hellO");
		parse.addPatterns("resources.languages/" + myLanguage);
		parse.addPatterns("resources.languages/Syntax");
		for (int i = 0; i < stringList.size(); i++){
			parsedList.add(parse.getSymbol(stringList.get(i)));
		}
//		createCommandTree(createCommandList(parsedList));
//
//		parseTree(commandTree.root);

		return stringList;

	}

	private List<String> separateStrings(String input){
		stringList = new ArrayList<String>();
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
		return stringList;

	}
	
	public String setLanguage(String language){
		myLanguage = language;
		return myLanguage;
	}

}
