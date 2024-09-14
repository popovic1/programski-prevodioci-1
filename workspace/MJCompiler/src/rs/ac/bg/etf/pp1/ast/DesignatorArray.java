// generated with ast extension for cup
// version 0.8
// 14/8/2024 18:24:11


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private DesignName DesignName;
    private Expr Expr;

    public DesignatorArray (DesignName DesignName, Expr Expr) {
        this.DesignName=DesignName;
        if(DesignName!=null) DesignName.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignName getDesignName() {
        return DesignName;
    }

    public void setDesignName(DesignName DesignName) {
        this.DesignName=DesignName;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignName!=null) DesignName.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignName!=null) DesignName.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignName!=null) DesignName.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(DesignName!=null)
            buffer.append(DesignName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
