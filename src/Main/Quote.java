package Main;//import java.io.*;

class Quote extends Special 
{
    void print(Node t, int n, boolean p) 
    {
        if (n > 0)
        {
            for (int i = 0; i < n; i++)
                System.out.print(" ");
        }
        
        if (p)
        {
            if (t.getCar() != null)
            { t.getCar().print(n, true); }
        }
        else
            System.out.print("'(");
    }
    
  public Node eval(Node t, Environment env) 
  {  
      if (t.getCdr().getCar() != null)
        return t.getCdr().getCar();
      else 
      {
          System.err.println("NULL value");
          return null;
      }
  }
}
