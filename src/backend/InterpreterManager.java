package backend;

import java.util.List;

import main.Playground;

public class InterpreterManager {
	
	private StringInterpreter stringInterp = new StringInterpreter();
	private CommandListInterpreter commListInterp = new CommandListInterpreter();
//	private Interpreter mainInterp = new Interpreter();
	private Playground myPlayground;
	private Turtle turtle;
	private String myLanguage;
	
	public InterpreterManager(Playground myPlay, Turtle turtle){
		this.myPlayground = myPlay;
		this.turtle = turtle;
	}
	
	public void InterpretUserInput(String input){
		List<String> stringList = stringInterp.interpretString(input);
		List<Command> commList = commListInterp.createCommandList(stringList);
//		mainInterp.parseTree(mainInterp.createCommandTree(commList).root);
	}

	public void setLanguage(String language){
		System.out.println(myLanguage);
		myLanguage = stringInterp.setLanguage(language);
		System.out.println(myLanguage);
	}

}
