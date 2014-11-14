package Main;//import java.io.*;

class Set extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            t.getCar().print(n, true);
            if (t.getCdr().isPair() && (t.getCdr().getCdr() != null))
            {
                System.out.print(" ");
                t.getCdr().getCar().print(n, true);
                t.getCdr().getCdr().print(n, true);
            }
            else if (!t.getCdr().isPair())
            {
                System.out.print(" ");
                t.getCdr().print(n,true);
            }
            else
                System.err.print(p);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.print("(set! ");
        }
    }
    
    public Node eval(Node t, Environment env)
    {
        Node var = t.getCdr().getCar();
        Node val = t.getCdr().getCdr().getCar();
        env.assign(var, val.eval(t, env));
        return val.eval(t, env);
    }
}
