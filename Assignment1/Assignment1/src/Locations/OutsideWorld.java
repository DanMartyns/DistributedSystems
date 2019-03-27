/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.CustomerOutSideWorld;
import Interfaces.ManagerOutsideWorld;
import MainProgram.GeneralInformationRepo;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import genclass.GenericIO;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class OutsideWorld implements ManagerOutsideWorld, CustomerOutSideWorld {
    /**
    * Logger class for debugging.
    */
    private GeneralInformationRepo logger;
    
    /**
    * Boolean variable "phoneCustomer" to wake the Customer from his normal life and go get the car.
    */
    private boolean phoneCustomer = false;
    
    /**
     * variable that corresponds to the current customer
     */
    private String currentCustomer = "";
    
    private boolean[] carRepaired = new boolean[NUM_CUSTOMERS];

    public OutsideWorld(GeneralInformationRepo logger) {
        this.logger = logger;
    }

   

    /**
     * Decide whether to get the car or not.
     * @return true or false, if he wants or not
     */
    public synchronized boolean decideOnRepair() {      
        return true; //Math.random() > 0.5;

    }

    /**
     * Synchronization point.
     * Get back to your normal life without a car. 
     * It waits to be notified that your car is repaired.
     */
    public synchronized void backToWorkByBus(int customer) {
        notifyAll();
        System.out.println("Back To Work By Bus : carRepaired ["+customer+"] = "+carRepaired [customer]);        
        while(carRepaired [customer] == false){
            try {
                System.out.println("On Bus! waiting for my repaired car! sou o "+customer);                
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        System.out.println("After Back To Work By Bus : carRepaired ["+customer+"] = "+carRepaired [customer]); 
        System.out.println("Respondi! Sou o "+customer);
        carRepaired [customer] = false;
        notifyAll();


    }
    /**
     * Synchronization point.
     * Get back to your normal life with your car after it's fixed 
     * or with a replacement car. If he is with a replacement car, 
     * he waits to be notified that his car is repaired.
     */
    public synchronized void backToWorkByCar(String info) {
        notifyAll();
        String[] inf = info.split(",");
        int customer = Integer.parseInt(inf[0]);
        logger.setReplecementCar(Integer.parseInt(inf[0]), info);

        System.out.println("Back To Work by car : carRepaired ["+customer+"] = "+carRepaired [customer]);      
        while(carRepaired [customer] == false){
            try {
                System.out.println("With Car! waiting for my repaired car! sou o "+customer);
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        System.out.println("After Back To Work By Car : carRepaired ["+customer+"] = "+carRepaired [customer]); 
        carRepaired [customer] = false;
        

    }
    /**
     * Synchronization point.
     * Notifies customers that your car is repaired.
     */
    public synchronized void phoneCustomer(String info) {
        System.out.println("Phone Customer : carRepaired ["+(Integer.parseInt(info.split(",")[0]))+"] = "+carRepaired [Integer.parseInt(info.split(",")[0])]);
        carRepaired [Integer.parseInt(info.split(",")[0])] = true;  
        notifyAll();
        System.out.println("Phone Customer : carRepaired ["+(Integer.parseInt(info.split(",")[0]))+"] = "+carRepaired [Integer.parseInt(info.split(",")[0])]);        

    }        
}
