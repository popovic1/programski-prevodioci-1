// generated with ast extension for cup
// version 0.8
// 11/8/2024 20:1:4


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl1 extends ConstDecl {

    private Type Type;
    private String I2;
    private Const Const;
    private ConstDeclSuffix ConstDeclSuffix;

    public ConstDecl1 (Type Type, String I2, Const Const, ConstDeclSuffix ConstDeclSuffix) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.Const=Const;
        if(Const!=null) Const.setParent(this);
        this.ConstDeclSuffix=ConstDeclSuffix;
        if(ConstDeclSuffix!=null) ConstDeclSuffix.setParent(this);
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

    public Const getConst() {
        return Const;
    }

    public void setConst(Const Const) {
        this.Const=Const;
    }

    public ConstDeclSuffix getConstDeclSuffix() {
        return ConstDeclSuffix;
    }

    public void setConstDeclSuffix(ConstDeclSuffix ConstDeclSuffix) {
        this.ConstDeclSuffix=ConstDeclSuffix;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(Const!=null) Const.accept(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(Const!=null) Const.traverseTopDown(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(Const!=null) Const.traverseBottomUp(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl1(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(Const!=null)
            buffer.append(Const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclSuffix!=null)
            buffer.append(ConstDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl1]");
        return buffer.toString();
    }
}
