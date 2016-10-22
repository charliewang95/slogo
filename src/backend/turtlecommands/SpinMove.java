package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public abstract class SpinMove extends Command {
	
	private final int NORTH = 0;
//	private final int EAST = 90;
//	private final int SOUTH = 180;
//	private final int WEST = 270;
	
	public SpinMove() {
		super("TurtleCommand", 1);
	}
	
	protected String turn(Turtle turtle, ArrayList<Command> inputs, boolean clockwise) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Integer degMove = getNumCommand(inputs, 0);
		int newDirection = (clockwise) ? turtle.getDirection()+degMove : turtle.getDirection()-degMove;
		newDirection %= 360;
		
		turtle.setDirection(newDirection);
		
		return degMove.toString();
		
	}
	
	protected String set(Turtle turtle, ArrayList<Command> inputs) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		int oldDirection = turtle.getDirection();
		int newDirection = ( getNumCommand(inputs, 0) )%360;
		
		Integer displacement = getAngleDisplacement(oldDirection, newDirection);
		turtle.setDirection(newDirection);
		
		return displacement.toString();
		
	}
	
	protected String toward(Turtle turtle, ArrayList<Command> inputs) {
		
		//makes sure that there are two inputs that are both a CommandNumber
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		int oldDirection = turtle.getDirection();
		int xDis = getNumCommand(inputs, 0) - turtle.getMyX();
		int yDis = getNumCommand(inputs, 1) - turtle.getMyY();
		int newDirection = calculateAngle(xDis,yDis);
		
		Integer displacement = getAngleDisplacement(oldDirection, newDirection);
		turtle.setDirection(newDirection);
		
		return displacement.toString();
		
	}
	
	private Integer getAngleDisplacement(int a, int b) {
		int degreesDif = Math.abs(a-b);
		return Math.min(degreesDif, 360-degreesDif);
	}
	
	private int calculateAngle(int x, int y) {
		
		if(x == 0 && y == 0) {
			return NORTH;
		}
		
		return ( (int) ( 180/Math.PI*Math.atan2(y, x) ) )%360;
		
	}

}
