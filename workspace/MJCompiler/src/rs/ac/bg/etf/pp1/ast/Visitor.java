// generated with ast extension for cup
// version 0.8
// 15/8/2024 2:29:27


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(FormParsOpt FormParsOpt);
    public void visit(Relop Relop);
    public void visit(MulopFactorList MulopFactorList);
    public void visit(ConstDeclSuffix ConstDeclSuffix);
    public void visit(VarDeclElems VarDeclElems);
    public void visit(VarOrConstDecl VarOrConstDecl);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(FormParsList FormParsList);
    public void visit(Condition Condition);
    public void visit(AndCondFactList AndCondFactList);
    public void visit(BracketsOpt BracketsOpt);
    public void visit(Label Label);
    public void visit(VarOrConstDeclList VarOrConstDeclList);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(OrCondTermList OrCondTermList);
    public void visit(ActPars ActPars);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(TypeOrVoid TypeOrVoid);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Const Const);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(VarDeclSuffix VarDeclSuffix);
    public void visit(StaticInitializer StaticInitializer);
    public void visit(CondFact CondFact);
    public void visit(CommaExprList CommaExprList);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(AddopTermList AddopTermList);
    public void visit(Modop Modop);
    public void visit(Divop Divop);
    public void visit(Mulop1 Mulop1);
    public void visit(Subop Subop);
    public void visit(AddopPlus AddopPlus);
    public void visit(LessThanOrEqualop LessThanOrEqualop);
    public void visit(LessThanop LessThanop);
    public void visit(GreaterThanOrEqualop GreaterThanOrEqualop);
    public void visit(GreaterThanop GreaterThanop);
    public void visit(NotEqualop NotEqualop);
    public void visit(Equalop Equalop);
    public void visit(Assignop Assignop);
    public void visit(LabelClass LabelClass);
    public void visit(DesignName DesignName);
    public void visit(DesignatorMatrix DesignatorMatrix);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(Designator1 Designator1);
    public void visit(Empty Empty);
    public void visit(Factor6 Factor6);
    public void visit(Factor5 Factor5);
    public void visit(Factor4 Factor4);
    public void visit(Factor3 Factor3);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(Factor2 Factor2);
    public void visit(Factor1 Factor1);
    public void visit(NoMulopFactorList NoMulopFactorList);
    public void visit(MulopFactorList1 MulopFactorList1);
    public void visit(Term2 Term2);
    public void visit(Term1 Term1);
    public void visit(NoAddopTermList NoAddopTermList);
    public void visit(AddopTermList1 AddopTermList1);
    public void visit(ExprMultiple ExprMultiple);
    public void visit(ExprMinus ExprMinus);
    public void visit(ExprPlus ExprPlus);
    public void visit(CondFact2 CondFact2);
    public void visit(CondFact1 CondFact1);
    public void visit(NoAndCondFactList NoAndCondFactList);
    public void visit(AndCondFactList1 AndCondFactList1);
    public void visit(CondTerm1 CondTerm1);
    public void visit(NoOrCondTermList NoOrCondTermList);
    public void visit(OrCondTermList1 OrCondTermList1);
    public void visit(Condition1 Condition1);
    public void visit(NoCommaExprList NoCommaExprList);
    public void visit(CommaExprList1 CommaExprList1);
    public void visit(NoActPars NoActPars);
    public void visit(ActPars1 ActPars1);
    public void visit(DesignatorStatementError DesignatorStatementError);
    public void visit(DesignatorStatementDec DesignatorStatementDec);
    public void visit(DesignatorStatementInc DesignatorStatementInc);
    public void visit(DesignatorStatement2 DesignatorStatement2);
    public void visit(DesignatorStatement1 DesignatorStatement1);
    public void visit(NoStatementList NoStatementList);
    public void visit(StatementList1 StatementList1);
    public void visit(Statement2 Statement2);
    public void visit(StatementPrintExprWithNum StatementPrintExprWithNum);
    public void visit(StatementPrintExpr StatementPrintExpr);
    public void visit(StatementRead StatementRead);
    public void visit(StatementReturn StatementReturn);
    public void visit(StatementReturnExpr StatementReturnExpr);
    public void visit(StatementContinue StatementContinue);
    public void visit(StatementBreak StatementBreak);
    public void visit(StatementIf StatementIf);
    public void visit(Statement1 Statement1);
    public void visit(Type Type);
    public void visit(NoFormParsList NoFormParsList);
    public void visit(FormParsList2 FormParsList2);
    public void visit(FormParsList1 FormParsList1);
    public void visit(FormPars2 FormPars2);
    public void visit(FormPars1 FormPars1);
    public void visit(TypeOrVoidVoid TypeOrVoidVoid);
    public void visit(TypeOrVoidType TypeOrVoidType);
    public void visit(NoFormParsOpt NoFormParsOpt);
    public void visit(FormParsOpt1 FormParsOpt1);
    public void visit(NoMethodDeclList NoMethodDeclList);
    public void visit(MethodDeclList1 MethodDeclList1);
    public void visit(MethodTypeNameVoid MethodTypeNameVoid);
    public void visit(MethodDecl MethodDecl);
    public void visit(StaticInitializer1 StaticInitializer1);
    public void visit(NoVarDeclSuffix NoVarDeclSuffix);
    public void visit(VarDeclSuffix1 VarDeclSuffix1);
    public void visit(VarDeclElemsMatrix VarDeclElemsMatrix);
    public void visit(VarDeclElemsArray VarDeclElemsArray);
    public void visit(VarDeclElems1 VarDeclElems1);
    public void visit(VarDeclElemsMatrixMultiple VarDeclElemsMatrixMultiple);
    public void visit(VarDeclElemsArrayMultiple VarDeclElemsArrayMultiple);
    public void visit(VarDeclElems1Multiple VarDeclElems1Multiple);
    public void visit(NoBracketsOpt NoBracketsOpt);
    public void visit(BracketsOptMatrix BracketsOptMatrix);
    public void visit(BracketsOptArray BracketsOptArray);
    public void visit(VarDeclErrorSemi VarDeclErrorSemi);
    public void visit(VarDeclErrorComma VarDeclErrorComma);
    public void visit(VarDecl1 VarDecl1);
    public void visit(NoVarDeclList NoVarDeclList);
    public void visit(VarDeclList1 VarDeclList1);
    public void visit(ConstBool ConstBool);
    public void visit(ConstChar ConstChar);
    public void visit(ConstNum ConstNum);
    public void visit(ConstDeclSuffixBool2 ConstDeclSuffixBool2);
    public void visit(ConstDeclSuffixBool1 ConstDeclSuffixBool1);
    public void visit(ConstDeclSuffixChar2 ConstDeclSuffixChar2);
    public void visit(ConstDeclSuffixChar1 ConstDeclSuffixChar1);
    public void visit(ConstDeclSuffixNum2 ConstDeclSuffixNum2);
    public void visit(ConstDeclSuffixNum1 ConstDeclSuffixNum1);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoVarOrConstDeclList NoVarOrConstDeclList);
    public void visit(VarOrConstDeclList1 VarOrConstDeclList1);
    public void visit(VarOrConstDeclConst VarOrConstDeclConst);
    public void visit(VarOrConstDeclVar VarOrConstDeclVar);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
