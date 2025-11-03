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

When you are writing with output stream, it does not take writeInt() or writeDouble() methods, this is only applicable
for DataOutputStream. Note that the write(int b) method of various binary stream based classes take an int parameter
but write only the low 8 bits (i.e. 1 byte) of that integer. DataOutputStream provides methods such as writeInt,
writeChar, and writeDouble, for writing complete value of the primitives to a file. So if you want to write an integer
to the file, you should use writeInt(1) in which case a file of size 4 bytes will be created.
You can read back the stored primitives using methods such as DataInputStream.readInt(). This is for DataInputStream
class.

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

### File-Related Exceptions

BufferedReader itself doesn’t touch the filesystem. The exception that is thrown depends on how you create it:

new BufferedReader(new FileReader("x.txt")) → FileNotFoundException (classic java.io path).
Files.newBufferedReader(Path.of("x.txt")) → NoSuchFileException (NIO java.nio.file path).

FileInputStream
new FileInputStream("x.txt") → FileNotFoundException if the file doesn’t exist (or is a directory / no permission).

FileOutputStream
new FileOutputStream("x.txt") → creates the file if it doesn’t exist.
Throws FileNotFoundException if the parent directory doesn’t exist, the path is a directory, or no permission.

RandomAccessFile
Mode "r": file must exist → FileNotFoundException if it doesn’t.
Mode "rw": will create the file if it doesn’t exist (but still fails with FileNotFoundException if parent dir missing /
no permission).

### RandomAccessFile Modes

RandomAccessFile supports four mode strings:

1. "r" – read-only. The file must exist. Any write methods throw IOException.
2. "rw" – read/write. Creates the file if it doesn’t exist (fails if parent dir missing / no permission).
   Writes are buffered by the OS; metadata flush timing isn’t guaranteed.
3. "rwd" – read/write with synchronous updates to file content only. Data is flushed, but metadata need not be.
   Middle ground between "rw" and "rws".
4. "rws" – read/write with synchronous updates to file content and metadata. After each write, the OS is asked to
   flush both the data and the file’s metadata (size, modified time, etc.) to stable storage. Safest, slowest.

