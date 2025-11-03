
### Updating Locale for ResourceBundle
Note that once a ResourceBundle is retrieved, changing the locale will not affect the ResourceBundle. 
You have to retrieve a new ResourceBundle by passing in the new Locale and then assign it to the variable.
```java
public static void main(String[] args) {
    Locale.setDefault(Locale.ENGLISH);
    ResourceBundle rb = ResourceBundle.getBundle("messages"); // bound to EN
    System.out.println(rb.getString("greet")); // "Hello"

    Locale.setDefault(Locale.FRENCH);
// Still prints English, because rb is already bound:
    System.out.println(rb.getString("greet")); // "Hello"

// To switch, get a new bundle:
    rb = ResourceBundle.getBundle("messages", Locale.FRENCH);
    System.out.println(rb.getString("greet")); // "Bonjour"
}
```

### Handle ParseException when parsing numbers or dates
For parsing number or dates, the method must have a parseException to handle parsing errors. If parse exception is 
not handled, a compilation error occurs.
```java
public static void main(String[] args) {
    Locale.setDefault(Locale.FRANCE);
    String numberStr = "1234,56"; // Note: the space is a non-breaking space (U+00A0)
    NumberFormat nf = NumberFormat.getInstance();
    try {
        Number number = nf.parse(numberStr);
        System.out.println("Parsed number: " + number); // Parsed number: 1234.56
    } catch (ParseException e) {
        System.out.println("ParseException: " + e.getMessage());
    }
}
```

