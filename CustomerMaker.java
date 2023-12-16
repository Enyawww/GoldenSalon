package goldensalon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CustomerMaker extends Thread {

    private Salon salon;
    private final Object lock;
    private final Clock clock;
    private Random random = new Random();
    private volatile boolean closingTime = false;
    private int totalCustomer = 1000;

    public CustomerMaker(Object lock, Clock clock, Salon salon) {
        this.lock = lock;
        this.clock = clock;
        this.salon = salon;
    }

    public String getTimestamp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        return dateFormat.format(new Date());

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        for (int i = 1; i < totalCustomer; i++) {
            Customer customer = new Customer(lock, clock, salon);
            customer.setcustomerName(i);
            if (closingTime) {
                System.out.println("\u001B[35m <" + getTimestamp() + "> " + "Closing..... No more new customer! \n");
                break;
            } else {
                try {

                    if (salon.queue.size() <= 10) {

                        salon.queue.put(i);

                        if (salon.chairsSemaphore.availablePermits() > 0) {
                            salon.chairsSemaphore.acquire();
                            System.out.println(" <" + getTimestamp() + "> " + " Customer " + this.getName() + " : Customer " + customer.getcustomerName() + " entered and sitting on a chair at waiting area.");
                            Thread.sleep(random.nextInt(3) * 1000);

                        } else {
                            System.out.println(" <" + getTimestamp() + "> " + " Customer " + this.getName() + " : Customer " + customer.getcustomerName() + " entered but couldn't find a chair, standing at waiting area.");
                            Thread.sleep(random.nextInt(3) * 1000);
                        }
                        System.out.println("\n-----------------------------------------------------------------------------------\n" + "\u001B[33mNumber of Customer at waiting area : " + salon.queue.size() + "\n-----------------------------------------------------------------------------------\n");

                    } else {
                        System.out.println(" <" + getTimestamp() + "> " + " Customer " + this.getName() + " : Customer " + customer.getcustomerName() + " leaves immediately because waiting area is full.");

                    }
                    

                } catch (InterruptedException e) {
                }
            }
        }
    }

    public synchronized void setClosingTime() {
        closingTime = true;
    }
}
