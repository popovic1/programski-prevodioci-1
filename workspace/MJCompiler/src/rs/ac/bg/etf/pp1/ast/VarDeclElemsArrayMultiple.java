// generated with ast extension for cup
// version 0.8
// 15/8/2024 17:50:36


package rs.ac.bg.etf.pp1.ast;

public class VarDeclElemsArrayMultiple extends VarDeclElems {

    private VarDeclElems VarDeclElems;
    private String varName;

    public VarDeclElemsArrayMultiple (VarDeclElems VarDeclElems, String varName) {
        this.VarDeclElems=VarDeclElems;
        if(VarDeclElems!=null) VarDeclElems.setParent(this);
        this.varName=varName;
    }

    public VarDeclElems getVarDeclElems() {
        return VarDeclElems;
    }

    public void setVarDeclElems(VarDeclElems VarDeclElems) {
        this.VarDeclElems=VarDeclElems;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclElems!=null) VarDeclElems.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclElems!=null) VarDeclElems.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclElems!=null) VarDeclElems.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclElemsArrayMultiple(\n");

        if(VarDeclElems!=null)
            buffer.append(VarDeclElems.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclElemsArrayMultiple]");
        return buffer.toString();
    }
}
