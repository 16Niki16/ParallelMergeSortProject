package sort;

import constants.Numbers;

public class Helpers {
    public static void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + Numbers.ONE];
        int i = left;
        int j = mid + Numbers.ONE;
        int k = Numbers.ZERO;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, Numbers.ZERO, array, left, temp.length);
    }
}
