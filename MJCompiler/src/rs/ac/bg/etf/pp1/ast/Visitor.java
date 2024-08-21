// generated with ast extension for cup
// version 0.8
// 21/7/2024 17:8:28


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(DeclarationList DeclarationList);
    public void visit(ActParsOptional ActParsOptional);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(Mulop Mulop);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(Relop Relop);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(FinalVarDeclaration FinalVarDeclaration);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(MethodReturnTypeAndName MethodReturnTypeAndName);
    public void visit(FormalParam FormalParam);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(FormalParams FormalParams);
    public void visit(ActPars ActPars);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ConstAssignList ConstAssignList);
    public void visit(NewFactor NewFactor);
    public void visit(ConstAssign ConstAssign);
    public void visit(Statement Statement);
    public void visit(Type Type);
    public void visit(CondFact CondFact);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(ModOperator ModOperator);
    public void visit(DivOperator DivOperator);
    public void visit(MulOperator MulOperator);
    public void visit(MinusOperator MinusOperator);
    public void visit(PlusOperator PlusOperator);
    public void visit(LessEqualOperator LessEqualOperator);
    public void visit(LessOperator LessOperator);
    public void visit(GreaterEqualOperator GreaterEqualOperator);
    public void visit(GreaterOperator GreaterOperator);
    public void visit(NotEqualOperator NotEqualOperator);
    public void visit(EqualOperator EqualOperator);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(CondFactMultiple CondFactMultiple);
    public void visit(CondTermAnd CondTermAnd);
    public void visit(CondTermFactor CondTermFactor);
    public void visit(ConditionOr ConditionOr);
    public void visit(ConditionCondTerm ConditionCondTerm);
    public void visit(StaticInitializer StaticInitializer);
    public void visit(ActParsList ActParsList);
    public void visit(SingleActParsExpr SingleActParsExpr);
    public void visit(NoActPars NoActPars);
    public void visit(ActParsExist ActParsExist);
    public void visit(ExprFactor ExprFactor);
    public void visit(NewFactorMethod NewFactorMethod);
    public void visit(NewFactorArray NewFactorArray);
    public void visit(DesignatorFactorMethod DesignatorFactorMethod);
    public void visit(DesignatorFactorDesignator DesignatorFactorDesignator);
    public void visit(FactorExprFactor FactorExprFactor);
    public void visit(FactorNewFactor FactorNewFactor);
    public void visit(FactorDesignatorFactor FactorDesignatorFactor);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNumber FactorNumber);
    public void visit(TermMul TermMul);
    public void visit(TermFactor TermFactor);
    public void visit(ExprAddTerm ExprAddTerm);
    public void visit(ExprTerm ExprTerm);
    public void visit(ExprMinusTerm ExprMinusTerm);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorDotAccess DesignatorDotAccess);
    public void visit(DesignatorIdent DesignatorIdent);
    public void visit(DesignatorScopeResolution DesignatorScopeResolution);
    public void visit(DesignatorStatementErrorSemi DesignatorStatementErrorSemi);
    public void visit(DesignatorStatementDec DesignatorStatementDec);
    public void visit(DesignatorStatementInc DesignatorStatementInc);
    public void visit(DesignatorStatementMethod DesignatorStatementMethod);
    public void visit(DesignatorStatementAssign DesignatorStatementAssign);
    public void visit(StatementStatementList StatementStatementList);
    public void visit(StatementPrintExprNumber StatementPrintExprNumber);
    public void visit(StatementPrintExpr StatementPrintExpr);
    public void visit(StatementRead StatementRead);
    public void visit(StatementReturnWithExpr StatementReturnWithExpr);
    public void visit(StatementReturn StatementReturn);
    public void visit(StatementContinue StatementContinue);
    public void visit(StatementBreak StatementBreak);
    public void visit(StatementIfElse StatementIfElse);
    public void visit(StatementIf StatementIf);
    public void visit(StatementDesignatorStatement StatementDesignatorStatement);
    public void visit(StatementListDoesntExist StatementListDoesntExist);
    public void visit(StatementListExist StatementListExist);
    public void visit(ArrayFormalParam ArrayFormalParam);
    public void visit(VariableFormalParam VariableFormalParam);
    public void visit(SingleFormalParam SingleFormalParam);
    public void visit(FormalParamsList FormalParamsList);
    public void visit(NoFormalParams NoFormalParams);
    public void visit(FormParsExist FormParsExist);
    public void visit(MethodReturnTypeNoVoid MethodReturnTypeNoVoid);
    public void visit(MethodReturnTypeVoid MethodReturnTypeVoid);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDeclarationList NoMethodDeclarationList);
    public void visit(MethodDeclarationList MethodDeclarationList);
    public void visit(TypeName TypeName);
    public void visit(ScopeResolution ScopeResolution);
    public void visit(NoVarDeclarationList NoVarDeclarationList);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(VarDeclErrorComma VarDeclErrorComma);
    public void visit(VarDeclNextArray VarDeclNextArray);
    public void visit(VarDeclNextElem VarDeclNextElem);
    public void visit(VarDeclErrorSemi VarDeclErrorSemi);
    public void visit(VarDeclArrayFinal VarDeclArrayFinal);
    public void visit(VarDeclFinalElem VarDeclFinalElem);
    public void visit(SingleVarDeclaration SingleVarDeclaration);
    public void visit(MultipleVarDeclaration MultipleVarDeclaration);
    public void visit(VarDeclType VarDeclType);
    public void visit(VarDecl VarDecl);
    public void visit(ConstName ConstName);
    public void visit(NoConstAssignmentList NoConstAssignmentList);
    public void visit(ConstAssignmentList ConstAssignmentList);
    public void visit(BoolConstValue BoolConstValue);
    public void visit(CharConstValue CharConstValue);
    public void visit(NumberConstValue NumberConstValue);
    public void visit(ConstTypeAndNameDecl ConstTypeAndNameDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoDeclarationList NoDeclarationList);
    public void visit(DeclarationListVar DeclarationListVar);
    public void visit(DeclarationListConst DeclarationListConst);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
