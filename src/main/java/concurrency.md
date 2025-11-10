
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

### Thread States and InterruptedException in Java
Threads can be in one of the following states:
- New: A thread that has been created but not yet started.
- Runnable: A thread that is ready to run and is waiting for CPU time.
- Blocked: A thread that is waiting for a monitor lock to enter a synchronized block/method.
- Waiting: A thread that is waiting indefinitely for another thread to perform a particular action.
- Timed Waiting: A thread that is waiting for another thread to perform an action for a specified amount of time.
- Terminated: A thread that has completed its execution.

When you interrupt a thread that is in the following states, the corresponding exceptions are thrown:
- Blocked: InterruptedException is thrown when the thread is waiting to acquire a monitor lock.
- Waiting: InterruptedException is thrown when the thread is waiting indefinitely.
- Timed Waiting: InterruptedException is thrown when the thread is waiting for a specified amount of time.
- Runnable/New: No exception is thrown when a thread in these states is interrupted. The interrupt status of the thread is set to true, and it can be checked using the isInterrupted() method or the static interrupted() method of the Thread class.
- Terminated: No exception is thrown when a thread in this state is interrupted, as the thread has already completed its execution.


### Making ReentrantReadWriteLock Thread Safe
To make lock variable thread sfe, you can make it private and final.
```java
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ThreadSafeReadWrite {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int sharedResource;

    public void write(int value) {
        lock.writeLock().lock();
        try {
            sharedResource = value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int read() {
        lock.readLock().lock();
        try {
            return sharedResource;
        } finally {
            lock.readLock().unlock();
        }
    }
}
```