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
	Display myDisplay;
	String output;

	public OutputObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, Communication output,
			Display display) {
		super(turtle, gcc, width, height);
		this.output = output.getOutput();
		myDisplay = display;
	}

	@Override
	public void update(Observable o, Object arg) {
		Communication out = (Communication) o;
		myDisplay.getConsole().updateOutput(out.getOutput());
	}

}
