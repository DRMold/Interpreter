package Main;

class Cons extends Node 
{
    private Node car;
    private Node cdr;
    private Special form;
    
    public Cons(Node a, Node d) 
     {
         car = a;
	 cdr = d;
	 parseList();
     }

    // parseList() `parses' special forms, constructs an appropriate

    // object of a subclass of Special, and stores a pointer to that

    // object in variable form.  It would be possible to fully parse

    // special forms at this point.  Since this causes complications

    // when using (incorrect) programs as data, it is easiest to let

    // parseList only look at the car for selecting the appropriate
 
    // object from the Special hierarchy and to leave the rest of
  
    // parsing up to the interpreter.
   
    void parseList() 
    {
        if (!car.isSymbol()) { form = new Regular(); }
        else if (car.getName().toLowerCase().equals("if"))     { form = new If(); }
        else if (car.getName().toLowerCase().equals("lambda")) { form = new Lambda(); }
        else if (car.getName().toLowerCase().equals("begin"))  { form = new Begin(); }
        else if (car.getName().toLowerCase().equals("quote"))  { form = new Quote(); }
        else if (car.getName().toLowerCase().equals("let"))    { form = new Let(); }
        else if (car.getName().toLowerCase().equals("cond"))   { form = new Cond(); }
        else if (car.getName().toLowerCase().equals("define")) { form = new Define(); }
        else if (car.getName().toLowerCase().equals("set!"))   { form = new Set(); }
        else { form = new Regular(); }
    }
    
    public boolean isPair()   { return true; }
    
    public Node getCar()      { return car; }
    public Node getCdr()      { return cdr; }
    
    public void setCar(Node a) 
    {
        car = a; 
        parseList();
    }
    
     public void setCdr(Node b) 
     {
         this.cdr = b;
         parseList();
     }
     
     void print(int n) 
     {
         form.print(this.car, n, false);
         form.print(this.cdr, n, true);
         System.out.println();
     }

    
    void print(int n, boolean p) 
    {
        if (this.car.isPair())
            form.print(this.car, n, false);
        else
            form.print(this.car, n, p);
        form.print(this.cdr, n, true);
    }
    
    public Node eval(Node t, Environment env)
    { return form.eval(this, env); }
}
