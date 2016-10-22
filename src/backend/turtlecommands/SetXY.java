package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetXY extends PositionMove {
	
	private Turtle myTurtle;

	public SetXY(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		int newX = getNumCommand(inputs, 0);
		int newY = getNumCommand(inputs, 1);
		
		return setPosition(newX, newY, myTurtle);
		
	}
	

}
