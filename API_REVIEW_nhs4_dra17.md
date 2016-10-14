#API Review
Niklas Sjoquist (nhs4), Ryan Anders (dra17)

##Part 1

* What about your API/design is intended to be flexible?
 * Using reflection to instantiate classes via text input allows us to easily add new commands, if necessary
 * We intend to create a separate class for each element of the GUI, such as, Toolbar, Console, History, etc.
* How is your API/design encapsulating your implementation decisions?
 * The implementation of public methods are hidden
 * Using a class for each GUI element neatens the code needed for the display
* What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
 * Not many exceptions should occur in the frontend...
 * Exceptions should be detected by the backend, and any errors that cannot be handled should be thrown so the frontend knows to display an error message
* Why do you think your API/design is good (also define what your measure of good is)?
 * Readable method names that makes the functionality clear
 * No extraneous methods are included
 * No methods that can change critical data are included

##Part 2

* Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
 * User clicks reset:
  * Display would have a reset button, and the click handler of the button would call resetWorld()
  * The resetWorld() method would set up a new TurtleLand window without any pen marks, with the original background color, and with the turtle set in the middle
  * Thus, resetWorld() will call setBackgroundColor(Color), setX(int), setY(int), and clearPenStrokes()
 * User enters an existing command into console:
  * The Playground class gets the text input from the console on clicking ENTER and then calls upon the Interpreter to interpret the command, interpret(String)
  * The Interpreter parses the command and decides which backend command classes to invoke
 * User enters an undefined command into console:
  * The Playground class gets the text input from the console on clicking ENTER and then calls upon the Interpreter to interpret the command, interpret(String)
  * The Interpreter recognizes that the command entered does not exist, and throws an exception
  * The Playground catches the exception thrown by the Interpreter and displays an error message on the display
 * User clicks on a previous command to rerun:
  * When the user clicks on a previous command, the Playground will fill the console with that command, so that the user can change it if necessary
  * So, Playground will need to call getOldCommand(int) to get the corresponding command from the History
 * User creates a user-defined command:
  * Playground gets text from console and sends to Interpreter, interpret(String)
  * The command should be created in the backend and stored
  * Once the user-defined command is stored in the backend, the display should update and show the command in the GUI
* How do you think at least one of the "advanced" Java features will help you implement your design?
 * Reflection will be helpful to instantiate commands directly via text input
 * Bindings will allow our view to update automatically when the model changes
 * Enums might be useful for encapsulating a set of options to choose from, such as the Toolbar
* What feature/design problem are you most excited to work on?
 * Using bindings to bind the view to the model
* What feature/design problem are you most worried about working on?
 * Creating the 'look and feel' of visual components