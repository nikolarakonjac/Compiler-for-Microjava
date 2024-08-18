// generated with ast extension for cup
// version 0.8
// 18/7/2024 20:48:29


package rs.ac.bg.etf.pp1.ast;

public class StatementListDoesntExist extends StatementList {

    public StatementListDoesntExist () {
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
        buffer.append("StatementListDoesntExist(\n");

        buffer.append(tab);
        buffer.append(") [StatementListDoesntExist]");
        return buffer.toString();
    }
}
