package rs.ac.bg.etf.pp1;

import java.io.ObjectStreamClass;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	
	
	Struct currentDeclType = MyTab.noType;
	Obj currentMethod = null;	//info o objektom cvoru metode cija se definicija trenutno obradjuje
	boolean errorDetected = false;
	String currentConstName = "";
	boolean currentConstNameAlreadyDeclered = false;
	boolean returnFound = false;
	boolean mainFuncExist = false;
	boolean mainFuncHasParam = false;
	int nVars;
	
	Logger log = Logger.getLogger(getClass());
	
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}

	public boolean passed(){
		if(!mainFuncExist) {
			report_error("Greska: u programu ne postoji void main() fja ", null);
		}
		if(mainFuncHasParam) {
			report_error("Greska: main fja ne sme da ima formalne parametre", null);
		}
    	return !errorDetected && mainFuncExist && !mainFuncHasParam;
    }
	
	//	------------------------------- Program ----------------------------------------------------
	
	public void visit(ProgramName programName) {
		programName.obj = MyTab.insert(Obj.Prog, programName.getProgramNameAtr(), MyTab.noType);
		MyTab.openScope();
	}
	
	public void visit(Program program) {
		nVars = MyTab.currentScope.getnVars();
		// kad se zavrsi program treba da se ulancaju svi objketi koji su bili deklarisani u objektni cvor programName.obj
		MyTab.chainLocalSymbols(program.getProgramName().obj);
		MyTab.closeScope();
	}
	
	
	
