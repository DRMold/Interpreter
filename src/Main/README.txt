Ritchie Hofmann II
Jacob Marzloff

Program 2: Interpreter

This was written in Java and all classes belong to the package Main. To run call:
java Main.Main

This program takes scheme code as input, scans it, breaks the input into tokens, parse each token
into meaningful parts, and then evaluates the input expression. 

To exit from interpreter, type ')'

BUGS:	
With built-in cons:
	Error in printing correct number of parenthesis when first argument is a list. 
	
With built-in symbol?
	Error in determining identifiers.
	
With dot identifier "."
	When in parameter list, causes error with parse tree