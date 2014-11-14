package Main;

import java.io.*;


class Begin extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            printBegin(t, n);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.println("(begin ");
        }
    }
    
    private void printBegin(Node t, int n)
    {
        if (t.getCdr().isNull())
        {
            t.getCar().print(n+4, false);
            System.out.println();
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            t.getCdr().print(n+4, true);
            return;
        }
        t.getCar().print(n+4, false);
        System.out.println();
        printBegin(t.getCdr(), n);
    }
    
    public Node eval(Node t, Environment env)
    {
        if (t.getCdr() != null)
            return this.evalEverything(t.getCdr(), env);
        else 
            System.err.println("Illegal 'Begin'");
        return null;
    }
    
    private Node evalEverything(Node t, Environment env)
    {
        if (t.getCdr().isNull())
            return t.getCar().eval(t.getCar(), env);
        else 
        {
            t.getCar().eval(t.getCar(), env);
            return evalEverything(t.getCdr(), env);
        }
    }
}