//	------------------------------- TypeName ----------------------------------------------------
	
	public void visit(TypeName typeName) {
		
		Obj typeNode = MyTab.find(typeName.getTypeName());	//ako se ne nadje cvor sa prosledjenim parametrom vraca se Tab.noObj
		if(typeNode == MyTab.noObj) {
			report_error("nije pronadjen tip" + typeName.getTypeName() + "u tabeli simbola", null);
			typeName.struct = MyTab.noType;
		}
		else {
			//vracen je neki objektni cvor, moramo da proverimo koji je tip tog objektnog cvora
			if(typeNode.getKind() == Obj.Type) {
				//vraceni objekat je po tipu Type
				typeName.struct = typeNode.getType();
				this.currentDeclType = typeNode.getType();	
			}
			else {
				//vraceni objekat nije Type nego je nesto drugo
				report_error("Greska: ime " + typeName.getTypeName() + " nije tip podataka", typeName);
				typeName.struct = MyTab.noType;
			}
		}
	}
	
	
	
	//	------------------------------- Var Declaration ----------------------------------------------------
	
	public void visit(VarDeclFinalElem ident){
		// IDENT SEMICOLON
		if(MyTab.currentScope.findSymbol(ident.getIdentName()) != null) {
			report_error("Promeljiva " + ident.getIdentName() + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", ident);
		}
		else {
			MyTab.insert(Obj.Var, ident.getIdentName(), this.currentDeclType);
			//report_info("Promenljiva " + ident.getIdentName() + " deklarisana ", ident);
		}
	}
	
	
	public void visit(VarDeclNextElem ident) {
		// IDENT COMMA 
		if(MyTab.currentScope.findSymbol(ident.getIdentName()) != null) {
			report_error("Promeljiva " + ident.getIdentName() + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", ident);
		}
		else {
			MyTab.insert(Obj.Var, ident.getIdentName(), this.currentDeclType);
			//report_info("Promenljiva " + ident.getIdentName() + " deklarisana ", ident);
		}
	}
	
	
	//	------------------------------- Array Declaration ----------------------------------------------------
	
	
	public void visit(VarDeclArrayFinal arr) {
		// IDENT:arrayName LBRACKET RBRACKET SEMICOLON
		String arrayName = arr.getArrayName();
		if(MyTab.find(arrayName) != MyTab.noObj) {
			report_error("Promeljiva " + arrayName + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", arr);
		}
		else {
			MyTab.insert(Obj.Var, arrayName, new Struct(Struct.Array, this.currentDeclType));
			//report_info("Promenljiva " + arrayName + " deklarisana ", arr);
		}
	}
	
	public void visit(VarDeclNextArray arr) {
		// IDENT:arrayName LBRACKET RBRACKET COMMA
		String arrayName = arr.getArrayName();
		if(MyTab.find(arrayName) != MyTab.noObj) {
			report_error("Promeljiva " + arrayName + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", arr);
		}
		else {
			MyTab.insert(Obj.Var, arrayName, new Struct(Struct.Array, this.currentDeclType));
			//report_info("Promenljiva " + arrayName + " deklarisana ", arr);
		}
	}
	
	//	------------------------------- Matrix Declaration ----------------------------------------------------
	
	public void visit(VarDeclMatrixFinal m) {
		// FinalVarDeclaration ::= IDENT:matrixName LBRACKET RBRACKET LBRACKET RBRACKET SEMICOLON
		
		String matrixName = m.getMatrixName();
		
		if(MyTab.find(matrixName) != MyTab.noObj) {
			report_error("Promeljiva " + matrixName + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", m);
		}
		else {
			MyTab.insert(Obj.Var, matrixName, new Struct(Struct.Array, new Struct(Struct.Array, this.currentDeclType)));
		}
		
	}
	
	public void visit(VarDeclNextMatrix m) {
		// VarDeclaration ::= IDENT:matrixName LBRACKET RBRACKET LBRACKET RBRACKET COMMA
		
		String matrixName = m.getMatrixName();
		
		if(MyTab.find(matrixName) != MyTab.noObj) {
			report_error("Promeljiva " + matrixName + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", m);
		}
		else {
			MyTab.insert(Obj.Var, matrixName, new Struct(Struct.Array, new Struct(Struct.Array, this.currentDeclType)));
		}
		
	}
	
	//	------------------------------- Method Declaration ---------------------------------------------------
	
	public void visit(MethodReturnTypeVoid voidFuncAndName) {
		// VOID IDENT	
		String methodName = voidFuncAndName.getMethodName();
		if(MyTab.currentScope.findSymbol(methodName) != null ) {
			report_error("Funkcija sa nazivom "  + methodName + " vec je deklarisana", voidFuncAndName);
		}
		else {
			if(methodName.equals("main")) {
				this.mainFuncExist = true;
			}
			this.currentMethod = MyTab.insert(Obj.Meth, voidFuncAndName.getMethodName(), MyTab.noType);
			voidFuncAndName.obj = this.currentMethod;
			MyTab.openScope();
		}
	}
	
	
	public void visit(MethodReturnTypeNoVoid retTypeAndName) {
//		Type:retType IDENT
		String methodName = retTypeAndName.getMethodName();
		if(MyTab.currentScope.findSymbol(methodName) != null ) {
			report_error("Funkcija sa nazivom "  + methodName + " vec je deklarisana", retTypeAndName);
		}
		else{
			this.currentMethod = MyTab.insert(Obj.Meth, retTypeAndName.getMethodName(), retTypeAndName.getType().struct);
			retTypeAndName.obj = this.currentMethod;
			MyTab.openScope();
		}
	}
	
	public void visit(MethodDecl methDecl) {
		// u slucaju da je fja vec deklarisana currentMethod ce ostati null pa moze da pravi problem ako se ne proveri
		if(this.currentMethod != null) {
			
			if(this.returnFound == false && this.currentMethod.getType() != MyTab.noType) {
				report_error("Funkcija " + currentMethod.getName() + " nema return naredbu", methDecl);
			}
			
			MyTab.chainLocalSymbols(this.currentMethod);
			MyTab.closeScope();
			this.returnFound = false;
			this.currentMethod = null;
		}
	}
	
	
	//	------------------------------- Const ----------------------------------------------------
	
	public void visit(ConstTypeAndNameDecl typeAndName) {
		
		String constName = typeAndName.getConstName();
		// tip konstante je sacuvan u promenljivu currentDeclType u fji TypeName
		
		if(MyTab.find(constName) != MyTab.noObj) {
			report_info("Konstanta " + constName + " je vec deklarisana, ponovna deklaracija ", typeAndName);
			this.currentConstNameAlreadyDeclered = true;
		}
		else {
			this.currentConstName = constName;
			this.currentConstNameAlreadyDeclered = false;
		}
	}
	
	public void visit(ConstName constName) {
		String name = constName.getConstName();
		
		if(MyTab.find(name) != MyTab.noObj) {
		report_info("Konstanta " + name + " je vec deklarisana, ponovna deklaracija ", constName);
		this.currentConstNameAlreadyDeclered = true;
		}
		else {
			this.currentConstName = name;
			this.currentConstNameAlreadyDeclered = false;
		}
	}
	
	public void visit(NumberConstValue numConst) {
		
		if(this.currentConstNameAlreadyDeclered) {
			return ;
		}
		
		// PROVERITI -> da li je vrednost kompatibilna sa tipom konstante
		
		if(!MyTab.intType.assignableTo(this.currentDeclType)) {
			//tipovi ne odgovaraju
			report_error("Vrednost ne moze da se dodeli konstanti jer nisu istog tipa", numConst);
			return ;
		}
		
		Obj newConst = MyTab.insert(Obj.Con, this.currentConstName, this.currentDeclType);
		
		// TREBA POSTAVITI VREDNOST KONSTANTE
		newConst.setAdr(numConst.getNumConst());
		
		
		//report_info("Konstanta " + this.currentConstName + " je dodata u tabelu simbola", numConst);
	}
	
	public void visit(CharConstValue charConst) {
		if(this.currentConstNameAlreadyDeclered) {
			return ;
		}
		
		if(!MyTab.charType.assignableTo(this.currentDeclType)) {
			//tipovi ne odgovaraju
			report_error("Vrednost ne moze da se dodeli konstanti jer nisu istog tipa", charConst);
			return ;
		}
		
		Obj newConst = MyTab.insert(Obj.Con, this.currentConstName, this.currentDeclType);
		newConst.setAdr(charConst.getCharConst());
		//report_info("Konstanta " + this.currentConstName + " je dodata u tabelu simbola", charConst);
	}
	
	public void visit(BoolConstValue boolConst) {
		
		if(this.currentConstNameAlreadyDeclered) {
			return ;
		}
		
		// PROVERITI -> da li je vrednost kompatibilna sa tipom konstante
		if(!MyTab.boolType.assignableTo(this.currentDeclType)) {
			//tipovi ne odgovaraju
			report_error("Vrednost ne moze da se dodeli konstanti jer nisu istog tipa", boolConst);
			return ;
		}
		
		Obj newConst = MyTab.insert(Obj.Con, this.currentConstName, this.currentDeclType);
		
		// TREBA POSTAVITI VREDNOST KONSTANTE
		if(boolConst.getBoolConst()) {
			newConst.setAdr(1);
		}
		else {
			newConst.setAdr(0);
		}
		
		
		report_info("Konstanta " + this.currentConstName + " je dodata u tabelu simbola", boolConst);
	}
	
	
	//	------------------------------- Term ----------------------------------------------------
	
	public void visit(TermFactor termFactor) {
		// Term ::= Factor:f
		termFactor.struct = termFactor.getFactor().struct;
	}
	
	public void visit(TermMul termMul) {
		// Term ::= Term Mulop Factor
		if(termMul.getTerm().struct != MyTab.intType || termMul.getFactor().struct != MyTab.intType) {
			report_error("Greska: vrednosti koje se mnoze moraju biti tipa int", termMul);
			termMul.struct = MyTab.noType;
		}
		termMul.struct = MyTab.intType;
	}
	
	//	------------------------------- Expr ----------------------------------------------------
	
	public void visit(ExprTerm exprTerm) {
		// Expr ::= Term:t
		exprTerm.struct = exprTerm.getTerm().struct;
	}
	
	public void visit(ExprAddTerm exprAddTerm) {
		// Expr:expr Addop Term:term
		Struct exprStruct = exprAddTerm.getExpr().struct;
		Struct termStruct = exprAddTerm.getTerm().struct;
		
		if(exprStruct.equals(termStruct) && exprStruct == MyTab.intType) {
			exprAddTerm.struct = exprStruct;
		}
		else {
			report_error("Tipovi nisu kompatibilni", exprAddTerm);
			exprAddTerm.struct = MyTab.noType;
		}
	}
	
	public void visit(ExprMinusTerm minusTerm) {
		// Expr ::= MINUS Term:t
		if(minusTerm.getTerm().struct != MyTab.intType) {
			report_error("izraz iza - mora biti tipa int", minusTerm);
			minusTerm.struct = MyTab.noType;
		}
		minusTerm.struct = MyTab.intType;
	}
	
	//	------------------------------- Factor ----------------------------------------------------
	
	public void visit(FactorNumber numConst) {
		// Factor ::= NUMBER
		
		numConst.struct = MyTab.intType;
	}
	
	public void visit(FactorChar charConst) {
		// Factor ::= CHAR
		
		charConst.struct = MyTab.charType;
	}
	
	public void visit(FactorBool boolConst) {
		// Factor ::= BOOL
		
		boolConst.struct = MyTab.boolType;
	}
	
	public void visit(FactorDesignatorFactor factorDesignator) {
		// Factor ::= DesignatorFactor
		
		factorDesignator.struct = factorDesignator.getDesignatorFactor().struct;
	}
	
	public void visit(DesignatorFactorDesignator designator) {
		// DesignatorFactor ::= Designator
		
		designator.struct = designator.getDesignator().obj.getType();
	}
	
	public void visit(NewFactorArray arr) {
		// NEW Type:arrType LBRACKET Expr:arraySize RBRACKET
		
		if(arr.getExpr().struct != MyTab.intType) {
			report_error("Greska: velicina niza za alokaciju mora biti tipa int", arr);
			arr.struct = MyTab.noType;
		}
		else {
			arr.struct = new Struct(Struct.Array, arr.getType().struct);
		}
	}
	
	public void visit(NewFactorMatrix m) {
		// NewFactor ::= NEW Type LBRACKET Expr:rows RBRACKET LBRACKET Expr:columns RBRACKET
		
		if(m.getExpr().struct != MyTab.intType && m.getExpr1().struct != MyTab.intType) {
			report_error("Greska: broj redova i broj kolona za alokaciju matrice mora biti tipa int", m);
		}
		else {
			m.struct = new Struct(Struct.Array, new Struct(Struct.Array, m.getType().struct));
		}
	}
	
	public void visit(FactorNewFactor newFactor) {
		// Factor ::= NewFactor
		newFactor.struct = newFactor.getNewFactor().struct;
	}
	
	public void visit(FactorExprFactor expr) {
		// Factor ::= ExprFactor
		expr.struct = expr.getExprFactor().struct;
	}
	
	public void visit(ExprFactor e) {
		// LPAREN Expr:e RPAREN
		e.struct = e.getExpr().struct;
	}
	
	//	------------------------------- Return  ----------------------------------------------------
	
	public void visit(StatementReturnWithExpr retExpr) {
		this.returnFound = true;
		Struct currentMethodType = currentMethod.getType();
		
		if(!currentMethodType.compatibleWith(retExpr.getExpr().struct)) {
			report_error("vrednost return naredbe fje " + this.currentMethod.getName() + " nije istog tipa kao i deklarisana povratna vrednost", retExpr);
		}
	}
	
	
	//	------------------------------- DesignatorStatement  ----------------------------------------------------
	
	public void visit(DesignatorStatementAssign stm) {
		// DesignatorStatement ::= Designator:destination Assignop Expr:value 
		
		if(!(stm.getDesignator().obj.getKind() == Obj.Var || stm.getDesignator().obj.getKind() == Obj.Elem)) {
			report_error("Greska: destinacija za dodelu vrednosti mora biti promenljiva ili element niza", stm);
			return;
		}
		
		
		if(stm.getExpr().struct == MyTab.intType || stm.getExpr().struct == MyTab.charType || stm.getExpr().struct == MyTab.boolType) {
			if(!stm.getExpr().struct.assignableTo(stm.getDesignator().obj.getType())) {
				report_error("Greska pri dodeli vrednosti, tipovi nisu kompatibilni",  stm);
			}
		}
		else {
			if(stm.getDesignator().obj.getType().getElemType().getKind() == Struct.Array) {
				// designator je matrica
				
				if(stm.getExpr().struct.getElemType().getKind() == Struct.Array && 
						stm.getExpr().struct.getElemType().getElemType() == stm.getDesignator().obj.getType().getElemType().getElemType()
						) {
					// m = new int[2][3];
					//report_info("tipovi se poklapaju za matricu", stm);
				}
				else {
					report_error("Greska pri alokaciji matrice, tipovi se ne poklapaju", stm);
				}
				
			}
		}
		
		
		
	}
	
	public void visit(DesignatorStatementInc inc) {
		// DesignatorStatement ::= Designator:designator INC SEMICOLON
		
		if(!( (inc.getDesignator().obj.getKind() == Obj.Var && inc.getDesignator().obj.getType() == MyTab.intType ) || 
				( inc.getDesignator().obj.getKind() == Obj.Elem && inc.getDesignator().obj.getType() == MyTab.intType) )){
			report_error("Greska: vrednost koja se inkrementira mora biti promenljiva tipa int ili element niza koji je int", inc);
			return ;
		}
		
	}
	
	public void visit(DesignatorStatementDec dec) {
		// DesignatorStatement ::= Designator:designator DEC SEMICOLON
		
		if(!( (dec.getDesignator().obj.getKind() == Obj.Var && dec.getDesignator().obj.getType() == MyTab.intType ) || 
				( dec.getDesignator().obj.getKind() == Obj.Elem && dec.getDesignator().obj.getType() == MyTab.intType) )){
			report_error("Greska: vrednost koja se dekrementira mora biti promenljiva tipa int ili element niza koji je int", dec);
			return ;
		}
	}
	
	//	------------------------------- DesignatorStatement  ----------------------------------------------------
	
	public void visit(VariableFormalParam param) {
		if(this.currentMethod.getName().equals("main")) {
			this.mainFuncHasParam = true;
		}
	}
	
	public void visit(ArrayFormalParam arrayParam) {
		if(this.currentMethod.getName().equals("main")) {
			this.mainFuncHasParam = true;
		}
	}
	
	//	------------------------------- Designator ----------------------------------------------------
	
	public void visit(DesignatorIdent designator) {
		// IDENT:designatorName
		Obj obj = MyTab.find(designator.getDesignatorName());
		if(obj == MyTab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + " ime: " + designator.getDesignatorName() + " nije deklarisano", null);
		}
		designator.obj = obj;
	}
	
	public void visit(DesignatorArray arr) {
		// Designator:designatorName LBRACKET Expr:expr RBRACKET
		Struct arrType = arr.getDesignator().obj.getType();
		
		if(arrType.getKind() != Struct.Array) {
			report_error("Greska: promenljiva kojoj se pristupa nije tipa niz", arr);
		}
		
		Struct exprType = arr.getExpr().struct;
		
		if(exprType.getKind() != Struct.Int) {
			report_error("Greska: Expr mora biti tipa int ako se pristupa nizu", arr);
		}
		
		// element niza, pravi se novi objekat i propagira se dalje
		arr.obj = new Obj(Obj.Elem, arr.getDesignator().obj.getName(), arr.getDesignator().obj.getType().getElemType());
	}
	
	public void visit(DesignatorMatrix m) {
		// Designator ::= Designator:designatorName LBRACKET Expr:rows RBRACKET LBRACKET Expr:columns RBRACKET
		
		Struct matrix = m.getDesignator().obj.getType();
		
		if(matrix.getElemType().getKind() != Struct.Array) {
			report_error("Greska: promenljiva kojoj se pristupa nije tipa matrice", m);
			return;
		}
		
		Struct rows = m.getExpr().struct;
		Struct columns = m.getExpr1().struct;
		
		if(rows != MyTab.intType || columns != MyTab.intType) {
			report_error("Greska: za pristup elementu matrice moraju se koristiti int tipovi", m);
			return;
		}
		
		m.obj = new Obj(Obj.Elem, m.getDesignator().obj.getName(), matrix.getElemType().getElemType());
		
	}
	
	public void visit(DesignatorFactorMethod funcCall) {
		Obj func = funcCall.getDesignator().obj;
		if(func.getKind() == Obj.Meth) {
			if(func.getType() == MyTab.noType) {
				report_error("Greska: void fja ne moze da se koristi u izrazima", funcCall);
			}
			else {
				report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
				funcCall.struct = func.getType();
			}
		}
		else {
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
		}
	}
	
	//	------------------------------- Read ----------------------------------------------------
	
	public void visit(StatementRead read) {
		// Statement ::= READ LPAREN Designator RPAREN SEMICOLON
		if(!(read.getDesignator().obj.getKind() == Obj.Var || read.getDesignator().obj.getKind() == Obj.Elem )) {
			report_error("Greska: Vrednost u koju se ucitava vrednost mora biti promenljiva ili element niza", read);
			return;
		}
		
		if(!(read.getDesignator().obj.getType() == MyTab.intType || read.getDesignator().obj.getType() == MyTab.boolType 
				|| read.getDesignator().obj.getType() == MyTab.charType)) {
			report_error("Greska: Vrednost u koju se ucitava vrednost mora biti tipa int, bool ili char", read);
			return;
		}
		
	}
	
	//	------------------------------- Print ----------------------------------------------------
	
	public void visit(StatementPrintExpr printExpr) {
		// Statement ::= PRINT LPAREN Expr RPAREN SEMICOLON
		if(!(printExpr.getExpr().struct == MyTab.intType || printExpr.getExpr().struct == MyTab.charType
				|| printExpr.getExpr().struct == MyTab.boolType)) {
			report_error("Greska: izraz koji se ispisuje mora biti int, bool ili char", printExpr);
		}
	}
	
	public void visit(StatementPrintExprNumber printExprNum) {
		// Statement ::= PRINT LPAREN Expr COMMA NUMBER RPAREN SEMICOLON
		if(!(printExprNum.getExpr().struct == MyTab.intType || printExprNum.getExpr().struct == MyTab.charType 
				|| printExprNum.getExpr().struct == MyTab.boolType)) {
			report_error("Greska: izraz koji se ispisuje mora biti int, bool ili char", printExprNum);
		}
	}
	
}







































