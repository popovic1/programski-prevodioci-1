// generated with ast extension for cup
// version 0.8
// 15/8/2024 17:50:36


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSuffixBool1 extends ConstDeclSuffix {

    private ConstDeclSuffix ConstDeclSuffix;
    private String constName;
    private Boolean bo;

    public ConstDeclSuffixBool1 (ConstDeclSuffix ConstDeclSuffix, String constName, Boolean bo) {
        this.ConstDeclSuffix=ConstDeclSuffix;
        if(ConstDeclSuffix!=null) ConstDeclSuffix.setParent(this);
        this.constName=constName;
        this.bo=bo;
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
        buffer.append("ConstDeclSuffixBool1(\n");

        if(ConstDeclSuffix!=null)
            buffer.append(ConstDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+bo);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSuffixBool1]");
        return buffer.toString();
    }
}
