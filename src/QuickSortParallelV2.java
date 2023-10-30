import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class QuickSortParallelV2 {
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
            System.out.println("Debugging mode");
        }

        long startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new QuickSortTask(array, 0, arraySize - 1, debugMode));

        long endTime = System.currentTimeMillis();

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    public static class QuickSortTask extends RecursiveAction {
        private final int[] arr;
        private final int left;
        private final int right;
        private final boolean debugMode;

        public QuickSortTask(int[] arr, int left, int right, boolean debugMode) {
            this.arr = arr;
            this.left = left;
            this.right = right;
            this.debugMode = debugMode;
        }

        @Override
        protected void compute() {
            quickSort(arr, left, right);
        }

        private void quickSort(int[] arr, int left, int right) {
            if (left < right) {
                int partitionIndex = partition(arr, left, right);
                QuickSortTask leftTask = new QuickSortTask(arr, left, partitionIndex - 1, debugMode);
                QuickSortTask rightTask = new QuickSortTask(arr, partitionIndex + 1, right, debugMode);

                if (debugMode) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                invokeAll(leftTask, rightTask);
            }
        }

        private int partition(int[] arr, int left, int right) {
            int pivot = arr[right];
            //System.out.println("Pivot: "+ arr[right]);
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
