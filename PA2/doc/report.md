# Decaf PA2 实验报告

计53 王润基 2015011279

## 实验内容

在给定框架下，补充实现新语法特性的：

1. 构造符号表
2. 语义检查

## 实验过程

以下对每个语法特性，简述所做的修改：

1. Complex

   * `util/Complex`：复数类
   * `BaseType`：新增`COMPLEX`
   * `BuildSym`：`visitTypeIdent()`新增`COMPLEX`
   * `TypeCheck`：
     * `visitUnary()`：增加@#$运算符的处理
     * `visitLiteral()`：将`IMG`字面量标记为`COMPLEX`类型，同时将value（一个int表示虚部）转化为Complex类型。
     * `checkBinaryOp()`：对加法和乘法，在原有基础上判断两个操作数是否是`INT`/`COMPLEX`，若是，在`INT`外面包一层转换到`COMPLEX`的函数。
   * 新增错误类：
     * `BadPrintCompArgError`

2. Case

   * `TypeCheck`：

     实现`visitCase()`，`visitACase()`，`visitDefault()`

     主要工作在`visitCase()`中判断以下三种错误

   * 新增错误类：

     * `CaseConditionNotUniqueError`
     * `IncompatibleCaseKeyTypeError`
     * `InconsistentCaseValueTypeError`

3. Super

   * `TypeCheck`：
     * `visitSuperExpr()`：照搬this
     * `checkCallExpr()`：当检测到super时，修改`receiverType`为父类
     * `visitCallExpr()`：在最后检测父类不存在的错误
   * 新增错误类：
     * `SuperInStaticFuncError`
     * `SuperMemberVarNotSupportedError`
     * `SuperNotExistError`

4. Copy

   * `TypeCheck`：
     * `visitDCopy()`/`visitSCopy()`：检测类型错误
     * `visitAssign()`：处理copy后紧接着赋值，出现类型不匹配的情况
   * 新增错误类：
     * `CopyAssignTypeError`
     * `CopyExprIsNotClassTypeError`

5. Do

   * `BuildSym`：
     * `visitDoStmt()`递归处理所有子语句
   * `TypeCheck`：
     * `visitDoStmt()`：push breaks
     * `visitDoBranch()`：处理条件类型错误
   * 新增错误类：
     * `DoCondTypeError`