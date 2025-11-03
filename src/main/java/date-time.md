### DateTimeFormatter cannot be instantiated
DateTimeFormatter cannot be instantiated because it has no public constructor.  
It provides various static methods such as ofPattern(...) and ofLocalizedDate/DateTime(...) methods and also various 
predefined static constants such as ISO_LOCAL_DATE and ISO_LOCAL_DATE_TIME, to get its instances.
```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DateTimeFormatterExample {
    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now();

        // Using a predefined formatter
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        System.out.println("Formatted DateTime (ISO): " + dateTime.format(formatter1));

        // Using a custom pattern
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Formatted DateTime (Custom): " + dateTime.format(formatter2));
    }
}
```