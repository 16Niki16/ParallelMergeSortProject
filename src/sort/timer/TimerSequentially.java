package sort.timer;

import constants.Numbers;
import sort.parallel.ParallelMergeSort;

public class TimerSequentially {
    public static long TimerSeqSorting(int[] arr){
        int[] temp = new int[arr.length];
        System.arraycopy(arr, Numbers.ZERO, temp, Numbers.ZERO, arr.length);
        long startTime = System.nanoTime();
        ParallelMergeSort.parallelMergeSort(temp);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
