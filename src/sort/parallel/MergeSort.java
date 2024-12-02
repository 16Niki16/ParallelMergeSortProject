package sort.parallel;

import constants.Numbers;
import sort.Helpers;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSort extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;
    private static final int THRESHOLD = 1000;

    public MergeSort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        System.out.println(array.length);
        if (right - left <= THRESHOLD) {
            Arrays.sort(array, left, right + Numbers.ONE);
        } else {
            int mid = left + (right - left) / Numbers.TWO;

            MergeSort leftTask = new MergeSort(array, left, mid);
            MergeSort rightTask = new MergeSort(array, mid + Numbers.ONE, right);

            invokeAll(leftTask, rightTask);

            Helpers.merge(array, left, mid, right);
        }
    }
}
