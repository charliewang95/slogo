Design - SLogo Team 12
=============
> Niklas Sjoquist (nhs4), Charlie Wang (qw42), Tripp Whaley (dgw11), Alex Zaldastani (az61)

### Introduction

* The project allows users to type input commands in a console, and a pen (symbolized as a turtle) will move and/or draw lines as commanded. Our SLogo project will contain three main sections -- Display, Controller, and Backend Classes. The Display section will contains classes that handle the console, e.g., how to take in a command and how to print previous commands on the screen. The command(s) will be passed to the Controller to be parsed and interpreted. Multiple classes will handle the backend algorithms and data memorizations in the Backend. After the command is parsed and interpreted, corresponding backend classes will work on the command and give out a result. The result could be in multiple format, e.g, int, String, etc. How to implement the returning arguments has not been decided. The result will be passed back to the Controller and then to Display, which will print the turtle’s new location and direction along with lines drawn. This will complete a full cycle of a command.

* The most flexible part in this project will be the Backend classes, where all the computations and variable assignments happen. After completing the basic implementations, developers are able to add more classes that interpret more commands from the console. 

### Design Overview

* FrontEnd: The _Display_ class will be the core of the frontend UI. It will control different small classes like _TurtleLand_, _UIObjectPlacer_, _Console_, _Toolbar_, _History_, etc. It contains a BorderPane that holds all the sections.
 * The _UIObjectPlacer_ class serves as a helper to place certain UI components on the screen. It will contain methods like _addButton()_, _addText()_, addTextField()_, etc. Each separate method takes in the coordinates, the message (if applicable) and creates that component and sends the object back to the class that calls the placer. This will save a lot of time if another class needs to add something to the screen because it can just pass the parameters to this placer instead of adding the elements on its own. This will also save space and make the Display class and its subclasses clean. 
 * The _Console_ class will contain a TextField that takes in the user’s command. The text (unparsed) will be sent to _Display_ and then to the _Playground_. The lower section of the _Console_ will be error reporting, which requires another Text output. This Text will also be in the _Console_ class. The error will be gotten from the interpreter class, the error will be reported directly to the _Console_. 
 * The _History_ class contains all the commands that the user has input before. This TextField will have a large field so that it can hold all the histories. 
 * The _ToolBar_ class will be somewhat complicated. It contains the modules (undecided) needed to make menus and options. 
* All these methods have no interaction with each other, and they are all controlled by the _Display_ class. The _Display_ class will create an instance of each of the modules and assign elements or change parameters through the module’s method. For example, the _Console_ class will have a _getCommand()_ method which returns a string. That string will be returned to playground through _Display_. 

* Backend
 * The _CommandController_ class will serve as a class to manage all components of the backend, mainly containing getter functions and instantiating backend classes to divide the tasks of interpreting user input as actual code, queueing up those commands as they are entered, and updating the Turtle’s parameters by popping off the queue.
 * The _Interpreter_ class intakes strings of user inputs, determines what type of command it is from a dictionary of possible commands, then converts it to an object of type Command. It then returns the “currentCommand” to the _CommandController_.
 * The _PriorityQueue_ class will push Commands interpreted by the _Interpreter_ onto a Priority Queue stack, which can be accessed by the _Turtle_ class.
 * The _Turtle_ class has parameters of the current Command the turtle is performing. Once the current Command is completed, it pops the next available command off the Priority Queue and instantiates that Command. 

