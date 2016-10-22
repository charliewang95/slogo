package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class ClearScreen extends PositionMove {
	
	private Turtle myTurtle;

	public ClearScreen(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		String output = setPosition(0, 0, myTurtle);
		myTurtle.eraseLines(); //needs to be implemented
		return output; //do this order to erase lines that setPosition might make
		
	}
	

}
