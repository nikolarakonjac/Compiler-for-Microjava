// generated with ast extension for cup
// version 0.8
// 17/7/2024 13:56:35


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ConstTypeAndNameDecl ConstTypeAndNameDecl;
    private ConstAssign ConstAssign;
    private ConstAssignList ConstAssignList;

    public ConstDecl (ConstTypeAndNameDecl ConstTypeAndNameDecl, ConstAssign ConstAssign, ConstAssignList ConstAssignList) {
        this.ConstTypeAndNameDecl=ConstTypeAndNameDecl;
        if(ConstTypeAndNameDecl!=null) ConstTypeAndNameDecl.setParent(this);
        this.ConstAssign=ConstAssign;
        if(ConstAssign!=null) ConstAssign.setParent(this);
        this.ConstAssignList=ConstAssignList;
        if(ConstAssignList!=null) ConstAssignList.setParent(this);
    }

    public ConstTypeAndNameDecl getConstTypeAndNameDecl() {
        return ConstTypeAndNameDecl;
    }

    public void setConstTypeAndNameDecl(ConstTypeAndNameDecl ConstTypeAndNameDecl) {
        this.ConstTypeAndNameDecl=ConstTypeAndNameDecl;
    }

    public ConstAssign getConstAssign() {
        return ConstAssign;
    }

    public void setConstAssign(ConstAssign ConstAssign) {
        this.ConstAssign=ConstAssign;
    }

    public ConstAssignList getConstAssignList() {
        return ConstAssignList;
    }

    public void setConstAssignList(ConstAssignList ConstAssignList) {
        this.ConstAssignList=ConstAssignList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstTypeAndNameDecl!=null) ConstTypeAndNameDecl.accept(visitor);
        if(ConstAssign!=null) ConstAssign.accept(visitor);
        if(ConstAssignList!=null) ConstAssignList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstTypeAndNameDecl!=null) ConstTypeAndNameDecl.traverseTopDown(visitor);
        if(ConstAssign!=null) ConstAssign.traverseTopDown(visitor);
        if(ConstAssignList!=null) ConstAssignList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstTypeAndNameDecl!=null) ConstTypeAndNameDecl.traverseBottomUp(visitor);
        if(ConstAssign!=null) ConstAssign.traverseBottomUp(visitor);
        if(ConstAssignList!=null) ConstAssignList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(ConstTypeAndNameDecl!=null)
            buffer.append(ConstTypeAndNameDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssign!=null)
            buffer.append(ConstAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssignList!=null)
            buffer.append(ConstAssignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
