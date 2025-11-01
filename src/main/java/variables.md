### Shadowing & definite assignment

If a local variable has the same name as a field (instance or static), the local variable shadows the field.
The closest scope wins. A local variable named x shadows any field named x (instance or static) in the same
class or superclasses. So an unqualified x inside the method refers to the local one.

Accessing the shadowed field:
Instance field: use this.x (or super.x for a superclass field).
Static field: use ClassName.x (preferred). this.x won’t select a static; ClassName.x bypasses the local shadowing.

Local Variable: 
```java
class ScopeTest {
    static int x = 5;

    public static void main(String[] args) {
        int x = (x = 3) * 4;  // 1       
        System.out.println(x);
    }
} 
```
Static Variable:
```java
class C {
static int x = 99;

    void demo() {
        int x = (x = 3) * 4;   // local x → 12
        System.out.println(C.x);  // 99  (explicitly use the class name)
    }

}
```

### Definite assignment (DA) for locals:

A local variable must be definitely assigned before it is read.

❌ int x = x; // read before assignment → compile error
❌ int x = x * 4; // same reason
✅ int x = (x = 3) * 4; // allowed because the inner assignment assigns x first; the assignment expression evaluates to
3, then 3 * 4 = 12 is stored into x.

var + self-reference:
You cannot use var if the variable appears in its own initializer (self-reference).

❌ var x = (x = 3) * 4; // illegal self reference (type inference can’t proceed)
