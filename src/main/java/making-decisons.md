### Reserved Keywords as Labels (Compilation Error)

When looping, you can make use of a label to skip or break out of the loop. Please not that not all case labels are
valid. Using a reserved keyword as a label will result in a compilation error.

```java
public class TestClass {
    public static void main(String[] args) {
        for :for (var i = 0; i < 10; i++) {
            for (var j = 0; j < 10; j++) {
                if (i + j > 10) break for ;
            }
            System.out.println("hello");
        }
    }
}
// Output: compilation error because 'for' is a reserved keyword
```
### Labeled Break Out of Scope (Compilation Error)
When using labels, you have to make sure it is written on the same line as the loop statement not on another loop,
otherwise it will also
result in a compilation error.

```java
public void method1(int i, double d) {
    int j = (i * 30 - 2) / 100;
    POINT1:
    for (; j < 10; j++) {
        var flag = false;
        while (!flag) {
            if (d > 0.5) break POINT1;
        }
    }
    while (j > 0) {
        System.out.println(j--);
        if (j == 4) break POINT1;
    }
}
// Output: A labeled break must target a label of an enclosing statement. The second appears after the for has finished, 
// so POINT1 no longer encloses that break. The label is out of scope → compile-time error: undefined label: POINT1.
```

### Infinite Loops
If a loop is infinite, the program will continue to run and never terminate unless externally interrupted. No exception
is thrown for infinite loops in Java.
