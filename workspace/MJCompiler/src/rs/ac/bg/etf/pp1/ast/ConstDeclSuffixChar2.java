// generated with ast extension for cup
// version 0.8
// 15/8/2024 16:27:20


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSuffixChar2 extends ConstDeclSuffix {

    private String constName;
    private Character ch;

    public ConstDeclSuffixChar2 (String constName, Character ch) {
        this.constName=constName;
        this.ch=ch;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Character getCh() {
        return ch;
    }

    public void setCh(Character ch) {
        this.ch=ch;
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
        buffer.append("ConstDeclSuffixChar2(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+ch);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSuffixChar2]");
        return buffer.toString();
    }
}
