// generated with ast extension for cup
// version 0.8
// 13/7/2024 15:12:37


package rs.ac.bg.etf.pp1.ast;

public class FactorDerived6 extends Factor {

    private ExprFactor ExprFactor;

    public FactorDerived6 (ExprFactor ExprFactor) {
        this.ExprFactor=ExprFactor;
        if(ExprFactor!=null) ExprFactor.setParent(this);
    }

    public ExprFactor getExprFactor() {
        return ExprFactor;
    }

    public void setExprFactor(ExprFactor ExprFactor) {
        this.ExprFactor=ExprFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprFactor!=null) ExprFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprFactor!=null) ExprFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprFactor!=null) ExprFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDerived6(\n");

        if(ExprFactor!=null)
            buffer.append(ExprFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDerived6]");
        return buffer.toString();
    }
}
