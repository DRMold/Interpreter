package Main;//import java.io.*;

//The main program.
public class Main 
{
    // Array of token names used for debugging the scanner
    public static final String TokenName[] = {
	"QUOTE",			// '
	"LPAREN",			// (
	"RPAREN",			// )
	"DOT",				// .
	"TRUE",				// #t
	"FALSE",			// #f
	"INT",				// integer constant
	"STRING",			// string constant
	"IDENT"				// identifier
    };
    
    public static Environment top;

    public static void main (String argv[]) 
    {
	// create scanner that reads from standard input
        System.out.print("Scheme48> ");
        Scanner scanner = new Scanner(System.in);

	if (argv.length > 2) {
	    System.err.println("Usage: java Main " + "[-d]");
	    System.exit(1);
	}
	
	// if commandline option -d is provided, debug the scanner
	if (argv.length == 1 && argv[0].equals("-d")) {
	    // debug scanner
	    Token tok = scanner.getNextToken();
	    while (tok != null) {
		int tt = tok.getType();
		System.out.print(TokenName[tt]);
		if (tt == Token.INT)
		    System.out.println(", intVal = " + tok.getIntVal());
		else if (tt == Token.STRING)
		    System.out.println(", strVal = " + tok.getStrVal());
		else if (tt == Token.IDENT)
		    System.out.println(", name = " + tok.getName());
		else
		    System.out.println();

		tok = scanner.getNextToken();
	    }
	}
	
	// Create parser
	Parser parser = new Parser(scanner);
	Node root;
        Environment env = new Environment();
        
        //Define Built-In environment
        Ident id  = new Ident("symbol?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("number?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b+");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b-");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b*");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b/");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b=");
        env.define(id, new BuiltIn(id));
        id  = new Ident("b<");
        env.define(id, new BuiltIn(id));
        id  = new Ident("car");
        env.define(id, new BuiltIn(id));
        id  = new Ident("cdr");
        env.define(id, new BuiltIn(id));
        id  = new Ident("cons");
        env.define(id, new BuiltIn(id));
        id  = new Ident("set-car!");
        env.define(id, new BuiltIn(id));
        id  = new Ident("set-cdr!");
        env.define(id, new BuiltIn(id));
        id  = new Ident("null?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("pair?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("eq?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("procedure?");
        env.define(id, new BuiltIn(id));
        id  = new Ident("read");
        env.define(id, new BuiltIn(id));
        id  = new Ident("write");
        env.define(id, new BuiltIn(id));
        id  = new Ident("display");
        env.define(id, new BuiltIn(id));
        id  = new Ident("newline");
        env.define(id, new BuiltIn(id));
        id  = new Ident("eval");
        env.define(id, new BuiltIn(id));
        id  = new Ident("apply");
        env.define(id, new BuiltIn(id));
        id  = new Ident("interaction-environment");
        env.define(id, new BuiltIn(id));
        
	// Parse and pretty-print each input expression; To test add additional environment
        top = new Environment(env);
	root = parser.parseExp();
        Node temp;
	while (root != null) 
        {
            temp = root.eval(root, top);
            if (temp != null)  
                temp.print(0);
            System.out.print("\nScheme48> ");
	    root = parser.parseExp();
	}
	System.exit(0);
    }
}
