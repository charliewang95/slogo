package frontend.center;

import java.util.List;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class Pen {
	private Path myPath = new Path();
	
	public Pen () {
		
	}
	
	public List<PathElement> getPathElements() {
	    return myPath.getElements();
	}
}
