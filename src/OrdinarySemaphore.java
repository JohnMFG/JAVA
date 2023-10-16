//Jonas Mažeika
//Using semaphore to simulate acquiring and realeasing resource for the threads

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
            wait();
        }
        availableResources--;
    }

    public synchronized void release() {
        availableResources++;
        notify();
    }

    public synchronized int numberAvailable() {
        return availableResources;
    }
}

 class Semaphore {
    public static void main(String[] args) {
        int startingResource =2;
        final OrdinarySemaphore semaphore = new OrdinarySemaphore(startingResource);
        System.out.println("Starting resources: " + startingResource);

        Runnable resourceConsumerA = () -> {
            try {
                semaphore.request();
                System.out.println("Thread A acquired a resource.");
                simulateResourceUsage();
                System.out.println("Thread A releasing a resource.");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable resourceConsumerB = () -> {
            try {
                semaphore.request();
                System.out.println("Thread B acquired a resource.");
                simulateResourceUsage();
                System.out.println("Thread B releasing a resource.");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable resourceConsumerC = () -> {
            try {
                semaphore.request();
                System.out.println("Thread C acquired a resource.");
                simulateResourceUsage();
                System.out.println("Thread C releasing a resource.");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread threadA = new Thread(resourceConsumerA);
        Thread threadB = new Thread(resourceConsumerB);
        Thread threadC = new Thread(resourceConsumerC);

        runThreads(semaphore, threadA, threadB, threadC);
    }

    private static void runThreads(OrdinarySemaphore semaphore, Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        joinThreads(threads);
        
        System.out.println("Number of available resources in the end: " + semaphore.numberAvailable());
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
