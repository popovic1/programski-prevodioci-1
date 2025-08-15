//package rs.ac.bg.etf.pp1;
//
//import org.apache.log4j.Logger;
//import rs.ac.bg.etf.pp1.ast.*;
//
//public class RuleVisitor extends VisitorAdaptor {
//
//	int printCallCount = 0;
//	int varDeclCount = 0;
//
//	Logger log = Logger.getLogger(getClass());
//
//	public void visit(VarDeclElems1Multiple vardecl) {
//		varDeclCount++;
//	}
//	
//	public void visit(VarDeclElems1 vardecl) {
//		varDeclCount++;
//	}
//	
//	public void visit(VarDeclElemsArrayMultiple vardecl) {
//		varDeclCount++;
//	}
//	
//	public void visit(VarDeclElemsArray vardecl) {
//		varDeclCount++;
//	}
//
//	public void visit(StatementPrintExpr print) {
//		printCallCount++;
//	}
//
//}
