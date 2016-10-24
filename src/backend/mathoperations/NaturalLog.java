package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class NaturalLog extends Command {

	public NaturalLog() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double log = Math.log( getNumCommandDouble(inputs, 0) );
		
		return log.toString();
		
	}
	
}
