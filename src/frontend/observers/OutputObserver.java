package frontend.observers;

import java.util.Observable;

import backend.observables.ObservableDirection;
import backend.observables.Communication;
import frontend.Display;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Charlie Wang
 */
public class OutputObserver extends TurtleObserver {
	private Display myDisplay;
	private Communication output;

	public OutputObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, Communication output,
			Display display) {
		super(turtle, gcc, width, height);
		this.output = output;
		myDisplay = display;
	}

	@Override
	public void update(Observable o, Object arg) {
	        if (o == output) {
	            Communication out = (Communication) o;
	            myDisplay.getConsole().updateOutput(out.getOutput());
	        }
	}

}
