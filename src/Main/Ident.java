package Main;

import java.io.*;

class Ident extends Node 
{
  private String name;

  public Ident(String n)    { name = n; }
  
  public boolean isSymbol() { return true; }
  
  public String getName()   { return name; }

  public void print(int n) 
  {
    if (n > 0)
    {
      for (int i = 0; i < n; i++)
       System.out.print(" ");
    }
    
    System.out.print(name + " ");
  }
  
  public Node eval(Node t, Environment env)
  { return env.lookup(t); }
}
