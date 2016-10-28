package frontend.observers;

import java.util.Observable;

import backend.observables.ObservableDirection;
import backend.observables.ObservableOutput;
import frontend.Display;
import frontend.center.TurtleMascot;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Charlie Wang
 */
public class OutputObserver extends TurtleObserver {
	Display myDisplay;
	String output;

	public OutputObserver(TurtleMascot turtle, GraphicsContext gcc, int width, int height, ObservableOutput output,
			Display display) {
		super(turtle, gcc, width, height);
		this.output = output.getOutput();
		myDisplay = display;
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableOutput out = (ObservableOutput) o;
		myDisplay.getConsole().updateOutput(out.getOutput());
	}

}
