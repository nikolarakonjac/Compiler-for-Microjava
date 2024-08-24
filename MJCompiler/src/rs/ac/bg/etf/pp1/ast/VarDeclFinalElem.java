// generated with ast extension for cup
// version 0.8
// 23/7/2024 14:4:5


package rs.ac.bg.etf.pp1.ast;

public class VarDeclFinalElem extends FinalVarDeclaration {

    private String identName;

    public VarDeclFinalElem (String identName) {
        this.identName=identName;
    }

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName=identName;
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
        buffer.append("VarDeclFinalElem(\n");

        buffer.append(" "+tab+identName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclFinalElem]");
        return buffer.toString();
    }
}
