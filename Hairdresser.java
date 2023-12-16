package goldensalon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.Random;

public class Hairdresser extends Thread {

    private final Object lock;
    private final Clock clock;
    private Salon salon;
    private int name;
    int TotalPayment;
    int CustomerServed;
    private boolean continueCutting = false;
    private boolean closingTime = false;
    private Customer customer;
    private Random random = new Random();

    public BlockingQueue<Salon> Hqueue = new ArrayBlockingQueue<>(3); // Creating a blocking queue  

    public Hairdresser(Object lock, Clock clock, Salon salon, int name) {
        this.lock = lock;
        this.clock = clock;
        this.salon = salon;
        this.name = name;
    }

    public int gethairdresserName() {
        return name;
    }

    public void sethairdresserName(int name) {
        this.name = name;
    }

    public String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
            System.out.println(" <" + getTimestamp() + "> " + " Hairdresser "+this.gethairdresserName()+"wake up and get ready to start working.");
            while (!closingTime || continueCutting) {

                Integer cust = salon.queue.poll();   
                
                if (closingTime && !salon.queue.isEmpty()) {
                    if (cust == null) {
                        break;
                    }                    
                }

                if (cust == null) {
                    if(closingTime){
                        break;
                    }
                    Thread.sleep(1000);
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + " : Hairdresser " + this.gethairdresserName() + " no customer waiting, sleep for a while zzzzz...");
                    Thread.sleep(1000);
                    continue;

                } else if (cust != null) {                   
                    
                    
                    int haircutDuration = (int) (random.nextInt(3) * 1000 + 3000);
                    int progress = 0;
                    int progressIncrement = haircutDuration / 25;

                   System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + " : Hairdresser " + this.gethairdresserName() + " notify by Customer " + cust + " and serve to Chair "+this.gethairdresserName());

                    Thread.sleep(500);

                    if (!salon.combSemaphore.tryAcquire()) {
                        System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                                " : Hairdresser " + this.gethairdresserName() + " cannot get a comb. Waiting...");
                        salon.combSemaphore.acquire(); // Wait until a comb is available
                    }
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " got a comb - "+salon.combSemaphore.availablePermits()+" available." );

                    if (!salon.scissorSemaphore.tryAcquire()) {
                        System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                                " : Hairdresser " + this.gethairdresserName() + " cannot get a pair of scissors. Waiting...");
                        salon.scissorSemaphore.acquire(); // Wait until scissors are available
                    }
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " got a pair of scissors - "+salon.scissorSemaphore.availablePermits()+" available." );

                    while (progress < 100) {
                        System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                                " : Hairdresser " + this.gethairdresserName() + " cutting hair for customer " + cust + " - " + progress + "%");
                        Thread.sleep(progressIncrement);
                        progress += 25;
                    }
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " done hair cut for customer " + cust + " - 100%");
                    Thread.sleep(2000);
                    salon.combSemaphore.release();
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " released a comb - "+salon.combSemaphore.availablePermits()+" available.");
                    Thread.sleep(3000);
                    salon.scissorSemaphore.release();
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " released a pair of scissors - "+salon.scissorSemaphore.availablePermits()+" available.");
                    Thread.sleep(500);
                    int pay = haircutDuration * 10 / 1000;
                    System.out.println(" <" + getTimestamp() + "> " + " Customer " + this.getName() + 
                            " : Customer " + cust + " paid RM " + pay + " - Hair Cut duration " + haircutDuration / 500 + 
                            " minutes.");
                    TotalPayment += pay;
                    Thread.sleep(1000);
                    System.out.println("\u001B[32m <" + getTimestamp() + "> " + " Customer " + this.getName() + 
                            " : Customer " + cust + " exit.");
                    CustomerServed += 1;
                    System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                            " : Hairdresser " + this.gethairdresserName() + " next customer please !");
                    Thread.sleep(2000);
                    salon.chairsSemaphore.release();                    
                }
                if (closingTime && cust != 0) {

                        System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                                " : Hairdresser " + this.gethairdresserName() + " feeling asleep and sleep on Chair " + 
                                this.gethairdresserName());
                        System.out.println(" <" + getTimestamp() + "> " + " Customer " + this.getName() + 
                                " : Customer " + cust + " wake Hairdresser " + this.gethairdresserName() + " and sit on salon chair " + this.gethairdresserName());
                        continueCutting = true;
                    }
            }
            Thread.sleep(5000);
            System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                    " : Hairdresser " + this.gethairdresserName() + " let's sleep, salon closed.");
            Thread.sleep(1000);
            System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                    " : Hairdresser " + this.gethairdresserName() + " - system is calculating my customer served today .....");
        } catch (InterruptedException e) {}
    }

    public synchronized void setClosingTime() {
        closingTime = true;
        System.out.println(" <" + getTimestamp() + "> " + " Hairdresser " + this.getName() + 
                " : Hairdresser " + this.gethairdresserName() + " says : closing? OK, got it.");
    }
}
