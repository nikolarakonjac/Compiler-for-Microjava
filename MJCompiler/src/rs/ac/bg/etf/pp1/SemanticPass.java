package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	
	
	Struct currentDeclType = MyTab.noType;
	Obj currentMethod = null;	//info o objektom cvoru metode cija se definicija trenutno obradjuje
	boolean errorDetected = false;
	
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

	
	public void visit(ProgramName programName) {
		programName.obj = MyTab.insert(Obj.Prog, programName.getProgramNameAtr(), MyTab.noType);
		MyTab.openScope();
	}
	
	public void visit(Program program) {
		// kad se zavrsi program treba da se ulancaju svi objketi koji su bili deklarisani u objektni cvor programName.obj
		MyTab.chainLocalSymbols(program.getProgramName().obj);
		MyTab.closeScope();
	}
	
	
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
				report_error("Greska: ime" + typeName.getTypeName() + " nije tip podataka", typeName);
				typeName.struct = MyTab.noType;
			}
		}
	}
	
	
	
//	------------------------------- Var Declaration ----------------------------------------------------
	
	public void visit(VarDeclFinalElem ident){
		// IDENT SEMICOLON
		if(MyTab.currentScope.findSymbol(ident.getIdentName()) != null) {
			report_error("Promeljiva " + ident.getIdentName() + " je vec deklarisana u ovom scope-u", ident);
		}
		else {
			MyTab.insert(Obj.Var, ident.getIdentName(), this.currentDeclType);
			report_info("Promenljiva " + ident.getIdentName() + " deklarisana", ident);
		}
	}
	
	
	public void visit(VarDeclNextElem ident) {
		// IDENT COMMA 
		if(MyTab.currentScope.findSymbol(ident.getIdentName()) != null) {
			report_error("Promeljiva " + ident.getIdentName() + " je vec deklarisana u ovom scope-u", ident);
			return ;
		}
		
		MyTab.insert(Obj.Var, ident.getIdentName(), this.currentDeclType);
		report_info("Promenljiva " + ident.getIdentName() + " deklarisana", ident);
		
	}
	
	
	//	------------------------------- Method Declaration ----------------------------------------------------
	
	public void visit(MethodReturnTypeVoid voidFuncAndName) {
		
		
//		if(MyTab.currentScope.findSymbol(voidFunc.getMethodName()) != MyTab.noObj) {
		if(MyTab.currentScope.findSymbol(voidFuncAndName.getMethodName()) !=null ) {
			report_error("Funkcija sa nazivom "  + voidFuncAndName.getMethodName() + " vec je deklarisana", voidFuncAndName);
			return ;
		}
		
		this.currentMethod = MyTab.insert(Obj.Meth, voidFuncAndName.getMethodName(), MyTab.noType);
		voidFuncAndName.obj = this.currentMethod;
		MyTab.openScope();
		
	}
	
	
	public void visit(MethodReturnTypeNoVoid retTypeAndName) {
		
//		if(MyTab.currentScope.findSymbol(voidFunc.getMethodName()) != MyTab.noObj) {
		if(MyTab.currentScope.findSymbol(retTypeAndName.getMethodName()) !=null ) {
			report_error("Funkcija sa nazivom "  + retTypeAndName.getMethodName() + " vec je deklarisana", retTypeAndName);
			return ;
		}
		
		this.currentMethod = MyTab.insert(Obj.Meth, retTypeAndName.getMethodName(), retTypeAndName.getType().struct);
		retTypeAndName.obj = this.currentMethod;
		MyTab.openScope();
	}
	
	public void visit(MethodDecl methDecl) {
//		report_info("usao u MethDecl ", null);
		MyTab.chainLocalSymbols(this.currentMethod);
//		report_info("dodao u locals ", null);
		MyTab.closeScope();
		
		this.currentMethod = null;
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
	
	
	public boolean passed(){
    	return !errorDetected;
    }
	
	
}





































