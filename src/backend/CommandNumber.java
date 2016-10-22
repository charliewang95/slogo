package backend;

import java.util.ArrayList;

public class CommandNumber extends Command {
	
	private Double commandNum;
	
	public CommandNumber(double val) {
		super("Constant", 0);
		commandNum = val;
	}
	
	public String compute(ArrayList<Command> inputs) {
		return commandNum.toString();
	}

}
