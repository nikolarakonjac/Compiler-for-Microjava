// generated with ast extension for cup
// version 0.8
// 12/7/2024 0:25:58


package rs.ac.bg.etf.pp1.ast;

public class ConstAssignDerived3 extends ConstAssign {

    public ConstAssignDerived3 () {
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
        buffer.append("ConstAssignDerived3(\n");

        buffer.append(tab);
        buffer.append(") [ConstAssignDerived3]");
        return buffer.toString();
    }
}
