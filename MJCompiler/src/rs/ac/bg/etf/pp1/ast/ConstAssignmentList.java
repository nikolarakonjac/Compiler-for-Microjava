// generated with ast extension for cup
// version 0.8
// 19/7/2024 12:38:8


package rs.ac.bg.etf.pp1.ast;

public class ConstAssignmentList extends ConstAssignList {

    private ConstAssignList ConstAssignList;
    private ConstName ConstName;
    private ConstAssign ConstAssign;

    public ConstAssignmentList (ConstAssignList ConstAssignList, ConstName ConstName, ConstAssign ConstAssign) {
        this.ConstAssignList=ConstAssignList;
        if(ConstAssignList!=null) ConstAssignList.setParent(this);
        this.ConstName=ConstName;
        if(ConstName!=null) ConstName.setParent(this);
        this.ConstAssign=ConstAssign;
        if(ConstAssign!=null) ConstAssign.setParent(this);
    }

    public ConstAssignList getConstAssignList() {
        return ConstAssignList;
    }

    public void setConstAssignList(ConstAssignList ConstAssignList) {
        this.ConstAssignList=ConstAssignList;
    }

    public ConstName getConstName() {
        return ConstName;
    }

    public void setConstName(ConstName ConstName) {
        this.ConstName=ConstName;
    }

    public ConstAssign getConstAssign() {
        return ConstAssign;
    }

    public void setConstAssign(ConstAssign ConstAssign) {
        this.ConstAssign=ConstAssign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAssignList!=null) ConstAssignList.accept(visitor);
        if(ConstName!=null) ConstName.accept(visitor);
        if(ConstAssign!=null) ConstAssign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAssignList!=null) ConstAssignList.traverseTopDown(visitor);
        if(ConstName!=null) ConstName.traverseTopDown(visitor);
        if(ConstAssign!=null) ConstAssign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAssignList!=null) ConstAssignList.traverseBottomUp(visitor);
        if(ConstName!=null) ConstName.traverseBottomUp(visitor);
        if(ConstAssign!=null) ConstAssign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstAssignmentList(\n");

        if(ConstAssignList!=null)
            buffer.append(ConstAssignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstName!=null)
            buffer.append(ConstName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssign!=null)
            buffer.append(ConstAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstAssignmentList]");
        return buffer.toString();
    }
}
