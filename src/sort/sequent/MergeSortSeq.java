package sort.sequent;

import constants.Numbers;
import sort.Helpers;

import java.util.Arrays;
import java.util.Random;

public class MergeSortSeq {
    public static void mergeSeq(int[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / Numbers.TWO;

            mergeSeq(arr, l, mid);
            mergeSeq(arr, mid + Numbers.ONE, r);

            Helpers.merge(arr, l, mid, r);
        }
    }
}
