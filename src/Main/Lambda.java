package Main;//import java.io.*;

class Lambda extends Special
{
    void print(Node t, int n, boolean p) 
    {
        if (p)
        {
            t.getCar().print(-n, false);
            System.out.println();
            printLambda(t.getCdr(), n);
        }
        else
        {
            if (n > 0)
            {
                for (int i = 0; i < n; i++)
                    System.out.print(" ");
            }
            System.out.print("(lambda ");
        }
    }
    
    private void printLambda(Node t, int n)
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
        printLambda(t.getCdr(), n);
    }
    
    public Node eval(Node t, Environment env)
    { return new Closure(t, env); }
}
