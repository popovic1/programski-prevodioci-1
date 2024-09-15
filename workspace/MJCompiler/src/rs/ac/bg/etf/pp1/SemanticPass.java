package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	int constDeclCount = 0;
	int globalDeclCount = 0;
	Obj currentMethod = null;
	Struct currentType = null;
	Struct currentStruct = null;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;
	
	
	Struct boolType = new Struct(5);
	Logger log = Logger.getLogger(getClass());
	
	SemanticPass(){
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
    
    public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program program){
    	nVars = Tab.currentScope.getnVars();

		Obj funct = Tab.find("main");

		if(funct == Tab.noObj){
			report_error("Semanticka greska: Main funkcija nije definisana.", null);
		}else if (funct.getType() != Tab.noType){
			report_error("Semanticka greska: Main funkcija ima povratnu vrednost koja nije void.", null);
			report_info("Main funkcija definisana.", null);
		}

    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = currentType = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    }
    
    public void visit(MethodTypeNameVoid methodTypeName){
    	currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
    	methodTypeName.obj = currentMethod;
    	Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
    }
    
    public void visit(MethodDecl methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
			return;
    	}
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
    }
    
    
    public void visit(VarDeclElems1 varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), currentType);
    	report_info("Deklarisan novi simbol na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    	
    }
  
    public void visit(VarDeclElems1Multiple varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), currentType);
    	report_info("Deklarisan novi simbol na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    }
    
    public void visit(VarDeclElemsArray varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), new Struct(Struct.Array, currentType));
    	report_info("Deklarisan novi simbol (niz) na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    }
    
    public void visit(VarDeclElemsArrayMultiple varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), new Struct(Struct.Array, currentType));
    	report_info("Deklarisan novi simbol (niz) na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    }
    
    public void visit(VarDeclElemsMatrix varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), new Struct(Struct.Array, new Struct(Struct.Array, currentType)));
    	report_info("Deklarisan novi simbol (matrica) na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    }
    
    public void visit(VarDeclElemsMatrixMultiple varDeclElems) {
    	if(Tab.currentScope.findSymbol(varDeclElems.getVarName()) != null) {
    		report_error("Semanticka greska na liniji "+ varDeclElems.getLine() + ": symbol " + varDeclElems.getVarName() + " je vec deklarisan", null);
    		return;
    	}
    	varDeclElems.obj = Tab.insert(Obj.Var, varDeclElems.getVarName(), new Struct(Struct.Array, new Struct(Struct.Array, currentType)));
    	report_info("Deklarisan novi simbol (matrica) na liniji " + varDeclElems.getLine() + ": " + varDeclElems.getVarName(), null);
    	
    	if(currentMethod == null) {
    		globalDeclCount ++;
    	}else {
    		varDeclCount++;
    	}
    }
    
    public void visit(ConstDeclSuffixNum1 constDecl) {
    	
    	if(!Tab.intType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.intType);
    	constDecl.obj.setAdr(constDecl.getLiteral());
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit(ConstDeclSuffixNum2 constDecl) {
    	if(!Tab.intType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.intType);
    	constDecl.obj.setAdr(constDecl.getLiteral());
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit(ConstDeclSuffixChar1 constDecl) {
    	if(!Tab.charType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.charType);
    	constDecl.obj.setAdr(constDecl.getCh());
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit(ConstDeclSuffixChar2 constDecl) {
    	if(!Tab.charType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.charType);
    	constDecl.obj.setAdr(constDecl.getCh());
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit(ConstDeclSuffixBool1 constDecl) {
    	if(!boolType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), boolType);
    	
    	if(constDecl.getBo() == true) {
    		constDecl.obj.setAdr(1);
    	}else {
    		constDecl.obj.setAdr(0);
    	}
    	
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit(ConstDeclSuffixBool2 constDecl) {
    	if(!boolType.equals(currentType)) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": tipovi nisu kompatibilni za sledeci simbol: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	if(Tab.find(constDecl.getConstName()) != Tab.noObj) {
    		report_error("Semanticka greska na liniji " + constDecl.getLine() + ": symbol sa sledecim imenom je vec deklarisan: " + constDecl.getConstName(), null);
    		return;
    	}
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), boolType);
    	
    	if(constDecl.getBo() == true) {
    		constDecl.obj.setAdr(1);
    	}else {
    		constDecl.obj.setAdr(0);
    	}
    	
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit (Designator1 designator) {
    	
    	designator.obj = Tab.find(designator.getDesName());
    	if(designator.obj  == Tab.noObj) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol nije deklarisan: " + designator.getDesName(),  null);
    		return;
    	}
    	
    	report_info("Koriscen simbol " + designator.getDesName() + " na liniji " + designator.getLine(), null);
    }
    
    public void visit (DesignatorArray designator) {
    	
    	Obj arrayObj = designator.getDesignName().obj;;
    	
    	
    	if(arrayObj.getType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (niz) nije tipa Array: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	if(designator.getExpr().struct != Tab.intType) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Expr nije tipa int: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	designator.obj = new Obj(Obj.Elem, arrayObj.getName(), arrayObj.getType().getElemType());
    	report_info("Koriscen simbol (niz) " + designator.getDesignName().getName() + " na liniji " + designator.getLine(), null);
    	
    }
    
    public void visit (DesignatorMatrix designator) {
    	report_info("USAO U DESIGNATORMATRIX NA LINIJI " + designator.getLine(), null);
    	
    	Obj matrixObj = designator.getDesignName().obj;
    	
    	if(matrixObj.getType().getElemType().getKind() != Struct.Array
    			|| matrixObj.getType().getElemType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (matrica) nije tipa Array: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	if(matrixObj.getType().getKind() != Struct.Array
    			|| matrixObj.getType().getElemType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (matrica) nije tipa Array: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	if(designator.getExpr().struct != Tab.intType) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Expr nije tipa int: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	designator.obj = new Obj(Obj.Elem, matrixObj.getName(), matrixObj.getType().getElemType().getElemType());
    	report_info("Koriscen simbol (matrica) " + designator.getDesignName().getName() + " na liniji " + designator.getLine(), null);
    	
    }
    
    public void visit (DesignName dn) {
    	Obj o = Tab.find(dn.getName());
    	
    	if(o != Tab.noObj) {
    		dn.obj = o;
    	}
    }
    
    public void visit (DesignatorStatement1 desStmt) {
    	
    	// TODO proveri da li je designator.obj null
    	if(!(desStmt.getDesignator().obj.getKind() == Obj.Elem 
    			|| desStmt.getDesignator().obj.getKind() == Obj.Var)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
    		return;
    	}
    	
    	if(!desStmt.getExpr().struct.compatibleWith(desStmt.getDesignator().obj.getType())) {
    		report_info("Tip expr: " + desStmt.getExpr().struct + ", tip designatora = " + desStmt.getDesignator().obj.getType(), null);
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
    		return;
    	}
    	report_info("Dodela vrednosti simbolu " + desStmt.getDesignator().obj.getName() + " na liniji " + desStmt.getLine(), null);
    }
    
    public void visit (DesignatorStatementInc desStmt) {
    	if(!(desStmt.getDesignator().obj.getKind() == Obj.Elem 
    			|| desStmt.getDesignator().obj.getKind() == Obj.Var)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
    		return;
    	}
    	
    	if(!Tab.intType.assignableTo(desStmt.getDesignator().obj.getType())) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije kompatiblinog tipa", null);
    		return;
    	}
    	
    	report_info("Inkrementirana vrednost simbola " + desStmt.getDesignator().obj.getName() + " na liniji " + desStmt.getLine(), null);
    }

    public void visit (DesignatorStatementDec desStmt) {
    	if(!(desStmt.getDesignator().obj.getKind() == Obj.Elem 
    			|| desStmt.getDesignator().obj.getKind() == Obj.Var)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
    		return;
    	}
    	
    	if(!Tab.intType.assignableTo(desStmt.getDesignator().obj.getType())) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije kompatiblinog tipa", null);
    		return;
    	}
    	
    	report_info("Dekrementirana vrednost simbola " + desStmt.getDesignator().obj.getName() + " na liniji " + desStmt.getLine(), null);
    }
    
    public void visit (ExprPlus expr) {
    	expr.struct = expr.getTerm().struct;
    	currentStruct = expr.struct;
    	report_info("ExprPlus na liniji " + expr.getLine() + " tipa: " + expr.getTerm().struct, null);
    }
    
    public void visit (ExprMinus expr) {
    	if(expr.getTerm().struct != Tab.intType) {
    		expr.struct = Tab.noType;
    		currentStruct = expr.struct;
    		report_error("Semanticka greska na liniji " + expr.getLine() + ": Negativni izraz nije tipa int.", null);
    		return;
    	}
    	expr.struct = expr.getTerm().struct;
    	currentStruct = expr.struct;
    	report_info("ExprMinus na liniji " + expr.getLine(), null);
    }
    
    public void visit (ExprMultiple expr) {
    	if(!(expr.getTerm().struct == Tab.intType && expr.getExpr().struct == Tab.intType)) {
    		report_error("Semanticka greska na liniji " + expr.getLine() + ": Kompleksni addop izraz nije tipa int.", null);
    		expr.struct = Tab.noType;
    		return;
    	}
    	expr.struct = Tab.intType;
    	report_info("AddopTermList Expr na liniji " + expr.getLine(), null);
    }
    
    public void visit (Term1 term) {
    	
    	term.struct = currentStruct = term.getFactor().struct;
    	
    }
    
    public void visit (Term2 term) {
	
    	if(term.getTerm().struct.getKind() == Struct.Array) {
//    		term.getTerm().struct = currentStruct =  term.getTerm().struct.getElemType();
    		if(term.getTerm().struct.getKind() == Struct.Array) {
//    			term.getTerm().struct = currentStruct =  term.getTerm().struct.getElemType();
    		}
    	}
    	
    	if(term.getFactor().struct.getKind() == Struct.Array) {
//    		term.getFactor().struct = currentStruct =  term.getFactor().struct.getElemType();
    		if(term.getFactor().struct.getKind() == Struct.Array) {
//    			term.getFactor().struct = currentStruct =  term.getFactor().struct.getElemType();
    		}
    	}
    	
    	if(term.getFactor().struct == Tab.intType &&  term.getTerm().struct == Tab.intType) {
    		term.struct = term.getTerm().struct;
    	}else {
    		report_error("Semanticka greska na liniji " + term.getLine() + ": Elementi mulop izraza nisu kompatibilni", null);
    	}
    	
    	
    	
    }
    
    public void visit(MulopFactorList1 term) {
    	// TODO sta ako je matrica ili niz?
    	if(!(currentStruct == Tab.intType && term.getFactor().struct == Tab.intType)) {
    		report_error("Semanticka greska na liniji " + term.getLine() + ": Kompleksni mulop izraz nije tipa int.", null);
    		term.struct = currentStruct = Tab.noType;
    		return;
    	}
    	term.struct = currentStruct;
    	report_info("MulopFactorList Expr na liniji " + term.getLine(), null);
    }
    
    public void visit(Factor1 fact) {
    	fact.struct = fact.getDesignator().obj.getType();
    }
    
    public void visit(FactorNum fact) {
    	fact.struct = Tab.intType;
    }
    
    public void visit(FactorChar fact) {
    	fact.struct = Tab.charType;
    }
    
    public void visit(FactorBool fact) {
    	fact.struct = boolType;
    }
    
    public void visit(Factor3 fact) {
    	fact.struct = new Struct(Struct.Array, fact.getType().struct);
    }
    
    public void visit(Factor4 fact) {
    	fact.struct = new Struct(Struct.Array, new Struct(Struct.Array, fact.getType().struct));
    }
    
    public void visit(Factor6 fact) {
    	fact.struct = fact.getExpr().struct;
    }
    
    public void visit(StatementPrintExpr p) {
    	Struct s = p.getExpr().struct;
    	
    	while(s.getKind() == Struct.Array) {
    		s = s.getElemType();
    	}
    	
    	if(!(s == Tab.intType || s == Tab.charType || s == boolType)) {
    		report_error("Semanticka greska na liniji " + p.getLine() + ": Print ne podrzava dati tip", null);
    		return;
    	}
    	report_info("Print na liniji " + p.getLine(), null);
    }
    
    public void visit(StatementPrintExprWithNum p) {
    	Struct s = p.getExpr().struct;
    	
    	while(s.getKind() == Struct.Array) {
    		s = s.getElemType();
    	}
    	
    	if(!(s == Tab.intType || s == Tab.charType || s == boolType)) {
    		report_error("Semanticka greska na liniji " + p.getLine() + ": Print ne podrzava dati tip", null);
    		return;
    	}
    	report_info("Print na liniji " + p.getLine(), null);
    }
    
    public void visit (StatementRead s) {
    	if(s.getDesignator().obj.getKind() == Obj.Var || s.getDesignator().obj.getKind() == Obj.Elem) {
    		if(s.getDesignator().obj.getType() == Tab.intType || s.getDesignator().obj.getType() == Tab.charType){} 
    		else{
    			report_error("Semanticka greska na liniji " + s.getLine() + ": Read ne podrzava dati tip", null);
    		}
    	}else {
    		report_error("Semanticka greska na liniji " + s.getLine() + ": Read ne podrzava dati kind", null);
    	}
    }
    
    
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
