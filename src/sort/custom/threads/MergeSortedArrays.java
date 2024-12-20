package sort.custom.threads;

import sort.Helpers;

import java.util.Iterator;
import java.util.Map;

public class MergeSortedArrays {
    public static void Merger(int[] arr, Map<Integer, Integer> intervals) {
        int startFirst = 0;
        int endFirst = 0;
        int endSecond = 0;
        while (intervals.size() > 1) {
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
