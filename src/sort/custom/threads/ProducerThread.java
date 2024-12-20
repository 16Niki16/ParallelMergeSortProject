package sort.custom.threads;

import constants.Numbers;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ProducerThread implements Runnable{
    private static final int MAX_ARRAY_CAPACITY = 1000;
    private BlockingQueue<Pieces> queue;
    private int[] arr;
    private int numberOfConsumers;
    private Map<Integer, Integer> intervals;
    public ProducerThread(BlockingQueue<Pieces> queue, int[] arr, int numberOfConsumers, Map<Integer, Integer> intervals) {
        this.queue = queue;
        this.arr = arr;
        this.numberOfConsumers = numberOfConsumers;
        this.intervals = intervals;
    }

    @Override
    public void run() {
        try{
            int startingPoint = Numbers.ZERO;
            while(arr.length - (startingPoint + MAX_ARRAY_CAPACITY) > Numbers.ZERO){
                Pieces piece = new Pieces(arr, startingPoint, startingPoint + MAX_ARRAY_CAPACITY - Numbers.ONE, Numbers.ONE);
                intervals.put(startingPoint, startingPoint + MAX_ARRAY_CAPACITY - Numbers.ONE);
                queue.put(piece);
                startingPoint += MAX_ARRAY_CAPACITY;
            }
            if(startingPoint < arr.length){
                Pieces piece = new Pieces(arr, startingPoint, arr.length - Numbers.ONE, Numbers.ONE);
                intervals.put(startingPoint, arr.length - Numbers.ONE);
                queue.put(piece);
            }
            for(int i = Numbers.ZERO; i<numberOfConsumers;i++){
                queue.put(new Pieces(null,Numbers.ZERO,Numbers.ZERO,Numbers.ZERO));
            }

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
