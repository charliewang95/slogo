package frontend.center;

import java.awt.geom.Line2D;
import java.util.List;
import frontend.coordinates.LayoutToTurtleLand;
import frontend.coordinates.TurtleLandToLayout;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.awt.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;


/**
 * TODO: The ListChangeListener is not drawing the newly added path elements
 * 
 * @author Niklas Sjoquist
 *
 */
public class Pen {
    private Path myPath = new Path();
    private TurtleLandToLayout converter;
    private GraphicsContext gc;
    private Color color = Color.BLACK;
    private double thickness = 1;
    private boolean drawing = true;

    private double myX;
    private double myY;

    public Pen (GraphicsContext gc, TurtleLandToLayout converter) {
        this.gc = gc;
        this.converter = converter;
        //listenToPath();
        moveTo(0, 0);
    }

    public Pen (GraphicsContext gc, TurtleLandToLayout converter, double x, double y) {
        this.gc = gc;
        this.converter = converter;
        //listenToPath();
        moveTo(x, y);
    }
    
    public double getX() {
        return myX;
    }
    
    public double getY() {
        return myY;
    }
    
    public void setDrawing(boolean value) {
        drawing = value;
    }
    
    public boolean isDrawing() {
        return drawing;
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public Color getColor () {
        return color;
    }

    public void setThickness (double t) {
        thickness = t;
    }

    public double getThickness () {
        return thickness;
    }

    public ObservableList<PathElement> getPathElements () {
        return myPath.getElements();
    }

    public void resetPath () {
        myPath.getElements().clear();
        moveTo(0, 0);
    }

    public void lineTo (double x, double y) {
        double newX = converter.convertX(x);
        double newY = converter.convertY(y);
        addPathElement(new LineTo(newX, newY));
        myX = x;
        myY = y;
    }

    public void moveTo (double x, double y) {
        double newX = converter.convertX(x);
        double newY = converter.convertY(y);
        addPathElement(new MoveTo(newX, newY));
        myX = x;
        myY = y;
    }

    private void addPathElement (PathElement pe) {
        myPath.getElements().add(pe);
    }

    /**
     * Draw a single PathElement on the Canvas.
     * 
     * @param pe
     */
    private void draw (PathElement pe) {
        gc.beginPath();
        if (pe.getClass() == MoveTo.class) {
            gc.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
        }
        else if (pe.getClass() == LineTo.class) {
            gc.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
        }
        gc.setStroke(color);
        gc.stroke();
        gc.closePath();
    }

    /**
     * Draw a list of PathElements on the Canvas.
     * 
     * @param path
     */
    private void drawAll (List<PathElement> path) {
        gc.beginPath();
        path.stream().forEach( (pe) -> {
            if (pe.getClass() == MoveTo.class) {
                gc.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
            }
            else if (pe.getClass() == LineTo.class) {
                gc.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
                System.out.println("LineTo");
            }
        });
        gc.setStroke(color);
        gc.setLineWidth(1);
        gc.stroke();
        gc.closePath();
    }

    private void listenToPath () {
        ListChangeListener<PathElement> listener = pathListener();
        myPath.getElements().addListener(listener);
    }

    private ListChangeListener<PathElement> pathListener () {
        ListChangeListener<PathElement> listener = new ListChangeListener<PathElement>() {
            @Override
            public void onChanged (javafx.collections.ListChangeListener.Change<? extends PathElement> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        System.out.println(c.getAddedSubList().toString());
                        drawAll((List<PathElement>) c.getAddedSubList());
                    }
                }
            }
        };
        return listener;
    }
}
