package goldensalon;


public class Customer{
    private final Object lock;
    private final Clock clock;
    private Salon salon;
    private int name; 
    public boolean closingTime = false;

    public Customer(Object lock, Clock clock,Salon salon) {
        this.lock = lock;
        this.clock = clock;
        this.salon = salon;
    }
    
    public int getcustomerName(){
        return name;
    }
    
    public void setcustomerName(int name){
        this.name=name;
    }

}