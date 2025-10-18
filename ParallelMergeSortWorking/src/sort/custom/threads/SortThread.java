package sort.custom.threads;

import java.util.concurrent.BlockingQueue;

public class SortThread implements Runnable {
    private BlockingQueue<Pieces> queue;

    public SortThread(BlockingQueue<Pieces> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pieces piece = queue.take();
                if (piece.checkPoison()) {
                    break;
                }
                piece.mergeSort();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
