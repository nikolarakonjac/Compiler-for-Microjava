// generated with ast extension for cup
// version 0.8
// 12/7/2024 21:0:49


package rs.ac.bg.etf.pp1.ast;

public class NumberConstValue extends ConstAssign {

    public NumberConstValue () {
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
        buffer.append("NumberConstValue(\n");

        buffer.append(tab);
        buffer.append(") [NumberConstValue]");
        return buffer.toString();
    }
}