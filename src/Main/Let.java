package Main;//import java.io.*;

class Let extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            printLet(t, n);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.println("(let ");
        }
    }
    
    private void printLet(Node t, int n)
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
        printLet(t.getCdr(), n);
    }
    
    public Node eval(Node t, Environment env)
    {
        Environment env2 = new Environment(env);
        Node temp = t.getCdr().getCar();
        env2.define(temp.getCar().getCar(), temp.getCar().getCdr().getCar().eval(temp.getCar().getCdr().getCar(), env));
        if(!temp.getCdr().isNull())
        {
            do
            {
                temp = temp.getCdr();
                env2.define(temp.getCar().getCar(), temp.getCar().getCdr().getCar().eval(temp.getCar().getCdr().getCar(), env));
            }while (!temp.getCdr().isNull());
        }
        return letBody(t.getCdr().getCdr(), env2);
    }
    
    private Node letBody(Node t, Environment env)
    {
        if (t.getCdr().isNull())
            return t.getCar().eval(t.getCar(), env);
        else 
        {
            t.getCar().eval(t.getCar(), env);
            return letBody(t.getCdr(), env);
        }
    }
}
