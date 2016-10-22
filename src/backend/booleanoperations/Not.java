package backend.booleanoperations;

import java.util.ArrayList;
import backend.Command;

public class Not extends Command{

	public Not(String type, int inputs) {
		super("BooleanOperation", 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		// TODO Auto-generated method stub
		if (Integer.parseInt(inputs.get(0).compute(null)) == 0){
			return "1";
		}
		return "0";
		
	}
}
