// generated with ast extension for cup
// version 0.8
// 26/7/2024 15:7:34


package rs.ac.bg.etf.pp1.ast;

public class NoActPars extends ActParsOptional {

    public NoActPars () {
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
        buffer.append("NoActPars(\n");

        buffer.append(tab);
        buffer.append(") [NoActPars]");
        return buffer.toString();
    }
}
