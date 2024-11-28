package transform;

import constants.Numbers;
import sort.parallel.ParallelMergeSort;

import java.util.Arrays;
import java.util.Random;

public class MessageTransform {
    public static int[] transform(String message) {
        return stringToIntArray(message);
    }

    private static int[] stringToIntArray(String arrayString) {
        String[] stringNumbers = arrayString.split(", ");
        int[] intArray = new int[stringNumbers.length];
        for (int i = Numbers.ZERO; i < stringNumbers.length; i++) {
            intArray[i] = Integer.parseInt(stringNumbers[i]);
        }
        return intArray;
    }
}
