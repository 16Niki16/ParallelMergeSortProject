package sort.timer;

import constants.Numbers;
import sort.sequent.MergeSortSeq;
public class TimerSequentially {
    public static double TimerSeqSorting(int[] arr) {
        int[] temp = new int[arr.length];
        System.arraycopy(arr, Numbers.ZERO, temp, Numbers.ZERO, arr.length);
        long startTime = System.nanoTime();
        MergeSortSeq.mergeSeq(temp, Numbers.ZERO, temp.length - Numbers.ONE);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }
}
