### Writing to a File (Replace vs Append)

When you create a FileOutputStream without specifying the append mode (which can be true or false),
it overwrites the existing file. If you want to append data to an existing file, you need to use the constructor that
takes a boolean
append parameter and set it to true.

Replace Example:

```java
public static void copy(String fileName1, String fileName2) throws Exception {
    try (InputStream is = new FileInputStream(fileName1); OutputStream os = new FileOutputStream(fileName2)) {
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
            System.out.println("Read and written bytes " + bytesRead);
        }
    }
}
```

Append Example:

```java
public static void copyAppend(String fileName1, String fileName2) throws Exception {
    try (InputStream is = new FileInputStream(fileName1);
         OutputStream os = new FileOutputStream(fileName2, true)) { // ← append mode
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
            System.out.println("Read and written bytes " + bytesRead);
        }
    }
}
```

### Writing Primitives to a File
When you are writing with output stream, the method does not take writeInt() or writeDouble() methods, this is only applicable 
for DataOutputStream. Note that the write(int b) method of various binary stream based classes take an int parameter 
but write only the low 8 bits (i.e. 1 byte) of that integer. DataOutputStream provides methods such as writeInt, 
writeChar, and writeDouble, for writing complete value of the primitives to a file. So if you want to write an integer 
to the file, you should use writeInt(1) in which case a file of size 4 bytes will be created. 
You can read back the stored primitives using methods such as DataInputStream.readInt(). This is for DataInputStream class.

OutputStream Example (writes only 1 byte):
```java
public static void writeByte(String fileName) throws Exception {
    try (OutputStream os = new FileOutputStream(fileName)) {
        os.write(65); // writes only 1 byte (the low 8 bits of the integer)
    }
}
```
DataOutputStream Example (writes full primitive):
```java
public static void writeInt(String fileName) throws Exception {
    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
        dos.writeInt(65); // writes full 4 bytes of the integer
    }
}
```

For reentrant locks, below are some important points to consider:
1. A reentrant lock allows the same thread to acquire the lock multiple times without causing a deadlock. 
This is useful in scenarios where a method that holds a lock calls another method that also tries to acquire 
the same lock.
2. You can have multiple readers or a single writer at any given time.
3. If no one is writing → multiple readers can proceed.
4. If someone is writing → everyone else must wait.
5. The same readLock() and writeLock() objects are reused across calls.
6. As with any lock, it is crucial to ensure that locks are released in a finally block to avoid deadlocks.

