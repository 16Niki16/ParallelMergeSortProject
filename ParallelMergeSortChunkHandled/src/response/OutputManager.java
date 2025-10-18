package response;

import constants.Numbers;
import exceptions.custom.EmptyArrayException;
import exceptions.handler.ExceptionHandler;
import sort.custom.threads.SortCustomThreads;
import sort.custom.timer.TimerCustomThreads;
import sort.timer.TimerParallel;
import sort.timer.TimerSequentially;
import transform.MessageTransform;
import transform.MessageTransformCustomThreads;
import transform.Pair;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class OutputManager {
    private static final String formatException = "Input string is not in the correct format!";
    private static final String getExceptions = "get exceptions";
    private static final String NUMBER_OF_THREADS = "Number of threads:";
    private static final String ARRAY = "Array:";

    public static String outputManager(String line, ForkJoinPool pool) {
        try {
            double timerParallel;
            double timerSequentially;
            StringBuilder buildAnswer = new StringBuilder("Sorted array: ");
            if (line.equalsIgnoreCase(getExceptions)) {
                return ExceptionHandler.getExceptions();
            } else if (line.contains(NUMBER_OF_THREADS) && line.contains(ARRAY)) {
                Pair<Integer, int[]> pp = MessageTransformCustomThreads.transformCustom(line);
                if (pp.getSecond().length == Numbers.ZERO) {
                    throw new EmptyArrayException("The array you want to sort is empty!");
                }
                timerParallel = TimerCustomThreads.TimerParallelCustom(pp.getSecond(), pp.getFirst());
                timerSequentially = TimerSequentially.TimerSeqSorting(pp.getSecond());
                buildAnswer.append(Arrays.toString(pp.getSecond())).append('\n');
            }
            else {
                int[] unsortedArray = MessageTransform.transform(line);
                if (unsortedArray.length == Numbers.ZERO) {
                    throw new EmptyArrayException("The array you want to sort is empty!");
                }
                timerSequentially = TimerSequentially.TimerSeqSorting(Arrays.copyOf(unsortedArray, unsortedArray.length));
                timerParallel = TimerParallel.TimerParallelSorting(unsortedArray, pool);
                buildAnswer.append(Arrays.toString(unsortedArray)).append('\n');
            }
            buildAnswer.append("Sequentially sorted time: ").append(timerSequentially).append('\n');
            buildAnswer.append("Parallel sorted time: ").append(timerParallel).append('\n');
            return buildAnswer.substring(Numbers.ZERO);

        } catch (NumberFormatException e) {
            ExceptionHandler.exceptionHandler(formatException);
            return formatException;
        } catch (EmptyArrayException e) {
            ExceptionHandler.exceptionHandler(e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }
}
