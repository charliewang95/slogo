package backend;

public class Turtle {

	private int myX;
	private int myY;
	private boolean penDown;
	private int direction;
	private boolean visable;
	private int xPixFromCenter;
	private int yPixFromCenter;
	
	public Turtle(int xPixFromCenter, int yPixFromCenter) {
		this.xPixFromCenter = xPixFromCenter;
		this.yPixFromCenter = yPixFromCenter;
		myX = 0;
		myY = 0;
		penDown = false;
		direction = 0;
		visable = true;
	}
	
	public int getMyX() {
		return myX;
	}

	public void setMyX(int myX) {
		//needs work with lines
		this.myX = myX;
	}

	public int getMyY() {
		return myY;
	}

	public void setMyY(int myY) {
		//needs work with lines
		this.myY = myY;
	}

	public boolean isPenDown() {
		return penDown;
	}

	public void setPenDown(boolean penDown) {
		this.penDown = penDown;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}
	
	public int getxPixFromCenter() {
		return xPixFromCenter;
	}

	public int getyPixFromCenter() {
		return yPixFromCenter;
	}
	
	
}
