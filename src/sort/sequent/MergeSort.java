package sort.sequent;

import constants.Numbers;
import sort.Helpers;

public class MergeSort {
    public static void mergeSeq(int[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / Numbers.TWO;
            mergeSeq(arr, l, mid);
            mergeSeq(arr, mid, r);

            Helpers.merge(arr, l, mid, r);
        }
    }
}
