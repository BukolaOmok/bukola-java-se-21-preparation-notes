### Reification vs Type Erasure in Generics
Reification is the opposite of type erasure in programming languages that support generics. While type erasure 
removes generic type information at runtime, reification preserves this information, allowing the program to retain 
knowledge about the specific types used in generic constructs.

In Java, generics are implemented using type erasure, meaning that generic type information is not available at runtime.
This can lead to certain limitations, such as the inability to create arrays of generic types or to use instanceof checks with generic types.
In contrast, languages like C# and Scala use reification for their generics, allowing them to retain type information at runtime.
In java asides from generics, certain constructs like enums and annotations are reified, meaning their type information is preserved at runtime.
