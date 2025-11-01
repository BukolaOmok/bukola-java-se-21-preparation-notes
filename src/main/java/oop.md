### Field Hiding
In java, fields are hidden when a subclass defines a field with the same name as a field in its superclass.
This is known as field hiding. When you access a field, the field that gets accessed is determined by the reference
type, not the object type.

Note that this behaviour is applicable to both static and instance fields.

```java
class A {
    int v = 1;
}

class B extends A {
    int v = 2;
} // hides A.v

public static void main(String[] args) {
    A x = new B();
    System.out.println(x.v);      // 1  (uses A's view, chosen by the type A)
    System.out.println(((B)x).v); // 2  (uses B's view, chosen by the type B)
}
``` 
### Inherited Static Fields
If the sub class does not define a field with the same name, the field from the superclass is accessible through the
subclass reference. This does not trigger the initialization of the subclass.

Note that only the static block in the superclass is executed, as the subclass does not have its own static fields or static
blocks to trigger its initialization.

```java
class Super {
    static String ID = "QBANK";
}

class Sub extends Super {
    static {
        System.out.print("In Sub");
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println(Sub.ID);
    }
}
// Output: QBANK
```

### Inherited Instance Fields
If the subclass does not define a field with the same name, the field from the superclass is accessible through the
subclass reference. This triggers the initialization of the subclass unlike static fields.

The order of initialization is as follows:
1. The superclass static fields and static blocks are executed first (if not already initialized).
2. The subclass static fields and static blocks are executed next (if not already initialized).
3. The superclass instance fields and instance initializers/constructor are executed.
4. The subclass instance fields and instance initializers/constructor are executed.

```java
class Super {
    String ID = "QBANK";
}
class Sub extends Super {
    Sub() {
        System.out.print("In Sub");
    }
}
public class Test {
    public static void main(String[] args) {
        System.out.println(new Sub().ID);
    }
}
// Output: In SubQBANK
```