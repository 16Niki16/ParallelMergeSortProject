package sort.timer;

import constants.Numbers;
import sort.parallel.MergeSortParallel;

import java.util.concurrent.ForkJoinPool;

public class TimerParallel {
    public static double TimerParallelSorting(int[] arr, ForkJoinPool pool) {
        long startTime = System.nanoTime();
        pool.invoke(new MergeSortParallel(arr, Numbers.ZERO, arr.length - Numbers.ONE));
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }
}
