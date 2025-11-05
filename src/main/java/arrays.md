
Equals method and == of arrays in Java compare references, not contents. To compare contents, use Arrays.equals() 
or Arrays.deepEquals() for nested arrays.

```java
import java.util.Arrays;
public class ArrayComparison {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};

        // Using == operator (compares references)
        System.out.println("Using == : " + (array1 == array2)); // Output: false

        // Using Arrays.equals() (compares contents)
        System.out.println("Using Arrays.equals(): " + Arrays.equals(array1, array2)); // Output: true

        // For nested arrays
        int[][] nestedArray1 = {{1, 2}, {3, 4}};
        int[][] nestedArray2 = {{1, 2}, {3, 4}};

        // Using Arrays.deepEquals() for nested arrays
        System.out.println("Using Arrays.deepEquals(): " + Arrays.deepEquals(nestedArray1, nestedArray2)); // Output: true
    }
}
```

A clone creates a shallow copy of the array. This means that the new array has its own reference, but the elements inside
the array are references to the same objects as in the original array. 

```java
import java.util.Arrays;
public class ArrayCloneExample {
    public static void main(String[] args) {
        // Original array
        int[] originalArray = {1, 2, 3};

        // Cloning the array
        int[] clonedArray = originalArray.clone();
        
        originalArray[0] == clonedArray[0]; // true, same primitive value
    }
} // Output: true because both refer to the same primitive value
```

###  Comparing Arrays
Comparing elements in arrays have the below scenarios:
1. If the elements are not the same values, then at the point they differ the return value will be 1 or -1 depending on which is greater.
2. If all elements are the same but the lengths differ, then the shorter array is considered less than the longer one.
and the return value will be the difference in lengths.
3. If both arrays are of the same length and all elements are equal, the return value will be 0.

```java
import java.util.Arrays;
public class ArrayComparisonExample {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        int[] array3 = {1, 2, 3, 4};
        int[] array4 = {1, 2, 3};
        int[] array5 = {1, 2, 3, 4, 5};

        // Comparing arrays with different elements
        System.out.println("Comparing array1 and array2: " + Arrays.compare(array1, array2)); // Output: -1

        // Comparing arrays of different lengths
        System.out.println("Comparing array1 and array3: " + Arrays.compare(array1, array3)); // Output: -1

        // Comparing identical arrays
        System.out.println("Comparing array1 and array4: " + Arrays.compare(array1, array4)); // Output: 0

        System.out.println("Comparing array1 and array5: " + Arrays.compare(array1, array5)); // Output: -2
    }
}
```

### Array Mismatch
Arrays.mismatch() method returns the index of the first mismatched element between two arrays. If the arrays are identical,
it returns -1. If the arrays have different lengths but are identical up to the length of the shorter array, it returns 
the length of the shorter array.

```java
import java.util.Arrays;
public class ArrayMismatchExample {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3, 4};
        int[] array2 = {1, 2, 0, 4};
        int[] array3 = {1, 2, 3, 4};
        int[] array4 = {1, 2, 3};

        // Finding mismatch index
        System.out.println("Mismatch index between array1 and array2: " + Arrays.mismatch(array1, array2)); // Output: 2

        // Identical arrays
        System.out.println("Mismatch index between array1 and array3: " + Arrays.mismatch(array1, array3)); // Output: -1

        // Different lengths but identical up to the length of the shorter array
        System.out.println("Mismatch index between array1 and array4: " + Arrays.mismatch(array1, array4)); // Output: 3
    }
}
```

