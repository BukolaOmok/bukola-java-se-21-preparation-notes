### Parameter Passing by value
java is a pass by value language. When you pass an object to a method, you are passing the reference to that object by
value.

```java
public class TestClass {
    public static Integer wiggler(Integer x) {
        Integer y = x + 10;
        x++;
        System.out.println(x);
        return y;
    }

    public static void main(String[] args) {
        Integer dataWrapper = Integer.valueOf("5", 16);
        Integer value = wiggler(dataWrapper);
        System.out.println(dataWrapper + value);
    }
}
// Output: 6 20
```
Reassigning the parameter (x++ rebinds x to a new Integer(6)) does not affect the caller’s variable.
If the object were mutable, changing its state via the reference would be visible to the caller—but Integer is
immutable, so you can’t mutate it.

