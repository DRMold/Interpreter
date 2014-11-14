package Main;
//import java.io.*;

class StrLit extends Node 
{
  private String strVal;

  public StrLit(String s)   { strVal = s; }
  
  public boolean isString() { return true; }

  public void print(int n) 
  {
        if (n > 0)
        {
            for (int i = 0;i < n; i++)
                System.out.print(" ");
        }

        System.out.print("\"" + strVal + "\" ");
  }
  
  public Node eval(Node t, Environment env)
  { return this; }
}
