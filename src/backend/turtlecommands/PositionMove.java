package backend.turtlecommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public abstract class PositionMove extends Command {
	
	public PositionMove() {
		super("TurtleCommand", 2);
	}
	
	public String setPosition(int x, int y, Turtle t) {
		Integer distance = computeDistance(x,y,t.getMyX(),t.getMyY());
		
		//should this draw a line?
		t.setMyX(x);
		t.setMyY(x);
		
		return distance.toString();
		
	}
	
	private Integer computeDistance(int x1, int y1, int x2, int y2) {
		
		return (int) Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
		
	}
	

}
