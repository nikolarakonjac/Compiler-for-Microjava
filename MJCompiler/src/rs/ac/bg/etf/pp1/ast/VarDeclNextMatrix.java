// generated with ast extension for cup
// version 0.8
// 25/7/2024 18:0:18


package rs.ac.bg.etf.pp1.ast;

public class VarDeclNextMatrix extends VarDeclaration {

    private String matrixName;

    public VarDeclNextMatrix (String matrixName) {
        this.matrixName=matrixName;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName=matrixName;
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
        buffer.append("VarDeclNextMatrix(\n");

        buffer.append(" "+tab+matrixName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclNextMatrix]");
        return buffer.toString();
    }
}
