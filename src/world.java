class MessagePrinter {
    public void printMessage(String message) {
        for (int i = 0; i < 50; i++) {
            System.out.println(message); // No synchronization, introducing a race condition
        }
    }
}

 class RaceConditionMessageExample {
    public static void main(String[] args) {
        MessagePrinter printer = new MessagePrinter();

        Thread thread1 = new Thread(() -> {
            printer.printMessage("Thread 1: Hello");
        });

        Thread thread2 = new Thread(() -> {
            printer.printMessage("Thread 2: World");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Incorrect Scenario: Race Condition in Message Printing");
    }
}
