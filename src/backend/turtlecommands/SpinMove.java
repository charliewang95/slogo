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
		double newDirection = (clockwise) ? turtle.getDirection()+degMove : turtle.getDirection()-degMove;
		newDirection %= 360;
		
		turtle.setDirection(newDirection);
		
		return degMove.toString();
		
	}
	
	protected String set(Turtle turtle, ArrayList<Command> inputs) {
		
		//makes sure that there is a single input that is a CommandNumber
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		double oldDirection = turtle.getDirection();
		double newDirection = ( getNumCommand(inputs, 0) )%360;
		
		Double displacement = getAngleDisplacement(oldDirection, newDirection);
		turtle.setDirection(newDirection);
		
		return displacement.toString();
		
	}
	
	protected String toward(Turtle turtle, ArrayList<Command> inputs) {
		
		//makes sure that there are two inputs that are both a CommandNumber
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		double oldDirection = turtle.getDirection();
		double xDis = getNumCommand(inputs, 0) - turtle.getMyX();
		double yDis = getNumCommand(inputs, 1) - turtle.getMyY();
		double newDirection = calculateAngle(xDis,yDis);
		
		Double displacement = getAngleDisplacement(oldDirection, newDirection);
		turtle.setDirection(newDirection);
		
		return displacement.toString();
		
	}
	
	private Double getAngleDisplacement(double a, double b) {
	        double degreesDif = Math.abs(a-b);
		return Math.min(degreesDif, 360-degreesDif);
	}
	
	private double calculateAngle(double x, double y) {
		
		if(x == 0 && y == 0) {
			return NORTH;
		}
		
		return ( ( 180/Math.PI*Math.atan2(y, x) ) )%360;
		
	}

}
