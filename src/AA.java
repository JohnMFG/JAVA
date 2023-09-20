import java.util.ArrayList;
import java.util.List;

 class AA {
    // Shared list
    private static List<Integer> sharedList = new ArrayList<>();

    // Function to add an element to the list WITHOUT synchronization (Wrong scenario)
    private static void addToSharedList(int value) {
        for (int i = 0; i < 10000; i++) {
            // Simulate some computation
            synchronized (sharedList) {
                sharedList.add(value); // Critical Section 1
            }
            // More computation can happen here without synchronization
            synchronized (sharedList) {
                // Additional operations on sharedList (Critical Section 2)
                sharedList.remove(0); // For example, removing an element
            }
            // More computation can happen here without synchronization
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Create two threads to add elements to the list concurrently
        Thread thread1 = new Thread(() -> addToSharedList(1));
        Thread thread2 = new Thread(() -> addToSharedList(2));

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // Print the final size of the list
        System.out.println("Final List Size (Smaller Critical Sections): " + sharedList.size());
    }
}
