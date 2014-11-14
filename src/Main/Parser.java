package Main;
// Parser.java -- the implementation of class Parser
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         | exp Y
//    Y -> rest
//      | . exp )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
//
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL) token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.

class Parser 
{
  private Scanner scanner;

  public Parser(Scanner s) { scanner = s; }
  
  public Node parseExp() 
  {
    Token tok = scanner.getNextToken();
    return parseExp(tok);
  }
  
  private Node parseExp(Token tok)
  {
      int tt = tok.getType();
      if (tt == Token.LPAREN)      { return parseRest(); }
      else if (tt == Token.FALSE)  { return new BooleanLit(false); }
      else if (tt == Token.TRUE)   { return new BooleanLit(true); }
      else if (tt == Token.QUOTE)  { return new Cons(new Ident("quote"),new Cons(parseExp(), new Nil())); }
      else if (tt == Token.INT)    { return new IntLit(tok.getIntVal()); }
      else if (tt == Token.STRING) { return new StrLit(tok.getStrVal()); }
      else if (tt == Token.IDENT)  { return new Ident(tok.getName()); }
      return null;
  }
  
  protected Node parseRest() 
  {
    Token tok = scanner.getNextToken();
    return parseRest(tok);
  }
  
  private Node parseRest(Token tok) 
  {
      if (tok.getType() == Token.RPAREN) { return new Nil(); }
      else if (tok.getType() != Token.DOT)
      {
        Node a = parseExp(tok);
        Node b = parseY();
        return new Cons(a, b);
      }
      else
        System.err.println("Incorrect Syntax");
      return new Nil(); //Just getting rid of errors
  }
  
  private Node parseY()
  {
      Token tok = scanner.getNextToken();
      if (tok.getType() == Token.DOT) 
      {
          return new Cons(new Ident("Dot"), new Cons(parseExp(), new Nil()));
      }
      else
          return parseRest(tok);
  }
  
};
