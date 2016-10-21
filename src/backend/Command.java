package backend;

public abstract class Command {
	private String stringText;
	private String commandType;
	
	public Command(String command, String type){
		stringText = command;
		commandType = type;
	}
	
	public String getString(){
		return stringText;
	}
	
	public String getType(){
		return commandType;
	}
}
