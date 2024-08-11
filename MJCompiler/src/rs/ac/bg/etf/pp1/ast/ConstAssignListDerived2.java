// generated with ast extension for cup
// version 0.8
// 12/7/2024 0:25:58


package rs.ac.bg.etf.pp1.ast;

public class ConstAssignListDerived2 extends ConstAssignList {

    public ConstAssignListDerived2 () {
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
        buffer.append("ConstAssignListDerived2(\n");

        buffer.append(tab);
        buffer.append(") [ConstAssignListDerived2]");
        return buffer.toString();
    }
}
