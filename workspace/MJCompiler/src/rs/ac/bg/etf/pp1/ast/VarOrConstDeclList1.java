// generated with ast extension for cup
// version 0.8
// 15/8/2024 15:1:41


package rs.ac.bg.etf.pp1.ast;

public class VarOrConstDeclList1 extends VarOrConstDeclList {

    private VarOrConstDeclList VarOrConstDeclList;
    private VarOrConstDecl VarOrConstDecl;

    public VarOrConstDeclList1 (VarOrConstDeclList VarOrConstDeclList, VarOrConstDecl VarOrConstDecl) {
        this.VarOrConstDeclList=VarOrConstDeclList;
        if(VarOrConstDeclList!=null) VarOrConstDeclList.setParent(this);
        this.VarOrConstDecl=VarOrConstDecl;
        if(VarOrConstDecl!=null) VarOrConstDecl.setParent(this);
    }

    public VarOrConstDeclList getVarOrConstDeclList() {
        return VarOrConstDeclList;
    }

    public void setVarOrConstDeclList(VarOrConstDeclList VarOrConstDeclList) {
        this.VarOrConstDeclList=VarOrConstDeclList;
    }

    public VarOrConstDecl getVarOrConstDecl() {
        return VarOrConstDecl;
    }

    public void setVarOrConstDecl(VarOrConstDecl VarOrConstDecl) {
        this.VarOrConstDecl=VarOrConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarOrConstDeclList!=null) VarOrConstDeclList.accept(visitor);
        if(VarOrConstDecl!=null) VarOrConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarOrConstDeclList!=null) VarOrConstDeclList.traverseTopDown(visitor);
        if(VarOrConstDecl!=null) VarOrConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarOrConstDeclList!=null) VarOrConstDeclList.traverseBottomUp(visitor);
        if(VarOrConstDecl!=null) VarOrConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarOrConstDeclList1(\n");

        if(VarOrConstDeclList!=null)
            buffer.append(VarOrConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarOrConstDecl!=null)
            buffer.append(VarOrConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarOrConstDeclList1]");
        return buffer.toString();
    }
}
