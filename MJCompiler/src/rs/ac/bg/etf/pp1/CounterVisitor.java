package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrayFormalParam;
import rs.ac.bg.etf.pp1.ast.VarDeclArrayFinal;
import rs.ac.bg.etf.pp1.ast.VarDeclFinalElem;
import rs.ac.bg.etf.pp1.ast.VarDeclNextArray;
import rs.ac.bg.etf.pp1.ast.VarDeclNextElem;
import rs.ac.bg.etf.pp1.ast.VariableFormalParam;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
		
		public void visit(VariableFormalParam param) {
			count++;
		}
		
		public void visit(ArrayFormalParam param) {
			count++;
		}
		
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(VarDeclFinalElem var) {
			count++;
		}
		
		public void visit(VarDeclArrayFinal var) {
			count++;
		}
		
		public void visit(VarDeclNextElem var) {
			count++;
		}
		
		public void visit(VarDeclNextArray var) {
			count++;
		}
		
	}
	
}
