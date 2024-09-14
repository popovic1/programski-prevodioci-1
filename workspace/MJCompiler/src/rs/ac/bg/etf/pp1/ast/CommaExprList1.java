// generated with ast extension for cup
// version 0.8
// 14/8/2024 3:4:20


package rs.ac.bg.etf.pp1.ast;

public class CommaExprList1 extends CommaExprList {

    private CommaExprList CommaExprList;
    private Expr Expr;

    public CommaExprList1 (CommaExprList CommaExprList, Expr Expr) {
        this.CommaExprList=CommaExprList;
        if(CommaExprList!=null) CommaExprList.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public CommaExprList getCommaExprList() {
        return CommaExprList;
    }

    public void setCommaExprList(CommaExprList CommaExprList) {
        this.CommaExprList=CommaExprList;
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
        if(CommaExprList!=null) CommaExprList.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CommaExprList!=null) CommaExprList.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CommaExprList!=null) CommaExprList.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CommaExprList1(\n");

        if(CommaExprList!=null)
            buffer.append(CommaExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CommaExprList1]");
        return buffer.toString();
    }
}
