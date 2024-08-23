package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	private boolean plusVisited = false, minusVisited = false;
	private boolean mulVisited = false, divVisited = false, modVisited = false;
	
	
	public int getMainPc() {
		return mainPc;
	}
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
	
	// ---------------------------------------------- Method -------------------------------------------
	
	public void visit(MethodReturnTypeVoid voidTypeAndName) {
		if(voidTypeAndName.getMethodName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		voidTypeAndName.obj.setAdr(Code.pc);	// za metode u polje adr objektnog cvora se upisuje adresa na kojoj se nalaze
		
		//enter instr pravi aktivacioni zapis na steku i da sve parametre sa exprStack-a iskopira u aktivacioni zapis pozvane metode 
		
		//dohvatanje argumenata i lokalnih promenljivih
		SyntaxNode methodNode = voidTypeAndName.getParent();
		
		VarCounter varCounter = new VarCounter();
		methodNode.traverseTopDown(varCounter);
		
		
		FormParamCounter formParamCounter = new FormParamCounter();
		methodNode.traverseTopDown(formParamCounter);
		
		// 
		Code.put(Code.enter);
		Code.put(formParamCounter.getCount());
		Code.put(formParamCounter.getCount() + varCounter.getCount());
		
		//sad su izgenerisane pocetne instrukcije za bilo koju fju
	}
	
	
	public void visit(MethodDecl methDecl) {
		// zavrsne instrukcije unutar metode
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	// ---------------------------------------------- PRINT -------------------------------------------
	
	public void visit(StatementPrintExpr printStm) {
		// Statement ::= PRINT LPAREN Expr RPAREN SEMICOLON
		
		// ... , val, width
		//val treba vec da se izracuna u expr i stavi na stek, tako da mi samo stavljamo width
		
		if(printStm.getExpr().struct == MyTab.intType || printStm.getExpr().struct == MyTab.boolType) {
			Code.loadConst(5);	//width
			Code.put(Code.print);
		}
		else {
			//char
			Code.loadConst(1); //width
			Code.put(Code.bprint);
		}
	}
	
	
	// ---------------------------------------------- READ -------------------------------------------
	
	public void visit(StatementRead read) {
		// Statement ::= READ LPAREN Designator RPAREN SEMICOLON
		
		//Designator  je npr x[0] i on nece biti struct.arr nego struct.int zato sto je element niza tako sam namestio u semantickoj analizi
		
		SyntaxNode designatorParent = read.getDesignator().getParent();
		Struct designatorStruct = read.getDesignator().obj.getType();
		
		
		
		if(read.getDesignator() instanceof DesignatorIdent) {
			report_info("1", null);
			if(designatorStruct == MyTab.intType || designatorStruct == MyTab.boolType) {
				Code.put(Code.pop);	//radi se pop je kad se ode u designatorIdent loaduje se vrednost jer je potrebna za read kad je designator niz
				Code.put(Code.read); // procita int i pusuje ga na stek
				Code.store(read.getDesignator().obj);
			}
			else if(designatorStruct == MyTab.charType){
				Code.put(Code.pop);
				Code.put(Code.bread);
				Code.store(read.getDesignator().obj);
			}
			else {
				report_info("Faza generisanja koda klasa StatementRead  designatorStruct" ,null);
			}
		}
		else if(read.getDesignator() instanceof DesignatorArray){
			report_info("2", null);
			// ucitavanje u element niza  -> aload se korist na steku treba da bude [adr, index, val] pa tek onda aload ili baload
			if(designatorStruct == MyTab.intType || designatorStruct == MyTab.boolType) {
				// adr i index su vec na steku
				Code.put(Code.read);	//val
				Code.put(Code.astore);
			}
			else if(designatorStruct == MyTab.charType){
				Code.put(Code.bread);
				Code.put(Code.bastore);
			}
			else {
				report_info("Faza generisanja koda klasa StatementRead  array slucaj" ,null);
			}
		}
		else {
			report_info("3", null);
		}
	}
	
	// ---------------------------------------------- Factor -------------------------------------------
	
	public void visit(FactorNumber num) {
		Obj numConst = MyTab.insert(Obj.Con, "$", num.struct);
		
		numConst.setLevel(0);
		numConst.setAdr(num.getN1());	//vrednost konstante cemo pokupiti iz leksickog analizatora
		// konstante se javljaju u okviru izraza i treba je staviti na exprStack
		
		Code.load(numConst);	// na exprStack se stavi objektni cvor
	}
	
	public void visit(FactorChar charConst) {
		Obj newCharConst = MyTab.insert(Obj.Con, "$", charConst.struct);
		
		newCharConst.setLevel(0);
		newCharConst.setAdr(charConst.getC1());  	// vrednost konstante je samo ime konstane
		
		Code.load(newCharConst);
	}
	
	public void visit(FactorBool boolConst) {
		Obj newBoolConst = MyTab.insert(Obj.Con, "$", boolConst.struct);
		
		newBoolConst.setLevel(0);
		
		if(boolConst.getB1()) {
			newBoolConst.setAdr(1);
		}
		else {
			newBoolConst.setAdr(0);
		}
		
		Code.load(newBoolConst);
	}
	
	public void visit(NewFactorArray arrayAlocation) {
		// NewFactor ::= NEW Type:arrType LBRACKET Expr:arraySize RBRACKET
		
		//expr:arraySize ce vec biti na steku
		
		Code.put(Code.newarray);
		if(arrayAlocation.getType().struct == MyTab.intType || arrayAlocation.getType().struct == MyTab.boolType) {
			// newarray 1 -> niz reci
			Code.put(1);
		}
		else {
			//newarray 0 -> niz bajtova  za char type
			Code.put(0);
		}
		   
	}
	
	// ---------------------------------------------- Term -------------------------------------------
	
	public void visit(TermMul mul) {
		// Term ::= Term Mulop Factor
		
		// elementi koji ucestvuju u izrazu su vec na steku
		
//		if(mulVisited) {
//			Code.put(Code.mul);
//		}
//		else if(divVisited) {
//			Code.put(Code.div);
//		}
//		else if(modVisited) {
//			Code.put(Code.rem);
//		}
//		else {
//			report_info("Faza generisanje koda u klasi TermMul Mulop nije ni mul ni div ni mod", null);
//		}
//		
//		mulVisited = divVisited = modVisited = false;
		
		
		if(mul.getMulop() instanceof MulOperator) {
			Code.put(Code.mul);
		}
		else if(mul.getMulop() instanceof DivOperator) {
			Code.put(Code.div);
		}
		else if(mul.getMulop() instanceof ModOperator) {
			Code.put(Code.rem);
		}
		else {
			report_info("Faza generisanje koda u klasi TermMul Mulop nije ni mul ni div ni mod", mul);
		}
		
	}

	// ---------------------------------------------- Expr -------------------------------------------
	
	public void visit(ExprAddTerm add) {
		// Expr ::= Expr:expr Addop Term:term
		
		// elementi koji ucestvuju u izrazu su vec na steku
		
		SyntaxNode addOpParent = add.getAddop().getParent();
		
//		if(plusVisited) {
//			Code.put(Code.add);
//		}
//		else if(minusVisited){
//			Code.put(Code.sub);
//		}
//		else {
//			report_info("Faza generisanje koda u klasi ExprAddTerm Addop nije ni plus ni minus", null);
//		}
//		
//		plusVisited = minusVisited = false;
		
		if(add.getAddop() instanceof PlusOperator) {
			Code.put(Code.add);
		}
		else if(add.getAddop() instanceof MinusOperator) {
			Code.put(Code.sub);
		}
		else {
			report_info("Faza generisanje koda u klasi ExprAddTerm Addop nije ni plus ni minus", add);
		}
		
		
	}
	
	
	public void visit(ExprMinusTerm minus) {
		// Expr ::= MINUS Term:t
		
		Code.put(Code.neg);
	}
	
	
	// ---------------------------------------------- Addop -------------------------------------------
	
	public void visit(PlusOperator plus) {
		this.plusVisited = true;
	}
	
	public void visit(MinusOperator minus) {
		this.minusVisited = true;
	}
	
	
	
	// ---------------------------------------------- Mulop -------------------------------------------
	
	public void visit(MulOperator mul) {
		this.mulVisited = true;
	}
	
	public void visit(DivOperator div) {
		this.divVisited = true;
	}

	public void visit(ModOperator mod) {
		this.modVisited = true;
	}
	
	// ---------------------------------------------- DesignatorStatement -------------------------------------------
	
	public void visit(DesignatorStatementAssign assignment) {
		// DesignatorStatement ::=  Designator:destination Assignop Expr:value SEMICOLON
		
		SyntaxNode designatorParent = assignment.getParent();
		SyntaxNode exprParent = assignment.getExpr().getParent();
		
		
		
		if(designatorParent.getClass().equals(DesignatorArray.class)){
			// dodela vrednosti elementu niza
			
			Struct arrayType = assignment.getDesignator().obj.getType().getElemType();
			
			if(arrayType == MyTab.intType || arrayType == MyTab.boolType ) {
				Code.put(Code.astore);
			}
			else if(arrayType == MyTab.charType) {
				Code.put(Code.bastore);
			}
			else {
				report_info("pri dodeli vrednosti designator je bio matrica", assignment);
			}
			
		}
		else {
			// dohvatamo vrednost exprStacka(jer smatramo da je expr vec postavio na stek vrednost) i sacuvamo je u destination
			Code.store(assignment.getDesignator().obj);	//odgovaraju instrukcija ce se generisati na osnovu prirode cvora
		}
		
		
		
	}
	
	
	// ---------------------------------------------- Designator -------------------------------------------
	
	public void visit(DesignatorIdent des) {
		// Designator ::= IDENT:designatorName
		
		SyntaxNode parent = des.getParent();	
		
		// za ove slucajeve roditeljskih klasa ne treba da se stavlja na stek designator
		// jer ili nema potrebe ili se to radi u toj roditeljskoj klasi kao npr za inc 
		if(!(
				parent.getClass().equals(DesignatorStatementAssign.class) ||
				parent.getClass().equals(DesignatorStatementInc.class) ||
				parent.getClass().equals(DesignatorStatementDec.class) 
				)) {
			Code.load(des.obj);	
		}

	}
	
	public void visit(DesignatorArray array) {
		// Designator ::= Designator:designatorName LBRACKET Expr:expr RBRACKET
		
		
		SyntaxNode parent = array.getParent();
		
		// u DesignatorIdent (jer se on prvi posecuje)  stavimo na stek designator -> niz
		// expr ce staviti const koja je arraySize na stek
		
		
		if(parent.getClass().equals(DesignatorFactorDesignator.class)) {
			// ovde se dolazi za slucaj print(niz[5])
			
			
			Struct elemType = array.getDesignator().obj.getType().getElemType();
			
			if(elemType == MyTab.intType || elemType == MyTab.boolType) {
				Code.put(Code.aload);	// dohvati vrednost elementa niza i stavi na stek, spremno je za print koje ocekuje vrednost za ispis
			}
			else if(elemType == MyTab.charType) {
				Code.put(Code.baload);	// dohvati vrednost elementa niza i stavi na stek, spremno je za print koje ocekuje vrednost za ispis
			}
			else {
				report_info("U fazi generisanja koda u klasi DesignatorArray elemType nije nijedan od standardnih tipova", null);
			}
		}
		
		
	}
	
	
	// ---------------------------------------------- Designator INC  -------------------------------------------
	
	public void visit(DesignatorStatementInc designatorInc) {
		// DesignatorStatement ::= Designator:designator INC SEMICOLON
		
		Obj designator = designatorInc.getDesignator().obj;
		
		Code.load(designator);	// x 
		Code.put(Code.const_1);	// x 1 
		Code.put(Code.add);		// (x+1)
		Code.store(designator); // x = x + 1
	}
	
	// ---------------------------------------------- Designator DEC  -------------------------------------------
	
	public void visit(DesignatorStatementDec designatorDec) {
		// DesignatorStatement ::= Designator:designator DEC SEMICOLON
		
		Obj designator = designatorDec.getDesignator().obj;
		
		Code.load(designator);
		Code.put(Code.const_1);
		Code.put(Code.sub);		
		Code.store(designator);
	}
	
}















































