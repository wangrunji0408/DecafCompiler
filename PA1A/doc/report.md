# Decaf PA1A 实验报告

计53 王润基 2015011279

## 实验内容

在Decaf语言编译器框架的基础上，增加如下语言特性

1. 整复数类型
   1. complex类型关键字
   2. 虚数常量
   3. @$#表达式
   4. PrintComp打印复数语句
2. case表达式
3. super表达式
4. 深浅对象复制（dcopy/scopy表达式）
5. 串行循环卫士语句

## 实验过程

添加每一个语言特性的基本流程：

分别查看`Lexer.l` `Parser.y` `BaseLexer.java` `BaseParser.java` `Tree.java`，找到相似的语法元素，模仿复制之。

* 对于关键字：

  * 在lexer中添加识别规则
  * 在parer中添加token
  * 在`Tree.java`中定义相应常量

  例如：complex

  ```java
  // Lexer.l
  "complex" { return keyword(Parser.COMPLEX); }
  // Parser.y
  %token COMPLEX
  // Tree.java
  public static final int COMPLEX = ... + 1;
  ```

* 对于新的字面量类型：

  * 还需在`BaseLexer.java`中添加相应的类

  例如：img常量

  ```java
  // BaseLexer.java
  protected int imgConst(String ival) {
    try {
      setSemantic(getLocation(), SemValue.createLiteral(
        Tree.IMG, Integer.decode(ival.substring(0, ival.length()-1))));
    } catch (NumberFormatException e) {
      Driver.getDriver().issueError(
        new IntTooLargeError(getLocation(), ival));
    }
    return Parser.LITERAL;
  }
  // Lexer.l
  {IMG} { return imgConst(yytext()); 
  ```

* 对于新的符号：

  * 在lexer中加入`SIMPLE_OPERATOR`
  * 在parser中定义优先级和结合性
  * 在`Tree.java`中定义类型常量

  例如：@#$符号

  ```java
  // Lexer.l
  SIMPLE_OPERATOR		= (...|"@"|"$"|"#")
  // Parser.y
  %token '@' '$' '#'
  %nonassoc '@' '$' '#'
  // Tree.java
  public static final int RE = NOT + 1;
  public static final int IM = RE + 1;
  public static final int COMPCAST = IM + 1;
  ```

* 对于新的表达式：

  * 先添加包含的关键字和符号

  * 在`Tree.java`定义新的AST节点类型

    同时添加visitor函数，构造函数，按输出格式实现print函数

  * 在parser中写出文法并构造AST节点

  简单例如：一元符号前缀表达式

  ```java
  // Parser.y
  Expr ...
    |	'@' Expr
    {$$.expr = new Tree.Unary(Tree.RE, $2.expr, $1.loc);}
  ```

  复杂例如：case表达式

  ```java
  // Tree.java
  public static class Case extends Expr {...}
  public static class ACase extends Expr {...}
  public static class Default extends Expr {...}
  // Parser.y
  ACaseExpr       :   Constant ':' Expr ';' {...};
  DefaultExpr     :   DEFAULT ':' Expr ';' {...};
  ACaseExprList   :	ACaseExprList ACaseExpr {...}
                  |	/* empty */ {...};
  Expr ... 
    | CASE '(' Expr ')' '{' ACaseExprList DefaultExpr '}'
  ```


## 新增测试

* `q1-complex-priority`：关于@$#优先级的测试
* `q2-case-nonconst`：关于case表达式只接受常数的测试
* `q2-case-default_order`：case表达式中default不在最后出现（请问是否有这个约束？）


