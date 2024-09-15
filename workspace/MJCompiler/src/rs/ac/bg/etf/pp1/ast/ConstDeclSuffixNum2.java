// generated with ast extension for cup
// version 0.8
// 15/8/2024 10:23:32


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSuffixNum2 extends ConstDeclSuffix {

    private String constName;
    private Integer literal;

    public ConstDeclSuffixNum2 (String constName, Integer literal) {
        this.constName=constName;
        this.literal=literal;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclSuffixNum2(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+literal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSuffixNum2]");
        return buffer.toString();
    }
}
