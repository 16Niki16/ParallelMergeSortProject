package transform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTransformCustomThreads {
    public static Pair<Integer, int[]> transformCustom(String line) {
        String lineArr = getStringAfterArray(line);
        String numberOfThreads = getStringBetweenNumberOfThreadsAndArray(line);
        int[] unsortedArr = MessageTransform.transform(lineArr);
        int numberOfThreadsNumb = Integer.parseInt(numberOfThreads);
        return new Pair<>(numberOfThreadsNumb, unsortedArr);
    }

    public static String getStringAfterArray(String input) {
        String regex = "Array:\\s*(.*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String getStringBetweenNumberOfThreadsAndArray(String input) {

        String regex = "Number of threads:\\s*(.*?)\\s*Array:";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
