// generated with ast extension for cup
// version 0.8
// 17/7/2024 13:56:35


package rs.ac.bg.etf.pp1.ast;

public class VarDeclArrayFinal extends FinalVarDeclaration {

    private String arrayName;

    public VarDeclArrayFinal (String arrayName) {
        this.arrayName=arrayName;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName=arrayName;
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
        buffer.append("VarDeclArrayFinal(\n");

        buffer.append(" "+tab+arrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclArrayFinal]");
        return buffer.toString();
    }
}
