package frontend.observers;

import java.awt.geom.Line2D;
import java.util.List;
import java.util.Observer;
import backend.Turtle;
import frontend.Display;
import frontend.center.Pen;
import frontend.center.TurtleMascot;
import frontend.coordinates.TurtleLandToLayout;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;

/**
 * @author Niklas Sjoquist
 *
 */
public abstract class TurtleObserver implements Observer {
    private static final double EPSILON = 0.005;
    
    private TurtleMascot myTurtleView;
    private Turtle myTurtleModel;
    private GraphicsContext gc;
    
    private TurtleLandToLayout converter;
    private Point2D[] corners;
    private Line2D[] sides;

    public TurtleObserver(TurtleMascot turtleView, Turtle turtleModel, GraphicsContext gcc, int width, int height) {
            myTurtleView = turtleView;
            myTurtleModel = turtleModel;
            gc = gcc;
            converter = new TurtleLandToLayout(width,height);
            initializeCorners(converter);
    }

    private void initializeCorners (TurtleLandToLayout converter) {
        double halfWidth = converter.getWidth() / 2.0;
        double halfHeight = converter.getHeight() / 2.0;
        Point2D tr = new Point2D(halfWidth, halfHeight);
        Point2D tl = new Point2D((-1) * halfWidth, halfHeight);
        Point2D bl = new Point2D((-1) * halfWidth, (-1) * halfHeight);
        Point2D br = new Point2D(halfWidth, (-1) * halfHeight);
        corners = new Point2D[]{tl,tr,br,bl};
        
        // Define sides
        sides = new Line2D[corners.length];
        for (int i = 0; i < corners.length-1; i++) {
            sides[i] = new Line2D.Double(new java.awt.geom.Point2D.Double(corners[i].getX(), corners[i].getY()),new java.awt.geom.Point2D.Double(corners[i+1].getX(), corners[i+1].getY()));
        }
        sides[corners.length-1] = new Line2D.Double(new java.awt.geom.Point2D.Double(corners[corners.length-1].getX(), corners[corners.length-1].getY()),new java.awt.geom.Point2D.Double(corners[0].getX(), corners[0].getY()));
    }
    
    // Only subclasses should be able to access the TurtleMascot

    protected double getMascotX() {
        return myTurtleView.getX();
    }
    
    protected double getMascotY() {
        return myTurtleView.getY();
    }
    
    protected void setPosition(double[] pos) {
            myTurtleView.setPosition(pos);
    }

    protected void setX(double x) {
            myTurtleView.setX(x);
    }

    protected void setY(double y) {
            myTurtleView.setY(y);
    }

    protected void setDirection(double degrees) {
            myTurtleView.setDirection(degrees);
    }

    protected void setVisibility(boolean visible) {
            myTurtleView.setVisible(visible);
    }

    protected void setPenDown(boolean penDown) {
            myTurtleView.setDrawing(penDown);
    }
    
    protected boolean getPenStatus() {
        return myTurtleView.isDrawing();
    }
    
    protected void moveTurtle(Point2D start, Point2D end) {
        double x = end.getX();
        double y = end.getY();
        
        // Update (and draw) path
        move(x,y);
        drawPath(myTurtleView.getPen());
    }
    
    private void move(double x, double y) {
        System.out.println("Move to "+x+","+y);
        
        double halfWidth = converter.getWidth() / 2.0;
        double halfHeight = converter.getHeight() / 2.0;
        
        Pen pen = myTurtleView.getPen();
        
        if (Math.abs(x) < halfWidth && Math.abs(y) < halfHeight) {
            System.out.println("Normal case, no collisions");
            double direction = getSegmentBearing(new Point2D(pen.getX(),pen.getY()),new Point2D(x,y));
            System.out.println("Turtle Direction = "+myTurtleModel.getDirection());
            // Normal case (within bounds)
            // Update position
            myTurtleView.setX(x);
            myTurtleView.setY(y);
            myTurtleModel.setMyPosQuiet(x,y);
            // Draw path or Move pen
            if (myTurtleView.isDrawing()) {
                pen.lineTo(x, y);
            } else {
                pen.moveTo(x, y);
            }
        }
        else {
            Point2D start = new Point2D(pen.getX(), pen.getY());
            Point2D end = new Point2D(x,y);
            Point2D wallCollisionPt = getCollisionPt(start, end, halfWidth, halfHeight);
            
            System.out.println("Collision Pt: "+wallCollisionPt.toString());
            
            if (myTurtleView.isDrawing()) {
                pen.lineTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            } else {
                pen.moveTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            }
            myTurtleView.setX(wallCollisionPt.getX());
            myTurtleView.setY(wallCollisionPt.getY());
            
            double distanceLeft = start.distance(end) - start.distance(wallCollisionPt);
            double direction = getSegmentBearing(start,end);
            System.out.println("Turtle Direction = "+myTurtleModel.getDirection());
            Point2D dirVector = end.subtract(wallCollisionPt);
            Point2D nextStart = null;
            Point2D nextEnd = null;
            
            // Corner case
            if (getCornerIndex(wallCollisionPt) > -1) {
                // split line up and start from opposite corner
                System.out.println("Hit corner");
                int cornerIndex = getCornerIndex(wallCollisionPt);
                nextStart = new javafx.geometry.Point2D(corners[(cornerIndex+2)%corners.length].getX(),corners[(cornerIndex+2)%corners.length].getY());
            }
            
            // Side case
            if (Math.abs(wallCollisionPt.getX()) == halfWidth) {
                // split line up and start at (-x,y)
                System.out.println("Hit side");
                nextStart = new javafx.geometry.Point2D((-1)*wallCollisionPt.getX(),wallCollisionPt.getY());
            }
            
            // Top/Bottom case
            if (Math.abs(wallCollisionPt.getY()) == halfHeight) {
                // split line up and start at (x,-y)
                System.out.println("Hit top/bottom");
                nextStart = new javafx.geometry.Point2D(wallCollisionPt.getX(),(-1)*wallCollisionPt.getY());
            }
            
            System.out.println("Pen Position (before update): "+pen.getX()+","+pen.getY());
            
            // Update positions and recurse
            myTurtleView.setX(nextStart.getX());
            myTurtleView.setY(nextStart.getY());
            myTurtleModel.setMyPosQuiet(nextStart.getX(),nextStart.getY());
            pen.moveTo(nextStart.getX(),nextStart.getY());
            nextEnd = nextStart.add(dirVector);
            
            System.out.println("Pen Position (after update): "+pen.getX()+","+pen.getY());
            System.out.println("Next Start: "+nextStart.toString()+"\nNext End: "+nextEnd.toString());
            
            move(nextEnd.getX(),nextEnd.getY());
        }
    }
    
