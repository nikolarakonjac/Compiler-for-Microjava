// generated with ast extension for cup
// version 0.8
// 21/7/2024 17:8:28


package rs.ac.bg.etf.pp1.ast;

public class GreaterEqualOperator extends Relop {

    public GreaterEqualOperator () {
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
        buffer.append("GreaterEqualOperator(\n");

        buffer.append(tab);
        buffer.append(") [GreaterEqualOperator]");
        return buffer.toString();
    }
}
