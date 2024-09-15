// generated with ast extension for cup
// version 0.8
// 15/8/2024 16:27:21


package rs.ac.bg.etf.pp1.ast;

public class VarDeclElemsMatrix extends VarDeclElems {

    private String varName;
    private Empty Empty;

    public VarDeclElemsMatrix (String varName, Empty Empty) {
        this.varName=varName;
        this.Empty=Empty;
        if(Empty!=null) Empty.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public Empty getEmpty() {
        return Empty;
    }

    public void setEmpty(Empty Empty) {
        this.Empty=Empty;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Empty!=null) Empty.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Empty!=null) Empty.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Empty!=null) Empty.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclElemsMatrix(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(Empty!=null)
            buffer.append(Empty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclElemsMatrix]");
        return buffer.toString();
    }
}
