package Main;
//import java.io.*;

class Regular extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            t.print(n, true);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.print("(");
            if (t.isPair() && t.getCdr() != null)
            {
                t.getCar().print(n, true);
                t.getCdr().print(-n, true);
            }
            else if (!t.isPair())
                t.print(-n, true);
            else
                System.err.println("Null Pointer");
        }
    }
    
    public Node eval(Node t, Environment env)
    {
        Node fun = t.getCar().eval(t.getCar(), env);
        Node param;
        if (t.getCdr() != null && !t.getCdr().isNull())
        {
            param = this.evalParams(t.getCdr(), env);
            if (param != null)
                return fun.apply(param);
            else
                return null;
        }
        else 
            return fun;
//        Node temp = param.getCar().eval(param.getCar(), env);
//        param = param.getCdr();
//        while (!param.isNull())
//        {
//            temp = new Cons(temp, param.getCar().eval(param.getCar(), env));
//            param = param.getCdr();
//        }      
    }
    
    private Node evalParams(Node t, Environment env)
    {
        if (t.getCdr().isNull())
        {
            if (t.getCar().eval(t.getCar(), env) != null)
                return new Cons(t.getCar().eval(t.getCar(), env), new Nil());
            else 
                System.err.println("NULLPOINTER");
                return null;
        }
        else
        {
            if (t.getCar().eval(t.getCar(), env) != null)
                return new Cons(t.getCar().eval(t.getCar(),env), this.evalParams(t.getCdr(), env));
            else
            {
                System.err.println("NULLPOINTER");
                return null;
            }
        }
    }
} 
