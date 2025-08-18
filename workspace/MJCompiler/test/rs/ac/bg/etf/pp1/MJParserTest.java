package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program1.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
	        Tab.init();
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticPass v = new SemanticPass();
			prog.traverseBottomUp(v); 
	      
			log.info(" Print count calls = " + v.printCallCount);

			log.info(" Deklarisanih lokalnih promenljivih ima = " + v.varDeclCount);
			log.info(" Deklarisanih globalnih promenljivih ima = " + v.globalDeclCount);
			
			log.info("===================================");
			Tab.dump();
			tsdumpPretty();
			
			if(!p.errorDetected && v.passed()){
				File objFile = new File("test/program.obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	public static void tsdumpPretty() {
	    final String NL = System.lineSeparator();
	    StringBuilder sb = new StringBuilder();
	    sb.append("=============== SYMBOL TABLE (pretty, ASCII) ===============").append(NL);

	    rs.etf.pp1.symboltable.concepts.Obj setTypeObj  = rs.etf.pp1.symboltable.Tab.find("set");
	    rs.etf.pp1.symboltable.concepts.Struct setStruct =
	            (setTypeObj != rs.etf.pp1.symboltable.Tab.noObj) ? setTypeObj.getType() : null;

	    rs.etf.pp1.symboltable.concepts.Obj boolTypeObj = rs.etf.pp1.symboltable.Tab.find("bool");
	    rs.etf.pp1.symboltable.concepts.Struct boolStruct =
	            (boolTypeObj != rs.etf.pp1.symboltable.Tab.noObj) ? boolTypeObj.getType() : null;

	    rs.etf.pp1.symboltable.concepts.Scope root = rs.etf.pp1.symboltable.Tab.currentScope();
	    while (root.getOuter() != null) root = root.getOuter();

	    java.util.Deque<java.util.Iterator<rs.etf.pp1.symboltable.concepts.Obj>> itStack = new java.util.ArrayDeque<>();
	    java.util.Deque<String> prefixStack = new java.util.ArrayDeque<>();
	    itStack.push(root.values().iterator());
	    prefixStack.push("");

	    while (!itStack.isEmpty()) {
	        java.util.Iterator<rs.etf.pp1.symboltable.concepts.Obj> it = itStack.peek();
	        String prefix = prefixStack.peek();

	        if (!it.hasNext()) { itStack.pop(); prefixStack.pop(); continue; }

	        rs.etf.pp1.symboltable.concepts.Obj o = it.next();
	        boolean isLast = !it.hasNext();

	        String kind;
	        switch (o.getKind()) {
	            case rs.etf.pp1.symboltable.concepts.Obj.Con:  kind = "Con";  break;
	            case rs.etf.pp1.symboltable.concepts.Obj.Var:  kind = "Var";  break;
	            case rs.etf.pp1.symboltable.concepts.Obj.Type: kind = "Type"; break;
	            case rs.etf.pp1.symboltable.concepts.Obj.Meth: kind = "Meth"; break;
	            case rs.etf.pp1.symboltable.concepts.Obj.Fld:  kind = "Fld";  break;
	            case rs.etf.pp1.symboltable.concepts.Obj.Prog: kind = "Prog"; break;
	            default: kind = "Obj"; break;
	        }

	        rs.etf.pp1.symboltable.concepts.Struct ts = o.getType();
	        String typeStr;
	        if (o.getKind() == rs.etf.pp1.symboltable.concepts.Obj.Var && "this".equalsIgnoreCase(o.getName())) {
	            typeStr = "<this>";
	        } else {
	            int k = ts.getKind();
	            if (k == rs.etf.pp1.symboltable.concepts.Struct.Int)      typeStr = "int";
	            else if (k == rs.etf.pp1.symboltable.concepts.Struct.Char) typeStr = "char";
	            else if (k == rs.etf.pp1.symboltable.concepts.Struct.Bool
	                     || (boolStruct != null && ts.equals(boolStruct))) typeStr = "bool";
	            else if (k == rs.etf.pp1.symboltable.concepts.Struct.None) typeStr = "notype";
	            else if (k == rs.etf.pp1.symboltable.concepts.Struct.Array) {
	                rs.etf.pp1.symboltable.concepts.Struct el = ts.getElemType();
	                int ek = el.getKind();
	                String base;
	                if (ek == rs.etf.pp1.symboltable.concepts.Struct.Int)       base = "int";
	                else if (ek == rs.etf.pp1.symboltable.concepts.Struct.Char)  base = "char";
	                else if (ek == rs.etf.pp1.symboltable.concepts.Struct.Bool
	                         || (boolStruct != null && el.equals(boolStruct)))   base = "bool";
	                else if (ek == rs.etf.pp1.symboltable.concepts.Struct.None)  base = "notype";
	                else if (ek == rs.etf.pp1.symboltable.concepts.Struct.Class) {
	                    base = (setStruct != null && el.equals(setStruct)) ? "set" : "class";
	                } else base = "type(" + ek + ")";
	                typeStr = base + "[]";
	            } else if (k == rs.etf.pp1.symboltable.concepts.Struct.Class) {
	                typeStr = (setStruct != null && ts.equals(setStruct)) ? "set" : "class";
	            } else {
	                typeStr = "type(" + k + ")";
	            }
	        }

	        String branch = isLast ? "`-- " : "+-- ";
	        sb.append(prefix).append(branch)
	          .append(kind).append(' ').append(o.getName())
	          .append(" : ").append(typeStr)
	          .append("    [adr=").append(o.getAdr())
	          .append(", lvl=").append(o.getLevel()).append(']')
	          .append(NL);

	        if (o.getKind() == rs.etf.pp1.symboltable.concepts.Obj.Meth
	         || o.getKind() == rs.etf.pp1.symboltable.concepts.Obj.Prog) {
	            java.util.Collection<rs.etf.pp1.symboltable.concepts.Obj> locals = o.getLocalSymbols();
	            if (!locals.isEmpty()) {
	                itStack.push(locals.iterator());
	                prefixStack.push(prefix + (isLast ? "    " : "|   "));
	            }
	        }
	    }

	    sb.append("===========================================================").append(NL);
	    System.out.print(sb.toString());
	}

}
