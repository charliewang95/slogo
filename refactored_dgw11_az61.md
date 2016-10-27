### Refactored.md

#### Contributors : Tripp Whaley (dgw11) and Alex Zaldastani

* After discussing design with Dr. Duvall, we were able to refactor our Interpreter.java class to dynamically iterate through all subpackages of backend and find commands without needing an overly complex enum in the Interpreter class
* While the double for loop is not ideal, it simplifies our extendability greatly by not having to add to an enum every time we add a new class of turtle commands, and adding a new subset of commands (like boolean ops, math ops, etc.) requires no extra work as well
* The code and logic is slightly slower, but the design is more maintainable from this change, so we believe it is a positive decision to change this code.