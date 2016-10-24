package frontend.center;

import java.util.List;
import frontend.coordinates.TurtleLandToLayout;
import javafx.collections.ObservableList;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class Pen {
	private Path myPath = new Path();
	private TurtleLandToLayout converter;
	
	public Pen (TurtleLandToLayout converter) {
	        this.converter = converter;
	        moveTo(0,0);
	}
	
	public Pen (TurtleLandToLayout converter, double x, double y) {
		this.converter = converter;
	        moveTo(x,y);
	}
	
	public void lineTo(double x, double y) {
	        double newX = converter.convertX(x);
	        double newY = converter.convertY(y);
	        addPathElement(new LineTo(newX,newY));
	}
	
	public void moveTo(double x, double y) {
	        double newX = converter.convertX(x);
	        double newY = converter.convertY(y);
	        addPathElement(new MoveTo(newX,newY));
	}
	
	private void addPathElement(PathElement pe) {
	        myPath.getElements().add(pe);
	}
	
	public List<PathElement> getPathElements() {
	        return myPath.getElements();
	}
}
