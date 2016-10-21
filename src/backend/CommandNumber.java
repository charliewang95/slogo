package backend;

import java.util.ArrayList;

public class CommandNumber extends Command {
	
	private Integer commandNum;
	
	public CommandNumber(int val) {
		super("Constant", 0);
		commandNum = val;
	}
	
	public String compute(ArrayList<Command> inputs) {
		return commandNum.toString();
	}

}
