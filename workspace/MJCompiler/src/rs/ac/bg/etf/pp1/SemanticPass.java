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
	Struct setType = new Struct(6);
	Logger log = Logger.getLogger(getClass());
	
	SemanticPass(){
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", boolType));
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "set", setType));
		
		if (Tab.find("add") == Tab.noObj) {
	        Obj add = Tab.insert(Obj.Meth, "add", Tab.noType); 
	        Tab.openScope();
	        Obj o1 = Tab.insert(Obj.Var, "a", setType);               
	        o1.setFpPos(1);
	        Obj o2 = Tab.insert(Obj.Var, "b", Tab.intType);       
	        o1.setFpPos(2);
	        add.setLevel(2);
	        Tab.chainLocalSymbols(add);
	        Tab.closeScope();
	    }
	    if (Tab.find("addAll") == Tab.noObj) {
	        Obj addAll = Tab.insert(Obj.Meth, "addAll", Tab.noType);
	        Tab.openScope();
	        Obj o1 = Tab.insert(Obj.Var, "a", setType); 
	        o1.setFpPos(1);
	        Obj o2 = Tab.insert(Obj.Var, "b", new Struct(Struct.Array, Tab.intType));
	        o2.setFpPos(2);
	        addAll.setLevel(2);
	        Tab.chainLocalSymbols(addAll);
	        Tab.closeScope();
	    }

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
    	
    	designator.getDesignName().obj = Tab.find(designator.getDesignName().getName());
    	if(designator.getDesignName().obj  == Tab.noObj) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol nije deklarisan: " + designator.getDesignName().getName(),  null);
    		return;
    	}
    	
    	report_info("Koriscen simbol " + designator.getDesignName().getName() + " na liniji " + designator.getLine(), null);
    }
	    
    public void visit (DesignatorArray designator) {
    	
    	designator.getDesignName().obj = Tab.find(designator.getDesignName().getName());
    	
    	if(designator.getDesignName().obj.getType().getKind() != Struct.Array) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Korisceni simbol (niz) nije tipa Array: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	if(designator.getExpr().struct != Tab.intType) {
    		report_error("Semanticka greska na liniji " + designator.getLine() + ". Expr nije tipa int: " + designator.getDesignName().getName(), null);
    		return;
    	}
    	
    	report_info("Koriscen simbol (niz) " + designator.getDesignName().getName() + " na liniji " + designator.getLine(), null);
    	
    }
    
	public void visit (DesignatorStatement1 desStmt) {
		Designator des = desStmt.getDesignator();
		Obj desObj;
		
		if(des instanceof Designator1) {
			desObj = ((Designator1)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var) { //moze da bude i set, set je tipa var
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(desStmt.getExpr().struct.assignableTo(desObj.getType()))) {
				report_info("!!!!!!!! Expr tipa: " + desStmt.getExpr().struct.getKind() + ", designator tipa " + desObj.getType().getKind(), null);
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}else {
			desObj = ((DesignatorArray)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var || desObj.getType().getKind() != Struct.Array) {
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(desStmt.getExpr().struct.assignableTo(desObj.getType().getElemType()))) {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}
    	
    	report_info("Dodela vrednosti simbolu " + desObj.getName() + " na liniji " + desStmt.getLine(), null);
    }
	
	public void visit (DesignatorStatementInc desStmt) {
		Designator des = desStmt.getDesignator();
		Obj desObj;
		
		if(des instanceof Designator1) {
			desObj = ((Designator1)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var) {
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(Tab.intType.assignableTo(desObj.getType()))) {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}else {
			desObj = ((DesignatorArray)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var || desObj.getType().getKind() != Struct.Array) {
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(Tab.intType.assignableTo(desObj.getType().getElemType()))) {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}
    	
    	report_info("Inkrementirana vrednost simbolu " + desObj.getName() + " na liniji " + desStmt.getLine(), null);
    }
	
	public void visit (DesignatorStatementDec desStmt) {
		Designator des = desStmt.getDesignator();
		Obj desObj;
		
		if(des instanceof Designator1) {
			desObj = ((Designator1)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var) {
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(Tab.intType.assignableTo(desObj.getType()))) {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}else {
			desObj = ((DesignatorArray)des).getDesignName().obj;
			
			if(desObj.getKind() != Obj.Var || desObj.getType().getKind() != Struct.Array) {
	    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator nije ispravnog tipa", null);
	    		return;
	    	}
			
			if(!(Tab.intType.assignableTo(desObj.getType().getElemType()))) {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Tipovi designatora i expr-a nisu kompatibilni.", null);
				return;
			}
		}
    	
    	report_info("Dekrementirana vrednost simbolu " + desObj.getName() + " na liniji " + desStmt.getLine(), null);
    }
	
	public void visit(DesignatorStatement2 desStmt) {
		Designator d = desStmt.getDesignator();
    	if(!(d instanceof Designator1)) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator u DesignatorStatement2(poziv metode) nije Designator1 (niz je).", null);
    		return;
    	}
    	
    	Obj o = ((Designator1)d).getDesignName().obj;
    	
    	if(o.getKind() != Obj.Meth) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Designator u DesignatorStatement2(poziv metode) nije tipa Obj.Meth.", null);
    		return;
    	}
    	
    	ActPars ap = desStmt.getActPars();
    	java.util.ArrayList<Struct> params = new java.util.ArrayList<>();
    	
    	if (!(ap instanceof NoActPars)) {
    		
    		ActPars i = ap;
    		
    		while (i instanceof ActPars2) {
    			params.add(((ActPars2)i).getExpr().struct);
    			i = ((ActPars2)i).getActPars();
    		}
    		params.add(((ActPars1)i).getExpr().struct);
    	}
    	
    	if(o.getLevel() != params.size()) {
    		report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Prosledjen pogresan broj argumenata: " + params.size() + " funkciji " + ((Designator1)d).getDesignName().getName() + " u DesignatorStatement2(poziv metode). Potreban broj argumenata: " + o.getLevel(), null);
    		return;
    	}
    	
    	java.util.Iterator<Obj> itArgs = o.getLocalSymbols().iterator();
    	int cnt = params.size();
    	while(itArgs.hasNext()) {
    		Obj tmp = itArgs.next();
    		Struct t = tmp.getType();
    		Struct p = params.get(--cnt);
    		
    		boolean ok = false;
    		
    		if(t.equals(p)) {
    			ok = true;
    		}else if(t.getKind() == Struct.Array && t.getElemType() == Tab.noType
    				&& p.getKind() == Struct.Array) {
    			ok = true;
    		}else if (p == Tab.nullType && t != null && (
    	            t.getKind() == Struct.Array ||
    	            t.equals(setType))) {
    			
    			ok = true;
    		}
    		
    		if(!ok) {
    			report_error("Semanticka greska na liniji " + desStmt.getLine() + ": Prosledjen pogresan tip argumenta " + cnt + " funkciji " + ((Designator1)d).getDesignName().getName() + " u DesignatorStatement2(poziv metode).", null);
        		return;
    		}
    	}
    	
    	report_info("DesignatorStatement2: ispravno pozvana metoda na liniji " + desStmt.getLine() + " povratne vrednosti " + o.getType().getKind(), null);
	}
	
	public void visit(DesignatorStatement3 desStmt) {
		Designator d1 = desStmt.getDesignator();
		Designator d2 = desStmt.getDesignator1();
		Designator d3 = desStmt.getDesignator2();
		
		if(d1 instanceof Designator1 
				&& d2 instanceof Designator1 
				&& d3 instanceof Designator1) {
			
			Obj o1 = ((Designator1)d1).getDesignName().obj;
			Obj o2 = ((Designator1)d2).getDesignName().obj;
			Obj o3 = ((Designator1)d3).getDesignName().obj;
			
			if(o1.getType() == setType
					&& o2.getType() == setType
					&& o3.getType() == setType) {
				
				report_info("DesignatorStatement3 (set1 = set2 union set3) na liniji " + desStmt.getLine(), null);
				
			}else {
				report_error("Semanticka greska na liniji " + desStmt.getLine() + ": DesignatorStatement3(set1 = set2 union set3): jedna od promenljivih nije tipa set.", null);
				return;
			}
			
		}else {
			report_error("Semanticka greska na liniji " + desStmt.getLine() + ": DesignatorStatement3(set1 = set2 union set3): jedna od promenljivih nije tipa Designator1.", null);
			return;
		}
	}
	
	public void visit (ExprPlus expr) {
    	expr.struct = expr.getTerm().struct;
    	currentStruct = expr.struct;
    	report_info("ExprPlus na liniji " + expr.getLine() + " tipa: " + expr.getTerm().struct.getKind(), null);
    }
    
    public void visit (ExprMinus expr) {
    	if(expr.getTerm().struct != Tab.intType) {
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
    		return;
    	}
    	expr.struct = Tab.intType;
    	report_info("AddopTermList Expr na liniji " + expr.getLine(), null);
    }
    
    public void visit (Term1 term) {
    	
    	term.struct = currentStruct = term.getFactor().struct;
    	report_info("Term1 tipa " + term.struct.getKind() + " na liniji " + term.getLine(), null);
    	
    }
    
    public void visit (Term2 term) {
    	
    	if(!(term.getTerm().struct == Tab.intType && term.getFactor().struct == Tab.intType)) {
    		report_error("Semanticka greska na liniji " + term.getLine() + ": Kompleksni mulop izraz nije tipa int.", null);
    		return;
    	}
    	term.struct = Tab.intType;
    	report_info("Mulop term (Term2) na liniji " + term.getLine(), null);  	
    	
    }
    
    public void visit(Factor1 fact) {
    	
    	Designator des = fact.getDesignator();
    	Obj desObj;
    	
    	if(des instanceof Designator1) {
    		desObj = ((Designator1)des).getDesignName().obj;
    		fact.struct = desObj.getType();
    	}else {
    		desObj = ((DesignatorArray)des).getDesignName().obj;
    		fact.struct = desObj.getType().getElemType();
    	}
    }
    
    public void visit(Factor2 fact) {
    	Designator d = fact.getDesignator();
    	if(!(d instanceof Designator1)) {
    		report_error("Semanticka greska na liniji " + fact.getLine() + ": Designator u Factor2(poziv metode) nije Designator1 (niz je).", null);
    		return;
    	}
    	
    	Obj o = ((Designator1)d).getDesignName().obj;
    	
    	if(o.getKind() != Obj.Meth) {
    		report_error("Semanticka greska na liniji " + fact.getLine() + ": Designator u Factor2(poziv metode) nije tipa Obj.Meth.", null);
    		return;
    	}
    	
    	ActPars ap = fact.getActPars();
    	java.util.ArrayList<Struct> params = new java.util.ArrayList<>();
    	
    	if (!(ap instanceof NoActPars)) {
    		
    		ActPars i = ap;
    		
    		while (i instanceof ActPars2) {
    			params.add(((ActPars2)i).getExpr().struct);
    			i = ((ActPars2)i).getActPars();
    		}
    		params.add(((ActPars1)i).getExpr().struct);
    	}
    	
    	if(o.getLevel() != params.size()) {
    		report_error("Semanticka greska na liniji " + fact.getLine() + ": Prosledjen pogresan broj argumenata funkciji " + ((Designator1)d).getDesignName().getName() + " u Factor2(poziv metode).", null);
    		return;
    	}
    	
    	java.util.Iterator<Obj> itArgs = o.getLocalSymbols().iterator();
    	int cnt = params.size();
    	while(itArgs.hasNext()) {
    		Obj tmp = itArgs.next();
    		Struct t = tmp.getType();
    		Struct p = params.get(--cnt);
    		
    		boolean ok = false;
    		
    		if(t.equals(p)) {
    			ok = true;
    		}else if(t.getKind() == Struct.Array && t.getElemType() == Tab.noType
    				&& p.getKind() == Struct.Array) {
    			ok = true;
    		}else if (p == Tab.nullType && t != null && (
    	            t.getKind() == Struct.Array ||
    	            t.equals(setType))) {
    			
    			ok = true;
    		}
    		
    		if(!ok) {
    			report_error("Semanticka greska na liniji " + fact.getLine() + ": Prosledjen pogresan tip argumenta " + cnt + " funkciji " + ((Designator1)d).getDesignName().getName() + " u Factor2(poziv metode).", null);
        		return;
    		}
    	}
    	
    	
    	fact.struct = o.getType();
    	report_info("Factor2: ispravno pozvana metoda na liniji " + fact.getLine() + " povratne vrednosti " + fact.struct.getKind(), null);
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
    	if(!(Tab.intType.assignableTo(fact.getExpr().struct))) {
			report_error("Semanticka greska na liniji " + fact.getLine() + ": Expr x kod new y[x] nije tipa int.", null);
			return;
		}
    	if(fact.getType().struct == setType) {
    		fact.struct = setType;
    	}else {
    		fact.struct = new Struct(Struct.Array, fact.getType().struct);
    	}
    	
    }
    
    public void visit(Factor6 fact) {
    	fact.struct = fact.getExpr().struct;
    }
    
    public void visit (StatementRead s) {
    	Designator d = s.getDesignator();
    	Obj o;
    	
    	if(d instanceof Designator1) {
    		o = ((Designator1)d).getDesignName().obj;
    		
    		if(o.getKind() != Obj.Var) {
    			report_error("Semanticka greska na liniji " + s.getLine() + ": Read: Designator nije tipa Var.", null);
    			return;
    		}else if(o.getType() != Tab.intType && o.getType() != Tab.charType && o.getType() != boolType){
    			report_error("Semanticka greska na liniji " + s.getLine() + ": Read ne podrzava dati tip", null);
        		return;
    		}else {
    			report_info("Read uspesno pozvan na liniji " + s.getLine(), null);
    		}
    		
    	}else {
    		o = ((DesignatorArray)d).getDesignName().obj;
    		
    		if(o.getKind() != Obj.Var || o.getType().getKind() != Struct.Array) {
    			report_error("Semanticka greska na liniji " + s.getLine() + ": Read: Designator nije element niza.", null);
    			return;
    		}else if(o.getType().getElemType() != Tab.intType && o.getType().getElemType() != Tab.charType && o.getType().getElemType() != boolType){
    			report_error("Semanticka greska na liniji " + s.getLine() + ": Read ne podrzava dati tip", null);
        		return;
    		}else {
    			report_info("Read uspesno pozvan na liniji " + s.getLine(), null);
    		}
    		
    	}
    }
    
    public void visit(StatementPrintExpr p) {
    	printCallCount++;
    	Struct s = p.getExpr().struct;
    	
    	while(s.getKind() == Struct.Array) {
    		s = s.getElemType();
    	}
    	
    	if(!(s == Tab.intType || s == Tab.charType || s == boolType || s == setType)) {
    		report_error("Semanticka greska na liniji " + p.getLine() + ": Print ne podrzava dati tip", null);
    		return;
    	}
    	report_info("Print na liniji " + p.getLine(), null);
    }
    
    public void visit(StatementPrintExprWithNum p) {
    	printCallCount++;
    	Struct s = p.getExpr().struct;
    	
    	while(s.getKind() == Struct.Array) {
    		s = s.getElemType();
    	}
    	
    	if(!(s == Tab.intType || s == Tab.charType || s == boolType || s == setType)) {
    		report_error("Semanticka greska na liniji " + p.getLine() + ": Print ne podrzava dati tip", null);
    		return;
    	}
    	report_info("Print na liniji " + p.getLine(), null);
    }

    public void visit (CondFact2 cf) {
    	
    	Struct l = cf.getExpr().struct;
    	Struct r = cf.getExpr1().struct;
    	
    	Relop rel = cf.getRelop();
    	
    	if(rel instanceof Equalop || rel instanceof NotEqualop) {
    		if(!l.compatibleWith(r)) {
    			report_error("Semanticka greska na liniji " + cf.getLine() + ": Tipovi u relop operaciji nisu kompatibilni.", null);
        		return;
    		}
    	}else {
    		if (!((l == Tab.intType && r == Tab.intType)
    				|| (l == Tab.charType && r == Tab.charType))) {
    			report_error("Semanticka greska na liniji " + cf.getLine() + ": Tipovi u relop operaciji nisu kompatibilni.", null);
        		return;
    		}
    	}
    	report_info("Ispravan CondFact2(relop operacija) na liniji " + cf.getLine(), null);
    	
    	cf.struct = boolType;
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
