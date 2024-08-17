// generated with ast extension for cup
// version 0.8
// 17/7/2024 13:56:35


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDeclaration extends VarDeclarations {

    private FinalVarDeclaration FinalVarDeclaration;

    public SingleVarDeclaration (FinalVarDeclaration FinalVarDeclaration) {
        this.FinalVarDeclaration=FinalVarDeclaration;
        if(FinalVarDeclaration!=null) FinalVarDeclaration.setParent(this);
    }

    public FinalVarDeclaration getFinalVarDeclaration() {
        return FinalVarDeclaration;
    }

    public void setFinalVarDeclaration(FinalVarDeclaration FinalVarDeclaration) {
        this.FinalVarDeclaration=FinalVarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FinalVarDeclaration!=null) FinalVarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FinalVarDeclaration!=null) FinalVarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FinalVarDeclaration!=null) FinalVarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDeclaration(\n");

        if(FinalVarDeclaration!=null)
            buffer.append(FinalVarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDeclaration]");
        return buffer.toString();
    }
}
