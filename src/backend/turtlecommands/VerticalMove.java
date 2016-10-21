package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.CommandNumber;
import backend.Turtle;

public abstract class VerticalMove extends Command {
	
	public VerticalMove() {
		super("TurtleCommand", 1);
	}
	
	public String computeVerticalMove(Turtle turtle, ArrayList<Command> inputs, boolean forward) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Integer pix = Integer.parseInt( inputs.get(0).compute(null) );
		
		//compute displacement
		//because direction 0 is north, xDis is sine and yDis is cosine
		int tDegree = (forward) ? turtle.getDirection() : -1*turtle.getDirection();
		int xDisplacement = (int) ( pix * Math.sin( tDegree*Math.PI/180 ) );
		int yDisplacement = (int) ( pix * Math.cos( tDegree*Math.PI/180 ) );
		
		//move turtle (does not deal with lines or going off the board)
		turtle.setMyX( turtle.getMyX() + xDisplacement );
		turtle.setMyY( turtle.getMyY() + yDisplacement );
		
		return pix.toString();
		
	}

}
