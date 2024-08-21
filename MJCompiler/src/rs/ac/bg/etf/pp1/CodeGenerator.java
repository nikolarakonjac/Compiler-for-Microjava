package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
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
		// ... , val, width
		//val treba vec da se izracuna u expr i stavi na stek, tako da mi samo stavljamo width
		if(printStm.getExpr().struct == MyTab.intType || printStm.getExpr().struct == MyTab.boolType) {
			//int ili char
			Code.loadConst(5);	//width
			Code.put(Code.print);
		}
		else {
			//char
			Code.loadConst(1); //width
			Code.put(Code.bprint);
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
	
	
	// ---------------------------------------------- Expr -------------------------------------------
	
	public void visit(ExprAddTerm add) {
		// elementi koji ucestvuju u izrazu su vec na steku
		Code.put(Code.add);
	}
	
	
	// ---------------------------------------------- DesignatorStatement -------------------------------------------
	
	public void visit(DesignatorStatementAssign assignment) {
		// DesignatorStatement ::=  Designator:destination Assignop Expr:value SEMICOLON
		
		// dohvatamo vrednost exprStacka(jer smatramo da je expr vec postavio na stek vrednost) i sacuvamo je u destination
		Code.store(assignment.getDesignator().obj);	//odgovaraju instrukcija ce se generisati na osnovu prirode cvora
	}
	
	
	// ---------------------------------------------- Designator -------------------------------------------
	
	public void visit(DesignatorIdent des) {
		// Designator ::= IDENT:designatorName
		
		SyntaxNode parent = des.getParent();	
		
		if(!parent.getClass().equals(DesignatorStatementAssign.class)) {
			Code.load(des.obj);	
		}
		
		
	
	}
	
	
	
}















































