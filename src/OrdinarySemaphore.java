class OrdinarySemaphore {
    private int availableResources;

    public OrdinarySemaphore(int initialResources) {
        if (initialResources < 0) {
            throw new IllegalArgumentException("Initial resources cannot be negative.");
        }
        availableResources = initialResources;
    }

    public synchronized void request() throws InterruptedException {
        while (availableResources == 0) {
            wait(); // Wait until a resource is released
        }
        availableResources--;
    }

    public synchronized void release() {
        availableResources++;
        notify(); // Notify a waiting thread that a resource is available
    }

    public synchronized int numberAvailable() {
        return availableResources;
    }
}

class Semaphore {
    public static void main(String[] args) {
        final OrdinarySemaphore semaphore = new OrdinarySemaphore(2);

        Runnable resourceConsumerA = () -> {
            try {
                semaphore.request();
                System.out.println("Thread A acquired a resource.");
                simulateResourceUsage();
                semaphore.release();
                System.out.println("Thread A released a resource.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable resourceConsumerB = () -> {
            try {
                semaphore.request();
                System.out.println("Thread B acquired a resource.");
                simulateResourceUsage();
                semaphore.release();
                System.out.println("Thread B released a resource.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable resourceConsumerC = () -> {
            try {
                semaphore.request();
                System.out.println("Thread C acquired a resource.");
                simulateResourceUsage();
                semaphore.release();
                System.out.println("Thread C released a resource.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread threadA = new Thread(resourceConsumerA);
        Thread threadB = new Thread(resourceConsumerB);
        Thread threadC = new Thread(resourceConsumerC);
        
        
        runThreads(threadA, threadB, threadC);
    }

    private static void runThreads(Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        joinThreads(threads);
        System.out.println("Number of available resources: " + threads[0].getState().name());
    }

    private static void simulateResourceUsage() throws InterruptedException {
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            result += i;
        }
    }

    private static void joinThreads(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
