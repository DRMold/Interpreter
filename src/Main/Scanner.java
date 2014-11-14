package Main;// Scanner.java -- the implementation of class Scanner

import java.io.*;

class Scanner 
{
  private PushbackInputStream in;
  private byte[] buf;

  public Scanner(InputStream i) { in = new PushbackInputStream(i); }
    
  public Token getNextToken()
  {
    buf = new byte[1000];
    int bite = -1;
	
    // It would be more efficient if we'd maintain our own input buffer
    // and read characters out of that buffer, but reading individual
    // characters from the input stream is easier.
    try {
      bite = in.read();
    } catch (IOException e) {
      System.err.println("We fail: " + e.getMessage());
    }
    
    //Removes whitespace and comments
    if (bite == 32 || (bite >= 9 && bite <= 13)) 
    {
        do
        {
            try {
                bite=in.read();
                //System.out.print(bite);
           } catch (IOException e) {
                System.err.println("We fail: " + e.getMessage());
           }
        } while (bite == 32 || (bite >= 9 && bite <= 13));
    }
    if (bite == 59) 
    {
       do 
       {
           try {
                bite=in.read();
           } catch (IOException e) {
                System.err.println("We fail: " + e.getMessage());
           }
       } while (bite != 10); //Looks for newline
       try {
                bite=in.read();
           } catch (IOException e) {
                System.err.println("We fail: " + e.getMessage());
           }
    }
        
    
    if (bite == -1)
      return null;

    char ch = (char) bite;
	
    // Special characters
    if (ch == '\'')
      return new Token(Token.QUOTE);
    else if (ch == '(')
      return new Token(Token.LPAREN);
    else if (ch == ')')
      return new Token(Token.RPAREN);
    else if (ch == '.')
      // We ignore the special identifier `...'.
      return new Token(Token.DOT);

    // Boolean constants
    else if (ch == '#') {
      try {
	bite = in.read();
      } catch (IOException e) {
	System.err.println("We fail: " + e.getMessage());
      }

      if (bite == -1) {
	System.err.println("Unexpected EOF following #");
	return null;
      }
      ch = (char) bite;
      if (ch == 't')
	return new Token(Token.TRUE);
      else if (ch == 'f')
	return new Token(Token.FALSE);
      else {
	System.err.println("Illegal character '" + (char) ch + "' following #");
	return getNextToken();
      }
    }

    // String constants
    else if (ch == '"') {
      int i = 0; 
      try {
            bite = in.read();
        } catch (IOException e) {
            System.err.println("We fail: " + e.getMessage());
        }
      while (bite != 34)
      {
        buf[i] = (byte) bite;
        i++;
        try {
            bite = in.read();
        } catch (IOException e) {
            System.err.println("We fail: " + e.getMessage());
        } 
        
      }  

      return new StrToken(new String(buf, 0, i)); //buf.toString()
    }

    // Integer constants
    else if (ch >= '0' && ch <= '9') 
    {
      int i = ch - '0';
      do
      {
          try {
            bite = in.read();
          } catch (IOException e) {
             System.err.println("We fail: " + e.getMessage());
          } 
          if (bite >= 48 && bite <= 57)
            i = (i * 10) + (bite - 48);
          else break;         
      } while (true);
      try {
          in.unread(bite);
      } catch (IOException e) {
         System.err.println("We fail: " + e.getMessage());
      } 
      return new IntToken(i);
    }

    // Identifiers
    else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || 
            (ch >= '$' && ch <= '&') || (ch >= '<' && ch <= '@') || 
            ch == '!' || ch == '+' || ch == '*' || ch == '-' || 
            ch == '/' || ch == ':' || ch == '^' || ch == '_' || ch == '~') 
    {
      int i = 0;
      do {
          buf[i] = (byte) bite;
          i++;
          try {
            bite = in.read();
          } catch (IOException e) {
             System.err.println("We fail: " + e.getMessage());
          } 
          ch = (char) bite;
      } while ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || 
            (ch >= '$' && ch <= '&') || (ch >= '<' && ch <= '@') || 
            ch == '!' || ch == '+' || ch == '*' || ch == '-' || 
            ch == '/' || ch == ':' || ch == '^' || ch == '_' || ch == '~');
                
      try {
          in.unread(bite);
      } catch (IOException e) {
         System.err.println("We fail: " + e.getMessage());
      } 
      return new IdentToken(new String(buf, 0, i)); //buf.toString()
    }

    // Illegal character
    else 
    {
      System.err.println("Illegal input character '" + (char) ch + '\'');
      return getNextToken();
    }
  };
}
