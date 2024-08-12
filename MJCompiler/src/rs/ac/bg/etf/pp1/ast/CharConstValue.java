// generated with ast extension for cup
// version 0.8
// 12/7/2024 21:0:49


package rs.ac.bg.etf.pp1.ast;

public class CharConstValue extends ConstAssign {

    public CharConstValue () {
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
        buffer.append("CharConstValue(\n");

        buffer.append(tab);
        buffer.append(") [CharConstValue]");
        return buffer.toString();
    }
}
