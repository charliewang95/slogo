package backend;

public class TurtleCommands {
	
	public int forward(Turtle t, int pix) {
		
		//compute displacement
		//because direction 0 is north, xDis is sine and yDis is cosine
		int tDegree = t.getDirection();
		int xDisplacement = (int) ( pix * Math.sin( tDegree*2*Math.PI/180 ) );
		int yDisplacement = (int) ( pix * Math.cos( tDegree*2*Math.PI/180 ) );
		
		//compute new (x,y)
		int newXLoc = t.getMyX() + xDisplacement;
		int newYLoc = t.getMyY() + yDisplacement;
		
		while( newXLoc > t.getxPixFromCenter() ) {
			newXLoc -= 2*t.getxPixFromCenter();
		} 
		while( newXLoc < -1*t.getxPixFromCenter() ) {
			newXLoc += 2*t.getxPixFromCenter();
		}
		while( newYLoc > t.getyPixFromCenter() ) {
			newYLoc -= 2*t.getyPixFromCenter();
		} 
		while( newYLoc < -1*t.getyPixFromCenter() ) {
			newYLoc += 2*t.getyPixFromCenter();
		}
		
		//move turtle
		t.setMyX( t.getMyX() + xDisplacement );
		t.setMyY( t.getMyY() + yDisplacement );
		
		//send FE new location
		
		//if pen up, pass playground lines with int[4]
		
		//return pix
		return pix;
		
	}

}
