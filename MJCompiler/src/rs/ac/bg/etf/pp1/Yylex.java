/* The following code was generated by JFlex 1.4.3 on 8/5/24 10:16 PM */

package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;



/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 8/5/24 10:16 PM from the specification file
 * <tt>spec/mjlexer.lex</tt>
 */
class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\1\2\1\2\1\4\1\0\1\2\1\3\22\0\1\1\1\40"+
    "\3\63\1\36\1\43\1\62\1\51\1\52\1\34\1\32\1\47\1\33"+
    "\1\50\1\35\12\57\1\46\1\45\1\41\1\37\1\42\2\63\32\60"+
    "\1\53\1\63\1\54\1\63\1\61\1\63\1\11\1\13\1\16\1\26"+
    "\1\14\1\24\1\10\1\60\1\23\1\60\1\15\1\17\1\12\1\21"+
    "\1\7\1\5\1\60\1\6\1\20\1\22\1\27\1\30\1\25\1\31"+
    "\2\60\1\55\1\44\1\56\1\63\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\1\14\3\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\1\1\12\1\13\2\1\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\1\2\27\1\30\21\27\14\3\1\31"+
    "\3\3\1\32\1\33\1\34\1\35\1\36\1\37\1\40"+
    "\1\41\1\42\1\0\14\3\1\43\1\3\1\44\2\3"+
    "\1\45\3\3\1\46\2\3\1\47\6\3\1\50\1\51"+
    "\1\3\1\52\1\53\1\3\1\54\1\3\1\55\1\3"+
    "\1\56\3\3\1\57\2\3\1\60\1\3\1\61\1\62"+
    "\2\3\1\63\1\3\1\64";

  private static int [] zzUnpackAction() {
    int [] result = new int[143];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\64\0\150\0\150\0\234\0\320\0\u0104\0\u0138"+
    "\0\u016c\0\u01a0\0\u01d4\0\u0208\0\u023c\0\u0270\0\u02a4\0\u02d8"+
    "\0\u030c\0\u0340\0\u0374\0\150\0\u03a8\0\150\0\u03dc\0\u0410"+
    "\0\u0444\0\u0478\0\u04ac\0\u04e0\0\150\0\150\0\150\0\150"+
    "\0\150\0\150\0\150\0\150\0\150\0\150\0\u0514\0\u0548"+
    "\0\150\0\234\0\150\0\320\0\u0104\0\u0138\0\u016c\0\u01a0"+
    "\0\u01d4\0\u0208\0\u023c\0\u0270\0\u02a4\0\u02d8\0\u030c\0\u0410"+
    "\0\u04ac\0\u04e0\0\u0514\0\u0548\0\u057c\0\u05b0\0\u05e4\0\u0618"+
    "\0\u064c\0\u0680\0\u06b4\0\u06e8\0\u071c\0\u0750\0\u0784\0\u07b8"+
    "\0\u0138\0\u07ec\0\u0820\0\u0854\0\150\0\150\0\150\0\150"+
    "\0\150\0\150\0\150\0\150\0\150\0\u0888\0\u08bc\0\u08f0"+
    "\0\u0924\0\u0958\0\u098c\0\u09c0\0\u09f4\0\u0a28\0\u0a5c\0\u0a90"+
    "\0\u0ac4\0\u0af8\0\u0138\0\u0b2c\0\u0138\0\u0b60\0\u0b94\0\150"+
    "\0\u0bc8\0\u0bfc\0\u0c30\0\u0138\0\u0c64\0\u0c98\0\u0138\0\u0ccc"+
    "\0\u0d00\0\u0d34\0\u0d68\0\u0d9c\0\u0dd0\0\u0138\0\u0138\0\u0e04"+
    "\0\u0138\0\u0138\0\u0e38\0\u0138\0\u0e6c\0\u0138\0\u0ea0\0\u0138"+
    "\0\u0ed4\0\u0f08\0\u0f3c\0\u0138\0\u0f70\0\u0fa4\0\u0138\0\u0fd8"+
    "\0\u0138\0\u0138\0\u100c\0\u1040\0\u0138\0\u1074\0\u0138";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[143];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\2\4\1\5\1\0\1\6\1\7\4\10\1\11"+
    "\1\12\1\10\1\13\1\10\1\14\1\15\1\16\1\17"+
    "\1\20\3\10\1\21\1\10\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45"+
    "\1\46\1\47\1\10\1\3\1\50\1\3\1\51\2\4"+
    "\1\52\1\53\1\54\1\55\4\56\1\57\1\60\1\56"+
    "\1\61\1\56\1\62\1\63\1\64\1\65\1\66\3\56"+
    "\1\67\1\56\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\70\1\31\1\32\1\71\1\72\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\73"+
    "\1\56\1\51\1\74\1\51\70\0\1\4\64\0\1\10"+
    "\1\75\23\10\25\0\3\10\7\0\4\10\1\76\2\10"+
    "\1\77\15\10\25\0\3\10\7\0\25\10\25\0\3\10"+
    "\7\0\1\10\1\100\23\10\25\0\3\10\7\0\12\10"+
    "\1\101\11\10\1\102\25\0\3\10\7\0\2\10\1\103"+
    "\7\10\1\104\12\10\25\0\3\10\7\0\15\10\1\105"+
    "\7\10\25\0\3\10\7\0\4\10\1\106\2\10\1\107"+
    "\15\10\25\0\3\10\7\0\1\10\1\110\23\10\25\0"+
    "\3\10\7\0\17\10\1\111\5\10\25\0\3\10\7\0"+
    "\2\10\1\112\1\10\1\113\20\10\25\0\3\10\7\0"+
    "\2\10\1\114\22\10\25\0\3\10\34\0\1\115\64\0"+
    "\1\116\65\0\1\51\65\0\1\117\2\0\1\120\60\0"+
    "\1\121\63\0\1\122\63\0\1\123\67\0\1\124\64\0"+
    "\1\125\76\0\1\47\5\0\1\126\3\0\57\126\5\0"+
    "\2\10\1\127\13\10\1\130\6\10\25\0\3\10\7\0"+
    "\14\10\1\131\10\10\25\0\3\10\7\0\4\10\1\132"+
    "\10\10\1\133\7\10\25\0\3\10\7\0\7\10\1\134"+
    "\15\10\25\0\3\10\7\0\13\10\1\135\11\10\25\0"+
    "\3\10\7\0\15\10\1\136\7\10\25\0\3\10\7\0"+
    "\14\10\1\137\10\10\25\0\3\10\7\0\4\10\1\140"+
    "\20\10\25\0\3\10\7\0\4\10\1\141\20\10\25\0"+
    "\3\10\7\0\5\10\1\142\17\10\25\0\3\10\7\0"+
    "\20\10\1\143\4\10\25\0\3\10\7\0\22\10\1\144"+
    "\2\10\25\0\3\10\7\0\1\10\1\145\23\10\25\0"+
    "\3\10\7\0\12\10\1\146\12\10\25\0\3\10\7\0"+
    "\16\10\1\147\6\10\25\0\3\10\64\0\1\150\6\0"+
    "\3\10\1\151\21\10\25\0\3\10\7\0\14\10\1\152"+
    "\10\10\25\0\3\10\7\0\3\10\1\153\21\10\25\0"+
    "\3\10\7\0\21\10\1\154\3\10\25\0\3\10\7\0"+
    "\22\10\1\155\2\10\25\0\3\10\7\0\4\10\1\156"+
    "\20\10\25\0\3\10\7\0\7\10\1\157\15\10\25\0"+
    "\3\10\7\0\7\10\1\160\15\10\25\0\3\10\7\0"+
    "\13\10\1\161\1\10\1\162\7\10\25\0\3\10\7\0"+
    "\13\10\1\163\11\10\25\0\3\10\7\0\15\10\1\164"+
    "\7\10\25\0\3\10\7\0\7\10\1\165\15\10\25\0"+
    "\3\10\7\0\7\10\1\166\15\10\25\0\3\10\7\0"+
    "\13\10\1\144\11\10\25\0\3\10\7\0\21\10\1\167"+
    "\3\10\25\0\3\10\7\0\1\10\1\170\23\10\25\0"+
    "\3\10\7\0\15\10\1\171\7\10\25\0\3\10\7\0"+
    "\7\10\1\172\15\10\25\0\3\10\7\0\1\10\1\173"+
    "\23\10\25\0\3\10\7\0\10\10\1\174\14\10\25\0"+
    "\3\10\7\0\14\10\1\175\10\10\25\0\3\10\7\0"+
    "\15\10\1\176\7\10\25\0\3\10\7\0\16\10\1\177"+
    "\6\10\25\0\3\10\7\0\13\10\1\200\11\10\25\0"+
    "\3\10\7\0\16\10\1\201\6\10\25\0\3\10\7\0"+
    "\13\10\1\202\11\10\25\0\3\10\7\0\4\10\1\203"+
    "\20\10\25\0\3\10\7\0\14\10\1\204\10\10\25\0"+
    "\3\10\7\0\21\10\1\205\3\10\25\0\3\10\7\0"+
    "\14\10\1\206\10\10\25\0\3\10\7\0\11\10\1\207"+
    "\13\10\25\0\3\10\7\0\1\210\24\10\25\0\3\10"+
    "\7\0\5\10\1\211\17\10\25\0\3\10\7\0\13\10"+
    "\1\212\11\10\25\0\3\10\7\0\22\10\1\213\2\10"+
    "\25\0\3\10\7\0\4\10\1\214\20\10\25\0\3\10"+
    "\7\0\7\10\1\215\15\10\25\0\3\10\7\0\11\10"+
    "\1\216\13\10\25\0\3\10\7\0\7\10\1\217\15\10"+
    "\25\0\3\10\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4264];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\2\11\17\1\1\11\1\1\1\11\6\1\12\11"+
    "\2\1\1\11\1\1\1\11\41\1\11\11\1\0\21\1"+
    "\1\11\47\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[143];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */

	private Symbol new_symbol(int type){
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value){
		return new Symbol(type, yyline+1, yycolumn, value);
	}



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 132) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 13: 
          { return new_symbol(sym.COLON, yytext());
          }
        case 53: break;
        case 4: 
          { return new_symbol(sym.PLUS, yytext());
          }
        case 54: break;
        case 36: 
          { return new_symbol(sym.FOR, yytext());
          }
        case 55: break;
        case 17: 
          { return new_symbol(sym.RPAREN, yytext());
          }
        case 56: break;
        case 48: 
          { return new_symbol(sym.STATIC, yytext());
          }
        case 57: break;
        case 37: 
          { return new_symbol(sym.CHAR, new Character(yytext().charAt(1)));
          }
        case 58: break;
        case 14: 
          { return new_symbol(sym.COMMA, yytext());
          }
        case 59: break;
        case 22: 
          { return new_symbol(sym.NUMBER, new Integer(yytext()));
          }
        case 60: break;
        case 12: 
          { return new_symbol(sym.SEMICOLON, yytext());
          }
        case 61: break;
        case 50: 
          { return new_symbol(sym.EXTENDS, yytext());
          }
        case 62: break;
        case 35: 
          { return new_symbol(sym.NEW, yytext());
          }
        case 63: break;
        case 21: 
          { return new_symbol(sym.RBRACE, yytext());
          }
        case 64: break;
        case 39: 
          { return new_symbol(sym.ELSE, yytext());
          }
        case 65: break;
        case 25: 
          { return new_symbol(sym.IF, yytext());
          }
        case 66: break;
        case 15: 
          { return new_symbol(sym.DOT, yytext());
          }
        case 67: break;
        case 9: 
          { return new_symbol(sym.ASSIGN, yytext());
          }
        case 68: break;
        case 7: 
          { return new_symbol(sym.DIV, yytext());
          }
        case 69: break;
        case 5: 
          { return new_symbol(sym.MINUS, yytext());
          }
        case 70: break;
        case 30: 
          { return new_symbol(sym.NOT_EQUAL, yytext());
          }
        case 71: break;
        case 29: 
          { return new_symbol(sym.ARROW, yytext());
          }
        case 72: break;
        case 41: 
          { return new_symbol(sym.VOID, yytext());
          }
        case 73: break;
        case 24: 
          { yybegin(YYINITIAL);
          }
        case 74: break;
        case 45: 
          { return new_symbol(sym.CONST, yytext());
          }
        case 75: break;
        case 3: 
          { return new_symbol(sym.IDENT, yytext());
          }
        case 76: break;
        case 33: 
          { return new_symbol(sym.AND, yytext());
          }
        case 77: break;
        case 16: 
          { return new_symbol(sym.LPAREN, yytext());
          }
        case 78: break;
        case 28: 
          { return new_symbol(sym.EQUAL, yytext());
          }
        case 79: break;
        case 10: 
          { return new_symbol(sym.LESS, yytext());
          }
        case 80: break;
        case 23: 
          { yybegin(COMMENT);
          }
        case 81: break;
        case 18: 
          { return new_symbol(sym.LBRACKET, yytext());
          }
        case 82: break;
        case 8: 
          { return new_symbol(sym.MOD, yytext());
          }
        case 83: break;
        case 6: 
          { return new_symbol(sym.MUL, yytext());
          }
        case 84: break;
        case 43: 
          { return new_symbol(sym.RANGE, yytext());
          }
        case 85: break;
        case 40: 
          { return new_symbol(sym.BOOL, new Boolean(yytext()));
          }
        case 86: break;
        case 44: 
          { return new_symbol(sym.BREAK, yytext());
          }
        case 87: break;
        case 20: 
          { return new_symbol(sym.LBRACE, yytext());
          }
        case 88: break;
        case 38: 
          { return new_symbol(sym.READ, yytext());
          }
        case 89: break;
        case 26: 
          { return new_symbol(sym.INC, yytext());
          }
        case 90: break;
        case 11: 
          { return new_symbol(sym.GREATER, yytext());
          }
        case 91: break;
        case 1: 
          { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1));
          }
        case 92: break;
        case 32: 
          { return new_symbol(sym.GREATER_EQUAL, yytext());
          }
        case 93: break;
        case 31: 
          { return new_symbol(sym.LESS_EQUAL, yytext());
          }
        case 94: break;
        case 42: 
          { return new_symbol(sym.PRINT, yytext());
          }
        case 95: break;
        case 19: 
          { return new_symbol(sym.RBRACKET, yytext());
          }
        case 96: break;
        case 52: 
          { return new_symbol(sym.NAMESPACE, yytext());
          }
        case 97: break;
        case 46: 
          { return new_symbol(sym.CLASS, yytext());
          }
        case 98: break;
        case 49: 
          { return new_symbol(sym.PROGRAM, yytext());
          }
        case 99: break;
        case 47: 
          { return new_symbol(sym.RETURN, yytext());
          }
        case 100: break;
        case 51: 
          { return new_symbol(sym.CONTINUE, yytext());
          }
        case 101: break;
        case 34: 
          { return new_symbol(sym.OR, yytext());
          }
        case 102: break;
        case 27: 
          { return new_symbol(sym.DEC, yytext());
          }
        case 103: break;
        case 2: 
          { 
          }
        case 104: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { 	return new_symbol(sym.EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
