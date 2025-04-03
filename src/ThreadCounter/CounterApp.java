package ThreadCounter;

public class CounterApp {
    public static void main(String[] args) {
        try {
            Thread countUpThread = new Thread(new CountUp(), "Count-Up-Thread");
            Thread countDownThread = new Thread(new CountDown(countUpThread), "Count-Down-Thread");

            countUpThread.start();
            countDownThread.start();
        } catch (SecurityException se) {
            System.out.println("Security Exception: " + se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error in main thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class CountUp implements Runnable {
    @Override
    public void run() {
        try {
            for (int i = 0; i <= 20; i++) {
                System.out.println(Thread.currentThread().getName() + " - Count Up: " + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Count-Up thread was interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Unexpected error in Count-Up thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class CountDown implements Runnable {
    private final Thread waitForThread;

    public CountDown(Thread waitForThread) {
        if (waitForThread == null) {
            throw new IllegalArgumentException("Thread to wait for must not be null.");
        }
        this.waitForThread = waitForThread;
    }

    @Override
    public void run() {
        try {
            waitForThread.join(); // Ensure count-up finishes first
            for (int i = 20; i >= 0; i--) {
                System.out.println(Thread.currentThread().getName() + " - Count Down: " + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Count-Down thread was interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Unexpected error in Count-Down thread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
