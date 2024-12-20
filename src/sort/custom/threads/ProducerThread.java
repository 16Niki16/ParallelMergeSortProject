package sort.custom.threads;

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
            int startingPoint = 0;
            while(arr.length - (startingPoint + MAX_ARRAY_CAPACITY) > 0){
                Pieces piece = new Pieces(arr, startingPoint, startingPoint + MAX_ARRAY_CAPACITY - 1, 1);
                intervals.put(startingPoint, startingPoint + MAX_ARRAY_CAPACITY - 1);
                queue.put(piece);
                startingPoint += MAX_ARRAY_CAPACITY;
            }
            if(startingPoint < arr.length){
                Pieces piece = new Pieces(arr, startingPoint, arr.length - 1, 1);
                intervals.put(startingPoint, arr.length - 1);
                queue.put(piece);
            }
            for(int i = 0; i<numberOfConsumers;i++){
                queue.put(new Pieces(null,0,0,0));
            }

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
