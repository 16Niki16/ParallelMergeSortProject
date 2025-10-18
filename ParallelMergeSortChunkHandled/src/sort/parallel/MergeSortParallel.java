package sort.parallel;

import constants.Numbers;
import sort.Helpers;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortParallel extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;
    private static final int THRESHOLD = 1000;

    public MergeSortParallel(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left <= THRESHOLD) {
            Arrays.sort(array, left, right + Numbers.ONE);
        } else {
            int mid = left + (right - left) / Numbers.TWO;

            MergeSortParallel leftTask = new MergeSortParallel(array, left, mid);
            MergeSortParallel rightTask = new MergeSortParallel(array, mid + Numbers.ONE, right);

            invokeAll(leftTask, rightTask);

            Helpers.merge(array, left, mid, right);
        }
    }
}
