package sort.sequent;

import constants.Numbers;
import sort.Helpers;

public class MergeSort {
    public static void Merge(int[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / Numbers.TWO;
            Merge(arr, l, mid);
            Merge(arr, mid, r);

            Helpers.merge(arr, l, mid, r);
        }
    }
}
