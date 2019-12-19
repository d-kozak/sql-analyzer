# Scala Interview Coding Challenge
Implement a simple parser using parser combinators. The parser should parse the following
strings:
```sql
SELECT a FROM t
SELECT a,b FROM t,d
SELECT a,b, c FROM t, d ,e
```
The result should be an Abstract Syntax Tree of some form.

Nice to have:
Process the AST and return the number of columns mentioned in the SELECT clause.

You can use any library you want or get inspired here:
https://dzone.com/articles/getting-started-with-scala-parser-combinators
Please send back a compressed file including:
- Scala source files
- Example output of parser (AST) taken from REPL as output.txt file