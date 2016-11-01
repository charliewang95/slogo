package backend.observables;

import java.util.Observable;
import java.util.ResourceBundle;
import frontend.ErrorException;
import javafx.scene.image.Image;

public class ObservableImage extends Observable {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources.common/";
    
    private ResourceBundle myResources;
    
    private Image[] images = new Image[3];
    private int myImageIndex;
    
    public ObservableImage() {
        buildImageList();
        
        // default is Turtle
        myImageIndex = 0;
    }
    
    public ObservableImage(int index) {
        buildImageList();
        
        if (index >= 0 && index <= images.length) {
            myImageIndex = index;
        } else {
            myImageIndex = 0; // Default is Turtle
        }
    }

    private void buildImageList () {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Common");
        
        try {
                images[0] = new Image(getClass().getClassLoader().getResourceAsStream("turtlemascot.png"));
        } catch (Exception e) {
                ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Turtle)");
        }
        try {
                images[1] = new Image(getClass().getClassLoader().getResourceAsStream("elephantmascot.png"));
        } catch (Exception e) {
                ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Elephant)");
        }
        try {
                images[2] = new Image(getClass().getClassLoader().getResourceAsStream("rocket.png"));
        } catch (Exception e) {
                ErrorException ee = new ErrorException(myResources.getString("NoDefaultImageError") + " (Rocket)");
        }
    }
    
    public void setImage(int index) {
        myImageIndex = index;
        setChanged();
        notifyObservers();
    }
    
    public Image getImage() {
        return images[myImageIndex];
    }

}
