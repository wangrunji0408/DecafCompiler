package decaf.frontend;

import decaf.Driver;
import decaf.Location;
import decaf.error.MsgError;
import decaf.tree.Tree;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser extends Table {
    /**
     * Lexer.
     */
    private Lexer lexer;

    /**
     * Set lexer.
     *
     * @param lexer the lexer.
     */
    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Lookahead token (can be eof/eos).
     */
    public int lookahead = eof;

    /**
     * Undefined token (when lexer fails).
     */
    private static final int undefined_token = -2;

    /**
     * Lexer will write this when a token is parsed.
     */
    public SemValue val = new SemValue();

    /**
     * Helper function.
     * Create a `MsgError` with `msg` and append it to driver.
     *
     * @param msg the message string.
     */
    private void issueError(String msg) {
        Driver.getDriver().issueError(new MsgError(lexer.getLocation(), msg));
    }

    /**
     * Error handler.
     */
    private void error() {
        issueError("syntax error");
    }

    /**
     * Lexer caller.
     *
     * @return the token parsed by the lexer. If `undefined_token` is returned, then lexer failed.
     */
    private int lex() {
        int token = undefined_token;
        try {
            token = lexer.yylex();
        } catch (Exception e) {
            issueError("lexer error: " + e.getMessage());
        }
        return token;
    }

    private void print(int symbol, List<Integer> right, int depth) {
        for(int i=0; i<depth; ++i)
            System.out.print("  ");
        System.out.print(name(symbol) + " := ");
        if(right != null)
            printSymbolList(right);
    }

    /**
     * Parse function for each non-terminal with error recovery.
     * TODO: add error recovery!
     *
     * @param symbol the non-terminal to be passed.
     * @return the parsed value of `symbol` if parsing succeeded, otherwise `null`.
     */
    private SemValue parse(int symbol, Set<Integer> follow, int depth) {
        Pair<Integer, List<Integer>> result = query(symbol, lookahead); // get production by lookahead symbol

        Set<Integer> endSet = new HashSet<>();
        endSet.addAll(follow);
        endSet.addAll(followSet(symbol));

        Location beginLoc = lexer.getLocation();

        if(result == null)
        {
            error();
            while(true) {
                result = query(symbol, lookahead);
                if(result != null)
                    break;
                if(endSet.contains(lookahead))
                {
//                    print(symbol, null, depth);
//                    System.out.println(" " + beginLoc + lexer.getLocation() + "  ----- Failed");
                    return null;
                }
                lookahead = lex();
            }
        }

        int actionId = result.getKey(); // get user-defined action

        List<Integer> right = result.getValue(); // right-hand side of production
        int length = right.size();
        SemValue[] params = new SemValue[length + 1];

//        print(symbol, right, depth);
//        System.out.println(beginLoc);

        for (int i = 0; i < length; i++) { // parse right-hand side symbols one by one
            int term = right.get(i);
            params[i + 1] = isNonTerminal(term)
                    ? parse(term, endSet, depth + 1) // for non terminals: recursively parse it
                    : matchToken(term) // for terminals: match token
                    ;
        }

        Location endLoc = lexer.getLocation();

        params[0] = new SemValue(); // initialize return value
        try {
            act(actionId, params); // do user-defined action
        } catch (Exception ignored) {

        }

        return params[0];
    }

    /**
     * Match if the lookahead token is the same as the expected token.
     *
     * @param expected the expected token.
     * @return same as `parse`.
     */
    private SemValue matchToken(int expected) {
        SemValue self = val;
        if (lookahead != expected) {
            error();
            return null;
        }

        lookahead = lex();
        return self;
    }

    /**
     * Explicit interface of the parser. Call it in `Driver` class!
     *
     * @return the program AST if successfully parsed, otherwise `null`.
     */
    public Tree.TopLevel parseFile() {
        lookahead = lex();
        SemValue r = parse(start, new HashSet<>(), 0);
        return r == null ? null : r.prog;
    }

    /**
     * Helper function. (For debugging)
     * Pretty print a symbol set.
     *
     * @param set symbol set.
     */
    private void printSymbolSet(Set<Integer> set) {
        StringBuilder buf = new StringBuilder();
        buf.append("{ ");
        for (Integer i : set) {
            buf.append(name(i));
            buf.append(" ");
        }
        buf.append("}");
        System.out.print(buf.toString());
    }

    /**
     * Helper function. (For debugging)
     * Pretty print a symbol list.
     *
     * @param list symbol list.
     */
    private void printSymbolList(List<Integer> list) {
        StringBuilder buf = new StringBuilder();
        buf.append(" ");
        for (Integer i : list) {
            buf.append(name(i));
            buf.append(" ");
        }
        System.out.print(buf.toString());
    }

    /**
     * Diagnose function. (For debugging)
     * Implement this by yourself on demand.
     */
    public void diagnose() {

    }

}
