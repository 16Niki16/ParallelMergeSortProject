import java.util.Random;
import java.util.Scanner;

public class GenerateNumbers {
    public static void main(String... args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        int len = scan.nextInt();
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < len; i++) {
            build.append(rand.nextInt(10000) + 1).append(',');
        }
        String arr = build.substring(0, build.length() - 1);
        System.out.println(arr);
    }
}
