package frontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;

public class TurtleLand {
    private Display myDisplay;
    private Pane wrapperPane;
    private Canvas myCanvas;
    private TurtleMascot myTurtle;
    private Path myPath;
    
    public TurtleLand(Display display) {
        myDisplay = display;
        
        // set up canvas in a wrapper pane
        wrapperPane = new Pane();
        myCanvas = new Canvas();
        
        
        myTurtle = new TurtleMascot();
        myPath = new Path();
        
    }
    
    public void addPath() {
        
    }
    
}
