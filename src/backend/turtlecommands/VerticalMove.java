package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
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
		
		Integer pix = getNumCommand(inputs, 0);
		
		//compute displacement
		//because direction 0 is north, xDis is sine and yDis is cosine
		double tDegree = turtle.getDirection();//(forward) ? turtle.getDirection() : -1*turtle.getDirection();
		double xDisplacement = ( pix * Math.sin( tDegree*Math.PI/180 ) );
		double yDisplacement = ( pix * Math.cos( tDegree*Math.PI/180 ) );
		
		//move turtle (does not deal with lines or going off the board)
		turtle.setMyX( turtle.getMyX() + ((forward) ?  xDisplacement : -1*xDisplacement)  );
		turtle.setMyY( turtle.getMyY() + ((forward) ? yDisplacement : -1*yDisplacement ));
		
		return pix.toString();
		
	}

}
