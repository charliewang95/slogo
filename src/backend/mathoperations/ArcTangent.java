package backend.mathoperations;

import java.util.ArrayList;

import backend.Command;

public class ArcTangent extends Command {

	public ArcTangent() {
		super("MathOperation", 1);
	}

	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double atan = Math.atan( Math.PI / 180 * getNumCommandDouble(inputs, 0) );
		
		return atan.toString();
		
	}
	
}
