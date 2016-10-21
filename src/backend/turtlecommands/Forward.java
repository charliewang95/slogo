package backend.turtlecommands;
import java.util.ArrayList;

import backend.*;

public class Forward extends Command {
	
	private Turtle myTurtle;
	
	Forward(Turtle t) {
		super("TurtleCommand", 1);
		myTurtle = t;
	}
		
	public String compute(ArrayList<Command> inputs) {
		
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
		int tDegree = myTurtle.getDirection();
		int xDisplacement = (int) ( pix * Math.sin( tDegree*Math.PI/180 ) );
		int yDisplacement = (int) ( pix * Math.cos( tDegree*Math.PI/180 ) );
		
		//move turtle
		myTurtle.setMyX( myTurtle.getMyX() + xDisplacement );
		myTurtle.setMyY( myTurtle.getMyY() + yDisplacement );
		
		return pix.toString();
		
	}

}
