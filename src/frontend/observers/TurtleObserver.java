package frontend.observers;

import java.awt.geom.Line2D;
import java.util.List;
import java.util.Observer;
import backend.Turtle;
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
            // Normal case (within bounds)
            // Update position
            myTurtleView.setX(x);
            myTurtleView.setY(y);
            // Draw path or Move pen
            if (myTurtleView.isDrawing()) {
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
            
            if (myTurtleView.isDrawing()) {
                pen.lineTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            } else {
                pen.moveTo(wallCollisionPt.getX(),wallCollisionPt.getY());
            }
            myTurtleView.setX(wallCollisionPt.getX());
            myTurtleView.setY(wallCollisionPt.getY());
            
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
            
            // Update positions and recurse
            myTurtleView.setX(nextStart.getX());
            myTurtleView.setY(nextStart.getY());
            myTurtleModel.setMyPosQuiet(nextStart.getX(),nextStart.getY());
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
        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Double(myTurtleView.getPen().getX(), myTurtleView.getPen().getY());
        Line2D path = new Line2D.Double(start,new java.awt.geom.Point2D.Double(end.getX(), end.getY()));

        // Find intersection point
        Point2D intersection = null;
        int count = 0;
        for (int j = 0; j < sides.length; j++) {
            Line2D side = sides[j];
            System.out.println("Side "+(j+1)+" ");
            if (side.intersectsLine(path)) {
                System.out.println("Intersected side "+j);
                count++;
                intersection = intersectLines(new Point2D(path.getP1().getX(),path.getP1().getY()),new Point2D(path.getP2().getX(),path.getP2().getY()),new Point2D(side.getP1().getX(),side.getP1().getY()),new Point2D(side.getP2().getX(),side.getP2().getY()));
                Point2D pt = intersectPathWithSide(new Point2D(path.getP1().getX(),path.getP1().getY()),new Point2D(path.getP2().getX(),path.getP2().getY()),j);
                return pt;
            }
        }
        
        System.out.println("Path intersected " + count + " sides."); // Debug
        
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
        double dx, dy;
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
        double dy = (invert) ? (-1)*(py-start.getY()) : (py-start.getY());
        double dx = dy/Math.tan((Math.PI/180)*getSegmentBearing(start,end));
        
        return start.getX() + dx;
    }
    
    private double calculatePy(Point2D start, Point2D end, double px, boolean invert) {
        double dx = (invert) ? (-1)*(px - start.getX()) : (px - start.getX());
        double dy = dx*Math.tan((Math.PI/180)*getSegmentBearing(start,end));
        
        return start.getY() + dy;
    }
    
    /**
     * Finds the intersection point between the lines PR and QS.
     * If segments are parallel or collinear, returns null.
     * 
     * TODO: doesn't return the correct point of intersection.
     * 
     * @param p - start of line 1
     * @param r - end of line 1
     * @param q - start of line 2
     * @param s - end of line 2
     * @return intersection point
     */
    private Point2D intersectLines (Point2D p, Point2D r, Point2D q, Point2D s) {
        System.out.println("Path: "+p.toString()+"->"+r.toString()+"\nSide: "+q.toString()+"->"+s.toString());
        
        System.out.println("Line 1: "+p.toString()+","+r.toString()+"\nLine 2: "+q.toString()+","+s.toString());
        
        double prXdet = det(p.getX(),1,r.getX(),1);
        double prYdet = det(p.getY(),1,r.getY(),1);
        double qsXdet = det(q.getX(),1,s.getX(),1);
        double qsYdet = det(q.getY(),1,s.getY(),1);
        double denominator = det(prXdet,prYdet,qsXdet,qsYdet);
        
        if (denominator != 0) {
            double prDet = det(p.getX(),p.getY(),r.getX(),r.getY());
            double qsDet = det(q.getX(),q.getY(),s.getX(),s.getY());
            double px = det(prDet,prXdet,qsDet,qsXdet);
            double py = det(prDet,prYdet,qsDet,qsYdet);
            System.out.println("Lines intersect: "+denominator+"\nPt: "+px+","+py);
            return new Point2D(px,py);
        }
        
        return null;
        
        /*
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
        */
    }
    
    /**
     * Returns the determinant of the 2D matrix defined by a,b,c,d
     * 
     * @param a - top left
     * @param b - top right
     * @param c - bottom left
     * @param d - bottom right
     * @return
     */
    private double det(double a, double b, double c, double d) {
        return a*d - b*c;
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
        gc.setStroke(myTurtleView.getPenColor());
        gc.setLineWidth(myTurtleView.getPenThickness());
        gc.stroke();
        gc.closePath();
    }
}
