**API_REVIEWS_ss833_dgw11**
======================

#### Authors: Soravit Sophastienphong, Tripp Whaley
#### Project: SLogo
#### Team Numbers: 17, 12

### review of Soravit Sophastienphong's API
#### Part 1
* using reflection means we only need class names and method names rather than specific methods and classes
* Only the controller has access to expression tree, and the frontend and core backend have no direct access to this data
* typos in string input, extra spaces, random characters, etc., combinations of commands that dont interact together
* we think its good because the implementation can be changed without affecting other parts of the project, showing modularity and extendability
* It's also useful that we can use a queue to match the fact that the first commands that are added should be the first ones to executed

#### Part 2
##### Use Cases
* command spelled incorrectly
	* detect that no regular expressions match
	* communicate to front end that error message should display
	* do not throw error to avoid termination, use a change listener
* two commands input together that can not interact
	* parent expression cannot pass in child expression as parameter
	* change listener displays to user, does not terminate program
* multiple lines of input
	* split string into lines using '\n'
	* put individual lines as expressions in the tree
	* continue going one by one through number of lines
* user inputs command that sends Turtle off the screen
	* core backend detects current Turtle position cannot handle addition of the new user input
	* terminate command once Turtle reaches its limit on the screen
* user inputs "HOME" (set back to 0,0)
	* instantiate expression tree of one node
	* call appropriate method of node type HOME (using reflection)
	* send to PQ to be executed when current commands finish
* Change listeners and bindings will allow us to update the view according to the model
* I'm excited to work on figuring out how to parse a command and change the model based on that command
* I'm worried about being able to handle complex or long commands that are valid but not easy to parse


### review of Tripp Whaley's API
#### Part 1
* ability to have a class of type Command that uses regular expressions to parse the command without passing strings, instantiating trees of commands (expression tree), and using a priority queue to add entire trees of linked commands with or without children (parameters) to be popped and stacked for the turtle to access
* Only the controller has access to expression tree, and the frontend and core backend have no direct access to this data except through controller getters
* typos in string input, extra spaces, random characters, etc., combinations of commands that dont interact together
* we think its good because the implementation can be changed without affecting other parts of the project, showing modularity and extendability, and the ability to instantiate different objects of type Command and using a PQ of trees of these nodes will allow long chains of commands without hardcoding strings of input possibilities

#### Part 2
##### Use Cases
* command spelled incorrectly
	* detect that no regular expressions match
	* communicate to front end that error message should display
	* do not throw error to avoid termination, use a change listener
* two commands input together that can not interact
	* parent expression cannot pass in child expression as parameter
	* change listener displays to user, does not terminate program
* multiple lines of input
	* split string into lines using '\n'
	* put individual lines as expressions in the tree
	* continue going one by one through number of lines
* user inputs command that sends Turtle off the screen
	* core backend detects current Turtle position cannot handle addition of the new user input
	* terminate command once Turtle reaches its limit on the screen
* user inputs "HOME" (set back to 0,0)
	* instantiate expression tree of one node
	* call appropriate method of node type HOME (using reflection)
	* send to PQ to be executed when current commands finish


* Expression trees will allow us to instantiate commands that aren't necessarily a single line command like FD 50. If the command is a for loop moving the turtle one pixel for every iteration, not having an expression tree would iterate through loop then move the turtle by one pixel once. Having the PQ be made of expression trees allows entire loops to be sent as commands to be popped and used by the turtle, allowing more complexity of input
* I am excited to work on the instantiation of the expression tree for given input commands, and how to add entire trees of varying children nodes to a stack, then adding those commands to the turtles current commands to be used.
* I am most worried about being able to interpret multiple languages, because I feel shaky on how to instantiate that feature with reflection or whatever it needs.