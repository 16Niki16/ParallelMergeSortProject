package server;

import constants.Numbers;
import sort.parallel.ParallelMergeSort;
import sort.timer.TimerParallel;
import sort.timer.TimerSequentially;
import transform.MessageTransform;

import java.util.Arrays;

public class OutputManager {
    public static String outputManager(String line){
        int[] unsortedArray = MessageTransform.transform(line);
        StringBuilder buildAnswer = new StringBuilder("Sorted array: ");
        long timeSequential = TimerSequentially.TimerSeqSorting(unsortedArray);
        long timeParallel = TimerParallel.TimerParallelSorting(unsortedArray);
        buildAnswer.append(Arrays.toString(unsortedArray)).append('\n');
        buildAnswer.append("Sequentially sorted time: ").append(timeSequential).append('\n');
        buildAnswer.append("Parallel sorted time: ").append(timeParallel).append('\n');
        return buildAnswer.substring(Numbers.ZERO);
    }
}
