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

### Period.of and Duration.of does not chain
Note that Period.of does not chain like LocalDateTime methods. Each call to Period.of creates a new Period instance.
The same applies to Duration.of methods.
```java
import java.time.LocalDate;
import java.time.Period;
public class PeriodExample {
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        
        // Incorrect chaining of Period.of
        Period period = Period.of(1, 0, 0).of(0, 2, 0).of(0, 0, 15); // Only the last call is effective
        LocalDate newDate = startDate.plus(period);
        System.out.println("New Date (Incorrect Chaining): " + newDate); // Outputs: 2020-01-16

        // Correct way to create a Period of 1 year, 2 months, and 15 days
        Period correctPeriod = Period.of(1, 2, 15);
        LocalDate correctNewDate = startDate.plus(correctPeriod);
        System.out.println("New Date (Correct): " + correctNewDate); // Outputs: 2021-03-16
    }
}
```
