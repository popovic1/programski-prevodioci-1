// generated with ast extension for cup
// version 0.8
// 15/8/2024 17:50:36


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSuffixBool2 extends ConstDeclSuffix {

    private String constName;
    private Boolean bo;

    public ConstDeclSuffixBool2 (String constName, Boolean bo) {
        this.constName=constName;
        this.bo=bo;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Boolean getBo() {
        return bo;
    }

    public void setBo(Boolean bo) {
        this.bo=bo;
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
        buffer.append("ConstDeclSuffixBool2(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+bo);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSuffixBool2]");
        return buffer.toString();
    }
}
