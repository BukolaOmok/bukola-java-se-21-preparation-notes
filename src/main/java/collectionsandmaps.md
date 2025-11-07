### Navigable Set
A navigable set is a subtype of a sorted set that provides additional methods for navigating the set based on the natural 
ordering of its elements or a specified comparator.

Here are some key features of a navigable set:
1. Navigation Methods: A navigable set provides methods such as `lower()`, `floor()`, `ceiling()`, and `higher()` to retrieve elements based on their order. For example, `lower(e)` returns the greatest element less than `e`, while `higher(e)` returns the least element greater than `e`.
2. Descending Order: A navigable set can be viewed in descending order using the `descendingSet()` method, which returns a view of the set with elements in reverse order.
3. Subsets: You can create subsets of a navigable set using methods like `subSet()`, `headSet()`, and `tailSet()`, which allow you to specify ranges of elements.

//Add example

### Treeset throws UnsupportedOperationException on addFirst/addLast of SequencedCollection
Calling addFirst/addLast on TreeSet throws UnsupportedOperationException.
addFirst, addLast, getFirst, getLast, removeFirst, removeLast, and reversed methods are declared in SequencedCollection. 
TreeSet does implement SequencedCollection but since a TreeSet keeps its elements sorted, its addFirst and addLast 
methods throw UnsupportedOperationException.  Note that its getFirst, getLast, removeFirst, removeLast methods work fine.

//Add example

### ClassCastException in TreeSet
For tree sets when you need to add elements, you should make sure that a comparator is provided either at the time of
creation of the tree set or the elements added should implement Comparable interface. If this is not the case, a
ClassCastException will be thrown at runtime.

```java
import java.util.TreeSet;
public class TreeSetExample {
    public static void main(String[] args) {
        // Creating a TreeSet without a comparator
        TreeSet<Object> treeSet = new TreeSet<>();

        try {
            // Attempting to add elements that do not implement Comparable
            treeSet.add(new Object());
            treeSet.add(new Object());
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }
    }
}
```

### Null Keys and Values in HashMap and TreeMap
If a HashMap already contains an entry for the specified key, the old value is replaced by the specified value and
the old value is returned. If the key was not already present, null is returned.

```java
import java.util.HashMap;
public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Adding a new key-value pair
        Integer oldValue1 = map.put("key1", 100);
        System.out.println("Old Value for key1: " + oldValue1); // Output: null

        // Replacing the value for the existing key
        Integer oldValue2 = map.put("key1", 200);
        System.out.println("Old Value for key1: " + oldValue2); // Output: 100
    }
}
```
Additionally, hash maps allow null keys and null values. A hash map can have one null key and multiple null values.

```java
import java.util.HashMap;
public class HashMapNullExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Adding a null key
        map.put(null, 100);
        System.out.println("Value for null key: " + map.get(null)); // Output: 100

        // Adding multiple null values
        map.put("key1", null);
        map.put("key2", null);
        System.out.println("Value for key1: " + map.get("key1")); // Output: null
        System.out.println("Value for key2: " + map.get("key2")); // Output: null
    }
}
```
Treemap on the other hand does not allow null keys but allows multiple null values. If you try to add a null key,
a NullPointerException is thrown.

```java
import java.util.TreeMap;
public class TreeMapNullExample {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();

        try {
            // Attempting to add a null key
            map.put(null, 100);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getMessage());
        }

        // Adding multiple null values
        map.put("key1", null);
        map.put("key2", null);
        System.out.println("Value for key1: " + map.get("key1")); // Output: null
        System.out.println("Value for key2: " + map.get("key2")); // Output: null
    }
}
```

### Null Keys and Values in ConcurrentHashMap
In concurrent hash maps (ConcurrentHashMap), null keys and null values are not allowed. Attempting to add a null key or
null value will result in a NullPointerException.

```java
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapNullExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        try {
            // Attempting to add a null key
            map.put(null, 100);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException for null key: " + e.getMessage());
        }

        try {
            // Attempting to add a null value
            map.put("key1", null);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException for null value: " + e.getMessage());
        }
    }
}
```

### Sublist Backed by Original List
When creating a sublist from a list using the subList() method, the returned sublist is backed by the original list.
If you modify the original list (e.g., adding or removing elements), the sublist may become invalid and throw a
ConcurrentModificationException when you try to access or modify it. However, modifying the elements in the sublist itself
(e.g., setting values) is allowed and will be reflected in the original list.

```java 
import java.util.ArrayList;
import java.util.List;
public class SubListExample {
    public static void main(String[] args) {
        List<String> originalList = new ArrayList<>();
        originalList.add("A");
        originalList.add("B");
        originalList.add("C");
        originalList.add("D");

        // Creating a sublist from index 1 to 3 (exclusive)
        List<String> subList = originalList.subList(1, 3);
        System.out.println("Sublist before modification: " + subList); // Output: [B, C]

        // Modifying the original list
        originalList.add("E");

        try {
            // Attempting to access the sublist after modifying the original list
            System.out.println("Sublist after modification: " + subList);
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException: " + e.getMessage());
        }

        // Modifying elements in the sublist
        subList.set(0, "X");
        System.out.println("Sublist after setting value: " + subList); // Output: [X, C]
        System.out.println("Original list after modifying sublist: " + originalList); // Output: [A, X, C, D, E]
    }
}
```

### of() and copyOf() Methods with Null Elements
The of() and copyof() methods do not allow null elements. Attempting to create a list, set, or map with null 
elements using these methods will result in a NullPointerException. 

```java
import java.util.List;
import java.util.Set;
import java.util.Map;
public class OfCopyOfNullExample {
    public static void main(String[] args) {
        try {
            // Attempting to create a list with a null element
            List<String> list = List.of("A", null, "C");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException for List.of: " + e.getMessage());
        }

        try {
            // Attempting to create a set with a null element
            Set<String> set = Set.of("A", null, "C");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException for Set.of: " + e.getMessage());
        }

        try {
            // Attempting to create a map with a null key
            Map<String, Integer> map = Map.of("A", 1, null, 2);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException for Map.of: " + e.getMessage());
        }
    }
}
```