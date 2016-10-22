package backend.booleanoperations;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Or extends Command{

	public Or(String type, int inputs) {
		super("BooleanOperation", 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		// TODO Auto-generated method stub
		if (or(inputs)){
			return "1";
		}
		return "0";
		
	}

	private boolean or(ArrayList<Command> inputs){
		return (Integer.parseInt(inputs.get(0).compute(null)) > 0 ||  Integer.parseInt(inputs.get(1).compute(null)) > 0);
	}
}