package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;
    private int tmpS, tmpX, tmpI, tmpJ, tmpK, tmpY;
    private Obj tS = new Obj(Obj.Var, "$s", Tab.intType);
    private Obj tX = new Obj(Obj.Var, "$x", Tab.intType);
    private Obj tI = new Obj(Obj.Var, "$i", Tab.intType);
    private Obj tJ = new Obj(Obj.Var, "$j", Tab.intType); // counter
    private Obj tK = new Obj(Obj.Var, "$k", Tab.intType); // index / srcB
    private Obj tY = new Obj(Obj.Var, "$y", Tab.intType); // srcA

    public int getMainPc(){
        return mainPc;
    }

    public void visit(StatementPrintExpr printStmt){
        Struct t = printStmt.getExpr().struct;
        if (t.getKind() == 6) {
            // print set: elements separated by space
            emitPrintSetInline();
        } else if (t != Tab.charType) {
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }

    public void visit(StatementPrintExprWithNum printStmt){
        Struct t = printStmt.getExpr().struct;
        if (isSetType(t)) {
            // width is ignored for sets; print elements separated by space
            emitPrintSetInline();
        } else {
            Code.loadConst(printStmt.getNum());
            if (t != Tab.charType) {
                Code.put(Code.print);
            } else {
                Code.put(Code.bprint);
            }
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
        tmpS = base; tmpX = base+1; tmpI = base+2; tmpJ = base+3; tmpK = base+4; tmpY = base+5;
        tS.setAdr(tmpS); tS.setLevel(1);
        tX.setAdr(tmpX); tX.setLevel(1);
        tI.setAdr(tmpI); tI.setLevel(1);
        tJ.setAdr(tmpJ); tJ.setLevel(1);
        tK.setAdr(tmpK); tK.setLevel(1);
        tY.setAdr(tmpY); tY.setLevel(1);
        Code.put(Code.enter);
        Code.put(0);
        Code.put(base + 6);
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
            } else if ("addAll".equals(name)) {
                emitAddAllInlineWithResult();
                Code.put(Code.pop);
            } else if ("union".equals(name)) {
                emitUnionInlineWithResult();
                Code.put(Code.pop);
            } else if ("open".equals(name)) {
                // prints first element of the set argument
                emitPrintSetFirstInline();
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
            } else if ("addAll".equals(name)) {
                emitAddAllInlineWithResult();
            } else if ("union".equals(name)) {
                emitUnionInlineWithResult();
            }
        }
    }

    // add(s, x) -> returns 1 if inserted, 0 otherwise
    private void emitAddInlineWithResult(){
        Code.store(tX);      // x
        Code.store(tS);      // s
        Code.load(tX); Code.loadConst(1); Code.put(Code.add); Code.store(tX); // x++ (store as value+1)
        Code.loadConst(0); Code.store(tI); // i = 0
        int loopStart = Code.pc;
        Code.load(tI);
        Code.load(tS); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0); // if !(i < s.length) -> done (return 0)
        int jDoneZero1 = Code.pc - 2;
        Code.load(tS); Code.load(tI); Code.put(Code.aload); // s[i]
        Code.load(tX);
        Code.putFalseJump(Code.ne, 0); // if s[i] == x -> done (return 0)
        int jDoneZero2 = Code.pc - 2;
        Code.load(tS); Code.load(tI); Code.put(Code.aload); // s[i]
        Code.loadConst(0);
        Code.putFalseJump(Code.eq, 0); // if s[i] != 0 -> i++
        int jInc = Code.pc - 2;
        Code.load(tS); Code.load(tI);
        Code.load(tX);
        Code.put(Code.astore);         // s[i] = x
        Code.loadConst(1);
        Code.putJump(0);               // return 1
        int jDone = Code.pc - 2;
        Code.fixup(jDoneZero1);
        Code.fixup(jDoneZero2);
        Code.loadConst(0);             // return 0
        Code.putJump(0);
        int jDone2 = Code.pc - 2;
        Code.fixup(jInc);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI); // i++
        Code.putJump(loopStart);
        Code.fixup(jDone);
        Code.fixup(jDone2);
    }

    // addAll(dest, src) -> returns count of newly inserted elements
    private void emitAddAllInlineWithResult(){
        Code.store(tY);  // src
        Code.store(tS);  // dest
        Code.loadConst(0); Code.store(tJ); // count = 0
        Code.loadConst(0); Code.store(tK); // i = 0
        int loopStart = Code.pc;
        Code.load(tK);
        Code.load(tY); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0); // if !(i < src.length) -> end
        int jEnd = Code.pc - 2;

        // call add(dest, src[i])
        Code.load(tS);
        Code.load(tY); Code.load(tK); Code.put(Code.aload);
        emitAddInlineWithResult(); // leaves 0/1 on stack

        // count += result
        Code.store(tX);
        Code.load(tJ); Code.load(tX); Code.put(Code.add); Code.store(tJ);

        // i++
        Code.load(tK); Code.loadConst(1); Code.put(Code.add); Code.store(tK);
        Code.putJump(loopStart);

        // end
        Code.fixup(jEnd);
        Code.load(tJ); // result on stack
    }

    // union(dest, a, b) -> clears dest and inserts elements from a and b; returns total number of insertions
    private void emitUnionInlineWithResult(){
        Code.store(tK); // b
        Code.store(tY); // a

        // allocate fresh dest of size len(a)+len(b)
        Code.load(tY); Code.put(Code.arraylength); Code.store(tI);
        Code.load(tK); Code.put(Code.arraylength); Code.store(tJ);
        Code.load(tI); Code.load(tJ); Code.put(Code.add);
        Code.put(Code.newarray); Code.put(1);
        Code.store(tS);

        // addAll from sets: decode (v-1) and skip empties
        Code.load(tS); Code.load(tY);
        emitAddAllFromSetInlineWithResult();
        Code.put(Code.pop); // discard count

        Code.load(tS); Code.load(tK);
        emitAddAllFromSetInlineWithResult();
        Code.put(Code.pop);

        // leave new set on stack as result
        Code.load(tS);
    }

    // addAll(dest, srcSet) where srcSet is a set-encoded int[] (0 = empty; value = elem+1)
    // returns count of newly inserted elements
    private void emitAddAllFromSetInlineWithResult(){
        Code.store(tY);  // src (set-encoded)
        Code.store(tS);  // dest set
        Code.loadConst(0); Code.store(tJ); // count = 0
        Code.loadConst(0); Code.store(tK); // i = 0
        int loopStart = Code.pc;
        // while (i < src.length)
        Code.load(tK);
        Code.load(tY); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);
        int jEnd = Code.pc - 2;

        // v = src[i]
        Code.load(tY); Code.load(tK); Code.put(Code.aload); Code.store(tX);
        // if (v == 0) skip insert
        Code.load(tX); Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        int jSkip = Code.pc - 2;

        // decode: val = v - 1
        Code.load(tX); Code.loadConst(1); Code.put(Code.sub); Code.store(tX);
        // call add(dest, val)
        Code.load(tS); Code.load(tX);
        emitAddInlineWithResult();
        // accumulate count
        Code.store(tX);
        Code.load(tJ); Code.load(tX); Code.put(Code.add); Code.store(tJ);

        // skip:
        Code.fixup(jSkip);
        // i++
        Code.load(tK); Code.loadConst(1); Code.put(Code.add); Code.store(tK);
        Code.putJump(loopStart);

        // end
        Code.fixup(jEnd);
        Code.load(tJ);
    }

    private boolean isSetType(Struct s){
        return s != null && s.getKind() == 6; // setType kind = 6
    }

    // print set stored as int[] with elements encoded as (value+1); 0 = empty
    private void emitPrintSetInline(){
        // consumes: set reference on stack
        Code.store(tS);                 // tS = set
        Code.loadConst(0); Code.store(tI); // i = 0
        Code.loadConst(0); Code.store(tJ); // printedAny = 0

        int loopStart = Code.pc;
        // while (i < s.length)
        Code.load(tI);
        Code.load(tS); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);
        int jEnd = Code.pc - 2;

        // if (s[i] == 0) goto inc;   // empty slot
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        int jInc = Code.pc - 2;

        // if (printedAny) print space
        Code.load(tJ);
        Code.loadConst(1);
        Code.putFalseJump(Code.eq, 0);
        int jSkipSpace = Code.pc - 2;
        Code.loadConst(32); Code.loadConst(1); Code.put(Code.bprint);
        Code.fixup(jSkipSpace);

        // print (s[i] - 1)
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.loadConst(1); Code.put(Code.sub);
        Code.loadConst(5); Code.put(Code.print);

        // printedAny = 1
        Code.loadConst(1); Code.store(tJ);

        // i++
        Code.fixup(jInc);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI);
        Code.putJump(loopStart);

        // end
        Code.fixup(jEnd);
    }
    private void emitPrintSetFirstInline(){
        // expects: set ref on stack; prints first present element or -1 if empty
        Code.store(tS);
        Code.loadConst(0); Code.store(tI); // i = 0

        int loopStart = Code.pc;
        // while (i < s.length)
        Code.load(tI);
        Code.load(tS); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);
        int jEmpty = Code.pc - 2; // no element found

        // if (s[i] == 0) goto inc;
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        int jInc = Code.pc - 2;

        // print (s[i] - 1) and finish
        Code.load(tS); Code.load(tI); Code.put(Code.aload);
        Code.loadConst(1); Code.put(Code.sub);
        Code.loadConst(5); Code.put(Code.print);
        Code.putJump(0);
        int jEnd = Code.pc - 2;

        // i++ and continue
        Code.fixup(jInc);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI);
        Code.putJump(loopStart);

        // empty set -> print -1
        Code.fixup(jEmpty);
        Code.loadConst(-1);
        Code.loadConst(5); Code.put(Code.print);
        Code.fixup(jEnd);
    }
}

