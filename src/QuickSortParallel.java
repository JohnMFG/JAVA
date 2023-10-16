// Program performs a parallel quicksort on an array, using the number of threads specified
// and sorting array size as a command line argument.
// Jonas Mazeika


import java.util.Arrays;

public class QuickSortParallel {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("To few arguments");
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
            System.out.println("Debugging mode.");
        }

        long startTime = System.currentTimeMillis();
        quickSortParallel(array, numThreads);
        long endTime = System.currentTimeMillis();

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    public static void quickSortParallel(int[] arr, int numThreads) {
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * (arr.length / numThreads);
            final int end = (i == numThreads - 1) ? arr.length - 1 : start + (arr.length / numThreads) - 1;

            threads[i] = new Thread(() -> {
                quickSort(arr, start, end);
            });
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    public static int partition(int[] arr, int left, int right) {
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
