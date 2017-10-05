# AST 打印规范

针对本次实验中新添加的5个语言特性，我们特别通过此打印规范文档，说明如何规范地将这些语法结点打印出来。
`TestCases/S1/result`目录下已经给出所有测例的标准输出，其中涉及新特性的部分均符合本文档之规范，
仅当你的输出与之完全一致时，才被视为正确。
对于已存在的各语法结点，请不要做任何修改。

本文假定一层缩进占4个空格。

### 复数

#### 虚数字面量

用形如`{INTEGER}j`方式定义的虚数字面量，打印格式为`imgconst {INTEGER}j`。
如表达式`1+2j`打印为
```
add
    intconst 1
    imgconst 2j
```
注意这里的`+`号视为算术运算中的加法。

#### 复数类型

`complex`类型打印为`comptype`。
如
```
complex z;
```
打印为
```
vardef z comptype
```

#### 取实部

取实部函数`@<expr>`打印为
```
re
    <expr>
```
如
```
y = 1 + @x;
```
打印为
```
assign
    varref y
    add
        intconst 1
        re
            varref a
```

#### 取虚部

与取实部函数类似，取实部函数`$<expr>`打印为
```
im
    <expr>
```
如
```
y = 1 + $x;
```
打印为
```
assign
    varref y
    add
        intconst 1
        im
            varref a
```

#### 类型转换

整数转复数函数`#<expr>`打印为
```
compcast
    <expr>
```
如
```
z = #n;
```
打印为
```
assign
    varref z
    compcast
        varref n
```

#### 复数打印

复数打印语句`PrintComp(<expr1>, ..., <exprN>)`打印为
```
printcomp
    <expr1>
    ...
    <exprN>
```
如
```
PrintComp(z1, z2);
```
打印为
```
printcomp
    varref z1
    varref z2
```


### 条件表达式

形如
```
case (<expr>) {
    <const1>: <expr1>;
    <const2>: <expr2>;
    ...
    <constN>: <exprN>;
    default: <expr0>;
}
```
的条件表达式打印成
```
cond
    <expr>
    cases
        case
            <const1>
            <expr1>
        case
            <const2>
            <expr2>
        ...
        case
            <constN>
            <exprN>
        default
            <expr0>
```
注意在开始打印各条件块之前，先打印标识符`cases`，增加缩进后才开始依次打印各条件块。
对于前 N 个条件块，先打印起始标记`case`，增加缩进后先打印整数`<const>`，再打印对应取值表达式`<expr>`。
对于最后一个缺省块，先打印起始标记`default`，增加缩进后打印默认取值表达式`<expr>`。

如
```
return case (n) {
    1: "Monday";
    2: "Tuesday";
    3: "Wednesday";
    4: "Thursday";
    5: "Friday";
    6: "Saturday";
    7: "Sunday";
    default: "<unknown>";
};
```
打印成
```
return
    cond
        varref n
        cases
            case
                intconst 1
                stringconst "Monday"
            case
                intconst 2
                stringconst "Tuesday"
            case
                intconst 3
                stringconst "Wednesday"
            case
                intconst 4
                stringconst "Thursday"
            case
                intconst 5
                stringconst "Friday"
            case
                intconst 6
                stringconst "Saturday"
            case
                intconst 7
                stringconst "Sunday"
            default
                stringconst "<unknown>"
```

### super 语句

`super` 语句打印成
```
super
```
如
```
super.f(x);
```
打印为
```
call f
    super
    varref x
```

### 拷贝函数

#### dcopy 函数

与取实部函数类似，表达式`dcopy(<expr>)`打印成
```
dcopy
    <expr>
```
如
```
p1 = dcopy(p);
```
打印成
```
assign
    varref p1
    dcopy
        varref p
```

#### scopy 函数

类似地，表达式`scopy(<expr>)`打印成
```
scopy
    <expr>
```
如
```
p1 = scopy(p);
```
打印成
```
assign
    varref p1
    scopy
        varref p
```

### 循环卫士语句

形如
```
do <expr1> : <stmt1> |||
   <expr2> : <stmt2> |||
   ...
   <exprN> : <stmtN> od
```
的循环卫士语句，打印成
```
do
    branches
        branch
            <expr1>
            <stmt1>
        branch
            <expr2>
            <stmt2>
        ...
        branch
            <exprN>
            <stmtN>
```
注意在开始打印各分支之前，先打印标识符`branches`，增加缩进后才开始依次打印各分支。
对于每一个分支，都要先打印块起始标记`branch`，然后增加缩进，并先打印其条件`<expr>`，再打印其执行语句`<stmt>`。

如
```
do
    y < 10: y = y + 1; |||
    x < 10: x = x + 1;
od;
```
打印成
```
do
    branches
        branch
            les
                varref y
                intconst 10
            assign
                varref y
                add
                    varref y
                    intconst 1
        branch
            les
                varref x
                intconst 10
            assign
                varref x
                add
                    varref x
                    intconst 1
```

## 新增表达式优先级

新增的`@`,`$`,`#`,`case`,`super`,`dcopy`,`scopy`均为前缀表达式，可以认为它们都具有最高的优先级，即：

```
1 + @z + 3 = (1 + (@z)) + 3
2 - case (x) {...} = 2 - (case (x) {...})
#@z = #(@z)
```
