// generated with ast extension for cup
// version 0.8
// 16/7/2024 0:33:54


package rs.ac.bg.etf.pp1.ast;

public class VarDeclErrorSemi extends FinalVarDeclaration {

    public VarDeclErrorSemi () {
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
        buffer.append("VarDeclErrorSemi(\n");

        buffer.append(tab);
        buffer.append(") [VarDeclErrorSemi]");
        return buffer.toString();
    }
}
