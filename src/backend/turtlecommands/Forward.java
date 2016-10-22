package backend.turtlecommands;
import java.util.ArrayList;

import backend.*;

public class Forward extends VerticalMove {
	
	private Turtle myTurtle;
	
	public Forward(Turtle t) {
		super();
		myTurtle = t;
		System.out.println(this.getClass().getName().toString());
	}
		
	public String compute(ArrayList<Command> inputs) {
		
		return computeVerticalMove(myTurtle, inputs, true); //true for moving forward
		
	}

}
