package sort.timer;

import sort.parallel.ParallelMergeSort;

public class TimerParallel {
    public static long TimerParallelSorting(int[] arr) {
        long startTime = System.nanoTime();
        ParallelMergeSort.parallelMergeSort(arr);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