    /**
     * Returns the index of which corner the given point is, or -1 if the point is not a corner.
     * 
     * @param pt
     * @return
     */
    private int getCornerIndex(Point2D pt) {
        for (int i = 0; i < corners.length; i++) {
            Point2D corner = corners[i];
            if (Math.abs(pt.getX()-corner.getX()) < EPSILON && Math.abs(pt.getY()-corner.getY()) < EPSILON) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Finds the point at which a path traveling to the given end point will collide 
     * with one of the sides of TurtleLand.
     * 
     * @param end
     * @param halfWidth
     * @param halfHeight
     * @return
     */
    private Point2D getCollisionPt (Point2D startPt, Point2D endPt, double halfWidth, double halfHeight) {
        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Double(startPt.getX(), startPt.getY());
        Line2D path = new Line2D.Double(start,new java.awt.geom.Point2D.Double(endPt.getX(), endPt.getY()));

        // Find intersection point
        Point2D intersection = null;
        for (int j = 0; j < sides.length; j++) {
            Line2D side = sides[j];
            if (side.intersectsLine(path)) {
                System.out.println("Intersected side "+j);
                intersection = intersectPathWithSide(new Point2D(path.getP1().getX(),path.getP1().getY()),new Point2D(path.getP2().getX(),path.getP2().getY()),j);
            }
        }
        
        return intersection;
    }
    
    /**
     * Returns the intersection point of path between start/end and given side.
     * 
     * @param start
     * @param end
     * @param side - index of side (0 is top, increases clockwise)
     * @return
     */
    private Point2D intersectPathWithSide(Point2D start, Point2D end, int side) {
        double halfWidth = converter.getWidth()/2.0;
        double halfHeight = converter.getHeight()/2.0;
        double px = Double.MIN_VALUE, py = Double.MIN_VALUE;
        switch (side) {
            case 0: // top
                py = halfHeight;
                px = calculatePx(start,end,py,false);
                break;
            case 1: // right
                px = halfWidth;
                py = calculatePy(start,end,px,false);
                break;
            case 2: // bottom
                py = (-1)*halfHeight;
                px = calculatePx(start,end,py,true);
                break;
            case 3: // left
                px = (-1)*halfWidth;
                py = calculatePy(start,end,px,true);
                break;
        }
        return new Point2D(px,py);
    }
    
    private double calculatePx(Point2D start, Point2D end, double py, boolean invert) {
        double dy = Math.abs(py-start.getY());
        double dx = dy/Math.tan((Math.PI/180)*getSegmentBearing(start,end));
        
        if (invert) {
            dx *= -1;
        }
        
        return start.getX() + dx;
    }
    
    private double calculatePy(Point2D start, Point2D end, double px, boolean invert) {
        double dx = Math.abs(px - start.getX());
        double dy = dx*Math.tan((Math.PI/180)*getSegmentBearing(start,end));
        
        if (invert) {
            dy *= -1;
        }
        
        return start.getY() + dy;
    }

    /**
     * Returns the bearing direction (in degrees) from a starting point to and ending point.
     * 
     * @param startingPoint
     * @param endingPoint
     * @return bearing direction, in degrees, from 0 to 360
     */
    private double getSegmentBearing (Point2D startingPoint, Point2D endingPoint) {
        Point2D originPoint = endingPoint.subtract(startingPoint);
        double bearingRadians = Math.atan2(originPoint.getY(), originPoint.getX()); // get bearing
                                                                                    // in radians
        double bearingDegrees = bearingRadians * (180.0 / Math.PI); // convert to degrees
        bearingDegrees = (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees)); // correct
                                                                                             // discontinuity
        System.out.println("Bearing = "+bearingDegrees);
        return bearingDegrees; 
    }
    
    private void drawPath(Pen pen) {
        List<PathElement> path = pen.getPathElements();
        
        gc.beginPath();
        path.stream().forEach((pe) -> {
            if (pe.getClass() == MoveTo.class) {
                gc.moveTo(((MoveTo)pe).getX(), ((MoveTo)pe).getY());
//                System.out.println(((MoveTo)pe).getX()+" "+((MoveTo)pe).getY());
//                Path p = new Path();
//                p.getElements().addAll(new MoveTo(15, 65), new LineTo(50, 65));
//                PathTransition pt = new PathTransition(Duration.millis(1000), p, myTurtleView.getImage());
//                Animation ani = new SequentialTransition(myTurtleView.getImage(), pt);
//                ani.play();
            } else if (pe.getClass() == LineTo.class) {
                gc.lineTo(((LineTo)pe).getX(), ((LineTo)pe).getY());
            }
        });
        gc.setStroke(myTurtleView.getPenColor());
        gc.setLineWidth(myTurtleView.getPenThickness());
        gc.stroke();
        gc.closePath();
    }
}
