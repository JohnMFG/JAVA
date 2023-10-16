// Program performs a parallel quicksort on an array, using the number of threads specified
// and sorting array size as a command line argument.
// Jonas Mazeika


import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class QuickSortParallel {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java QuickSortParallel <numThreads> <arraySize> <debugMode>");
            System.exit(1);
        }

        int numThreads = Integer.parseInt(args[0]);
        int arraySize = Integer.parseInt(args[1]);
        boolean debugMode = Boolean.parseBoolean(args[2]);

        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = (int) (Math.random() * 1000);
        }

        System.out.println("Sorting an array of size " + arraySize + " with " + numThreads + " threads.");

        if (debugMode) {
            System.out.println("Debugging Mode: Program execution will be artificially slowed down.");
        }

        long startTime = System.currentTimeMillis();
        quickSortParallel(array, numThreads);
        long endTime = System.currentTimeMillis();

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    public static void quickSortParallel(int[] arr, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            executor.invokeAll(Arrays.asList(new QuickSortTask(arr, 0, arr.length - 1)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class QuickSortTask implements Callable<Void> {
        private final int[] arr;
        private final int left;
        private final int right;

        public QuickSortTask(int[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        public Void call() {
            quickSort(arr, left, right);
            return null;
        }

        private void quickSort(int[] arr, int left, int right) {
            if (left < right) {
                int partitionIndex = partition(arr, left, right);
                quickSort(arr, left, partitionIndex - 1);
                quickSort(arr, partitionIndex + 1, right);
            }
        }

        private int partition(int[] arr, int left, int right) {
            int pivot = arr[right];
            int i = left - 1;
            for (int j = left; j < right; j++) {
                if (arr[j] <= pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            int temp = arr[i + 1];
            arr[i + 1] = arr[right];
            arr[right] = temp;
            return i + 1;
        }
    }
}
