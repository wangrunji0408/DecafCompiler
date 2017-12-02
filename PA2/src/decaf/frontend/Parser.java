//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short COMPLEX=257;
public final static short PRINTCOMP=258;
public final static short CASE=259;
public final static short DEFAULT=260;
public final static short SUPER=261;
public final static short DCOPY=262;
public final static short SCOPY=263;
public final static short VOID=264;
public final static short BOOL=265;
public final static short INT=266;
public final static short STRING=267;
public final static short CLASS=268;
public final static short SPLIT=269;
public final static short DO=270;
public final static short OD=271;
public final static short NULL=272;
public final static short EXTENDS=273;
public final static short THIS=274;
public final static short WHILE=275;
public final static short FOR=276;
public final static short IF=277;
public final static short ELSE=278;
public final static short RETURN=279;
public final static short BREAK=280;
public final static short NEW=281;
public final static short PRINT=282;
public final static short READ_INTEGER=283;
public final static short READ_LINE=284;
public final static short LITERAL=285;
public final static short IDENTIFIER=286;
public final static short AND=287;
public final static short OR=288;
public final static short STATIC=289;
public final static short INSTANCEOF=290;
public final static short LESS_EQUAL=291;
public final static short GREATER_EQUAL=292;
public final static short EQUAL=293;
public final static short NOT_EQUAL=294;
public final static short UMINUS=295;
public final static short EMPTY=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   14,   14,
   14,   26,   26,   23,   23,   25,   28,   30,   31,   31,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   29,   29,   27,   27,   32,   32,   33,
   34,   34,   15,   17,   18,   22,   16,   35,   35,   19,
   19,   20,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    2,
    1,    1,    1,    2,    2,    2,    2,    1,    3,    1,
    0,    2,    0,    2,    4,    5,    4,    4,    2,    0,
    1,    1,    1,    8,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    2,    2,
    2,    2,    2,    3,    3,    1,    1,    4,    4,    4,
    5,    6,    5,    1,    1,    1,    0,    3,    1,    3,
    3,    1,    3,    5,    9,    1,    6,    2,    0,    2,
    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    9,    6,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,    0,    0,    0,    0,
    0,   77,    0,    0,    0,   85,   76,    0,    0,    0,
    0,   96,    0,    0,    0,    0,   84,    0,    0,    0,
    0,   25,   28,   38,   26,    0,    0,   31,   32,   33,
    0,    0,    0,    0,    0,    0,    0,    0,   53,    0,
   51,   71,   52,   72,   73,    0,    0,    0,    0,   92,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   30,   34,   35,   36,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   93,    0,    0,    0,    0,
    0,    0,   74,   75,    0,    0,   68,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  103,    0,   78,   79,   90,   91,
    0,    0,    0,   80,    0,  102,    0,    0,   45,    0,
    0,    0,   50,   94,    0,    0,   81,    0,   83,   46,
    0,    0,    0,   97,   82,    0,   49,    0,    0,    0,
   98,    0,    0,   54,    0,    0,    0,   95,   48,   47,
};
final static short yydgoto[] = {                          2,
    3,    4,   73,   21,   34,    8,   11,   23,   35,   36,
   74,   46,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,   91,   86,   93,   88,  190,  207,   89,  209,
  201,  140,  100,  101,  204,
};
final static short yysindex[] = {                      -260,
 -267,    0, -260,    0, -245,    0, -246,  -79,    0,    0,
  269,    0,    0,    0,    0,    0, -241, -164,    0,    0,
   -1,  -90,    0,    0,  -87,    0,   28,  -17,   35, -164,
    0, -164,    0,  -86,   48,   55,   70,    0,   -9, -164,
   -9,    0,    0,    0,    0,   -3,   75,  303,  303,  303,
   76,    0,   79,   81,  303,    0,    0,   84,   85,   88,
  303,    0, -216,   90,   91,   92,    0,   93,  303,  303,
   72,    0,    0,    0,    0,   80,   86,    0,    0,    0,
   87,  100,  101,  109,   73,  901,    0, -143,    0,  303,
    0,    0,    0,    0,    0,  303,  303,  303,  448,    0,
 -205,  303,  303,  303,  901,  104,   58,  303,  110,  131,
  303,  -35,  -35, -112,  470,    0,    0,    0,    0,    0,
    0,  303,  303,  303,  303,  303,  303,  303,  303,  303,
  303,  303,  303,  303,  303,    0,  303,  136,  901,  -28,
  507,  554,  565,   27,  303,    0,  576,  118,  606,  140,
  102,  -27,    0,    0,  643,  142,    0,  901,  959,  933,
  373,  373,  983,  983,  -22,  -22,  -35,  -35,  -35,  373,
  373,  723,  303,  303,    0,   62,    0,    0,    0,    0,
   27,  303,   27,    0,  744,    0, -100,  303,    0,  146,
  149,  901,    0,    0,  829,  -81,    0,  157,    0,    0,
 -226,  303,   27,    0,    0,  143,    0,  144,   78,  167,
    0,  303,  303,    0,   27,  859,  880,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  210,    0,   89,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  152,    0,    0,  173,
    0,  173,    0,    0,    0,  174,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,  -70,  -70,  -70,
    0,    0,    0,    0,  -70,    0,    0,    0,    0,    0,
  -16,    0,    0,    0,    0,    0,    0,    0,  -70,  -70,
  -70,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  912,    0,  437,    0,    0,  -70,
    0,    0,    0,    0,    0,  -70,  -70,  -70,    0,    0,
    0,  -70,  -41,  -70,  158,    0,    0,  -70,    0,    0,
  -70,  111,  120,    0,    0,    0,    0,    0,    0,    0,
    0,  -70,  -70,  -70,  -70,  -70,  -70,  -70,  -70,  -70,
  -70,  -70,  -70,  -70,  -70,    0,  -70,   36,   -6,    0,
    0,    0,    0,  -41,  -70,    0,    0,    0,    0,    0,
  -70,    0,    0,    0,    0,    0,    0,  -32,   82,   -5,
  428,  792,  941,  998,  767, 1008,  147,  384,  408,  821,
  925,    0,  -20,  -70,    0,    0,    0,    0,    0,    0,
  -41,  -70,  -41,    0,    0,    0,    0,  -70,    0,    0,
  177,   69,    0,    0,    0,  -33,    0,    0,    0,    0,
    0,  -18,  -41,    0,    0,    0,    0,    0,    0,    0,
    0,  -70,  -70,    0,  -41,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  216,  209,   25,   11,    0,    0,    0,  189,    0,
   46,    0,  -97,  -93,    0,    0,    0,    0,    0,    0,
    0,    0,  344, 1186,  530,    0,    0,    0,   21,    0,
    0, -102,  130,    0,    0,
};
final static int YYTABLESIZE=1399;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         99,
   28,   99,   99,   28,   28,  152,   99,    1,   39,  148,
  136,   99,  175,  186,  133,  174,  174,   41,    5,  131,
   87,   22,   41,  136,  132,   99,   39,    7,   25,   70,
   99,   50,   49,  206,   89,   67,   71,   89,   67,    9,
   12,   69,  101,   10,   24,   56,  179,   13,   14,   15,
   16,   17,   67,   67,   33,  137,   33,   26,   67,   70,
   48,   50,   49,  145,   44,  146,   71,   30,  137,  106,
  191,   69,   44,  107,   32,   31,   44,   44,   44,   44,
   44,   44,   44,  194,   43,  196,   45,   67,   39,   99,
   48,   99,   12,   44,   44,   44,   44,   44,   40,   13,
   14,   15,   16,   17,   70,  211,   50,   49,  210,   88,
   41,   71,   88,   42,   90,   96,   69,  218,   97,   42,
   98,   72,   66,  102,  103,   66,   44,  104,   44,  108,
  109,  110,  111,  122,   70,   48,   50,   49,  116,   66,
   66,   71,  138,  150,  117,  118,   69,   69,  151,   42,
  153,   69,   69,   69,   69,   69,   70,   69,  119,  120,
   70,   70,   70,   70,   70,   48,   70,  121,   69,   69,
   69,  154,   69,  156,   66,  173,  182,   70,   70,   70,
  184,   70,  188,   57,  193,  198,  200,   57,   57,   57,
   57,   57,  174,   57,   31,   27,  203,  205,   29,   38,
  212,  213,  214,   69,   57,   57,   57,  215,   57,    1,
    5,   15,   70,   20,   19,   43,  100,   86,    6,   20,
   37,  208,    0,   99,   99,   99,    0,   99,   99,   99,
   99,   99,   99,   99,   99,   99,   99,   99,   99,   57,
   99,   99,   99,   99,   43,   99,   99,   99,   99,   99,
   99,   99,   99,   12,   47,   51,   99,   52,   53,   54,
   13,   14,   15,   16,   17,   43,   55,   43,   56,   43,
   57,   58,   59,   60,  180,   61,   62,   63,   64,   65,
   66,   67,   67,   12,   47,   51,   68,   52,   53,   54,
   13,   14,   15,   16,   17,    0,   55,    0,   56,    0,
   57,   58,   59,   60,    0,   61,   62,   63,   64,   65,
   66,   67,    0,    0,    0,    0,   68,    0,    0,    0,
    0,    0,   44,   44,    0,    0,   44,   44,   44,   44,
   51,    0,   52,   53,   54,   70,    0,   50,   49,  114,
    0,    0,   71,   56,    0,   57,    0,   69,    0,    0,
    0,    0,   63,    0,   65,   66,   67,    0,    0,    0,
   51,   68,   52,   53,   54,    0,   48,    0,   66,   66,
    0,    0,    0,   56,    0,   57,    0,    0,    0,    0,
    0,    0,   63,    0,   65,   66,   67,    0,    0,   85,
    0,   68,    0,   19,    0,    0,    0,   69,   69,    0,
    0,   69,   69,   69,   69,    0,   70,   70,    0,  133,
   70,   70,   70,   70,  131,  129,    0,  130,  136,  132,
   58,    0,    0,    0,   58,   58,   58,   58,   58,    0,
   58,    0,    0,   57,   57,    0,    0,   57,   57,   57,
   57,   58,   58,   58,   59,   58,   85,    0,   59,   59,
   59,   59,   59,    0,   59,    0,    0,    0,    0,    0,
    0,    0,    0,  137,    0,   59,   59,   59,   64,   59,
    0,   64,    0,   52,    0,    0,   58,   40,   52,   52,
    0,   52,   52,   52,  133,   64,   64,   85,    0,  131,
  129,    0,  130,  136,  132,   40,   52,    0,   52,    0,
   59,    0,    0,    0,    0,  144,  133,  135,    0,  134,
  157,  131,  129,    0,  130,  136,  132,    0,    0,    0,
   64,    0,    0,    0,   85,   12,   85,   52,    0,  135,
    0,  134,   13,   14,   15,   16,   17,    0,  137,    0,
    0,    0,    0,  133,    0,   85,   85,  176,  131,  129,
    0,  130,  136,  132,    0,    0,    0,   18,   85,    0,
  137,   51,    0,   52,   53,   54,  135,    0,  134,    0,
    0,    0,    0,    0,   56,   87,   57,    0,    0,    0,
    0,    0,    0,   63,    0,   65,   66,   67,    0,    0,
  133,    0,   68,    0,  177,  131,  129,  137,  130,  136,
  132,  133,    0,    0,    0,  178,  131,  129,    0,  130,
  136,  132,  133,  135,    0,  134,  181,  131,  129,    0,
  130,  136,  132,    0,  135,    0,  134,    0,    0,    0,
    0,    0,   87,    0,    0,  135,    0,  134,    0,    0,
    0,    0,  133,    0,  137,    0,  183,  131,  129,    0,
  130,  136,  132,    0,    0,  137,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  135,  137,  134,    0,    0,
   58,   58,    0,   87,   58,   58,   58,   58,    0,  133,
    0,    0,    0,    0,  131,  129,  187,  130,  136,  132,
    0,    0,    0,    0,   59,   59,  137,    0,   59,   59,
   59,   59,  135,    0,  134,    0,    0,    0,    0,    0,
   87,    0,   87,    0,   64,   64,    0,    0,    0,    0,
   64,   64,    0,   52,   52,    0,    0,   52,   52,   52,
   52,   87,   87,  137,  123,  124,    0,    0,  125,  126,
  127,  128,    0,    0,   87,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  123,  124,    0,  133,
  125,  126,  127,  128,  131,  129,    0,  130,  136,  132,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  133,    0,  135,    0,  134,  131,  129,    0,  130,  136,
  132,    0,    0,  123,  124,    0,    0,  125,  126,  127,
  128,    0,    0,  135,    0,  134,    0,   55,    0,   55,
   55,   55,    0,  137,    0,  189,    0,    0,    0,    0,
    0,    0,    0,    0,   55,   55,   55,    0,   55,    0,
    0,    0,   65,    0,  137,   65,  197,    0,    0,    0,
  123,  124,    0,    0,  125,  126,  127,  128,    0,   65,
   65,  123,  124,    0,    0,  125,  126,  127,  128,   55,
    0,   63,  123,  124,   63,  133,  125,  126,  127,  128,
  131,  129,    0,  130,  136,  132,    0,    0,   63,   63,
    0,    0,    0,    0,   65,    0,    0,  202,  135,    0,
  134,    0,  123,  124,    0,  133,  125,  126,  127,  128,
  131,  129,    0,  130,  136,  132,    0,    0,    0,    0,
    0,    0,    0,   63,    0,    0,  133,  219,  135,  137,
  134,  131,  129,    0,  130,  136,  132,    0,    0,  123,
  124,    0,    0,  125,  126,  127,  128,  133,  220,  135,
    0,  134,  131,  129,    0,  130,  136,  132,   51,  137,
    0,    0,    0,   51,   51,    0,   51,   51,   51,    0,
  135,    0,  134,    0,    0,   62,    0,    0,   62,  133,
  137,   51,    0,   51,  131,  129,    0,  130,  136,  132,
    0,   60,   62,   62,   60,    0,    0,    0,    0,    0,
    0,  137,  135,    0,  134,  133,    0,    0,   60,   60,
  131,  129,   51,  130,  136,  132,    0,    0,    0,  123,
  124,    0,    0,  125,  126,  127,  128,   62,  135,  133,
  134,    0,    0,  137,  131,  129,    0,  130,  136,  132,
  123,  124,    0,   60,  125,  126,  127,  128,   61,    0,
    0,   61,  135,    0,  134,    0,    0,    0,   56,  137,
   56,   56,   56,   55,   55,   61,   61,   55,   55,   55,
   55,    0,    0,    0,    0,   56,   56,   56,    0,   56,
    0,    0,    0,  137,    0,    0,    0,    0,   65,   65,
    0,    0,    0,    0,   65,   65,    0,    0,    0,    0,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,    0,    0,    0,    0,    0,    0,   63,   63,    0,
    0,    0,    0,   63,   63,  123,  124,    0,    0,  125,
  126,  127,  128,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  123,  124,    0,    0,  125,
  126,  127,  128,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  123,  124,    0,    0,
  125,  126,  127,  128,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  123,  124,    0,
    0,  125,  126,  127,  128,    0,    0,    0,   51,   51,
    0,    0,   51,   51,   51,   51,    0,    0,    0,    0,
    0,   62,   62,    0,    0,    0,    0,   62,   62,  123,
    0,    0,    0,  125,  126,  127,  128,   60,   60,    0,
    0,    0,    0,   92,   94,   95,    0,    0,    0,    0,
   99,    0,    0,    0,    0,    0,  105,    0,    0,  125,
  126,  127,  128,    0,  112,  113,  115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  125,  126,  139,    0,    0,    0,    0,
    0,  141,  142,  143,   61,   61,    0,  147,    0,  149,
    0,    0,    0,  139,   56,   56,  155,    0,   56,   56,
   56,   56,    0,    0,    0,    0,    0,  158,  159,  160,
  161,  162,  163,  164,  165,  166,  167,  168,  169,  170,
  171,    0,  172,    0,    0,    0,    0,    0,    0,    0,
   99,    0,    0,    0,    0,    0,  185,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  139,  192,
    0,    0,    0,    0,    0,    0,    0,  195,    0,    0,
    0,    0,    0,  199,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  216,  217,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   35,   36,   91,   91,  108,   40,  268,   41,  103,
   46,   45,   41,   41,   37,   44,   44,   59,  286,   42,
   41,   11,   41,   46,   47,   59,   59,  273,   18,   33,
   64,   35,   36,  260,   41,   41,   40,   44,   44,  286,
  257,   45,   59,  123,  286,  272,  144,  264,  265,  266,
  267,  268,   58,   59,   30,   91,   32,   59,  285,   33,
   64,   35,   36,  269,   40,  271,   40,   40,   91,  286,
  173,   45,   37,   63,   40,   93,   41,   42,   43,   44,
   45,   46,   47,  181,   39,  183,   41,   93,   41,  123,
   64,  125,  257,   58,   59,   60,   61,   62,   44,  264,
  265,  266,  267,  268,   33,  203,   35,   36,  202,   41,
   41,   40,   44,  123,   40,   40,   45,  215,   40,  123,
   40,  125,   41,   40,   40,   44,   91,   40,   93,   40,
   40,   40,   40,   61,   33,   64,   35,   36,   59,   58,
   59,   40,  286,   40,   59,   59,   45,   37,   91,  123,
   41,   41,   42,   43,   44,   45,   37,   47,   59,   59,
   41,   42,   43,   44,   45,   64,   47,   59,   58,   59,
   60,   41,   62,  286,   93,   40,   59,   58,   59,   60,
   41,   62,   41,   37,  123,  286,   41,   41,   42,   43,
   44,   45,   44,   47,   93,  286,  278,   41,  286,  286,
   58,   58,  125,   93,   58,   59,   60,   41,   62,    0,
   59,  123,   93,   41,   41,  286,   59,   41,    3,   11,
   32,  201,   -1,  257,  258,  259,   -1,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,   93,
  274,  275,  276,  277,  286,  279,  280,  281,  282,  283,
  284,  285,  286,  257,  258,  259,  290,  261,  262,  263,
  264,  265,  266,  267,  268,  286,  270,  286,  272,  286,
  274,  275,  276,  277,  145,  279,  280,  281,  282,  283,
  284,  285,  288,  257,  258,  259,  290,  261,  262,  263,
  264,  265,  266,  267,  268,   -1,  270,   -1,  272,   -1,
  274,  275,  276,  277,   -1,  279,  280,  281,  282,  283,
  284,  285,   -1,   -1,   -1,   -1,  290,   -1,   -1,   -1,
   -1,   -1,  287,  288,   -1,   -1,  291,  292,  293,  294,
  259,   -1,  261,  262,  263,   33,   -1,   35,   36,  268,
   -1,   -1,   40,  272,   -1,  274,   -1,   45,   -1,   -1,
   -1,   -1,  281,   -1,  283,  284,  285,   -1,   -1,   -1,
  259,  290,  261,  262,  263,   -1,   64,   -1,  287,  288,
   -1,   -1,   -1,  272,   -1,  274,   -1,   -1,   -1,   -1,
   -1,   -1,  281,   -1,  283,  284,  285,   -1,   -1,   46,
   -1,  290,   -1,  125,   -1,   -1,   -1,  287,  288,   -1,
   -1,  291,  292,  293,  294,   -1,  287,  288,   -1,   37,
  291,  292,  293,  294,   42,   43,   -1,   45,   46,   47,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,  287,  288,   -1,   -1,  291,  292,  293,
  294,   58,   59,   60,   37,   62,  103,   -1,   41,   42,
   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   58,   59,   60,   41,   62,
   -1,   44,   -1,   37,   -1,   -1,   93,   41,   42,   43,
   -1,   45,   46,   47,   37,   58,   59,  144,   -1,   42,
   43,   -1,   45,   46,   47,   59,   60,   -1,   62,   -1,
   93,   -1,   -1,   -1,   -1,   58,   37,   60,   -1,   62,
   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   93,   -1,   -1,   -1,  181,  257,  183,   91,   -1,   60,
   -1,   62,  264,  265,  266,  267,  268,   -1,   91,   -1,
   -1,   -1,   -1,   37,   -1,  202,  203,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,  289,  215,   -1,
   91,  259,   -1,  261,  262,  263,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,  272,   46,  274,   -1,   -1,   -1,
   -1,   -1,   -1,  281,   -1,  283,  284,  285,   -1,   -1,
   37,   -1,  290,   -1,   41,   42,   43,   91,   45,   46,
   47,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   37,   60,   -1,   62,   41,   42,   43,   -1,
   45,   46,   47,   -1,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,  103,   -1,   -1,   60,   -1,   62,   -1,   -1,
   -1,   -1,   37,   -1,   91,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   91,   62,   -1,   -1,
  287,  288,   -1,  144,  291,  292,  293,  294,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   44,   45,   46,   47,
   -1,   -1,   -1,   -1,  287,  288,   91,   -1,  291,  292,
  293,  294,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
  181,   -1,  183,   -1,  287,  288,   -1,   -1,   -1,   -1,
  293,  294,   -1,  287,  288,   -1,   -1,  291,  292,  293,
  294,  202,  203,   91,  287,  288,   -1,   -1,  291,  292,
  293,  294,   -1,   -1,  215,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  287,  288,   -1,   37,
  291,  292,  293,  294,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   -1,   -1,  287,  288,   -1,   -1,  291,  292,  293,
  294,   -1,   -1,   60,   -1,   62,   -1,   41,   -1,   43,
   44,   45,   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   -1,
   -1,   -1,   41,   -1,   91,   44,   93,   -1,   -1,   -1,
  287,  288,   -1,   -1,  291,  292,  293,  294,   -1,   58,
   59,  287,  288,   -1,   -1,  291,  292,  293,  294,   93,
   -1,   41,  287,  288,   44,   37,  291,  292,  293,  294,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   58,   59,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   59,   60,   -1,
   62,   -1,  287,  288,   -1,   37,  291,  292,  293,  294,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   -1,   37,   59,   60,   91,
   62,   42,   43,   -1,   45,   46,   47,   -1,   -1,  287,
  288,   -1,   -1,  291,  292,  293,  294,   37,   59,   60,
   -1,   62,   42,   43,   -1,   45,   46,   47,   37,   91,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   60,   -1,   62,   -1,   -1,   41,   -1,   -1,   44,   37,
   91,   60,   -1,   62,   42,   43,   -1,   45,   46,   47,
   -1,   41,   58,   59,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   60,   -1,   62,   37,   -1,   -1,   58,   59,
   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,  287,
  288,   -1,   -1,  291,  292,  293,  294,   93,   60,   37,
   62,   -1,   -1,   91,   42,   43,   -1,   45,   46,   47,
  287,  288,   -1,   93,  291,  292,  293,  294,   41,   -1,
   -1,   44,   60,   -1,   62,   -1,   -1,   -1,   41,   91,
   43,   44,   45,  287,  288,   58,   59,  291,  292,  293,
  294,   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,  287,  288,
   -1,   -1,   -1,   -1,  293,  294,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,  287,  288,   -1,
   -1,   -1,   -1,  293,  294,  287,  288,   -1,   -1,  291,
  292,  293,  294,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  287,  288,   -1,   -1,  291,
  292,  293,  294,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  287,  288,   -1,   -1,
  291,  292,  293,  294,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  287,  288,   -1,
   -1,  291,  292,  293,  294,   -1,   -1,   -1,  287,  288,
   -1,   -1,  291,  292,  293,  294,   -1,   -1,   -1,   -1,
   -1,  287,  288,   -1,   -1,   -1,   -1,  293,  294,  287,
   -1,   -1,   -1,  291,  292,  293,  294,  287,  288,   -1,
   -1,   -1,   -1,   48,   49,   50,   -1,   -1,   -1,   -1,
   55,   -1,   -1,   -1,   -1,   -1,   61,   -1,   -1,  291,
  292,  293,  294,   -1,   69,   70,   71,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  291,  292,   90,   -1,   -1,   -1,   -1,
   -1,   96,   97,   98,  287,  288,   -1,  102,   -1,  104,
   -1,   -1,   -1,  108,  287,  288,  111,   -1,  291,  292,
  293,  294,   -1,   -1,   -1,   -1,   -1,  122,  123,  124,
  125,  126,  127,  128,  129,  130,  131,  132,  133,  134,
  135,   -1,  137,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  145,   -1,   -1,   -1,   -1,   -1,  151,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,  174,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  182,   -1,   -1,
   -1,   -1,   -1,  188,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  212,  213,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"COMPLEX","PRINTCOMP","CASE",
