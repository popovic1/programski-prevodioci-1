// generated with ast extension for cup
// version 0.8
// 15/8/2024 15:1:41


package rs.ac.bg.etf.pp1.ast;

public class Mulop1 extends Mulop {

    public Mulop1 () {
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
        buffer.append("Mulop1(\n");

        buffer.append(tab);
        buffer.append(") [Mulop1]");
        return buffer.toString();
    }
}
