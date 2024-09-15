// generated with ast extension for cup
// version 0.8
// 15/8/2024 16:27:21


package rs.ac.bg.etf.pp1.ast;

public class ActPars1 extends ActPars {

    private Expr Expr;
    private CommaExprList CommaExprList;

    public ActPars1 (Expr Expr, CommaExprList CommaExprList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CommaExprList=CommaExprList;
        if(CommaExprList!=null) CommaExprList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CommaExprList getCommaExprList() {
        return CommaExprList;
    }

    public void setCommaExprList(CommaExprList CommaExprList) {
        this.CommaExprList=CommaExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(CommaExprList!=null) CommaExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CommaExprList!=null) CommaExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CommaExprList!=null) CommaExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActPars1(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CommaExprList!=null)
            buffer.append(CommaExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActPars1]");
        return buffer.toString();
    }
}
