// generated with ast extension for cup
// version 0.8
// 15/8/2024 2:29:27


package rs.ac.bg.etf.pp1.ast;

public class LessThanOrEqualop extends Relop {

    public LessThanOrEqualop () {
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
        buffer.append("LessThanOrEqualop(\n");

        buffer.append(tab);
        buffer.append(") [LessThanOrEqualop]");
        return buffer.toString();
    }
}
