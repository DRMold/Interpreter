package Main;
import java.io.*;


class Cond extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            printCond(t, n);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.println("(cond ");
        }
    }
    
    private void printCond(Node t, int n)
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
        printCond(t.getCdr(), n);
    }
    
    public Node eval(Node t, Environment env)
    {
        Node cond = new Nil();
        Node temp = t;
        
        do
        {
            temp = temp.getCdr();
            if (temp.getCar().getCar().getName().equals("else"))
                return evalExp(temp.getCar().getCdr(), env);
            cond = temp.getCar().getCar().eval(temp.getCar().getCar(), env);
            if (cond.getBool())
            {
                if (temp.getCar().getCdr().isNull())
                    return cond;
                else
                    return evalExp(temp.getCar().getCdr(), env);
            }
        } while(!temp.getCdr().isNull() );
        return new BooleanLit(false);
    }
    
    private Node evalExp(Node t, Environment env)
    {
        if (t.getCdr().isNull())
            return t.getCar().eval(t.getCar(), env);
        else 
            t.getCar().eval(t.getCar(), env);
        return evalExp(t.getCdr(), env);
    }
}
