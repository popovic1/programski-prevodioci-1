// generated with ast extension for cup
// version 0.8
// 15/8/2024 2:29:27


package rs.ac.bg.etf.pp1.ast;

public class AndCondFactList1 extends AndCondFactList {

    private AndCondFactList AndCondFactList;
    private CondFact CondFact;

    public AndCondFactList1 (AndCondFactList AndCondFactList, CondFact CondFact) {
        this.AndCondFactList=AndCondFactList;
        if(AndCondFactList!=null) AndCondFactList.setParent(this);
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
    }

    public AndCondFactList getAndCondFactList() {
        return AndCondFactList;
    }

    public void setAndCondFactList(AndCondFactList AndCondFactList) {
        this.AndCondFactList=AndCondFactList;
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AndCondFactList!=null) AndCondFactList.accept(visitor);
        if(CondFact!=null) CondFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AndCondFactList!=null) AndCondFactList.traverseTopDown(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AndCondFactList!=null) AndCondFactList.traverseBottomUp(visitor);
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AndCondFactList1(\n");

        if(AndCondFactList!=null)
            buffer.append(AndCondFactList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AndCondFactList1]");
        return buffer.toString();
    }
}
