// generated with ast extension for cup
// version 0.8
// 15/8/2024 15:1:41


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Type Type;
    private ConstDeclSuffix ConstDeclSuffix;

    public ConstDecl (Type Type, ConstDeclSuffix ConstDeclSuffix) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstDeclSuffix=ConstDeclSuffix;
        if(ConstDeclSuffix!=null) ConstDeclSuffix.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstDeclSuffix getConstDeclSuffix() {
        return ConstDeclSuffix;
    }

    public void setConstDeclSuffix(ConstDeclSuffix ConstDeclSuffix) {
        this.ConstDeclSuffix=ConstDeclSuffix;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstDeclSuffix!=null) ConstDeclSuffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclSuffix!=null)
            buffer.append(ConstDeclSuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
