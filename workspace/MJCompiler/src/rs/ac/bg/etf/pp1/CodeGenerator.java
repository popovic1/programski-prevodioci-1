package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	public void visit(StatementPrintExpr printStmt){
		if(printStmt.getExpr().struct == Tab.intType){
			Code.loadConst(5);
			Code.put(Code.print);
		}else{
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

    public void visit(StatementPrintExprWithNum printStmt){
        Code.loadConst(printStmt.getNum());
		if(printStmt.getExpr().struct == Tab.intType){
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
		Obj con = Tab.insert(Obj.Con, "$", cnst.struct);
		con.setLevel(0);
		con.setAdr(cnst.getN1());
		
		Code.load(con);
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
		// TODO MATRICA
	}
	
	public void visit(DesignatorStatement1 dStmt) {
		Code.store(dStmt.getDesignator().obj);
	}
	
	public void visit(DesignatorStatementInc dStmt) {
		
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(dStmt.getDesignator().obj);
	}
	
	public void visit(DesignatorStatementDec dStmt) {
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
	}
	
	public void visit (DesignatorArray dsg) {
		Code.load(dsg.getDesignName().obj);
		
		if(!(dsg.getParent() instanceof DesignatorStatement1 ||
			dsg.getParent() instanceof StatementRead)){
				
			if(dsg.getDesignName().obj.getType() != Tab.charType) {
				Code.put(Code.aload);
			}else {
				Code.put(Code.baload);
			}
		}
		
	}
	
	public void visit (DesignatorMatrix dsg) {
		if(dsg.getParent() instanceof DesignatorStatementInc
				|| dsg.getParent() instanceof DesignatorStatementDec
				|| dsg.getParent() instanceof Factor1) {
			Code.load(dsg.obj);
		}
	}
	
	
	
	

}
