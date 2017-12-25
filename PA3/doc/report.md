# Decaf PA3 实验报告

计53 王润基 2015011279

## 实验内容

在给定框架下，补充实现新语法特性的生成中间代码。

## 实验过程

以下对每个语法特性，简述所做的修改（按实现顺序）：

1. Case

   实现`visitCase`，模仿if-else

2. Do

   实现`visitDoStmt`，模仿while

3. Complex

   当做对象存储在堆上，占8bytes空间。

   在Translater中实现`genLoadComplex(Temp,Temp)`函数

   在`visitLiteral`中实现虚数常量加载

   在`visitUnary`中实现@#$运算符

   在`visitBinary`中实现复数加法和乘法

   在`visitAssign`中实现虚数赋值，相当于scopy

   实现`visitPrintComp`，模仿Print

4. Super

   实现`visitSuperExpr`，与this相同

   在`visitCallExpr`的中特判receiver是this/super的情况，直接调取对应类型的虚函数表

5. SCopy

   在Translater中实现`genAlloc`，`genMemcpy`辅助函数

   实现`visitSCopy`

6. DCopy

   给每个类额外生成`dcopy`函数（`genDCopyForClass`），放在虚表第3位，为此还更改了：

   * `Transpass1.visitMethodDef`
   *  `createVTable `
   *  `fillVTableEntries`

   实现`visitDCopy`，直接调用类的dcopy函数：首先分配空间，然后枚举成员变量，对于class类型，调用相应类型的dcopy函数。

   踩过的坑：直接用虚表的地址（而不是值）去调用函数了。由于TAC没有调试功能，因此费了3小时Debug。

7. CheckDivByZero

   实现`genCheckDivByZero`，模仿数组的检查

   在`visitBinary`中增加检查语句

   ​

   ​