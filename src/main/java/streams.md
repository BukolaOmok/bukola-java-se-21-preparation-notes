### Reduce in Java Streams
Reduce has three overloaded variants: 
1. `reduce(BinaryOperator<T> accumulator)`: This variant takes a binary operator that combines two elements of the stream and returns an `Optional<T>` containing the reduced value. If the stream is empty, it returns an empty `Optional`.
2. `reduce(T identity, BinaryOperator<T> accumulator)`: This variant takes an identity value and a binary operator. It returns the reduced value directly (not wrapped in an `Optional`). If the stream is empty, it returns the identity value.
3. `reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)`: This variant is used for parallel streams. It takes an identity value, a function that combines an accumulator with an element of the stream, and a combiner function that combines two accumulators. It returns the reduced value of type `U`.

The first one needs to return Optional because if the stream is empty, there is no value to return, so we use .get() on the Optional to retrieve the value safely.
The second and third variants have an identity value to return in case of an empty stream, so they can return the value directly without wrapping it in an Optional.


### Collector.toMap throws IllegalStateException on duplicate keys
If you try to store a key that already exists in a Map using collector.toMap, an IllegalStateException is thrown at
runtime. To get around this, you can provide a merge function as the third argument to the toMap collector.

On a normal Map, if you try to put a key that already exists, the old value is replaced by the new value.

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class CollectorToMapExample {
    public static void main(String[] args) {
        List<String> list = List.of("apple", "banana", "apple");

        try {
            Map<String, Integer> map = list.stream()
                    .collect(Collectors.toMap(
                            fruit -> fruit,
                            fruit -> 1
                    ));
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: " + e.getMessage());
        }
    }
}
```

### Optional.of vs Optional.ofNullable
Optional.of method throws NullPointerException if you pass a null value to it, whereas Optional.ofNullable method 
can handle null values without throwing an exception. If you use a .get() on an Optional that is empty, it will throw NoSuchElementException.
```java
import java.util.Optional;
public class OptionalExample {
    public static void main(String[] args) {
        try {
            Optional<String> optional1 = Optional.of(null); // Throws NullPointerException
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getMessage());
        }

        Optional<String> optional2 = Optional.ofNullable(null); // Does not throw an exception
        try {
            String value = optional2.get(); // Throws NoSuchElementException
        } catch (java.util.NoSuchElementException e) {
            System.out.println("NoSuchElementException: " + e.getMessage());
        }
    }
}
```
### Joining Collector with Prefix and Suffix
If you see joining collector with 3 arguements, the first is delimiter, second is prefix, third is suffix.
```java
import java.util.List;
import java.util.stream.Collectors;
public class JoiningCollectorExample {
    public static void main(String[] args) {
        List<String> list = List.of("apple", "banana", "cherry");

        String result = list.stream()
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(result); // Output: [apple, banana, cherry]
    }
}
```

### Primitive Specialised Streams
All primitive specialised versions of streams helps to avoid the additional overhead of boxing and unboxing that occurs when using the generic Stream<T> with wrapper classes like Integer, Double, etc.
They take what is in front of them as argument but they return any type. e.g IntStream takes int as argument but can return long, double or any object type when used with mapToLong, mapToDouble or mapToObj methods respectively.


### Difference between takeWhile and dropWhile in Java Streams
Take while and drop while are short-circuiting operations that work based on a predicate. Both operations process elements of the stream until a certain condition is met, but they behave differently:
1. takeWhile(Predicate<? super T> predicate): This operation returns a stream consisting of the longest prefix of elements that match the given predicate. 
It processes elements from the beginning of the stream and includes them in the resulting stream as long as they satisfy the predicate. 
Once an element is encountered that does not match the predicate, the operation stops processing further elements, and those elements are excluded from the result.
2. dropWhile(Predicate<? super T> predicate): This operation returns a stream consisting of the remaining elements after dropping the longest prefix of elements that match the given predicate.
It processes elements from the beginning of the stream and skips them as long as they satisfy the predicate. 
Once an element is encountered that does not match the predicate, the operation stops skipping further elements, and all subsequent elements are included in the result.

The main difference between takeWhile and dropWhile is that takeWhile includes elements that match the predicate until the first non-matching element is found,
while dropWhile excludes elements that match the predicate until the first non-matching element is found.

```java
import java.util.List;
import java.util.stream.Collectors;
public class TakeDropWhileExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(2, 4, 6, 7, 8, 10);

        // Using takeWhile to get even numbers until the first odd number
        List<Integer> taken = numbers.stream()
                .takeWhile(n -> n % 2 == 0)
                .toList();
        System.out.println("Taken: " + taken); // Output: [2, 4, 6]

        // Using dropWhile to skip even numbers until the first odd number
        List<Integer> dropped = numbers.stream()
                .dropWhile(n -> n % 2 == 0)
                .toList();
        System.out.println("Dropped: " + dropped); // Output: [7, 8, 10]
    }
}
```

If a sorted() method is used with an object that does not implement Comparable interface, it will throw a ClassCastException at runtime.
```java
import java.util.List;
import java.util.stream.Collectors;
public class SortedExample {
    public static void main(String[] args) {
        List<NonComparable> list = List.of(new NonComparable(3), new NonComparable(1), new NonComparable(2));

        try {
            List<NonComparable> sortedList = list.stream()
                    .sorted() // This will throw ClassCastException
                    .collect(Collectors.toList());
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }
    }
}
class NonComparable {
    int value;
    NonComparable(int value) {
        this.value = value;
    }
} // It will throw a class cast exception at runtime and not a compilation error.
```

### Difference between anyMatch, allMatch and noneMatch in Java Streams
anyMatch, allMatch and nonMatch require a predicate as argument and return boolean values.
```java
import java.util.List;
import java.util.function.Predicate;
public class MatchExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(2, 4, 6, 8, 10);

        Predicate<Integer> isEven = n -> n % 2 == 0;

        boolean anyMatchResult = numbers.stream().anyMatch(isEven);
        System.out.println("Any Match (is even): " + anyMatchResult); // true

        boolean allMatchResult = numbers.stream().allMatch(isEven);
        System.out.println("All Match (is even): " + allMatchResult); // true

        boolean noneMatchResult = numbers.stream().noneMatch(n -> n > 10);
        System.out.println("None Match (greater than 10): " + noneMatchResult); // true
    }
}
```