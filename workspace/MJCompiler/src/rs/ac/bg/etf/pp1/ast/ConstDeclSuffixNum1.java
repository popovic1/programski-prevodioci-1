// generated with ast extension for cup
// version 0.8
// 15/8/2024 2:29:27


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSuffixNum1 extends ConstDeclSuffix {

    private ConstDeclSuffix ConstDeclSuffix;
    private String constName;
    private Integer literal;

    public ConstDeclSuffixNum1 (ConstDeclSuffix ConstDeclSuffix, String constName, Integer literal) {
        this.ConstDeclSuffix=ConstDeclSuffix;
        if(ConstDeclSuffix!=null) ConstDeclSuffix.setParent(this);
        this.constName=constName;
        this.literal=literal;
    }

    public ConstDeclSuffix getConstDeclSuffix() {
        return ConstDeclSuffix;
    }

    public void setConstDeclSuffix(ConstDeclSuffix ConstDeclSuffix) {
        this.ConstDeclSuffix=ConstDeclSuffix;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Integer getLiteral() {
        return literal;
    }

    public void setLiteral(Integer literal) {
        this.literal=literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclSuffix!=null) ConstDeclSuffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclSuffixNum1(\n");

        if(ConstDeclSuffix!=null)
            buffer.append(ConstDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+literal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSuffixNum1]");
        return buffer.toString();
    }
}
