package sort.parallel;

import constants.Numbers;

import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {
    public static void parallelMergeSort(int[] array) {
        try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
            pool.invoke(new MergeSort(array, Numbers.ZERO, array.length - Numbers.ONE));
        }
    }
}

