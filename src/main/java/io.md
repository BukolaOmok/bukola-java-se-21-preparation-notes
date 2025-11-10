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

### Serialization and Deserialization Constructor Behavior
When deserialising the constructor of the class that is being deserialized is not called. It calls the constructors of the
first non-serializable superclass. An exception to this is for record classes, where the canonical constructor is called during
deserialization even though the record implements Serializable.

```java
import java.io.Serializable;
class Parent {
    Parent() {
        System.out.println("Parent Constructor Called");
    }
}
class Child extends Parent implements Serializable {
    Child() {
        System.out.println("Child Constructor Called");
    }
}
public class TestClass {
    public static void main(String[] args) throws Exception {
        Child child = new Child();
        // Serialize the child object
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("child.ser"))) {
            oos.writeObject(child);
        }
        // Deserialize the child object
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("child.ser"))) {
            Child deserializedChild = (Child) ois.readObject();
        }
    }
}
```

### File Size when Writing Characters
When you write characters to a file using FileWriter or PrintWriter, the size of the file depends on the character encoding used.
For FileWriter, the size of the file depends on the default character encoding of the platform. For example, if the default character encoding is UTF-8,
then each character may take 1 to 4 bytes depending on the character. If you write ASCII characters, each character will take 1 byte.

```java
public static void writeCharacters(String fileName) throws Exception {
    try (FileWriter writer = new FileWriter(fileName)) {
        writer.write("Hello, World!"); // 13 characters
    }
} //
```

###  PrintWriter Error Handling
Printwriter's methods do not throw IO exception as they supress the errors internally. It could have a throws IO Exception it just becomes essentially useless. You can check for errors using checkError() method.
All it's write and print method return void so they cannot be assigned to a variable. Additionally the println and printf methods are different in that
in printf allows for channing (i.e. you can call another method on the returned PrintWriter object) whereas println does not allow for chaining.
The reason is that printf returns the PrintWriter object itself whereas println returns void.
```java
public static void writeWithPrintWriter(String fileName) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
        pw.println("Hello, World!");
        if (pw.checkError()) {
            System.out.println("An error occurred while writing to the file.");
        }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
}
```