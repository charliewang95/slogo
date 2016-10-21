package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.CommandNumber;
import backend.Turtle;

public abstract class VerticalMove extends Command {
	
	public VerticalMove() {
		super("TurtleCommand", 1);
	}
	
	public String computeVerticalMove(Turtle myTurtle, ArrayList<Command> inputs, boolean forward) {
		
		if(inputs.size() != 1) {
			//error length
			return "";
		} else if(! (inputs.get(0) instanceof CommandNumber) ) {
			//error not number
			return "";
		}
		
		Integer pix = Integer.parseInt( inputs.get(0).compute(null) );
		
		//compute displacement
		//because direction 0 is north, xDis is sine and yDis is cosine
		int tDegree = (forward) ? myTurtle.getDirection() : -1*myTurtle.getDirection();
		int xDisplacement = (int) ( pix * Math.sin( tDegree*Math.PI/180 ) );
		int yDisplacement = (int) ( pix * Math.cos( tDegree*Math.PI/180 ) );
		
		//move turtle
		myTurtle.setMyX( myTurtle.getMyX() + xDisplacement );
		myTurtle.setMyY( myTurtle.getMyY() + yDisplacement );
		
		return pix.toString();
		
	}

}
