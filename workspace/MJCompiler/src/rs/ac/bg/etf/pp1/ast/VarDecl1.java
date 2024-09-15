// generated with ast extension for cup
// version 0.8
// 15/8/2024 17:50:36


package rs.ac.bg.etf.pp1.ast;

public class VarDecl1 extends VarDecl {

    private Type Type;
    private VarDeclElems VarDeclElems;

    public VarDecl1 (Type Type, VarDeclElems VarDeclElems) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclElems=VarDeclElems;
        if(VarDeclElems!=null) VarDeclElems.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclElems getVarDeclElems() {
        return VarDeclElems;
    }

    public void setVarDeclElems(VarDeclElems VarDeclElems) {
        this.VarDeclElems=VarDeclElems;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclElems!=null) VarDeclElems.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclElems!=null) VarDeclElems.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclElems!=null) VarDeclElems.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl1(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclElems!=null)
            buffer.append(VarDeclElems.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl1]");
        return buffer.toString();
    }
}
