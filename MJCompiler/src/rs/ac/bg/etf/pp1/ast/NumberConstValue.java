// generated with ast extension for cup
// version 0.8
// 25/7/2024 18:0:18


package rs.ac.bg.etf.pp1.ast;

public class NumberConstValue extends ConstAssign {

    private Integer numConst;

    public NumberConstValue (Integer numConst) {
        this.numConst=numConst;
    }

    public Integer getNumConst() {
        return numConst;
    }

    public void setNumConst(Integer numConst) {
        this.numConst=numConst;
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
        buffer.append("NumberConstValue(\n");

        buffer.append(" "+tab+numConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumberConstValue]");
        return buffer.toString();
    }
}
