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
    			type.struct = typeNode.getType();
    			currentType = type.struct;
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
    	
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
    	
    	constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
    	
    	if(constDecl.getBo() == true) {
    		constDecl.obj.setAdr(1);
    	}else {
    		constDecl.obj.setAdr(0);
    	}
    	
    	
    	report_info("Konstanta definisana na liniji "+ constDecl.getLine() + ": " + constDecl.getConstName(), null);
    	constDeclCount++;
    }
    
    public void visit (Designator1 designator) {
    	if((designator.obj = Tab.find(designator.getDesName())) == Tab.noObj) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol nije deklarisan: " + designator.getDesName(),  null);
    		return;
    	}
    	report_info("Koriscen simbol " + designator.getDesName() + " na liniji " + designator.getLine(), null);
    }
    
    public void visit (DesignatorArray designator) {
    	Obj arrayObj;
    	if((arrayObj = Tab.find(designator.getDesName())) == Tab.noObj) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (niz) nije deklarisan: " + designator.getDesName(),  null);
    		return;
    	}
    	
    	if(designator.obj.getType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (niz) nije tipa Array: " + designator.getDesName(), null);
    		return;
    	}
    	
    	if(designator.getExpr().struct != Tab.intType) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Expr nije tipa int: " + designator.getDesName(), null);
    		return;
    	}
    	designator.obj = new Obj(Obj.Elem, arrayObj.getName(), arrayObj.getType().getElemType().getElemType());
    	report_info("Koriscen simbol (niz) " + designator.getDesName() + " na liniji " + designator.getLine(), null);
    	
    }
    
    public void visit (DesignatorMatrix designator) {
    	Obj matrixObj;
    	if((matrixObj = Tab.find(designator.getDesName())) == Tab.noObj) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (matrica) nije deklarisan: " + designator.getDesName(),  null);
    		return;
    	}
    	
    	if(designator.obj.getType().getKind() != Struct.Array
    			|| designator.obj.getType().getElemType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (matrica) nije tipa Array: " + designator.getDesName(), null);
    		return;
    	}
    	
    	if(designator.getExpr().struct != Tab.intType) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Expr nije tipa int: " + designator.getDesName(), null);
    		return;
    	}
    	
    	designator.obj = new Obj(Obj.Elem, matrixObj.getName(), matrixObj.getType().getElemType().getElemType());
    	report_info("Koriscen simbol (matrica) " + designator.getDesName() + " na liniji " + designator.getLine(), null);
    	
    }
    
    public void visit (DesignatorStatement1 desStmt) {
    	
    	// TODO proveri da li je designator.obj null
    	if(!(desStmt.getDesignator().obj.getKind() == Obj.Elem 
    			|| desStmt.getDesignator().obj.getKind() == Obj.Var)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
    		return;
    	}
    	
    	if(!desStmt.getDesignator().obj.getType().compatibleWith(desStmt.getExpr().struct)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
    		return;
    	}
    	report_info("Dodela vrednosti simbolu " + desStmt.getDesignator() + " na liniji " + desStmt.getLine(), null);
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
    	
    	report_info("Inkrementirana vrednost simbola " + desStmt.getDesignator() + " na liniji " + desStmt.getLine(), null);
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
    	
    	report_info("Dekrementirana vrednost simbola " + desStmt.getDesignator() + " na liniji " + desStmt.getLine(), null);
    }
    

    
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
