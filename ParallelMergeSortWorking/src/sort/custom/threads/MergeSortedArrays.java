package sort.custom.threads;

import constants.Numbers;
import sort.Helpers;

import java.util.Iterator;
import java.util.Map;

public class MergeSortedArrays {
    public static void Merger(int[] arr, Map<Integer, Integer> intervals) {
        int startFirst = Numbers.ZERO;
        int endFirst = Numbers.ZERO;
        int endSecond = Numbers.ZERO;
        while (intervals.size() > Numbers.ONE) {
            Iterator<Map.Entry<Integer, Integer>> iterator = intervals.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<Integer, Integer> first = iterator.next();
                startFirst = first.getKey();
                endFirst = first.getValue();
                iterator.remove();
            }
            if (iterator.hasNext()) {
                Map.Entry<Integer, Integer> second = iterator.next();
                endSecond = second.getValue();
                iterator.remove();
            }
            Helpers.merge(arr, startFirst, endFirst, endSecond);
            intervals.put(startFirst,endSecond);
        }
    }
}
