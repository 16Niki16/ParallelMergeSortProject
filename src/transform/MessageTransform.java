package transform;

import java.util.Arrays;

public class MessageTransform {

    public static int[] transform(String arrayString) {
        return Arrays.stream(arrayString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
