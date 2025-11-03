### Reverse method
Strings do not have a built-in method to reverse the characters in the string. However, string builder classes 
like `StringBuilder` and `StringBuffer` provide a `reverse()` method that can be used to reverse the characters 
in the string.

### ToString() Method
If a class does not implement toString() method, the object class toString() method is used which returns the package
and class name followed by the at the rate sign (@) and the object's hashcode in hexadecimal.
```java
public class ToStringExample {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        System.out.println(obj); // Output: MyClass@<hashcode>
    }
}
class MyClass {
    // No toString() method implemented
}
```
