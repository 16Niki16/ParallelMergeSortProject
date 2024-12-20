package sort.custom.threads;

import constants.Numbers;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SortCustomThreads implements SortCustomThreadsAPI{
    private int[] arr;
    private int numberOfThreads;
    private BlockingQueue<Pieces> queue;
    private Map<Integer, Integer> intervals;
    public SortCustomThreads(int[] arr, int numberOfThreads) {
        this.queue = new ArrayBlockingQueue<>(numberOfThreads);
        this.arr = arr;
        this.numberOfThreads = numberOfThreads;
        this.intervals = new TreeMap<>();
    }

    public void sortArray(){
        Thread[] consumers = new Thread[numberOfThreads - 1];
        for(int i = 0; i<numberOfThreads - 1; i++){
            consumers[i] = new Thread(new SortThread(queue));
        }
        Thread producer = new Thread(new ProducerThread(queue,arr, numberOfThreads - 1, intervals));
        producer.start();
        for (Thread consumerThread : consumers) {
            consumerThread.start();
        }
        try {
            producer.join();
            for (Thread consumerThread : consumers) {
                consumerThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        MergeSortedArrays.Merger(arr,intervals);

    }
}
