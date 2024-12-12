package response;

import constants.Numbers;
import exceptions.EmptyArrayException;
import sort.timer.TimerParallel;
import sort.timer.TimerSequentially;
import transform.MessageTransform;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class OutputManager {
    public static String outputManager(String line, ForkJoinPool pool) {
        try {
            int[] unsortedArray = MessageTransform.transform(line);
            if (unsortedArray.length == Numbers.ZERO) {
                throw new EmptyArrayException("The array you want to sort is empty!");
            }
            StringBuilder buildAnswer = new StringBuilder("Sorted array: ");
            double timeSequential = TimerSequentially.TimerSeqSorting(unsortedArray);
            double timeParallel = TimerParallel.TimerParallelSorting(unsortedArray, pool);
            buildAnswer.append(Arrays.toString(unsortedArray)).append('\n');
            buildAnswer.append("Sequentially sorted time: ").append(timeSequential).append('\n');
            buildAnswer.append("Parallel sorted time: ").append(timeParallel).append('\n');
            return buildAnswer.substring(Numbers.ZERO);
        } catch (NumberFormatException e) {
            return "Input string is not in the correct format!";
        } catch (EmptyArrayException e) {
            return e.getLocalizedMessage();
        }
    }
}
