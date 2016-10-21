package backend.turtlecommands;
import java.util.ArrayList;

import backend.*;

public class Backward extends VerticalMove {
	
	private Turtle myTurtle;
	
	public Backward(Turtle t) {
		super();
		myTurtle = t;
	}
	
	public String compute(ArrayList<Command> inputs) {
		
		return computeVerticalMove(myTurtle, inputs, false); //false for moving backward
		
	}

}
