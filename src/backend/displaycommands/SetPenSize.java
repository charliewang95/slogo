package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetPenSize extends Command{

	private Turtle myTurtle;

	public SetPenSize(Turtle t) {
		super("Display Command", 1);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		int pix = Integer.parseInt(inputs.get(0).compute(null));
		
		myTurtle.setPenSize(pix);
		
		return inputs.get(0).compute(null);
		
	}

}
