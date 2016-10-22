package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Home extends PositionMove {
	
	private Turtle myTurtle;

	public Home(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		return setPosition(0, 0, myTurtle);
		
	}
	

}
