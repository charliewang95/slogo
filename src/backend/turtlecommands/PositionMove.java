package backend.turtlecommands;

import backend.Command;
import backend.Turtle;

public abstract class PositionMove extends Command {
	
	public PositionMove() {
		super("TurtleCommand", 2);
	}
	
	public String setPosition(int x, int y, Turtle t) {
		Double distance = computeDistance(x,y,t.getMyX(),t.getMyY());
		
		//should this draw a line?
		t.setMyX(x);
		t.setMyY(x);
		
		return distance.toString();
		
	}
	
	private Double computeDistance(double x1, double y1, double x2, double y2) {
		
		return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
		
	}
	

}
