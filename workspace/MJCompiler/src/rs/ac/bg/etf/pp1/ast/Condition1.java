// generated with ast extension for cup
// version 0.8
// 15/8/2024 17:50:36


package rs.ac.bg.etf.pp1.ast;

public class Condition1 extends Condition {

    private CondTerm CondTerm;
    private OrCondTermList OrCondTermList;

    public Condition1 (CondTerm CondTerm, OrCondTermList OrCondTermList) {
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
        this.OrCondTermList=OrCondTermList;
        if(OrCondTermList!=null) OrCondTermList.setParent(this);
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public OrCondTermList getOrCondTermList() {
        return OrCondTermList;
    }

    public void setOrCondTermList(OrCondTermList OrCondTermList) {
        this.OrCondTermList=OrCondTermList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTerm!=null) CondTerm.accept(visitor);
        if(OrCondTermList!=null) OrCondTermList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
        if(OrCondTermList!=null) OrCondTermList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        if(OrCondTermList!=null) OrCondTermList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Condition1(\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OrCondTermList!=null)
            buffer.append(OrCondTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Condition1]");
        return buffer.toString();
    }
}
