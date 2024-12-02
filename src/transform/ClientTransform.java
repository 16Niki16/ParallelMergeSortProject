package transform;

import java.util.Arrays;

public class ClientTransform {
    public static int[] transform(String arrayString) {
        return Arrays.stream(arrayString.replace("Sorted array: [", "").replace("]", "").split(","))
            .map(String::strip)
            .filter(s -> !s.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
    }
}
