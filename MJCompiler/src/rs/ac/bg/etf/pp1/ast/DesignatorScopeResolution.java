// generated with ast extension for cup
// version 0.8
// 23/7/2024 14:4:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorScopeResolution extends Designator {

    private String I1;
    private String I2;

    public DesignatorScopeResolution (String I1, String I2) {
        this.I1=I1;
        this.I2=I2;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
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
        buffer.append("DesignatorScopeResolution(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorScopeResolution]");
        return buffer.toString();
    }
}