"DEFAULT","SUPER","DCOPY","SCOPY","VOID","BOOL","INT","STRING","CLASS","SPLIT",
"DO","OD","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","UMINUS",
"EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : COMPLEX",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : DoStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"ACaseExpr : Constant ':' Expr ';'",
"DefaultExpr : DEFAULT ':' Expr ';'",
"ACaseExprList : ACaseExprList ACaseExpr",
"ACaseExprList :",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : CASE '(' Expr ')' '{' ACaseExprList DefaultExpr '}'",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : '@' Expr",
"Expr : '$' Expr",
"Expr : '#' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : SUPER",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"DoBranch : Expr ':' Stmt",
"DoBranchList : DoBranchList SPLIT DoBranch",
"DoBranchList : DoBranch",
"DoStmt : DO DoBranchList OD",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 516 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 704 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 60 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 66 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 70 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 80 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 86 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 90 "Parser.y"
{
				        yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
				    }
break;
case 8:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 9:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 10:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 11:
//#line 106 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 110 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 116 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 122 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 126 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 132 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 136 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 148 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 155 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 159 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 166 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 170 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 176 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 182 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 186 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 193 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 198 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 215 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 40:
//#line 219 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 41:
//#line 223 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 230 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 44:
//#line 236 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 45:
//#line 243 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 46:
//#line 249 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 47:
//#line 258 "Parser.y"
{
                        yyval.expr = new Tree.ACase(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 48:
//#line 264 "Parser.y"
{
                        yyval.expr = new Tree.Default(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 49:
//#line 270 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 50:
//#line 274 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.elist = new ArrayList<Expr>();
                    }
break;
case 51:
//#line 281 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 54:
//#line 287 "Parser.y"
{
                        yyval.expr = new Tree.Case(val_peek(5).expr, val_peek(2).elist, val_peek(1).expr, val_peek(7).loc);
                    }
break;
case 55:
//#line 291 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 295 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 299 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 303 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 307 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 311 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 315 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 319 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 323 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 327 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 331 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 335 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 339 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 343 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 69:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 351 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 355 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 359 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 73:
//#line 363 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 367 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 371 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 76:
//#line 375 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 77:
//#line 379 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
                	}
break;
case 78:
//#line 383 "Parser.y"
{
                        yyval.expr = new Tree.DCopy(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 79:
//#line 387 "Parser.y"
{
                        yyval.expr = new Tree.SCopy(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 80:
//#line 391 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 81:
//#line 395 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 82:
//#line 399 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 83:
//#line 403 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 84:
//#line 409 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 85:
//#line 413 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 87:
//#line 420 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 88:
//#line 427 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 89:
//#line 431 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 90:
//#line 438 "Parser.y"
{
                        yyval.stmt = new Tree.DoBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 91:
//#line 444 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 92:
//#line 448 "Parser.y"
{
                        yyval.slist = new ArrayList<Tree>();
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 93:
//#line 455 "Parser.y"
{
						yyval.stmt = new Tree.DoStmt(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 94:
//#line 461 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 95:
//#line 467 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 96:
//#line 473 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 97:
//#line 479 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 98:
//#line 485 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 99:
//#line 489 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 100:
//#line 495 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 101:
//#line 499 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 102:
//#line 505 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 103:
//#line 510 "Parser.y"
{
                        yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
                    }
break;
//#line 1395 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
