package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.CommandNumber;
import backend.Turtle;

public abstract class SpinMove extends Command {
	
	public SpinMove() {
		super("TurtleCommand", 1);
	}
	
	public String turn(Turtle turtle, ArrayList<Command> inputs, boolean clockwise) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Integer degMove = Integer.parseInt( inputs.get(0).compute(null) );
		int newDirection = (clockwise) ? turtle.getDirection()+degMove : turtle.getDirection()-degMove;
		newDirection %= 360;
		
		turtle.setDirection(newDirection);
		
		return degMove.toString();
		
	}
	
	public String set(Turtle turtle, ArrayList<Command> inputs) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		int degMove = ( Integer.parseInt( inputs.get(0).compute(null) ) )%360;
		int currentDirection = turtle.getDirection();
		int degreesDif = Math.abs(degMove-currentDirection);
		Integer displacement = Math.min(degreesDif, 360-degreesDif);
		
		turtle.setDirection(degMove);
		
		return displacement.toString();
		
	}

}
