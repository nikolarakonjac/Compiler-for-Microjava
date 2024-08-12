// generated with ast extension for cup
// version 0.8
// 12/7/2024 21:0:49


package rs.ac.bg.etf.pp1.ast;

public class FormParsExist extends FormPars {

    private FormalParams FormalParams;

    public FormParsExist (FormalParams FormalParams) {
        this.FormalParams=FormalParams;
        if(FormalParams!=null) FormalParams.setParent(this);
    }

    public FormalParams getFormalParams() {
        return FormalParams;
    }

    public void setFormalParams(FormalParams FormalParams) {
        this.FormalParams=FormalParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParams!=null) FormalParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParams!=null) FormalParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParams!=null) FormalParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsExist(\n");

        if(FormalParams!=null)
            buffer.append(FormalParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsExist]");
        return buffer.toString();
    }
}