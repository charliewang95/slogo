package frontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public class TurtleLand {
    public static final String DEFAULT_BACKGROUND_COLOR = "lightgray";
    
    private StackPane wrapperPane;
    private Canvas myCanvas;
    private TurtleMascot myTurtle;
    private Path myPath;
    
    public TurtleLand(BorderPane border) {
        initWrapper();
        wrapCanvas();
        
        myTurtle = new TurtleMascot();
        wrapperPane.getChildren().add(myTurtle.getImage());
        
        myPath = new Path();
        
    }
    
    public StackPane getWrapperPane() {
        return wrapperPane;
    }
    
    // sets up wrapper pane
    private void initWrapper() {
        wrapperPane = new StackPane();
        wrapperPane.setStyle("-fx-background-color:"+DEFAULT_BACKGROUND_COLOR);
    }
    
    // put canvas in wrapper
    // bind the width/height property to the wrapper pane
    private void wrapCanvas() {
        myCanvas = new Canvas();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        wrapperPane.getChildren().add(myCanvas);
        bindBounds();
        addBoundsListeners(gc);
    }
    
    private void bindBounds() {
        myCanvas.widthProperty().bind(wrapperPane.widthProperty());
        myCanvas.heightProperty().bind(wrapperPane.heightProperty());
    }
    
    private void addBoundsListeners(GraphicsContext gc) {
        myCanvas.widthProperty().addListener(event -> initDraw(gc));
        myCanvas.heightProperty().addListener(event -> initDraw(gc));
    }
    
    private void initDraw(GraphicsContext gc) {
        // TODO: initialize canvas
        drawLine(gc);
    }
    
    // draws a test line
    private void drawLine(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.beginPath();
        gc.moveTo(myCanvas.getWidth()/2-7, myCanvas.getHeight()/2);
        gc.lineTo(myCanvas.getWidth()/2-7, myCanvas.getHeight()/2+100);
        gc.stroke();
        gc.closePath();
        System.out.println(myTurtle.getX());
    }
}
