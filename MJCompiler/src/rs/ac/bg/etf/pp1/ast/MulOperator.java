// generated with ast extension for cup
// version 0.8
// 15/7/2024 13:12:50


package rs.ac.bg.etf.pp1.ast;

public class MulOperator extends Mulop {

    public MulOperator () {
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
        buffer.append("MulOperator(\n");

        buffer.append(tab);
        buffer.append(") [MulOperator]");
        return buffer.toString();
    }
}