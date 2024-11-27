package transform;

import sort.ParallelMergeSort;

import java.util.Arrays;
import java.util.Random;

public class MessageTransform {
    public static int[] transform(String message) {
        return stringToIntArray(message);
    }

    private static int[] stringToIntArray(String arrayString) {
        String[] stringNumbers = arrayString.split(", ");

        int[] intArray = new int[stringNumbers.length];
        for (int i = 0; i < stringNumbers.length; i++) {
            intArray[i] = Integer.parseInt(stringNumbers[i]);
        }
        return intArray;
    }
    public static void main(String... args){
        int[] arr = new int[10000];
        Random rand = new Random();
        for(int i = 0; i<10000; i++){
            arr[i] = rand.nextInt(20) + 1;
        }
        ParallelMergeSort.parallelMergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
