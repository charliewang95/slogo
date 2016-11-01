package frontend.observers;

import java.awt.geom.Line2D;
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
    private static final double EPSILON = 0.005;
    
    private TurtleMascot myTurtle;
    private GraphicsContext gc;
    
    private TurtleLandToLayout converter;
    private Point2D[] corners;

    public TurtleObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height) {
            myTurtle = turtle;
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
        corners = new Point2D[]{tr,br,bl,tl};
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
        
        // Update (and draw) path
        move(x,y);
        drawPath(myTurtle.getPen());
    }
    
    private void move(double x, double y) {
        System.out.println("Move to "+x+","+y);
        
        double halfWidth = converter.getWidth() / 2.0;
        double halfHeight = converter.getHeight() / 2.0;
        
        Pen pen = myTurtle.getPen();
        
        if (Math.abs(x) < halfWidth && Math.abs(y) < halfHeight) {
            System.out.println("Normal case, no collisions");
            // Normal case (within bounds)
            // Update position
            myTurtle.setX(x);
            myTurtle.setY(y);
            // Draw path or Move pen
            if (myTurtle.isDrawing()) {
                pen.lineTo(x, y);
            } else {
                pen.moveTo(x, y);
            }
        }
        else {
            // Out of bounds, split path into at least 2 segments
            System.out.println("Out of bounds, split path into 2 segments");
            
            Point2D start = new Point2D(pen.getX(), pen.getY());
            Point2D end = new Point2D(x,y);
            Point2D wallCollisionPt = getCollisionPt(end, halfWidth, halfHeight);
            
            System.out.println("Collision Point: "+wallCollisionPt.toString());
            
            if (myTurtle.isDrawing()) {
                pen.lineTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            } else {
                pen.moveTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            }
            myTurtle.setX(wallCollisionPt.getX());
            myTurtle.setY(wallCollisionPt.getY());
            
            double distanceLeft = start.distance(end) - start.distance(wallCollisionPt);
            double direction = getSegmentBearing(start,end);
            javafx.geometry.Point2D dirVector = new javafx.geometry.Point2D(end.getX()-wallCollisionPt.getX(),end.getY()-wallCollisionPt.getY());
            javafx.geometry.Point2D nextStart = null;
            javafx.geometry.Point2D nextEnd = null;
            
            // Corner case
            if (getCornerIndex(wallCollisionPt) > -1) {
                // split line up and start from opposite corner
                System.out.println("Hit a corner");
                int cornerIndex = getCornerIndex(wallCollisionPt);
                nextStart = new javafx.geometry.Point2D(corners[(cornerIndex+2)%corners.length].getX(),corners[(cornerIndex+2)%corners.length].getY());
            }
            
            // Side case
            if (Math.abs(wallCollisionPt.getX()) == halfWidth) {
                // split line up and start at (-x,y)
                System.out.println("Hit a side");
                nextStart = new javafx.geometry.Point2D((-1)*wallCollisionPt.getX(),wallCollisionPt.getY());
            }
            
            // Top/Bottom case
            if (Math.abs(wallCollisionPt.getY()) == halfHeight) {
                // split line up and start at (x,-y)
                System.out.println("Hit top or bottom");
                nextStart = new javafx.geometry.Point2D(wallCollisionPt.getX(),(-1)*wallCollisionPt.getY());
            }
            
            System.out.println("Next Starting Point: "+nextStart.toString());
            
            // Update position and recurse
            myTurtle.setX(nextStart.getX());
            myTurtle.setY(nextStart.getY());
            pen.moveTo(nextStart.getX(),nextStart.getY());
            nextEnd = nextStart.add(dirVector);
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
    private Point2D getCollisionPt (Point2D end, double halfWidth, double halfHeight) {
        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Double(myTurtle.getPen().getX(), myTurtle.getPen().getY());
        Line2D path = new Line2D.Double(start,new java.awt.geom.Point2D.Double(end.getX(), end.getY()));

        // Define sides
        Line2D[] sides = new Line2D[corners.length];
        for (int i = 0; i < corners.length-1; i++) {
            sides[i] = new Line2D.Double(new java.awt.geom.Point2D.Double(corners[i].getX(), corners[i].getY()),new java.awt.geom.Point2D.Double(corners[i+1].getX(), corners[i+1].getY()));
        }
        sides[corners.length-1] = new Line2D.Double(new java.awt.geom.Point2D.Double(corners[corners.length-1].getX(), corners[corners.length-1].getY()),new java.awt.geom.Point2D.Double(corners[0].getX(), corners[0].getY()));
        
        // Find intersection point
        Point2D intersection = null;
        int count = 0;
        for (int j = 0; j < sides.length; j++) {
            Line2D side = sides[j];
            if (side.intersectsLine(path)) {
                count++;
                intersection = getIntersectionPt(new Point2D(path.getP1().getX(),path.getP1().getY()),new Point2D(path.getP2().getX(),path.getP2().getY()),new Point2D(side.getP1().getX(),side.getP1().getY()),new Point2D(side.getP2().getX(),side.getP2().getY()));
            }
        }
        
        System.out.println("Path intersected " + count + " sides."); // Debug
        
        return intersection;
    }
    
    /**
     * Finds the intersection point between the line segments pr and qs.
     * If segments don't intersect, returns null.
     * 
     * TODO: doesn't return the correct point of intersection.
     * 
     * @param p - start of line 1
     * @param r - end of line 1
     * @param q - start of line 2
     * @param s - end of line 2
     * @return intersection point
     */
    private Point2D getIntersectionPt (Point2D p, Point2D r, Point2D q, Point2D s) {
        System.out.println("Path: "+p.toString()+"->"+r.toString()+"\nSide: "+q.toString()+"->"+s.toString());
        
        // Find  t,u   s.t.   p + tr = q + us
        Point2D qMinP = q.subtract(p);
        double rXs = cross(r,s);
        double t = cross(qMinP,s)/rXs;
        double u = cross(qMinP,r)/rXs;
        
        System.out.println("t = "+t+"\nu = "+u+"\nRxS = "+cross(r,s));

        if (cross(r,s) != 0 && between0and1(t) && between0and1(u)) {
            double x = p.getX() + t*r.getX();
            double y = p.getY() + t*r.getY();
            double otherx = q.getX() + u*s.getX();
            double othery = q.getY() + u*s.getY();
            System.out.println("Found the intersection point: "+otherx+","+othery);
            return new Point2D(x,y);
        }
        
        return null;
    }
    
    private boolean between0and1(double value) {
        //return (value >= 0 && value <= 1);
        return Math.abs(value) <= 1;
    }
    
    private double cross(javafx.geometry.Point2D v, javafx.geometry.Point2D w) {
        return v.getX()*w.getY() - v.getY()*w.getX();
    }

    /**
     * Returns the bearing direction (in degrees) from a starting point to and ending point.
     * 
     * @param startingPoint
     * @param endingPoint
     * @return bearing direction, in degrees, from 0 to 360
     */
    private double getSegmentBearing (Point2D startingPoint, Point2D endingPoint) {
        Point2D originPoint =
                new Point2D(endingPoint.getX() - startingPoint.getX(),
                                   endingPoint.getY() - startingPoint.getY()); // get origin point to
                                                                               // origin by subtracting end
                                                                               // from start
        double bearingRadians = Math.atan2(originPoint.getY(), originPoint.getX()); // get bearing
                                                                                    // in radians
        double bearingDegrees = bearingRadians * (180.0 / Math.PI); // convert to degrees
        bearingDegrees = (bearingDegrees > 0.0 ? bearingDegrees : (360.0 + bearingDegrees)); // correct
                                                                                             // discontinuity
        return bearingDegrees;
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
        gc.setLineWidth(myTurtle.getPenThickness());
        gc.stroke();
        gc.closePath();
    }
}
