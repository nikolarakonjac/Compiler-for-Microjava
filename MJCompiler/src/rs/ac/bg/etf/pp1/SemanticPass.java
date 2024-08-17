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
    	return !errorDetected;
    }
	
	//	------------------------------- Program ----------------------------------------------------
	
	public void visit(ProgramName programName) {
		programName.obj = MyTab.insert(Obj.Prog, programName.getProgramNameAtr(), MyTab.noType);
		MyTab.openScope();
	}
	
	public void visit(Program program) {
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
			report_info("Promenljiva " + ident.getIdentName() + " deklarisana ", ident);
		}
	}
	
	
	public void visit(VarDeclNextElem ident) {
		// IDENT COMMA 
		if(MyTab.currentScope.findSymbol(ident.getIdentName()) != null) {
			report_error("Promeljiva " + ident.getIdentName() + " je vec deklarisana u ovom scope-u, ponovna deklaracija ", ident);
		}
		else {
			MyTab.insert(Obj.Var, ident.getIdentName(), this.currentDeclType);
			report_info("Promenljiva " + ident.getIdentName() + " deklarisana ", ident);
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
			report_info("Promenljiva " + arrayName + " deklarisana ", arr);
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
			report_info("Promenljiva " + arrayName + " deklarisana ", arr);
		}
	}
	
	//	------------------------------- Method Declaration ---------------------------------------------------
	
	public void visit(MethodReturnTypeVoid voidFuncAndName) {
		// VOID IDENT	
		if(MyTab.currentScope.findSymbol(voidFuncAndName.getMethodName()) != null ) {
			report_error("Funkcija sa nazivom "  + voidFuncAndName.getMethodName() + " vec je deklarisana", voidFuncAndName);
		}
		else {
			this.currentMethod = MyTab.insert(Obj.Meth, voidFuncAndName.getMethodName(), MyTab.noType);
			voidFuncAndName.obj = this.currentMethod;
			MyTab.openScope();
		}
	}
	
	
	public void visit(MethodReturnTypeNoVoid retTypeAndName) {
//		Type:retType IDENT
		if(MyTab.currentScope.findSymbol(retTypeAndName.getMethodName()) != null ) {
			report_error("Funkcija sa nazivom "  + retTypeAndName.getMethodName() + " vec je deklarisana", retTypeAndName);
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
	
	
//	------------------------------- Designator ----------------------------------------------------
	
	public void visit(DesignatorIdent designator) {
		Obj obj = MyTab.find(designator.getDesignatorName());
		if(obj == MyTab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + " ime: " + designator.getDesignatorName() + " nije deklarisano", null);
		}
		designator.obj = obj;
	}
	
	public void visit(DesignatorFactorMethod funcCall) {
		Obj func = funcCall.getDesignator().obj;
		if(func.getKind() == Obj.Meth) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
		}
		else {
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
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
		
		
		report_info("Konstanta " + this.currentConstName + " je dodata u tabelu simbola", numConst);
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
		report_info("Konstanta " + this.currentConstName + " je dodata u tabelu simbola", charConst);
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
		// Designator:destination Assignop Expr:value SEMICOLON
		if(!stm.getExpr().struct.assignableTo(stm.getDesignator().obj.getType())) {
			report_error("Greska pri dodeli vrednosti, tipovi nisu kompatibilni",  stm);
		}
	}
	
	
}







































