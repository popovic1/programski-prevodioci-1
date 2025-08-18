// generated with ast extension for cup
// version 0.8
// 18/7/2025 3:17:32


package rs.ac.bg.etf.pp1.ast;

public class Designator1 extends Designator {

    private DesignName DesignName;

    public Designator1 (DesignName DesignName) {
        this.DesignName=DesignName;
        if(DesignName!=null) DesignName.setParent(this);
    }

    public DesignName getDesignName() {
        return DesignName;
    }

    public void setDesignName(DesignName DesignName) {
        this.DesignName=DesignName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignName!=null) DesignName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignName!=null) DesignName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignName!=null) DesignName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator1(\n");

        if(DesignName!=null)
            buffer.append(DesignName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator1]");
        return buffer.toString();
    }
}
