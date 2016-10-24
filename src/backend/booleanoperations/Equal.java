package backend.booleanoperations;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class Equal extends Command{

	public Equal(Turtle t) {
		super("BooleanOperation", 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		// TODO Auto-generated method stub
		if (equal(inputs)){
			return "1";
		}
		return "0";
		
	}

	private boolean equal(ArrayList<Command> inputs){
		return (Double.parseDouble(inputs.get(0).compute(null)) == Double.parseDouble(inputs.get(1).compute(null)));
	}
}
