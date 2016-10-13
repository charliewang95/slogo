### SLogo Team 12 APIs (Alex Zaldastani – az61, Niklas Sjoquist – nhs4, Tripp Whaley – dgw11, Charlie Wang - wq42)

#### SLogo Architecture Design
* Each command is delivered to the playground and is later passed to the interpreter, which 

#### Front End External
* Playground: 
 * setLanguage(int) - chooses the language in which SLogo commands are understood
 * getCommand(String) - read from command window
 * interpret() - pass the command to the Intepreter
 * getPreviousCommands() - returns list of previous commands
 * getAvailableVariables() - returns list of currently available variables
* Turtle:
 * setVisible(boolean) - set the visibility of the turtle
 * getVisible() - get the visibility of the turtle
 * setPenStatus(boolean) - set pen up/down
 * getPenStatus() - get pen up/down
 * setDegree(double) - set degree (up=0)
 * getDegree() - get degree (up=0)
 * getX() - returns the x position of the turtle
 * getY() - returns the y position of the turtle
 * setX(int) - sets the x position
 * setY(int) - sets the y position
 * setImage(Image) - sets the image of the turtle
 * getImage() - gets the image of the turtle

















#### Front End Internal
* InputHandler
 * getCommandQueue() - returns the current queue of commands to run
 * step() - steps and updates frontend elements
* DisplayController
 * setPenColor(Color) - sets the color of the pen
 * setBackgroundImage(Image) - sets the background image of the environment
 * setTurtleBackgroundColor(Color) - sets a background color for the turtle’s display area
* UIObjectPlacer()
 * buttonPlacer()
 * turtlePlacer()
 
 
 
 



#### Back End External
java
 * package Interpreter;
 *	Public class Intepreter(){
    *	Public Command stringToCommand(String command) - parses input command string as a command
	*   Public String commandToString(Command command) - returns string value of current command object from input string given
	*   Public void mathCommand(Command command) - instantiates math command from math subpackage
	*   Public void turtleCommand(Command command) - instantiates command from command subpackage
	*   Public void turtleQuery(Command command) - instantiates command from query subpackage
	*   Public void turtleBoolean(Command command) - instantiates command from boolean subpackage
	*   Public void turtleLogic(Command command) - instantiates command from logic subpackage (loops, conditionals, etc.)
    *	Public void setConstant(Command command) - allows user to set a constant value
    *	Public void setVariable(Command command) - allows user to set a variable
    * 	Public void executeCommand(Command command) - chooses which command to access from command type and accesses proper subpackage	
}

* package Interpreter;
	* Public abstract class Command{
		* 	Public Command(String) - constructor
		* 	Public String getString() - returns input string of command
		* 	Public Type getCommandType() - returns whether command is math, bool, etc.
		* 	Public Type setCommandType() - returns whether command is math, bool, etc.

}


#### Back End Internal
* Boolean Class: (returns 0/1 for false/true)
 * private int less(int,int)
 * private int greater(int,int)
 * private int equal(int,int)
 * private int notequal(int,int)
 * private int and(int,int)
 * private int or(int,int)
 * private int not(int)
* Math Class:
 * private double sum(int,int)
 * private double product(int,int)
 * private double difference(int,int)
 * private double product(int,int)
 * private double quotient(int,int)
 * private double remainder(int,int)
 * private double minus(int,int)
 * private double random(int)
 * private double sin(int)
 * private double cos(int)
 * private double tan(int)
 * private double atan(int)
 * private double log(int)
 * private double pow(int,int)
 * private double pi()
* Variable & Control:
 * private void set(String,COMMAND)
 * private void repeat(int,COMMAND)
 * private void doTime(int,COMMAND)
 * private void for(String,int,int,int,COMMAND)
 * private void if(boolean,COMMAND)
 * private void ifelse(boolean,COMMAND,COMMAND)
 * private void to(String,List<Object>,COMMAND)
