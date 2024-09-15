// generated with ast extension for cup
// version 0.8
// 15/8/2024 10:23:32


package rs.ac.bg.etf.pp1.ast;

public class OrCondTermList1 extends OrCondTermList {

    private OrCondTermList OrCondTermList;
    private CondTerm CondTerm;

    public OrCondTermList1 (OrCondTermList OrCondTermList, CondTerm CondTerm) {
        this.OrCondTermList=OrCondTermList;
        if(OrCondTermList!=null) OrCondTermList.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public OrCondTermList getOrCondTermList() {
        return OrCondTermList;
    }

    public void setOrCondTermList(OrCondTermList OrCondTermList) {
        this.OrCondTermList=OrCondTermList;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OrCondTermList!=null) OrCondTermList.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OrCondTermList!=null) OrCondTermList.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OrCondTermList!=null) OrCondTermList.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OrCondTermList1(\n");

        if(OrCondTermList!=null)
            buffer.append(OrCondTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OrCondTermList1]");
        return buffer.toString();
    }
}
