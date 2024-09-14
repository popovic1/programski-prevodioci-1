// generated with ast extension for cup
// version 0.8
// 14/8/2024 18:24:11


package rs.ac.bg.etf.pp1.ast;

public class VarDeclElemsMatrixMultiple extends VarDeclElems {

    private VarDeclElems VarDeclElems;
    private String varName;
    private Empty Empty;

    public VarDeclElemsMatrixMultiple (VarDeclElems VarDeclElems, String varName, Empty Empty) {
        this.VarDeclElems=VarDeclElems;
        if(VarDeclElems!=null) VarDeclElems.setParent(this);
        this.varName=varName;
        this.Empty=Empty;
        if(Empty!=null) Empty.setParent(this);
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
        if(VarDeclElems!=null) VarDeclElems.accept(visitor);
        if(Empty!=null) Empty.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclElems!=null) VarDeclElems.traverseTopDown(visitor);
        if(Empty!=null) Empty.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclElems!=null) VarDeclElems.traverseBottomUp(visitor);
        if(Empty!=null) Empty.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclElemsMatrixMultiple(\n");

        if(VarDeclElems!=null)
            buffer.append(VarDeclElems.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(Empty!=null)
            buffer.append(Empty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclElemsMatrixMultiple]");
        return buffer.toString();
    }
}
