package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetXY extends Command {
	
	private Turtle myTurtle;

	public SetXY(Turtle t) {
		super("TurtleCommand", 1);
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		int newX = getNumCommand(inputs, 0);
		int newY = getNumCommand(inputs, 1);
		Integer distance = computeDistance(newX,newY);
		
		//should this draw a line?
		myTurtle.setMyX(newX);
		myTurtle.setMyY(newY);
		
		return distance.toString();
		
	}
	
	private Integer computeDistance(int x, int y) {
		
		return (int) Math.sqrt( Math.pow(x-myTurtle.getMyX(), 2) + Math.pow(y-myTurtle.getMyY(), 2) );
		
	}
	

}
