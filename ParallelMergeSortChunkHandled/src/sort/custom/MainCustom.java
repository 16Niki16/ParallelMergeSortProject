package sort.custom;

import sort.custom.threads.SortCustomThreads;
import sort.custom.timer.TimerCustomThreads;
import transform.MessageTransform;

import java.util.Arrays;
import java.util.Scanner;

public class MainCustom {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String arr = scan.nextLine();
        int[] unsArr = MessageTransform.transform(arr);
        System.out.println(TimerCustomThreads.TimerParallelCustom(unsArr,5));
        SortCustomThreads ss = new SortCustomThreads(unsArr,5);
        ss.sortArray();
        System.out.println(Arrays.toString(unsArr));
    }
}
