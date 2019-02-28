package MainProgram.InterveningEntities;

import java.util.*;
/**
 *
 * @author danielmartins
 */
public class Customer extends Thread {
    
    private Random gerador = new Random();
    private int NORMAL_LIFE = gerador.nextInt(60); // random number between 0 and 60
    private int DECIDE_ON_REPAIR = gerador.nextInt(10); //random number between 0 and 10 to decide if he want repair de car or not 
    
    /* Means the Life Cycle of the Customer */
    @Override
    public void run(){
     
    }
}
