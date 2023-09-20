//Jonas Mažeika
//Demonstration of the concurrent addition of elements to a shared list by two separate threads
//Corect output: 60
//To get wrong answer comment synchronized block and uncomment line below this block
//Since CS is quite small it's not needed to divide it into smaller sections.

import java.util.ArrayList;
import java.util.List;

 class ListAccess {
    private static List<Integer> sharedList = new ArrayList<>();

    private static void addToSharedList(int value) {
        for (int i = 0; i < 30; i++) { //CS section starts
            synchronized (sharedList) { 
                sharedList.add(value);
            }
            //sharedList.add(value);
        } //CS section ends
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> addToSharedList(1));
        Thread thread2 = new Thread(() -> addToSharedList(2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Correct aswer: 60");
        System.out.println("Output resut: " + sharedList.size());
    }
}
