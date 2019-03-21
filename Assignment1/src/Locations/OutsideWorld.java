/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Manager;
import Interfaces.CustomerOutSideWorld;
import Interfaces.ManagerOutsideWorld;
import MainProgram.LoggerInterface;
import genclass.GenericIO;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class OutsideWorld implements ManagerOutsideWorld, CustomerOutSideWorld {
    /**
    * Logger class for debugging.
    */
    private LoggerInterface logger;
    
    /**
    * Boolean variable "phoneCustomer" to wake the Customer from his normal life and go get the car.
    */
    private boolean phoneCustomer = false;
    
    private String currentCustomer;
    

    /**
     * Decide whether to get the car or not.
     * @return true or false, if he wants or not
     */
    public synchronized boolean decideOnRepair() {
        Customer customer = ((Customer)Thread.currentThread());
        GenericIO.writelnString(">>>>> (OutsideWorld) Customer "+customer.getID()+" - decideOnRepair function");
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);        
        return true;//Math.random() > 0.5;

    }

    /**
     * Get back to your normal life without a car. 
     * It waits to be notified that your car is repaired.
     */
    public synchronized void backToWorkByBus(String info) {
        GenericIO.writelnString(">>>>> (OutsideWorld) backToWorkByBus function");

        System.out.println("backToWorkByBus : "+phoneCustomer);
//        if (phoneCustomer == false){
//            try {
//                wait();
//            } catch (InterruptedException ex) {
//                    GenericIO.writelnString("backToWorkByBus - Manager thread was interrupted.");
//            }            
//        } 
//        
//        if( currentCustomer == customer.getID()){
//            phoneCustomer = false;
//        }
        
    }
    /**
     * Get back to your normal life with your car after it's fixed 
     * or with a replacement car. If he is with a replacement car, 
     * he waits to be notified that his car is repaired.
     */
    public synchronized void backToWorkByCar(String info) {
        GenericIO.writelnString(">>>>> (OutsideWorld) backToWorkByCar function");

        System.out.print(info);
        if (phoneCustomer == false){
            try {
                System.out.println("Waiting for manager call");
                wait();
            } catch (InterruptedException ex) {
                    GenericIO.writelnString("backToWorkByCar - Manager thread was interrupted.");
            }            
        }
        
        if( currentCustomer.equals(info)){
            phoneCustomer = false;
        }

    }
    /**
     * Notifies customers that your car is repaired.
     */
    public synchronized void phoneCustomer(String id) {
        GenericIO.writelnString(">>>>> (Lounge) phoneCustomer function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ALERTING_CUSTOMER);
        phoneCustomer = true;
        currentCustomer = id;
        System.out.println("phoneCustomer : "+phoneCustomer);
        notifyAll();

    }    
    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
