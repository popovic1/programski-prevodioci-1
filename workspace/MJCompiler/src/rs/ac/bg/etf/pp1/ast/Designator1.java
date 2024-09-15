// generated with ast extension for cup
// version 0.8
// 15/8/2024 15:1:41


package rs.ac.bg.etf.pp1.ast;

public class Designator1 extends Designator {

    private String desName;

    public Designator1 (String desName) {
        this.desName=desName;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName=desName;
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
        buffer.append("Designator1(\n");

        buffer.append(" "+tab+desName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator1]");
        return buffer.toString();
    }
}
