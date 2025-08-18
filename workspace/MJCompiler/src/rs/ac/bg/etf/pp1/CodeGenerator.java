package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;
    private int tmpS, tmpX, tmpI;
    private Obj tS = new Obj(Obj.Var, "$s", Tab.intType);
    private Obj tX = new Obj(Obj.Var, "$x", Tab.intType);
    private Obj tI = new Obj(Obj.Var, "$i", Tab.intType);

    public int getMainPc(){
        return mainPc;
    }

    public void visit(StatementPrintExpr printStmt){
        if(printStmt.getExpr().struct != Tab.charType){
            Code.loadConst(5);
            Code.put(Code.print);
        }else{
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }

    public void visit(StatementPrintExprWithNum printStmt){
        Code.loadConst(printStmt.getNum());
        if(printStmt.getExpr().struct != Tab.charType){
            Code.put(Code.print);
        }else{
            Code.put(Code.bprint);
        }
    }

    public void visit (StatementRead rdStmt) {
        Designator d = rdStmt.getDesignator();
        if (d instanceof Designator1) {
            Obj o = ((Designator1)d).getDesignName().obj;
            if(o.getType() == Tab.charType) {
                Code.put(Code.bread);
            } else {
                Code.put(Code.read);
            }
            Code.store(o);
        } else if (d instanceof DesignatorArray) {
            Obj arr = ((DesignatorArray)d).getDesignName().obj;
            Struct elemType = arr.getType().getElemType();
            if(elemType == Tab.charType) {
                Code.put(Code.bread);
                Code.put(Code.bastore);
            } else {
                Code.put(Code.read);
                Code.put(Code.astore);
            }
        }
    }

    public void visit(FactorNum cnst){ Code.loadConst(cnst.getN1()); }
    public void visit(FactorChar cnst){ Code.loadConst(cnst.getC1()); }
    public void visit(FactorBool cnst){ Code.loadConst(cnst.getB1() ? 1 : 0); }

    public void visit(Factor1 fd) {
        Designator d = fd.getDesignator();
        if (d instanceof Designator1) {
            Obj o = ((Designator1)d).getDesignName().obj;
            Code.load(o);
        } else if (d instanceof DesignatorArray) {
            Obj arr = ((DesignatorArray)d).getDesignName().obj;
            Struct elemType = arr.getType().getElemType();
            if (elemType == Tab.charType) Code.put(Code.baload); else Code.put(Code.aload);
        }
    }

    public void visit(DesignatorStatement1 assign) {
        Designator d = assign.getDesignator();
        if (d instanceof Designator1) {
            Obj o = ((Designator1)d).getDesignName().obj;
            Code.store(o);
        } else if (d instanceof DesignatorArray) {
            Obj arr = ((DesignatorArray)d).getDesignName().obj;
            Struct elemType = arr.getType().getElemType();
            if (elemType == Tab.charType) Code.put(Code.bastore); else Code.put(Code.astore);
        }
    }

    public void visit(MethodTypeNameVoid methodTypeName){
        if("main".equalsIgnoreCase(methodTypeName.getMethName())){
            mainPc = Code.pc;
        }
        methodTypeName.obj.setAdr(Code.pc);
        SyntaxNode methodNode = methodTypeName.getParent();
        VarCounter varCnt = new VarCounter();
        methodNode.traverseTopDown(varCnt);
        int base = varCnt.getCount();
        tmpS = base; tmpX = base+1; tmpI = base+2;
        tS.setAdr(tmpS); tS.setLevel(1);
        tX.setAdr(tmpX); tX.setLevel(1);
        tI.setAdr(tmpI); tI.setLevel(1);
        Code.put(Code.enter);
        Code.put(0);
        Code.put(base + 3);
    }

    public void visit(MethodDecl methodDecl){
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(DesignatorArray da) {
        Code.load(da.getDesignName().obj);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);
    }

    public void visit(Designator1 d1) { }

    public void visit(Factor3 newArr) {
        Struct t = newArr.getType().struct;
        Code.put(Code.newarray);
        Code.put(t == Tab.charType ? 0 : 1);
    }

    public void visit(Factor6 paren) { }

    public void visit(ExprMinus em) { Code.put(Code.neg); }

    public void visit(ExprMultiple e) {
        if (e.getAddop() instanceof AddopPlus) Code.put(Code.add);
        else if (e.getAddop() instanceof Subop) Code.put(Code.sub);
    }

    public void visit(Term2 t) {
        if (t.getMulop() instanceof Mulop1) Code.put(Code.mul);
        else if (t.getMulop() instanceof Divop) Code.put(Code.div);
        else if (t.getMulop() instanceof Modop) Code.put(Code.rem);
    }

    public void visit(DesignatorStatementInc inc) {
        Designator d = inc.getDesignator();
        if (d instanceof Designator1) {
            Obj o = ((Designator1)d).getDesignName().obj;
            Code.load(o); Code.loadConst(1); Code.put(Code.add); Code.store(o);
        } else if (d instanceof DesignatorArray) {
            Obj arr = ((DesignatorArray)d).getDesignName().obj;
            Struct elem = arr.getType().getElemType();
            Code.put(Code.dup2);
            if (elem == Tab.charType) Code.put(Code.baload); else Code.put(Code.aload);
            Code.loadConst(1); Code.put(Code.add);
            if (elem == Tab.charType) Code.put(Code.bastore); else Code.put(Code.astore);
        }
    }

    public void visit(DesignatorStatementDec dec) {
        Designator d = dec.getDesignator();
        if (d instanceof Designator1) {
            Obj o = ((Designator1)d).getDesignName().obj;
            Code.load(o); Code.loadConst(1); Code.put(Code.sub); Code.store(o);
        } else if (d instanceof DesignatorArray) {
            Obj arr = ((DesignatorArray)d).getDesignName().obj;
            Struct elem = arr.getType().getElemType();
            Code.put(Code.dup2);
            if (elem == Tab.charType) Code.put(Code.baload); else Code.put(Code.aload);
            Code.loadConst(1); Code.put(Code.sub);
            if (elem == Tab.charType) Code.put(Code.bastore); else Code.put(Code.astore);
        }
    }

    public void visit(DesignatorStatement2 call) {
        Designator des = call.getDesignator();
        if (des instanceof Designator1) {
            Obj f = ((Designator1)des).getDesignName().obj;
            String name = f.getName();
            if ("add".equals(name)) {
                emitAddInlineWithResult();
                Code.put(Code.pop);
            }
        }
    }

    public void visit(Factor2 fcall) {
        Designator des = fcall.getDesignator();
        if (des instanceof Designator1) {
            Obj f = ((Designator1)des).getDesignName().obj;
            String name = f.getName();
            if ("len".equals(name)) {
                Code.put(Code.arraylength);
            } else if ("ord".equals(name) || "chr".equals(name)) {
            } else if ("add".equals(name)) {
                emitAddInlineWithResult();
            }
        }
    }

    private void emitAddInlineWithResult(){
        Code.store(tX);
        Code.store(tS);
        Code.load(tX); Code.loadConst(1); Code.put(Code.add); Code.store(tX);
        Code.loadConst(0); Code.store(tI);
        int loopStart = Code.pc;
        Code.load(tI);
        Code.load(tS); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);
        int jDoneZero1 = Code.pc - 2;
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.load(tX);
        Code.putFalseJump(Code.ne, 0);
        int jDoneZero2 = Code.pc - 2;
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.loadConst(0);
        Code.putFalseJump(Code.eq, 0);
        int jInc = Code.pc - 2;
        Code.load(tS); Code.load(tI);
        Code.load(tX);
        Code.put(Code.astore);
        Code.loadConst(1);
        Code.putJump(0);
        int jDone = Code.pc - 2;
        Code.fixup(jDoneZero1);
        Code.fixup(jDoneZero2);
        Code.loadConst(0);
        Code.putJump(0);
        int jDone2 = Code.pc - 2;
        Code.fixup(jInc);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI);
        Code.putJump(loopStart);
        Code.fixup(jDone);
        Code.fixup(jDone2);
    }
}
