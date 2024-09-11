// generated with ast extension for cup
// version 0.8
// 11/8/2024 20:1:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorSuffix2 extends DesignatorSuffix {

    private DesignatorSuffix DesignatorSuffix;
    private Expr Expr;

    public DesignatorSuffix2 (DesignatorSuffix DesignatorSuffix, Expr Expr) {
        this.DesignatorSuffix=DesignatorSuffix;
        if(DesignatorSuffix!=null) DesignatorSuffix.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorSuffix getDesignatorSuffix() {
        return DesignatorSuffix;
    }

    public void setDesignatorSuffix(DesignatorSuffix DesignatorSuffix) {
        this.DesignatorSuffix=DesignatorSuffix;
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
        if(DesignatorSuffix!=null) DesignatorSuffix.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorSuffix!=null) DesignatorSuffix.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorSuffix!=null) DesignatorSuffix.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorSuffix2(\n");

        if(DesignatorSuffix!=null)
            buffer.append(DesignatorSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorSuffix2]");
        return buffer.toString();
    }
}
