### Exception Handling when exceptions are thrown in catch blocks

When exceptions are thrown in catch blocks, the behavior of try-catch-finally constructs in Java is as follows:
1. Catch selection is based on the exception from the try block; only one matching catch runs (order matters;
   put more specific first).
2. An exception thrown inside a catch is not handled by the other catch blocks of the same try.
3. If you need to handle it, nest another try–catch inside that catch.
4. The finally block runs regardless of how you exit the try/catch (normal, return, or throw), except for VM shutdown
   (System.exit, crash).
5. Code after the try–catch–finally executes only if no uncaught exception escapes.

```java
public class TestClass {
    public static void main(String args[]) {
        try {
            m1();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("1");
            throw new NullPointerException();
        } catch (NullPointerException e) {
            System.out.println("2");
            return;
        } catch (Exception e) {
            System.out.println("3");
        } finally {
            System.out.println("4");
        }
        System.out.println("END");
    }

    static void m1() {
        System.out.println("m1 Starts");
        throw new IndexOutOfBoundsException("Big Bang ");
    }
}
// The null pointer exception thrown in the first catch is not caught by the second catch block. It propagates out of main,
// and the finally block runs before that happens.
```