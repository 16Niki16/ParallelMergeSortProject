package sort.custom.timer;

import constants.Numbers;
import sort.custom.threads.SortCustomThreads;
import sort.parallel.MergeSortParallel;

public class TimerCustomThreads {
    public static double TimerParallelCustom(int[] arr, int numberOfThreads){
        long startTime = System.nanoTime();
        SortCustomThreads ss = new SortCustomThreads(arr,numberOfThreads);
        ss.sortArray();
        long endTime = System.nanoTime();
        return (endTime - startTime) / Numbers.ONE_MILLION_DOUBLE;
    }
}
