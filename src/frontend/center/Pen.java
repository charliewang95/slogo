package frontend.center;

import java.awt.geom.Line2D;
import java.util.List;
import frontend.coordinates.LayoutToTurtleLand;
import frontend.coordinates.TurtleLandToLayout;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.awt.geom.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


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
    private TurtleMascot myTurtle;
    private boolean drawing = true;

    private double myX;
    private double myY;

    public Pen (GraphicsContext gc, TurtleLandToLayout converter, TurtleMascot turtle) {
        this.gc = gc;
        this.converter = converter;
        moveTo(0, 0);
        myTurtle = turtle;
    }

    public Pen (GraphicsContext gc,
                TurtleLandToLayout converter,
                double x,
                double y,
                TurtleMascot turtle) {
        this.gc = gc;
        this.converter = converter;
        moveTo(x, y);
        myTurtle = turtle;
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

    public double getX () {
        return myX;
    }

    public double getY () {
        return myY;
    }

    public void setDrawing (boolean value) {
        drawing = value;
    }

    public boolean isDrawing () {
        return drawing;
    }

    public void setDash () {
        gc.setLineDashes(2);
    }

    public void setSolid () {
        gc.setLineDashes(null);
    }

    public ObservableList<PathElement> getPathElements () {
        return myPath.getElements();
    }

    private void addPathElement (PathElement pe) {
        myPath.getElements().add(pe);
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

}
