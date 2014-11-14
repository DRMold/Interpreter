package Main;
import java.io.*;

class If extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            t.getCar().print(-n, false);
            System.out.println();
            printIf(t.getCdr(), n);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.print("(if ");
        }
    }
    
    private void printIf(Node t, int n)
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
        printIf(t.getCdr(), n);
    }
    
    public Node eval(Node t, Environment env)
    {
        Node cond = t.getCdr().getCar();
        cond = cond.eval(cond, env);
        Node then = t.getCdr().getCdr().getCar();
        Node elif = null;
        if (t.getCdr().getCdr().getCdr().getCar() != null)
        { elif = t.getCdr().getCdr().getCdr().getCar(); }
        
        if (cond.getBool())
        { return then.eval(then, env); }
        else if (!cond.getBool() && elif != null)
        { return elif.eval(elif, env); }
        else
            return null; 
    }
}
