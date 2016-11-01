package frontend.center;

import java.util.List;
import frontend.coordinates.TurtleLandToLayout;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
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
	
	private double myX;
	private double myY;
	
	public Pen (GraphicsContext gc, TurtleLandToLayout converter) {
	        this.gc = gc;
	        this.converter = converter;
	        listenToPath();
	        moveTo(0,0);
	}
	
	public Pen (GraphicsContext gc, TurtleLandToLayout converter, double x, double y) {
		this.gc = gc;
	        this.converter = converter;
	        listenToPath();
	        moveTo(x,y);
	}
	
	public void setColor(Color color) {
	    this.color = color;
	}
	
	public Color getColor() {
	    return color;
	}
        
        public ObservableList<PathElement> getPathElements() {
                return myPath.getElements();
        }
        
        public void resetPath() {
                myPath.getElements().clear();
                moveTo(0,0);
        }
	
	public void lineTo(double x, double y) {
	        double newX = converter.convertX(x);
	        double newY = converter.convertY(y);
	        addPathElement(new LineTo(newX,newY));
                myX = newX;
                myY = newY;
	}
	
	public void moveTo(double x, double y) {
	        double newX = converter.convertX(x);
	        double newY = converter.convertY(y);
	        addPathElement(new MoveTo(newX,newY));
	        myX = newX;
	        myY = newY;
	}
	
	private boolean handleToroidal(double x, double y) {
	    double sideEdge = converter.getWidth()/2.0;
            double topEdge = converter.getHeight()/2.0;
            
	    if (Math.abs(x) > sideEdge || Math.abs(y) > topEdge) {
	        return false;
	    }
	    else {
	        // corner case
	        Point2D[] corners = {new Point2D(sideEdge,topEdge),
	                             new Point2D(sideEdge,(-1)*topEdge),
	                             new Point2D((-1)*sideEdge,topEdge),
                                     new Point2D((-1)*sideEdge,(-1)*topEdge)};
	        
	        Point2D wallCollisionPt = getCollisionPt(x,y,sideEdge,topEdge);
	        // side case
	        
	        // top/bottom case
	        
	        // normal
	    }
	    
	    double[] pt = new double[2];
	    
	    
	    
	    if (Math.abs(x) > sideEdge) {
	        pt[0] = (-1)*sideEdge;
	    } else {
	        pt[0] = x;
	    }
	    
	    if (Math.abs(y) > topEdge) {
	        pt[1] = (-1)*topEdge;
	    } else {
	        pt[1] = y;
	    }
	    
	    return false;
	}
	
	private Point2D getCollisionPt (double x, double y, double halfWidth, double halfHeight) {
	    double ratioYtoX = y/x;
	    
	    if (Math.abs(x) > halfWidth) {
	        // collides with side wall
                return new Point2D(halfWidth,halfHeight*ratioYtoX);
            } else {
                // collides with 
                return new Point2D(halfWidth/ratioYtoX,halfHeight);
            }
        }

        private void addPathElement(PathElement pe) {
	        myPath.getElements().add(pe);
	}
	
	/**
	 * Draw a single PathElement on the Canvas.
	 * @param pe
	 */
	private void draw(PathElement pe) {
	    gc.beginPath();
	    if (pe.getClass() == MoveTo.class) {
	        gc.moveTo(((MoveTo)pe).getX(), ((MoveTo)pe).getY());
	    } else if (pe.getClass() == LineTo.class) {
	        gc.lineTo(((LineTo)pe).getX(), ((LineTo)pe).getY());
	    }
	    gc.setStroke(color);
	    gc.stroke();
	    gc.closePath();
	}
	
	/**
	 * Draw a list of PathElements on the Canvas.
	 * @param path
	 */
	private void drawAll(List<PathElement> path) {
	        gc.beginPath();
	        path.stream().forEach((pe) -> {
	            if (pe.getClass() == MoveTo.class) {
	                gc.moveTo(((MoveTo)pe).getX(), ((MoveTo)pe).getY());
	            } else if (pe.getClass() == LineTo.class) {
	                gc.lineTo(((LineTo)pe).getX(), ((LineTo)pe).getY());
	                System.out.println("LineTo");
	            }
	        });
	        gc.setStroke(color);
	        gc.setLineWidth(1);
	        gc.stroke();
	        gc.closePath();
	}
	
	private void listenToPath() {
	    ListChangeListener<PathElement> listener = pathListener();
            myPath.getElements().addListener(listener);
	}
	
	private ListChangeListener<PathElement> pathListener() {
	    ListChangeListener<PathElement> listener = new ListChangeListener<PathElement>() {
                @Override
                public void onChanged (javafx.collections.ListChangeListener.Change<? extends PathElement> c) {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            System.out.println(c.getAddedSubList().toString());
                            drawAll((List<PathElement>)c.getAddedSubList());
                        }
                    }
                }
            };
            return listener;
	}
}
