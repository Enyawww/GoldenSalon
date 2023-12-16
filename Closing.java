package goldensalon;

public class Closing extends Thread {
    private Hairdresser hairdresser1;
    private Hairdresser hairdresser2;
    private Hairdresser hairdresser3;
    private CustomerMaker customerMaker;
    private Clock clock;

    public Closing(Hairdresser hairdresser1,Hairdresser hairdresser2,Hairdresser hairdresser3, CustomerMaker customerMaker, Clock clock) {
        this.hairdresser1 = hairdresser1;
        this.hairdresser2 = hairdresser2;
        this.hairdresser3 = hairdresser3;
        this.customerMaker = customerMaker;
        this.clock = clock;
    }

    public void run() {
        try {
            Thread.sleep(60000);
            notifyClosed();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void notifyClosed() {
        System.out.println("\n\t\u001B[35mDing Dong ~~~  It's closing time! Ready to close...\n");
        hairdresser1.setClosingTime();
        hairdresser2.setClosingTime();
        hairdresser3.setClosingTime();
        customerMaker.setClosingTime();
        clock.setclosingTime();
    }
    
    public void report() throws InterruptedException{
        int TotalServedCustomer;
        int TotalEarn;
        TotalServedCustomer = hairdresser1.CustomerServed + hairdresser1.CustomerServed + hairdresser1.CustomerServed;
        TotalEarn = hairdresser1.TotalPayment + hairdresser2.TotalPayment + hairdresser3.TotalPayment;
        
        System.out.println("\nGenerating Report........\n");
        Thread.sleep(2000);
        System.out.println("\n===============================================");
        System.out.println("\t\tDaily Report");
        System.out.println("===============================================\n");
        
        System.out.println("Customer Served by Hairdresser 1 \t: "+hairdresser1.CustomerServed);
        System.out.println("Customer Served by Hairdresser 2 \t: "+hairdresser1.CustomerServed);
        System.out.println("Customer Served by Hairdresser 3 \t: "+hairdresser1.CustomerServed);  
        System.out.println("Total Served Customer \t\t\t: "+TotalServedCustomer);
        System.out.println("Money earned by today \t\t\t: RM"+TotalEarn); 
        System.out.println("\n===============================================\n");
    }
}
