// generated with ast extension for cup
// version 0.8
// 15/8/2024 16:27:21


package rs.ac.bg.etf.pp1.ast;

public class LessThanop extends Relop {

    public LessThanop () {
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
        buffer.append("LessThanop(\n");

        buffer.append(tab);
        buffer.append(") [LessThanop]");
        return buffer.toString();
    }
}
