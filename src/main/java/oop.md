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
### Method Overriding
In Java, method overriding occurs when a subclass provides a specific implementation of a method that is already defined
in its superclass. When you call an overridden method, the method that gets executed is determined by the object type,
not the reference type.

Note the below points for method overidding:
1. This behaviour is applicable only to instance methods. Static methods cannot be overridden; they can only be hidden.
2. The method signature (name and parameters) must be the same in both the superclass and subclass.
3. The access level of the overriding method in the subclass must be the same or more permissive than that of the method in
   the superclass.
4. The overriding method can throw no exceptions or a subset of the checked exceptions thrown by the overridden method. (meaning
it cannot throw new or broader checked exceptions). It can throw any unchecked exceptions.
5. The return type of the overriding method must be the same or a subtype (covariant return type) of the return type of the
   overridden method.

Note that for private, static, and final methods, overriding is not possible. In such cases, the method in the subclass 
is treated as a new method, not an override.

```java
class A {
    void show() {
        System.out.println("A's show");
    }
}
class B extends A {
    void show() {
        System.out.println("B's show");
    }
} // overrides A.show
public static void main(String[] args) {
    A x = new B();
    x.show();  // B's show (uses B's view, chosen by the object type B)
}
```

### Accessing Overridden Methods
You can access the overridden method in the superclass using the `super` keyword within the subclass. This allows you to
call the superclass version of the method from the subclass.

```java
class A { public int v = 1; }
class B extends A { private int v = 2; } // hides A.v

void print() {
   System.out.println(v);        // B.v (2)
   System.out.println(super.v);  // A.v (1)
}
```
Note however that you cannot access a private method of a superclass from a subclass, even using `super`. It will 
result in a compilation error.

Example: From outside B:
```java
public static void main(String[] args) {
   A a = new B();
   System.out.println(a.v);         // 1  → uses A’s view (type of reference = A)

   B b = new B();
   System.out.println(b.v);         // ❌ compile error: B.v is private
   System.out.println(((A)b).v);    // 1  → upcast to A to access A.v
}
```

### Accessing Non-Existent Methods

Method calls are checked against the declared type of the reference.
If that type (e.g., the superclass) doesn’t declare the method, the call won’t compile—even if the actual object 
(a subclass) provides it.

```java

class Base{
   void methodA(){
      System.out.println("base - MethodA");
   }
}

class Sub extends Base{
   public void methodA(){
      System.out.println("sub - MethodA");
   }
   public void methodB(){
      System.out.println("sub - MethodB");
   }
   public static void main(String args[]){
      Base b=new Sub(); //1
      b.methodA(); //2
      b.methodB(); //3
   }
}

// Output: Compilation error at line 3 because methodB() is not defined in Base class
```

### Method Overloading
Method overloading in Java occurs when multiple methods in the same class have the same name but different parameter
lists (different type, number, or both). The return type can be different, but it is not used to distinguish overloaded methods.

When you call an overloaded method, the method that gets executed is determined at compile time based on the reference type
and the argument types provided in the method call.

When multiple overloaded methods could match the method call, Java uses the most specific method available. If there is
no most specific method (i.e., ambiguity), the code will not compile.

Order of specificity:
1. If the method call parameter type exactly matches one of the overloaded method parameter types, that method is chosen.
2. If there is no exact match, Java looks for a method with a parameter type that can be reached through widening conversion e.g int to long.
3. If there are no methods that can be reached through widening, Java looks for methods that can be reached through boxing conversion e.g int to Integer.
4. If there are still no matches, Java looks for methods that can be reached through varargs e.g method(int... args).
5. If multiple methods are still applicable and none is more specific than the others, a compile-time error occurs due to ambiguity.
6. If null is passed as an argument, Java will choose the most specific method that can accept null. If there are multiple
   methods that can accept null and none is more specific than the others, a compile-time error occurs due to ambiguity.

Note that for widening if we have a method call with a byte argument, the order of preference will be: 
byte -> short -> int -> long -> float -> double (from more specific to less specific).

```java
public class TestClass{
   public void method(Object o){
      System.out.println("Object Version");
   }
   public void method(java.io.FileNotFoundException s){
      System.out.println("java.io.FileNotFoundException Version");
   }
   public void method(java.io.IOException s){
      System.out.println("IOException Version");
   }
   public static void main(String args[]){
      TestClass tc = new TestClass();
      tc.method(null);
   }
}
//Output: java.io.FileNotFoundException Version
```

