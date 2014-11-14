package Main;
// BuiltIn.java -- the data structure for function closures

// Class BuiltIn is used for representing the value of built-in functions
// such as +.  Populate the initial environment with
// (name, new BuiltIn(name)) pairs.

// The object-oriented style for implementing built-in functions would be
// to include the Java methods for implementing a Scheme built-in in the
// BuiltIn object.  This could be done by writing one subclass of class
// BuiltIn for each built-in function and implementing the method apply
// appropriately.  This requires a large number of classes, though.
// Another alternative is to program BuiltIn.apply() in a functional
// style by writing a large if-then-else chain that tests the name of
// of the function symbol.

class BuiltIn extends Node 
{
    private Node symbol;

    public BuiltIn(Node s)		{ symbol = s; }

    public Node getSymbol()		{ return symbol; }

    public boolean isProcedure()	{ return true; }

    public void print(int n)
    {
	// there got to be a more efficient way to print n spaces
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println("#{Built-in Procedure");
	symbol.print(n+3);
	for (int i = 0; i < n; i++)
	    System.out.print(' ');
	System.out.println('}');
    }

    public Node apply (Node args) 
    {
        Node arg1 = null;
        Node arg2 = null;
        if (args.getCar() != null)
        { arg1 = args.getCar(); }
        if (args.getCdr().getCar() != null)
        { arg2 = args.getCdr().getCar(); }
        
        if (arg1 != null && arg2 != null)
        {
            
            if (symbol.getName().equals("cons"))
            { return new Cons(arg1, arg2); } 
            else if (symbol.getName().equals("set-car!"))
            { arg1.setCar(arg2); }
            else if (symbol.getName().equals("set-cdr!"))
            { arg1.setCdr(arg2); }
            else if (symbol.getName().equals("eq?"))
            { return new BooleanLit(arg1 == arg2 || arg1.isNull() && arg2.isNull() || (arg1.isSymbol() && arg2.isSymbol() && arg1.getName().equals(arg2.getName()))); } 
            else if (symbol.getName().equals("apply"))
            { return arg1.apply(arg2); }
            else if (symbol.getName().equals("eval"))
            { return arg1.eval(arg1, Main.top); }
            else if (arg1.isNumber() && arg2.isNumber())
            {
                if (symbol.getName().equals("b+"))
                { return new IntLit(arg1.getValue() + arg2.getValue()); }
                else if (symbol.getName().equals("b-"))
                { return new IntLit(arg1.getValue() - arg2.getValue()); }
                else if (symbol.getName().equals("b*"))
                { return new IntLit(arg1.getValue() * arg2.getValue()); }
                else if (symbol.getName().equals("b/"))
                { return new IntLit(arg1.getValue() / arg2.getValue()); }
                else if (symbol.getName().equals("b="))
                { return new BooleanLit(arg1.getValue() == arg2.getValue()); }
                else if (symbol.getName().equals("b<"))
                { return new BooleanLit(arg1.getValue() < arg2.getValue()); }
            }
        }
        else if (arg1 != null)
        {
            if (symbol.getName().equals("number?"))
            { return new BooleanLit(arg1.isNumber()); }
            else if (symbol.getName().equals("symbol?"))
            { 
                if (args != null)
                    return new BooleanLit(args.isSymbol());
                else
                    return new BooleanLit(arg1.isSymbol()); 
            }
            else if (symbol.getName().equals("null?"))
            { return new BooleanLit(arg1.isNull()); }
            else if (symbol.getName().equals("pair?"))
            { return new BooleanLit(arg1.isPair()); }
            else if (symbol.getName().equals("procedure?"))
            { return new BooleanLit(arg1.isProcedure()); }
            else if (symbol.getName().equals("car"))            
            { return arg1.getCar(); }
            else if (symbol.getName().equals("cdr"))
            { return arg1.getCdr(); }                    
            else if (symbol.getName().equals("write"))
            { arg1.print(0); }     
            else if (symbol.getName().equals("display"))
            { arg1.print(0); }     
        } 
        else
        {
            if (symbol.getName().equals("read"))
            {
                Scanner scanner = new Scanner(System.in);
                Parser parser = new Parser(scanner);
                return parser.parseExp();
            }
            else if (symbol.getName().equals("newline"))
            { System.out.println(); }
            else if (symbol.getName().equals("interaction-environment"))
            { return Main.top; }
        }
	return null;
    }
}
