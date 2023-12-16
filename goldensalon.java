package goldensalon;

public class goldensalon {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\u001B[36m--------------- Welcome to Golden Salon ---------------\n");
        System.out.println("\u001B[32mAll hairdresser are sleeping zzZZZ");
        System.out.println("\u001B[32mSalon is ready to open ...");
        System.out.println("\u001B[32mSalon opened! Customer can come in.\n");       
     
        Object lock = new Object();
        Clock clock = new Clock();
        clock.start();  
        Salon salon = new Salon();        
        CustomerMaker cm = new CustomerMaker(lock, clock, salon);
        
        Hairdresser hairdresser1 = new Hairdresser(lock, clock, salon,1);
        hairdresser1.start();
        Hairdresser hairdresser2 = new Hairdresser(lock, clock, salon,2);
        hairdresser2.start();
        Hairdresser hairdresser3 = new Hairdresser(lock, clock, salon,3);
        hairdresser3.start();       
        
        Closing cl = new Closing(hairdresser1,hairdresser2,hairdresser3, cm, clock);
        Thread Ct = new Thread(cl);
        Thread cmt = new Thread(cm);
        
        Ct.start();            
        cmt.start();
        
        Thread.sleep(110000);
        cl.report();
        System.out.println("\nDone for the day ~\n");
        
    }
}
