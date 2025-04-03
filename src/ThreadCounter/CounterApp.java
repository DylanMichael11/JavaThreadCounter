package ThreadCounter;

// Main application class
public class CounterApp {
    public static void main(String[] args) {
        try {
            // Create a new thread to run the CountUp task
            Thread countUpThread = new Thread(new CountUp(), "Count-Up-Thread");

            // Create another thread to run the CountDown task, which waits for countUpThread to finish
            Thread countDownThread = new Thread(new CountDown(countUpThread), "Count-Down-Thread");

            // Start both threads
            countUpThread.start();
            countDownThread.start();
        } catch (SecurityException se) {
            // Handle security-related exceptions
            System.out.println("Security Exception: " + se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            // Handle any unexpected exceptions in the main thread
            System.out.println("Unexpected error in main thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Task that counts up from 0 to 20
class CountUp implements Runnable {
    @Override
    public void run() {
        try {
            for (int i = 0; i <= 20; i++) {
                // Print the current count with thread name
                System.out.println(Thread.currentThread().getName() + " - Count Up: " + i);

                // Pause for 100 milliseconds between counts
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // Handle interruption during sleep
            System.out.println("Count-Up thread was interrupted.");
            Thread.currentThread().interrupt(); // Preserve the interrupted status
        } catch (Exception e) {
            // Handle any unexpected exceptions in Count-Up thread
            System.out.println("Unexpected error in Count-Up thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Task that counts down from 20 to 0 after waiting for another thread to finish
class CountDown implements Runnable {
    private final Thread waitForThread;

    // Constructor takes the thread to wait for before starting the countdown
    public CountDown(Thread waitForThread) {
        if (waitForThread == null) {
            // Validate input: the provided thread must not be null
            throw new IllegalArgumentException("Thread to wait for must not be null.");
        }
        this.waitForThread = waitForThread;
    }

    @Override
    public void run() {
        try {
            // Wait for the Count-Up thread to finish before proceeding
            waitForThread.join();

            for (int i = 20; i >= 0; i--) {
                // Print the current countdown number with thread name
                System.out.println(Thread.currentThread().getName() + " - Count Down: " + i);

                // Pause for 100 milliseconds between counts
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            // Handle interruption during join or sleep
            System.out.println("Count-Down thread was interrupted.");
            Thread.currentThread().interrupt(); // Preserve the interrupted status
        } catch (Exception e) {
            // Handle any unexpected exceptions in Count-Down thread
            System.out.println("Unexpected error in Count-Down thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
