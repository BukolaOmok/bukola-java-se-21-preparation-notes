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

When switching with enums, you cannot use the enum name as a case statement. This will result in a compilation error.

```java
enum Color {
    RED, GREEN, BLUE
}

public class TestClass {
    public static void main(String[] args) {
        Color color = Color.RED;
        switch (color) {
            case Color.RED: // Compilation error: 'Color.RED' is not a constant expression
                System.out.println("Red color");
                break;
            case Color.GREEN:
                System.out.println("Green color");
                break;
            case Color.BLUE:
                System.out.println("Blue color");
                break;
        }
    }
}
// Output: compilation error because 'Color.RED' is not a constant expression
```

Additionally, if you do not se a case: and there are just labels, they are not considered as cases and if a caseless
label is called, it will print nothing. This is for enums as well as for other types.

```java
import java.time.LocalDate;

import static java.time.DayOfWeek.*;

public class TestClass {
    public static void main(String[] args) {
        var day = LocalDate.now().with(FRIDAY).getDayOfWeek();
        switch (day) {
            case MONDAY:
                TUESDAY:
                WEDNESDAY:
                THURSDAY:
                FRIDAY:
                System.out.println("working");
            case SATURDAY:
                SUNDAY:
                System.out.println("off");
        }
    }
} // It prints nothing because there are no case statements, only labels.
```

### Combining Other Case Labels with Default Label (Compilation Error)
For regular switch statements or expressions, other case label cannot be combined with default label. This will cause compilation error. Only in 
pattern matching switch statements, a null case label can be combined with default label.

```java
public class TestClass {
        public int switchTest(byte x){    
            return switch(x){ 
            case 'b', 'c' -> 10;       
            case -2 -> 20;           
            case 80, default  -> 30;    
            }; 
        }
}
```
