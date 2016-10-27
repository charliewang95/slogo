package frontend.observers;

import java.util.List;
import java.util.Observer;

import frontend.Display;
import frontend.center.Pen;
import frontend.center.TurtleMascot;
import frontend.coordinates.TurtleLandToLayout;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

/**
 * @author Niklas Sjoquist
 *
 */
public abstract class TurtleObserver implements Observer {
    private TurtleMascot myTurtle;
    private GraphicsContext gc;
    
    private TurtleLandToLayout converter;

    public TurtleObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height) {
            myTurtle = turtle;
            gc = gcc;
            converter = new TurtleLandToLayout(width,height);
    }
    
    // Only subclasses should be able to access the TurtleMascot
    
    protected double getMascotX() {
        return myTurtle.getX();
    }
    
    protected double getMascotY() {
        return myTurtle.getY();
    }
    
    protected void setPosition(double[] pos) {
            myTurtle.setPosition(pos);
    }

    protected void setX(double x) {
            myTurtle.setX(x);
    }

    protected void setY(double y) {
            myTurtle.setY(y);
    }

    protected void setDirection(double degrees) {
            myTurtle.setDirection(degrees);
    }

    protected void setVisibility(boolean visible) {
            myTurtle.setVisible(visible);
    }

    protected void setPenDown(boolean penDown) {
            myTurtle.setDrawing(penDown);
    }
    
    protected boolean getPenStatus() {
        return myTurtle.isDrawing();
    }
    
    protected void moveTurtle(Point2D start, Point2D end) {
        double x = end.getX();
        double y = end.getY();
        
        // Update position
        myTurtle.setX(x);
        myTurtle.setY(y);
        
        Pen pen = myTurtle.getPen();
        
        // Update (and draw) path
        if (myTurtle.isDrawing()){
            pen.lineTo(x, y);
        } else {
            pen.moveTo(x, y);
        }
        drawPath(pen);
    }
    
    private void drawPath(Pen pen) {
        //gcc.setStroke(myPenColor);
        //gcc.setLineWidth(1);
        
        List<PathElement> path = pen.getPathElements();
        
        gc.beginPath();
        path.stream().forEach((pe) -> {
            if (pe.getClass() == MoveTo.class) {
                gc.moveTo(((MoveTo)pe).getX(), ((MoveTo)pe).getY());
            } else if (pe.getClass() == LineTo.class) {
                gc.lineTo(((LineTo)pe).getX(), ((LineTo)pe).getY());
            }
        });
        gc.setStroke(myTurtle.getPenColor());
        gc.stroke();
        gc.closePath();
    }
    
    /**
     * Returns the bearing direction (in degrees) from a starting point to and ending point.
     * 
     * @param startingPoint
     * @param endingPoint
     * @return bearing direction, in degrees, from 0 to 360
     */
    private double getSegmentBearing(Point2D startingPoint, Point2D endingPoint) {
        Point2D originPoint = new Point2D(endingPoint.getX() - startingPoint.getX(), endingPoint.getY() - startingPoint.getY()); // get origin point to origin by subtracting end from start
        double bearingRadians = Math.atan2(originPoint.getY(), originPoint.getX()); // get bearing in radians
        double bearingDegrees = bearingRadians * (180.0 / Math.PI); // convert to degrees
        bearingDegrees = (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees)); // correct discontinuity
        return bearingDegrees;
    }
}
