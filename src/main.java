//Jonas Mažeika
//Demonstration of the concurrent addition of elements to a shared list by two separate threads
//Corect output: 200
//To get correct output, comment : lock();
                                // sharedArray[index++] = value + 1;
                                // unlock();

                                //And uncomment synchronized (sharedArray)
//CS is correct

class CustomListAccess {
    private static int[] sharedArray = new int[2000];
    private static int index = 0;

    private static volatile boolean isAdding = false;

    private static void lock() {
        while (isAdding) {
            System.out.println("Locked");
        }
        isAdding = true;
    }

    private static void unlock() {
        isAdding = false;
    }

    private static void addToSharedList(int value) {
        for (int i = 0; i < 1000; i++) {
            lock();
            sharedArray[index++] = value + 1;
            unlock();

            // synchronized (sharedArray) {
            //     sharedArray[index++] = value + 1;
            // }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> addToSharedList(1));
        Thread thread2 = new Thread(() -> addToSharedList(2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int count = 0;
        for (int i : sharedArray) {
            if (i != 0) {
                count++;
            }
        }

        System.out.println("Correct answer: 2000");
        System.out.println("Output result: " + count);
    }
}
