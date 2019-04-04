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
    
    /**
     * Boolean array, index = carID, and the value = true or false. It means if the car of carID was repaired or not.
     */
    private boolean[] carRepaired = new boolean[NUM_CUSTOMERS];

    public OutsideWorld(GeneralInformationRepo logger) {
        this.logger = logger;
    }

   

    /**
     * Decide whether to get the car or not.
     * @return true or false, if he wants or not
     */
    public synchronized boolean decideOnRepair(int customer, String customerState) {
        logger.setCustomerState(customer, customerState);
        return Math.random() > 0.5;

    }

    /**
     * Synchronization point.
     * Get back to your normal life without a car. 
     * It waits to be notified that your car is repaired.
     */
    public synchronized void backToWorkByBus(int customer, String customerState) {
        logger.setCustomerState(customer, customerState);    
        notifyAll();     
        while(carRepaired [customer] == false){
            try {              
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }

        carRepaired [customer] = false;
        notifyAll();


    }
    /**
     * Synchronization point.
     * Get back to your normal life with your car after it's fixed 
     * or with a replacement car. If he is with a replacement car, 
     * he waits to be notified that his car is repaired.
     */
    public synchronized void backToWorkByCar(String info, String customerState) {
        
        notifyAll();
        String[] inf = info.split(",");
        int customer = Integer.parseInt(inf[0]);
        logger.setReplecementCar(Integer.parseInt(inf[0]), info);
        logger.setCustomerState(customer, customerState);
        while(carRepaired [customer] == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        carRepaired [customer] = false;
        

    }
    /**
     * Synchronization point.
     * Notifies customers that your car is repaired.
     */
    public synchronized void phoneCustomer(String info, String managerState) {
        logger.setManagerState(managerState);
        carRepaired [Integer.parseInt(info.split(",")[0])] = true;  
        notifyAll();      
    }        
}
