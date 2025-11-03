
### Understanding Executor, ExecutorService, and Executors in Java
You need to remember the following points about a few important classes in java.util.concurrent package:  
1. ExecutorService interface extends Executor interface. While Executor allows you to execute a Runnable, ExecutorService allows you to execute a Callable. 
2. Executors is a utility class that provides several static methods to create instances of ExecutorService. All such methods start with new e.g. newSingleThreadExecutor(). 
You should at least remember the following methods: newFixedThreadPool(int noOfThreads), newSingleThreadExecutor(), 
newCachedThreadPool(), newSingleThreadScheduledExecutor(), newScheduledThreadPool(int corePoolSize), newVirtualThreadPerTaskExecutor().
3. Executor interface has only one method: void execute(Runnable command). ExecutorService interface has several methods including submit(), invokeAll(), invokeAny(), shutdown(), and shutdownNow().

### Reentrant Read Write Lock in Java
For reentrant locks, below are some important points to consider:
1. A reentrant lock allows the same thread to acquire the lock multiple times without causing a deadlock.
   This is useful in scenarios where a method that holds a lock calls another method that also tries to acquire
   the same lock.
2. You can have multiple readers or a single writer at any given time.
3. If no one is writing → multiple readers can proceed.
4. If someone is writing → everyone else must wait.
5. The same readLock() and writeLock() objects are reused across calls.
6. As with any lock, it is crucial to ensure that locks are released in a finally block to avoid deadlocks.


##### Exception Handling in Synchronized Methods
If a synchronized method ends up throwing an exception to the caller, the lock acquired by the thread associated with
this method due to the usage of the synchronized keyword is released automatically. This is important to prevent deadlocks
in scenarios where an exception occurs within a synchronized method.

```java
public class SynchronizedExceptionExample {
    private final Object lock = new Object();

    public void synchronizedMethod() {
        synchronized (lock) {
            try {
                // Some code that may throw an exception
                throw new RuntimeException("An error occurred");
            } finally {
                // The lock will be released automatically when the method exits,
                // even if an exception is thrown.
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedExceptionExample example = new SynchronizedExceptionExample();
        try {
            example.synchronizedMethod();
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
```


