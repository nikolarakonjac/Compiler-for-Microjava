// generated with ast extension for cup
// version 0.8
// 13/7/2024 15:12:37


package rs.ac.bg.etf.pp1.ast;

public class FinalVarDeclarationDerived2 extends FinalVarDeclaration {

    public FinalVarDeclarationDerived2 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FinalVarDeclarationDerived2(\n");

        buffer.append(tab);
        buffer.append(") [FinalVarDeclarationDerived2]");
        return buffer.toString();
    }
}
