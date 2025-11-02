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