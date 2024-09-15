// generated with ast extension for cup
// version 0.8
// 15/8/2024 16:27:21


package rs.ac.bg.etf.pp1.ast;

public class AddopTermList1 extends AddopTermList {

    private AddopTermList AddopTermList;
    private Addop Addop;

    public AddopTermList1 (AddopTermList AddopTermList, Addop Addop) {
        this.AddopTermList=AddopTermList;
        if(AddopTermList!=null) AddopTermList.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
    }

    public AddopTermList getAddopTermList() {
        return AddopTermList;
    }

    public void setAddopTermList(AddopTermList AddopTermList) {
        this.AddopTermList=AddopTermList;
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddopTermList!=null) AddopTermList.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddopTermList!=null) AddopTermList.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddopTermList!=null) AddopTermList.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddopTermList1(\n");

        if(AddopTermList!=null)
            buffer.append(AddopTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddopTermList1]");
        return buffer.toString();
    }
}
