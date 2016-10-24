package frontend.center;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class Pen {
	private Path myPath = new Path();
	
	public Pen () {
	    
	}
	
	public Pen (double x, double y) {
		ObservableList<PathElement> p = myPath.getElements();
		p.add(new MoveTo(x,y));
	}
	
	public List<PathElement> getPathElements() {
	    return myPath.getElements();
	}
}
