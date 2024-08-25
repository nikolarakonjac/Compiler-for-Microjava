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
		else if(read.getDesignator() instanceof DesignatorMatrix) {
			//stek -> m, row, col
			Code.put(Code.dup_x2);
			Code.put(Code.pop);		//col, m, row
			
			if(read.getDesignator().obj.getType().getElemType().getElemType() == MyTab.intType) {
				Code.put(Code.aload);	// col, m[row]
				
				Code.put(Code.dup_x1);	// m[row], col, m[row]
				Code.put(Code.pop);		// m[row], col
				
				
				Code.put(Code.read);	//procita vrednost i stavi je na stek -> m[row], col, val
				
				Code.put(Code.astore);	//m[row][col] = val
			}
			else {
				Code.put(Code.baload);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);	
				
				Code.put(Code.bread);	//procita vrednost i stavi je na stek
				
				Code.put(Code.bastore);
			}
			
			report_info("3", null);
		}
		else {
			report_info("4", null);
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
		
		report_info("usao u NewFactorArray", null);
		
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
	
	public void visit(NewFactorMatrix m) {
		// NewFactor ::= NEW Type LBRACKET Expr:rows RBRACKET LBRACKET Expr:columns RBRACKET
		
		// na steku je -> rows, columns 
		
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
			
						
		if( assignment.getDesignator() instanceof DesignatorArray){
			// niz[0] = 5
			// dodela vrednosti elementu niza -> na steku su: niz, index, val 
			
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
			
			report_info("DesignatorStatementAssign za niz", null);
		}
		else if(assignment.getDesignator() instanceof DesignatorMatrix) {
			// za slucaj m[1][2] = 5
			
			// m[row], col, val su vec na steku
			
			if(assignment.getDesignator().obj.getType().getElemType().getElemType() == MyTab.intType) {
				Code.put(Code.astore);
			}
			else {
				Code.put(Code.bastore);
			}
			
			report_info("usao u DesignatorStatementAssign i dosao je iz DesignatorMatrix", null);
			
		}
		else {
			
			if(assignment.getDesignator().obj.getType().getKind() != Struct.Array) {
				// obicna promenljiva
				Code.store(assignment.getDesignator().obj);	
				
				report_info("DesignatorStatementAssign za obicnu promenljivu", null);	
			}
			else if(assignment.getDesignator().obj.getType().getElemType() == MyTab.intType ||
					assignment.getDesignator().obj.getType().getElemType() == MyTab.charType) {
				// ovo je za nizove -> npr n = new int[3];
				
				Code.store(assignment.getDesignator().obj);	
				
				report_info("klasa DesignatorStatementAssign -> kreiranje niza", null);	
			}
			else {
				// matrica
				// stek -> row, col (col je na vrhu)
					
				Code.put(Code.dup2);		//row, col, row, col
				Code.put(Code.pop);			// row, col, row
				Code.put(Code.newarray);	//row, col, row, newarray 
				
				if(assignment.getDesignator().obj.getType().getElemType().getElemType() == MyTab.intType) {
					Code.put(1);
				}
				else {
					Code.put(0);
				}
				
				//row, col, row, newarray, 1
				
				Code.store(assignment.getDesignator().obj);	// row, col -> smestio je u designator adresu novog niza
				
				// m[0] = new int[col]; m[1] = new int[col]; ...
				
				// OSNOVNI NIZ JE ALOCIRAN, SADA TREBA ZA SVAKU ULAZ TOG OSNOVNOG NIZA DA SE ALOCIRA NOVI NIZ DUZINE COL
				
				// stek -> row, col
				
				// OVDE TREBA DA SE SKOCI
				int mestoPovratka = Code.pc;
				Code.put(Code.dup2);	//OVDE DUPLIRAMO STANJE NA STEKU DA BI NAKON JEDNE ITERACIJE OSTALE NA STEKU ROW I COL
				
				report_info("iteracija", null);
				
				Code.load(assignment.getDesignator().obj);		// row, col, m
				
				Code.put(Code.dup_x2);	// m, row, col, m
				Code.put(Code.pop);		// m, row, col
				
				Code.put(Code.dup_x1);	// m, col, row, col
				Code.put(Code.pop);		// m, col, row
				
				Code.put(Code.const_1);	// m, col, row, 1
				Code.put(Code.sub);		// m, col, row-1
				
				Code.put(Code.dup_x1);	// m, row-1, col, row-1
				Code.put(Code.pop);		// m, row-1, col
				
				Code.put(Code.newarray);	//m, row-1, col, newarray
				
				if(assignment.getDesignator().obj.getType().getElemType().getElemType() == MyTab.intType) {
					Code.put(1);	// m, row-1, col, newarray, 1
					Code.put(Code.astore);	// m, row-1, newArrayAddress, astore   => m[row-1] = newArrayAddress
				}
				else {
					Code.put(0);
					Code.put(Code.bastore);
				}
				
				// m[index] = adr ; index = row - 1
				
				// stek -> row, col  -> 2, 3
				
				// OBRADA POSLE JEDNE ZAVRSENE ITERACIJE
				
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				
				Code.put(Code.const_1);
				Code.put(Code.sub);
				
				Code.put(Code.dup_x1);
				
				Code.loadConst(0);
				
				// provera uslova da li treba da se vrati nazad
				
				Code.putFalseJump(Code.le, mestoPovratka);	//le ide jer se radi inverz od tog niza ???? pogledati metodu u klasi Code
				
				// za slucaj da ne treba da se skace nazad, treba isparzaniti sa steka vrednosti row i col
				Code.put(Code.pop);
				Code.put(Code.pop);
				
				report_info("usao u else if za matrix", null);	
			
			}
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
		
		array.obj = array.getDesignator().obj;
		
	}
	
	public void visit(DesignatorMatrix m) {
		// Designator ::= Designator:designatorName LBRACKET Expr:rows RBRACKET LBRACKET Expr:columns RBRACKET
		
		// m[1][2]
		
		// stek -> m, row, col (col je na vrhu)
		
		SyntaxNode parent = m.getParent();
		
		if(parent.getClass().equals(DesignatorStatementAssign.class)) {
			// m[1][2] = 5;
			
			Code.put(Code.dup_x2);
			Code.put(Code.pop);
			
			if(m.getDesignator().obj.getType().getElemType().getElemType().getKind() == Struct.Int) {
				Code.put(Code.aload);
			}
			else {
				Code.put(Code.baload);
			}
			
			// col, m[row]
			
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
			
			// m[r], col  na steku;   jos u DesignatorStatementAssign treba da se doda value i da se uradi astore ili bastore
			
			
			m.obj = m.getDesignator().obj;	//NOVODODATA LINIJA RESILA JE PROBLEM KOD DesignatorStatementAssign ZA PROVERU TIPA PODATAKA DESIGNATORA
			
			report_info("usao u DesignatorMatrix", null);
		}
		else if(parent.getClass().equals(DesignatorFactorDesignator.class)) {
			// ovde se dolazi za slucaj print(m[1][2])
			
			// na steku je m, row, col 
			
			Code.put(Code.dup_x2);
			Code.put(Code.pop);	//col, m, row
			
			if(m.getDesignator().obj.getType().getElemType().getElemType().getKind() == Struct.Int) {
				Code.put(Code.aload);
			}
			else {
				Code.put(Code.baload);
			}
			
			Code.put(Code.dup_x1);
			Code.put(Code.pop);	// m[row], col
			
			if(m.getDesignator().obj.getType().getElemType().getElemType().getKind() == Struct.Int) {
				Code.put(Code.aload);
			}
			else {
				Code.put(Code.baload);
			}
			
			// m[row][col]
			
			report_info("u DesignatorMatrix klasi je u if-u za DesignatorFactorDesignator", null);
		}
		else {
			m.obj = m.getDesignator().obj;
			report_info("u klasi DesignatorMatrix parent nije ni DesignatorStatementAssign ni DesignatorFactorDesignator", null);
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















































