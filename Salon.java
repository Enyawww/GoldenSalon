package goldensalon;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Salon {

    public BlockingQueue<Integer> queue; // Creating a blocking queue
    public BlockingQueue<Integer> combQueue;
    public BlockingQueue<Integer> scissorsQueue;
    public Semaphore combSemaphore;
    public Semaphore scissorSemaphore;
    public Semaphore chairsSemaphore;

    public Salon() {
        
        queue = new ArrayBlockingQueue<>(10);
        chairsSemaphore = new Semaphore(5);
        combQueue = new ArrayBlockingQueue<>(2);
        scissorsQueue = new ArrayBlockingQueue<>(2);
        combSemaphore = new Semaphore(2);
        scissorSemaphore = new Semaphore(2);
    }
}





