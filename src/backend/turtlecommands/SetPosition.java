package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetPosition extends PositionMove {
	
	private Turtle myTurtle;

	public SetPosition(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		int newX = (int) (double) getNumCommand(inputs, 0);
		int newY = (int) (double) getNumCommand(inputs, 1);
		
		return setPosition(newX, newY, myTurtle);
		
	}
	

}
