package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VarDeclInFunctionArray;
import rs.ac.bg.etf.pp1.ast.VarDeclInFunctionVars;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
    
	public int getCount() {
		return count;
	}
	
	public static class VarCounter extends CounterVisitor {
		public void visit(VarDeclInFunctionArray varDeclarationArr) { count++; }
		public void visit(VarDeclInFunctionVars varDeclarationVar) { count++; }
	}
}