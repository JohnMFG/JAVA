class SharedResource {
    private int value = 0;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public void decrement() {
        // synchronized (this) {
        //     value--;
        // }
        value--;
    }

    public int getValue() {
        synchronized (this) {
            return value;
        }
    }
}

class Race {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.increment();
            }
        });

        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                resource.decrement();
            }
        });

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Correct Scenario: 0");
        System.out.println("Resource Value: " + resource.getValue());
    }
}
