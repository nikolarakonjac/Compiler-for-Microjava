// generated with ast extension for cup
// version 0.8
// 26/7/2024 15:7:34


package rs.ac.bg.etf.pp1.ast;

public class FactorNewFactor extends Factor {

    private NewFactor NewFactor;

    public FactorNewFactor (NewFactor NewFactor) {
        this.NewFactor=NewFactor;
        if(NewFactor!=null) NewFactor.setParent(this);
    }

    public NewFactor getNewFactor() {
        return NewFactor;
    }

    public void setNewFactor(NewFactor NewFactor) {
        this.NewFactor=NewFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewFactor!=null) NewFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewFactor!=null) NewFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewFactor!=null) NewFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNewFactor(\n");

        if(NewFactor!=null)
            buffer.append(NewFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNewFactor]");
        return buffer.toString();
    }
}
