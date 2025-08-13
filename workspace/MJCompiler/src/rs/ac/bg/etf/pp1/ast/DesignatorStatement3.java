// generated with ast extension for cup
// version 0.8
// 13/7/2025 12:38:35


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement3 extends DesignatorStatement {

    private Designator Designator;
    private Assignop Assignop;
    private Designator Designator1;
    private Setop Setop;
    private Designator Designator2;

    public DesignatorStatement3 (Designator Designator, Assignop Assignop, Designator Designator1, Setop Setop, Designator Designator2) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.Setop=Setop;
        if(Setop!=null) Setop.setParent(this);
        this.Designator2=Designator2;
        if(Designator2!=null) Designator2.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public Setop getSetop() {
        return Setop;
    }

    public void setSetop(Setop Setop) {
        this.Setop=Setop;
    }

    public Designator getDesignator2() {
        return Designator2;
    }

    public void setDesignator2(Designator Designator2) {
        this.Designator2=Designator2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(Setop!=null) Setop.accept(visitor);
        if(Designator2!=null) Designator2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(Setop!=null) Setop.traverseTopDown(visitor);
        if(Designator2!=null) Designator2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(Setop!=null) Setop.traverseBottomUp(visitor);
        if(Designator2!=null) Designator2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement3(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Setop!=null)
            buffer.append(Setop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator2!=null)
            buffer.append(Designator2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement3]");
        return buffer.toString();
    }
}
