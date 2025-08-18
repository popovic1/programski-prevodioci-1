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
        if (t.getKind() == 6) {
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
            }else if ("open".equals(name)) {
                // prints first element of the set argument
                emitPrintSetFirstInline();
            }
        }
    }
    
    public void visit(DesignatorStatement3 ds) {
    	
    	Obj res = ((Designator1)(ds.getDesignator())).getDesignName().obj;
    	Obj o1 = ((Designator1)(ds.getDesignator1())).getDesignName().obj;
    	Obj o2 = ((Designator1)(ds.getDesignator2())).getDesignName().obj;
    	
    	Code.load(res);
        Code.load(o1);
        Code.load(o2);
    	
    	emitUnionInlineWithResult();
        Code.put(Code.pop);
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

        // call add(dest, src[i])b
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

 // dest = a union b -> clears dest and inserts elements from a and b; returns total number of insertions
    private void emitUnionInlineWithResult(){
    	
//    	Code.loadConst(111111);
//    	Code.loadConst(5);
//    	Code.put(Code.print);
    	
        // Stack ulaz: ..., dest, a, b  (b je na vrhu)
        Code.store(tK);  // srcB
        Code.store(tY);  // srcA
        Code.store(tS);  // dest
        
        // Helper: in-place decrement all non-zero elements of an int[] array referenced by 'arrObj'
        // i = 0; while (i < arr.length) { if (arr[i] != 0) arr[i] = arr[i] - 1; i++; }
        // --- decrement srcA (tY)
        Code.loadConst(0); Code.store(tI); // i = 0
        int decAStart = Code.pc;
        Code.load(tI);
        Code.load(tY); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);                 // if !(i < len) -> endDecA
        int jEndDecA = Code.pc - 2;

//        // if (arr[i] == 0) goto incA;
//        Code.load(tY); Code.load(tI); Code.put(Code.aload);
//        Code.loadConst(0);
//        Code.putFalseJump(Code.ne, 0);
//        int jIncA = Code.pc - 2;

        // arr[i] = arr[i] - 1;
        Code.load(tY); Code.load(tI);                  // ... arr, i
        Code.load(tY); Code.load(tI); Code.put(Code.aload); // value
        Code.loadConst(1); Code.put(Code.sub);
        Code.put(Code.astore);

        // incA: i++
//        Code.fixup(jIncA);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI);
        Code.putJump(decAStart);
        Code.fixup(jEndDecA);

        // --- decrement srcB (tK)
        Code.loadConst(0); Code.store(tI); // i = 0
        int decBStart = Code.pc;
        Code.load(tI);
        Code.load(tK); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);                 // if !(i < len) -> endDecB
        int jEndDecB = Code.pc - 2;

//        // if (arr[i] == 0) goto incB;
//        Code.load(tK); Code.load(tI); Code.put(Code.aload);
//        Code.loadConst(0);
//        Code.putFalseJump(Code.ne, 0);
//        int jIncB = Code.pc - 2;

        // arr[i] = arr[i] - 1;
        Code.load(tK); Code.load(tI);                  // ... arr, i
        Code.load(tK); Code.load(tI); Code.put(Code.aload); // value
        Code.loadConst(1); Code.put(Code.sub);
        Code.put(Code.astore);

        // incB: i++
//        Code.fixup(jIncB);
        Code.load(tI); Code.loadConst(1); Code.put(Code.add); Code.store(tI);
        Code.putJump(decBStart);
        Code.fixup(jEndDecB);

        // 1) clear(dest): for (i = 0; i < dest.length; i++) dest[i] = 0;
        Code.loadConst(0); Code.store(tI); // i = 0
        int loopStart = Code.pc;
        Code.load(tI);
        Code.load(tS); Code.put(Code.arraylength);
        Code.putFalseJump(Code.lt, 0);         // if !(i < len) -> endClear
        int jEndClear = Code.pc - 2;

        Code.load(tS); Code.load(tI);          // dest[i] = 0;
        Code.loadConst(0);
        Code.put(Code.astore);

        Code.load(tI); Code.loadConst(1);      // i++
        Code.put(Code.add); Code.store(tI);
        Code.putJump(loopStart);

        Code.fixup(jEndClear);

        // save the context
        Code.load(tS);
        Code.load(tY);
        Code.load(tK);
        Code.load(tI);
        
        // 2) r1 = addAll(dest, srcA)
        Code.load(tS);
        Code.load(tY);
        emitAddAllInlineWithResult();          // ostavlja r1 na steku
        Code.store(tJ);// tJ = r1    
        
        // restore context
        Code.store(tI);
        Code.store(tK);
        Code.store(tY);
        Code.store(tS);
        
        // r1 na stek
        Code.load(tJ);

        // 3) r2 = addAll(dest, srcB)
        Code.load(tS);
        Code.load(tK);
        emitAddAllInlineWithResult();          // ostavlja r2 na steku

        // 4) return r1 + r2 (na steku)
        Code.put(Code.add);
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

