### Java evaluation & assignment rules

Left-to-right evaluation
In Java, the operands of an operator and method arguments are evaluated left to right.
For E1 op E2, evaluate E1 first (including any side effects), then E2.

This also applies to assignments: evaluate the left side (to locate the variable/array element/field)
before evaluating the right side.

```java
class InitTest {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        a += (a = 4);
        b = b + (b = 5);
        System.out.println(a + ", " + b);
    }
}
// Output: 14, 25 because: we first evaluate the left side (a or b), then the right side, and finally perform the assignment.
```

### NumberFormatException with underscores in numeric literals
Even though Java allows you to use underscores while writing numeric values in Java code, the parseXXX and valueOf 
methods of various wrapper classes (such as Integer.parseInt, Integer.valueOf, and Float.parseFloat) do not consider 
a String containing _ as a valid value. So, Integer.parseInt("1_00") will throw a NumberFormatException.
```java
public class UnderscoreInNumericString {
    public static void main(String[] args) {
        String numericString = "1_00";
        try {
            int value = Integer.parseInt(numericString);
            System.out.println("Parsed value: " + value);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }
    }
}
// Output: NumberFormatException: For input string: "1_00"
```

### Caching of Wrapper Objects
Java caches certain wrapper objects for performance optimization. For example, Integer objects representing values from -128 to 127 are cached.
```java
public class WrapperCaching {
    public static void main(String[] args) {
        Integer a = 100;
        Integer b = 100;
        Integer c = 200;
        Integer d = 200;

        System.out.println(a == b); // true, because values between -128 and 127 are cached
        System.out.println(c == d); // false, because values outside this range are not cached
    }
}
```
With valueOf() method, the same caching behavior applies:
```java
public class WrapperCachingValueOf {
    public static void main(String[] args) {
        Integer a = Integer.valueOf(100);
        Integer b = Integer.valueOf(100);
        Integer c = Integer.valueOf(200);
        Integer d = Integer.valueOf(200);

        System.out.println(a == b); // true, because values between -128 and 127 are cached
        System.out.println(c == d); // false, because values outside this range are not cached
    }
}
```
If however, you create Integer objects using the new keyword, caching does not apply:
```java
public class WrapperCachingNew {
    public static void main(String[] args) {
        Integer a = new Integer(100);
        Integer b = new Integer(100);

        System.out.println(a == b); // false, because new always creates a new object
    }
}
```
Note that the following will not compile though:
```java
Byte b = 1; Integer i = 1; 
b == i; //Invalid because both operands are of different class.
```

