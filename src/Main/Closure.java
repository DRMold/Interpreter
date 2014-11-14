package Main;
// Closure.java -- the data structure for function closures

// Class Closure is used to represent the value of lambda expressions.
// It consists of the lambda expression itself, together with the
// environment in which the lambda expression was evaluated.

// The method apply() takes the environment out of the closure,
// adds a new frame for the function call, defines bindings for the
// parameters with the argument values in the new frame, and evaluates
// the function body.

class Closure extends Node 
{
    private Node fun;		// a lambda expression
    private Environment env;	// the environment in which the function
				// was defined

    public Closure(Node f, Environment e)	{ fun = f;  env = e; }

    public Node getFun()		        { return fun; }
    public Environment getEnv()		        { return env; }

    public boolean isProcedure()	        { return true; }

    public void print(int n)
    {
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println("#{Procedure");
	fun.print(n+3);
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println('}');
    }

    public Node apply (Node args) 
    {
        Node params = fun.getCdr().getCar();
        Node body = fun.getCdr().getCdr().getCar();
        Environment func = new Environment(env);
        
        //Assign args to params
        Node tempParams = params;
        Node tempArgs = args;
        while(!tempParams.isNull())
        {
            if (!(tempParams.getCar().isPair() || tempParams.getCar() == null || tempParams.isNull() 
                    || tempArgs.getCar().isPair() || tempArgs.getCar() == null || tempArgs.isNull()))
            {
                func.define(tempParams.getCar(), tempArgs.getCar());
                tempParams = tempParams.getCdr();
                tempArgs = tempArgs.getCdr();
            }
            else
                System.err.println("Illegal Closure");
        }
        
        return body.eval(body, func);
    }
}
