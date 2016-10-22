package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Tan extends Command {
	
	private final String LARGE_NUMBER = "10000000000000000";

	public Tan() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		double deg = getNumCommandDouble(inputs, 0);
		if(deg == 90 || deg == 270) {
			return LARGE_NUMBER;
		}
		
		Double tan = Math.tan( Math.PI / 180 * deg );
		
		return tan.toString();
		
	}
	
}
