// generated with ast extension for cup
// version 0.8
// 13/7/2024 17:38:3


package rs.ac.bg.etf.pp1.ast;

public class EqualOperator extends Relop {

    public EqualOperator () {
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
        buffer.append("EqualOperator(\n");

        buffer.append(tab);
        buffer.append(") [EqualOperator]");
        return buffer.toString();
    }
}