* Description of interaction of APIs 
 * The program opens to a blank UI Screen of the turtle and the input command box. From there, the user can input text commands to command the turtle on the screen. The Front End External API methods will handle the input of these commands, then pass them to the Back End External API for them to be interpreted by the Interpreter class which is controlled by a Command Controller superclass, controlling all backend APIs. The Interpreter class will determine the type of command given by checking its dictionary of known possible commands, then instantiate a subclass of Interpreter depending on the operation type (math, boolean, command, etc.). These will then return the command type to the Command Controller class, which then adds it to a Priority Queue of commands in a PQ class. The Turtle class (back end internal API), will then update its parameters based on the iterations of all commands in the priority Queue. The turtle’s current state can be accessed with a getter from the Front End Internal API (getCommandQueue()), and the Display Class will handle the actual setting of the turtle and its trail of movement based on the Turtle’s current parameters. 

    [Design Layout](https://git.cs.duke.edu/CompSci308_2016Fall/slogo_team12/blob/master/images/Slogo_team12_DesignLayout.png)


### User Interface 

* The graphical user interface will consist of four main parts:
  * Console: This window will allow the users to type in commands line by line. There should be two ways for a user to finish a command -- when the user has finished entering a command, press “Enter” or click on a button to manually input the command. This command window will take up the area left of the TurtleLand. There will also be an area where the program will display an error, if the user entered an undefined command. 
  * TurtleLand: This area will allow the user to visualize the turtle’s actions in a central window. The turtle’s movement will be affected by commands provided by the user from the console. The height of the TurtleLand should be the same as the Console window and its width is about 5 times as the console’s size. The background color will be able to be set to any desired color at any time. The turtle will be able to leave patterns in TurtleLand by moving with its pen down.
  * Command History: This shows a list of previously entered commands, in order, and includes information about the status of each command (i.e., different colors will be assigned to commands currently running, already completed, and yet to run). In addition to typing commands into the console, we will attempt to implement the ability for users to be able to reuse previous commands by clicking on them in the command history. Different types of commands will need to be represented differently, visually speaking; e.g., FOR loops will need to look different from a simple FORWARD command, in order to incorporate the additional code within them.
  * Toolbar: This section will be above the TurtleLand. The height of this section is small while it is as wide as the other three sections combined. This part will contain buttons with dropdown lists handling the following features: a Utilities menu, for setting the background color, setting the image of the turtle, setting the pen color, and perhaps more; a View menu, for showing/hiding different parts of the UI such as, previously called commands, current variables, current user-defined commands, etc.; and a Help menu, which will allow the user to access the help page for the available commands.

   [UI Layout](https://git.cs.duke.edu/CompSci308_2016Fall/slogo_team12/blob/master/images/uilayout.jpg)
### API Details 

Frontend:
* The external API is mainly composed of _Playground_ which is responsible for invoking the interpreter, connecting the frontend to the backend. This will include methods for interpreting a command from the console, as well as getters/setters for a list of previous commands from the history, current available variables, and current available user-defined functions. 

* The internal API consists of _Display_ and all of its components, such as _UIObjectPlacer_, _TurtleLand_, _Console_, _Toolbar_, _History_, _Variables_, and _Functions_. The _Display_ uses the _UIObjectPlacer_ to place the various components on the window. The _Console_ will contain a TextField to accept input from the user and getters/setters for the text, the _TurtleLand_ will include getters/setters for the position of the turtle and the pen, as well as perhaps for the pattern, the _Toolbar_ will contain JavaFX classes, perhaps dropdown menus such as ComboBox, and handlers on the options which call the _Playground_ to update accordingly, and the _History_, _Variables_, and _Functions_, will include getters/setters for displaying and providing clickable interaction with their respective lists.

Backend:
* There are four main APIs that the backend needs to take care of. The first is  _CommandController_ which will be responsible for all parts of the backend. This will be comprised of mainly getter functions that will act to allow many small tasks to work independently. It will also queue up commands as they are written into the input box on the display. Another important part of _CommandController_ is updating Turtle’s parameters.

* The next backend API will be the  _Interpreter_. The job of the Interpreter is to figure out what input wants to be done. It will also determine what the type of command and convert it into Command object. This API will also be in communication with the _CommandController_ because it returns the commands that is currently being worked on to it.

* Another API is the _PriorityQueue_ which is solely responsible for the ordering of the commands. It will push these commands to a queue that will be acted on.

* The last API is the  _Turtle_ which has parameters including the current command, direction, and location. The  _PriorityQueue_ will be in constant communication with the _Turtle_ to make sure its current command is the correct one.

##### Front End External:

* public class Playground: 
 * public String getCommand() - read from command window
 * public void interpret() - pass the command to the Interpreter
 * public void displayError(String) - displays an error with the given message to the user
 * public List<Command> getPreviousCommands() - returns list of previous commands
 * public List<Variable>/void get/setVariables(List<Variable>) - sets/returns list of currently available variables
 * public List<Function>/void get/setFunctions(List<Function>) - sets/returns list of currently available functions
 * public void setLanguage(int) - chooses the language in which SLogo commands are understood
 * public boolean/void get/setVisible(boolean) - set the visibility of the turtle
 * public boolean/void get/setPenStatus(boolean) - set/get pen up/down
 * public boolean/void get/setDegree(double) - set/get degree (up=0)
 * public int/void get/setX(int) - returns/sets the x position of the turtle
 * public int/void get/setY(int) - returns/sets the y position of the turtle
 * public Image/void get/setImage(Image) - gets/sets the image of the turtle  

##### Front End Internal

* public class Display
 * public void setPenColor(Color) - sets the color of the pen
 * public void setBackgroundImage(Image) - sets the background image of the environment
 * public void setTurtleBackgroundColor(Color) - sets a background color for the turtle’s display area
 * public void reset() - resets the TurtleLand to the turtle in the middle of the screen and wipes the pattern
* public class UIObjectPlacer()
 * public void placeTop(UIElement) - places a UI Element on the screen, a UI Element might be a generic for anything that is placed on the screen, so either an abstract class or an interface
 * public void placeBottom(UIElement)
 * public void placeRight(UIElement)
 * public void placeLeft(UIElement)
 * public void placeCenter(UIElement)

##### Back End External

* public class Intepreter{
 * public Command stringToCommand(String command) - parses input command string as a command
 * public String commandToString(Command command) - returns string value of current command object from input string given
 * public Command getCurrentCommand() - returns the "currentCommand" we are working with
 * public void mathCommand(Command command) - instantiates math command from math subpackage
 * public void turtleCommand(Command command) - instantiates command from command subpackage
 * public void turtleQuery(Command command) - instantiates command from query subpackage
 * public void turtleBoolean(Command command) - instantiates command from boolean subpackage
 * public void turtleLogic(Command command) - instantiates command from logic subpackage (loops, conditionals, etc.)
 * public void setConstant(Command command) - allows user to set a constant value
 * public void setVariable(Command command) - allows user to set a variable
 * public void executeCommand(Command command) - chooses which command to access from command type and accesses proper subpackage
 * public Node commandToNode(Command command) - instantiates the currentCommand as an expression tree of nodes of individual command pieces in ExpressionTree Class 	
}

```java
* public class ExpressionTree{
	public Tree(Node[] n)  // This code is more of a black box for now. It will modify Dr. Duvall's code to instantiate a tree from possible expressions  - iterates through all nodes in currentCommand and puts them in tree
}
```

* public abstract class Command{
 * public Command(String) - constructor
 * public String getString() - returns input string of command
 * public Type getCommandType() - returns whether command is math, bool, etc.
 * public Type setCommandType() - returns whether command is math, bool, etc.
}

* public class Queue {
 * public PriorityQueue<>()
 * public void stackCommand(Tree treeNode, PriorityQueue< Iterable > PQ) - adds a tree of Command Nodes (and they're children) to a PQ (FIFO)
 * public void popCommand(PriorityQueue< Iterable > PQ) - pops the top of the PQ off and stores it as nextAction
 * public Tree getNextAction() - returns most recently popped Command Tree 
}

* public class Turtle{
	Consists of images, locations, etc.
}

##### Back End Internal

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


### API Example Code

* General steps to get to the execution of “fd 50”:
 * The user types “fd 50” into the text box on the playground
 * This five character string is passed to the Interpreter
 * The Interpreter parses the String and passes the int 50 to the TurtleProperties Class
 * The TurtleProperties Class computes the method fd(int)
 * fd(int) adds its action to the Turtle Class to be executed

* Frontend Internal:
 * Placing the Console at the bottom of the Display
 * Create the UIObjectPlacer: objectPlacer = new UIObjectPlacer();
 * Create the Console: console = new Console();
 * Place the Console in the bottom border: objectPlacer.placeBottom(console);

* Frontend External:
 * In _Display_, get the command using the _Console.getCommand()_ method;
 * Save it as an instance variable _myCommand_;
 * Create a getter (_getCommand()_) to send the command to _PlayGround()_;
 * _Playground_ sends the command to the controller (interpreter)

* Backend External
 * Process of parsing a command of “PENDOWNP” (query if the turtle’s pen is down) assuming the text “PENDOWNP” is being received as a String by the Interpreter Class
 * stringToCommand(“PENDOWNP”); - instantiates and returns object of type Command with given Command name from Interpreter class
 *  getCommandType(command) - checks what command the command is enlisted as(in abstract Command class)
 * executeCommand(command) - instantiates Command regardless of type in Interpreter class - selects which command subpackage to access
 * turtleQuery(command) - instantiates Command based on result of executeCommand (Query in this case) in Interpreter Class
Also saves the command input as “currentCommand”
 * returnCurrentCommand() - returns the current command to the CommandController superclass from the Interpreter Class
 * PriorityQueue.add(currentCommand) - PriorityQueue class (subclass of CommandController) adds currentCommand to its list of commands to be executed.

* Backend Internal
 * TurtleProperties
 * public void fd(int spaces)

 * This method appends an action to the back of the turtle’s actionQueue. The action that is added moves the turtle a given number of spaces, measured in pixels, in the current direction it is facing.

 * Parameters: spaces – amount of pixels that the turtle will move in the direction they’re facing

 * Throws: If the input to spaces is zero, the action will not be appended to the actionQueue. If the input is negative, the move backward method will be called with the absolute value of the original input for spaces.
 
### Design Considerations

* Two turtle classes – one with methods, other with direction and actionQueue

* One of the first considerations we made as a team was how the front and backend of the program were going to communicate. The first idea we came up to combat this issue was to create an Interpreter class that will receive the results of method calls from the backend and also work to get that information back to the frontend of the code. We thought that this might be able to be broken up into two separate parts, one that handles going to the backend and another that takes the information back to the frontend. After a few days of contemplating, we decided that this method might be too confusing and thought of implementing a different idea. Now, our current plan is that each method will figure out its own way to return information. For example, the TurtleProperties class will append an action to a Turtle’s actionQueue while a boolean method might return its result straight back to the playground class. The downside of this is that the backend classes are going to need more vision of frontend classes. The upside is that the methods will have more flexibility and there will not be constant, messy back-and-forth of information all the time.

* When we were contemplating how the frontend was going to be setup, we originally decided that there should be a hub class that does a lot of the communicating while also in charge of displaying the GUI. This would be the Display class and the focal point of the frontend. After returning to the idea, we thought that the class was doing too much and blurring its line between if it was an internal and external functioning class. We determined that a better approach to the Display would be to organize its internal and external components and split the class accordingly. This brings us to our current plan of implementing a Playground class as well that will handle more of the communication with other frontend class and allowing the Display class to focus on solely presenting various components to the GUI. We believe this will create a more understandable setup while producing more readable, focused code. The downside of this is that the communication between the two classes may be slightly clunky if not much thought goes into it.

* With the newly designed Display/Playground configuration, these classes were still responsible for the input of text. We decided that this setup didn’t make too much sense in the overall structure of our design. The Playground should be responsible for communicating we the various modules that are displayed rather than do any of the computing. Also, the input into the program is extremely important and should be designed to show this. Now, we have an Input class that’s sole responsibility is to create a textbox, receive text from a user, and send it to the Playground. This will help to not muddy up the Playground class while concentrating an important feature into a neat class.

* Another design consideration that the team came across was where to house Turtle in the design. The Turtle needs to be displayed in the frontend of the code, but also needs to be maneuvered from the backend of the program. We also came to the thought that our program needs to allow for the possibility for multiple Turtles or actors on the GUI. After considering all of this, we decided that there should be a Turtle class that works closely with the front end that comprises its direction, location, and queue of actions while also having a backend class, called TurtleProperties, that takes care of computing what action input will do. The backend methods, will know which Turtle they are working with and will be able to return outputs to Turtles instance variables. All of this creates a very clean split between front and backend, although it does make both of these class external. The biggest upside we see for this configuration is the ease of adding another moving unit to the equation.

### Team Responsibilities

* Charlie and Niklas will be focusing on the frontend of the code. This will include Display, UI, the Playground, and the Console Input. Charlie will code the majority of the external classes while Niklas will construct more of the internal frontend. Alex and Tripp will be in charge of creating the backend side of the project. This includes the Turtle class, the Command Control, an interpreter, and the many functions that will be called from the frontend. Tripp will dedicate more of his work into the external piece of the backend that will be responsible for communicating well with a lot of Charlie’s code. Alex will focus more of the internal side of the backend that will encompass the functions and the interpreter class. 
