package sort.timer;

import sort.parallel.ParallelMergeSort;

public class TimerParallel {
    public static double TimerParallelSorting(int[] arr) {
        long startTime = System.nanoTime();
        ParallelMergeSort.parallelMergeSort(arr);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }
}
