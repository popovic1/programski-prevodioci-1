// generated with ast extension for cup
// version 0.8
// 15/8/2024 2:29:27


package rs.ac.bg.etf.pp1.ast;

public class VarDeclSuffix1 extends VarDeclSuffix {

    private VarDeclSuffix VarDeclSuffix;
    private Type Type;
    private String I3;
    private BracketsOpt BracketsOpt;

    public VarDeclSuffix1 (VarDeclSuffix VarDeclSuffix, Type Type, String I3, BracketsOpt BracketsOpt) {
        this.VarDeclSuffix=VarDeclSuffix;
        if(VarDeclSuffix!=null) VarDeclSuffix.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I3=I3;
        this.BracketsOpt=BracketsOpt;
        if(BracketsOpt!=null) BracketsOpt.setParent(this);
    }

    public VarDeclSuffix getVarDeclSuffix() {
        return VarDeclSuffix;
    }

    public void setVarDeclSuffix(VarDeclSuffix VarDeclSuffix) {
        this.VarDeclSuffix=VarDeclSuffix;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI3() {
        return I3;
    }

    public void setI3(String I3) {
        this.I3=I3;
    }

    public BracketsOpt getBracketsOpt() {
        return BracketsOpt;
    }

    public void setBracketsOpt(BracketsOpt BracketsOpt) {
        this.BracketsOpt=BracketsOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclSuffix!=null) VarDeclSuffix.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(BracketsOpt!=null) BracketsOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclSuffix!=null) VarDeclSuffix.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclSuffix!=null) VarDeclSuffix.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclSuffix1(\n");

        if(VarDeclSuffix!=null)
            buffer.append(VarDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I3);
        buffer.append("\n");

        if(BracketsOpt!=null)
            buffer.append(BracketsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclSuffix1]");
        return buffer.toString();
    }
}
