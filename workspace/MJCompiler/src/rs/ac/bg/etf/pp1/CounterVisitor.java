package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
    
	public int getCount() {
		return count;
	}
	
	public static class VarCounter extends CounterVisitor {
		public void visit(VarDeclElems1Multiple varDeclarationVar) { count++; }
		public void visit(VarDeclElemsArrayMultiple varDeclarationVar) { count++; }
		public void visit(VarDeclElemsMatrixMultiple varDeclarationVar) { count++; }
		public void visit(VarDeclElems1 varDeclarationVar) { count++; }
		public void visit(VarDeclElemsArray varDeclarationVar) { count++; }
		public void visit(VarDeclElemsMatrix varDeclarationVar) { count++; }
	}
}