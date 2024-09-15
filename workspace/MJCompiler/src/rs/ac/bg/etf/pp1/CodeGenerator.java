package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	
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
	
	
	public void visit(MethodTypeNameVoid methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());
	
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit (StatementRead rdStmt) {
		if(rdStmt.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		
		Code.store(rdStmt.getDesignator().obj);
	}
	
	public void visit(FactorNum cnst){
		Code.loadConst(cnst.getN1());
	}
	
	public void visit(FactorBool cnst){
		int flag = 1;
		if(cnst.getB1() == false) {
			flag = 0;
		}
		Code.loadConst(flag);
	}
	
	public void visit(FactorChar cnst){
		Code.loadConst(cnst.getC1());
	}
	
	public void visit(Factor3 cnst){
		Code.put(Code.newarray);
		if(cnst.struct.getElemType() != Tab.charType) {
			Code.put(1);
		}else {
			Code.put(0);
		}
	}
	
	public void visit(Factor4 cnst){
		Code.put(Code.enter);
		Code.put(2);
		Code.put(3);
		
		Code.put(Code.load_n);
		Code.put(Code.newarray);
		Code.put(1);
		
		int whileAddress = Code.pc;
		Code.put(Code.load_2);
		Code.put(Code.load_n);
		Code.putFalseJump(Code.lt, 0);
		int addressZero = Code.pc-2;
		
		//while
		Code.put(Code.dup);
		Code.put(Code.load_2);
		Code.put(Code.load_1);
		Code.put(Code.newarray);
		if(cnst.getType().struct == Tab.charType) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
		Code.put(Code.astore);
		
		Code.put(Code.load_2);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_2);
		
		Code.putJump(whileAddress);
		Code.fixup(addressZero);
		
		Code.put(Code.exit);
		
	}
	
	public void visit(DesignatorStatement1 dStmt) {
		Code.store(dStmt.getDesignator().obj);
	}
	
	public void visit(DesignatorStatementInc dStmt) {
		
		if(dStmt.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
//		Code.load(dStmt.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(dStmt.getDesignator().obj);
		
	}
	
	public void visit(DesignatorStatementDec dStmt) {
		if(dStmt.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
//		Code.load(dStmt.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(dStmt.getDesignator().obj);
	}
	
	public void visit (ExprMinus expr) {
		Code.put(Code.neg);
	}
	
	public void visit(ExprMultiple expr) {
		if(expr.getAddop() instanceof Subop) {
			Code.put(Code.sub);
		}else if(expr.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		}
	}
	
	public void visit(Term2 term) {
		if(term.getMulop() instanceof Divop) {
			Code.put(Code.div);
		}else if(term.getMulop() instanceof Modop) {
			Code.put(Code.rem);
		}else if(term.getMulop() instanceof Mulop1) {
			Code.put(Code.mul);
		}
	}
	
	public void visit (Designator1 dsg) {
		if(!(dsg.getParent() instanceof DesignatorStatement1)) {
			Code.load(dsg.obj);
		}
		
//		if(dsg.getParent() instanceof Factor1) {
//				Code.load(dsg.obj);
//			}
	}
	
	public void visit (DesignatorArray dsg) {
		
//		if(dsg.getParent().getParent() instanceof Designator1 ||
//			dsg.getParent() instanceof StatementRead){
//				return;
//		}
		
		if(!(dsg.getParent().getParent() instanceof Statement1)) {
//		
			if (dsg.getDesignName().obj.getType().getElemType() == Tab.charType) {
				Code.put(Code.baload);
				
			}else {
			
				Code.put(Code.aload);
			}
		}
		
	}
	
	public void visit (DesignatorMatrix dsg) {
		
	}
	
	public void visit (DesignName dn) {
		Code.load(dn.obj);
	}
	
	public void visit (ConstDeclSuffixNum1 cnst) {
		Code.loadConst(cnst.getLiteral());
	}
	
	public void visit (ConstDeclSuffixNum2 cnst) {
		Code.loadConst(cnst.getLiteral());
	}
	
	public void visit (ConstDeclSuffixChar1 cnst) {
		Code.loadConst(cnst.getCh());
	}
	
	public void visit (ConstDeclSuffixChar2 cnst) {
		Code.loadConst(cnst.getCh());
	}
	
	public void visit (ConstDeclSuffixBool1 cnst) {
		Code.loadConst(cnst.getBo()? 1 : 0);
	}
	
	public void visit (ConstDeclSuffixBool2 cnst) {
		Code.loadConst(cnst.getBo()? 1 : 0);
	}
	

	
	
	
	

}
