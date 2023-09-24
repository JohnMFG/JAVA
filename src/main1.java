import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class ListAccess {
    private static List<Integer> sharedList = new ArrayList<>();
    private static volatile AtomicBoolean isAdding = new AtomicBoolean(false);

    private static void addToSharedList(int value) {
        for (int i = 0; i < 100; i++) {
            while (!isAdding.compareAndSet(false, true)) {
                // Busy-wait until we successfully set isAdding to true
            }
            sharedList.add(value + 1);
            isAdding.set(false);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> addToSharedList(1));
        Thread thread2 = new Thread(() -> addToSharedList(2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Correct answer: 200");
        System.out.println("Output result: " + sharedList.size());
    }
}
