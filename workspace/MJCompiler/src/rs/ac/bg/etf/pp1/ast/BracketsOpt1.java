// generated with ast extension for cup
// version 0.8
// 11/8/2024 20:1:4


package rs.ac.bg.etf.pp1.ast;

public class BracketsOpt1 extends BracketsOpt {

    public BracketsOpt1 () {
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
        buffer.append("BracketsOpt1(\n");

        buffer.append(tab);
        buffer.append(") [BracketsOpt1]");
        return buffer.toString();
    }
}
