package Main;

import java.io.*;

class BooleanLit extends Node 
{  
    private boolean booleanVal;

  
    public BooleanLit(boolean b)    { booleanVal = b; }
    
    public boolean getBool()        { return booleanVal; }

    public boolean isBoolean()      { return true; } 
  
    public void print(int n)
    {
        if (n > 0)
        {
            for (int i = 0;i < n; i++)
                System.out.print(" ");
        }
   
        if (booleanVal) 
        {
            System.out.print("#t");
        } 
        else 
        {
            System.out.print("#f");
        }
  }
    
  public Node eval(Node t, Environment env)
  { return this; }
}
