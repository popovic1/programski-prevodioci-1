// generated with ast extension for cup
// version 0.8
// 11/8/2024 20:1:4


package rs.ac.bg.etf.pp1.ast;

public class VarDecl1 extends VarDecl {

    private Type Type;
    private String I2;
    private BracketsOpt BracketsOpt;
    private VarDeclSuffix VarDeclSuffix;

    public VarDecl1 (Type Type, String I2, BracketsOpt BracketsOpt, VarDeclSuffix VarDeclSuffix) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.BracketsOpt=BracketsOpt;
        if(BracketsOpt!=null) BracketsOpt.setParent(this);
        this.VarDeclSuffix=VarDeclSuffix;
        if(VarDeclSuffix!=null) VarDeclSuffix.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public BracketsOpt getBracketsOpt() {
        return BracketsOpt;
    }

    public void setBracketsOpt(BracketsOpt BracketsOpt) {
        this.BracketsOpt=BracketsOpt;
    }

    public VarDeclSuffix getVarDeclSuffix() {
        return VarDeclSuffix;
    }

    public void setVarDeclSuffix(VarDeclSuffix VarDeclSuffix) {
        this.VarDeclSuffix=VarDeclSuffix;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(BracketsOpt!=null) BracketsOpt.accept(visitor);
        if(VarDeclSuffix!=null) VarDeclSuffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseTopDown(visitor);
        if(VarDeclSuffix!=null) VarDeclSuffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(BracketsOpt!=null) BracketsOpt.traverseBottomUp(visitor);
        if(VarDeclSuffix!=null) VarDeclSuffix.traverseBottomUp(visitor);
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

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(BracketsOpt!=null)
            buffer.append(BracketsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclSuffix!=null)
            buffer.append(VarDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl1]");
        return buffer.toString();
    }
}
