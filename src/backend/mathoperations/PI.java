package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class PI extends Command {

	public PI() {
		super("MathOperation", 0);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkNoInputs(inputs) ) {
			return "0";
		}
		
		Double pie = Math.PI;
		
		return pie.toString();
		
	}
	
}
