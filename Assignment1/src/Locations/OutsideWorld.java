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

    public synchronized boolean decideOnRepair() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) decideOnRepair function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);        
        return Math.random() > 0.5;

    }

    public synchronized void backToWorkByCar() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) backToWorkByCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);

        if (phoneCustomer == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                    GenericIO.writelnString("backToWorkByCar - Manager thread was interrupted.");
            }            
        }

        phoneCustomer = false;
    }

    /**
     * Waits for the repairment of the car
     */
    public synchronized void backToWorkByBus() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) backToWorkByBus function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
        
        if (phoneCustomer == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                    GenericIO.writelnString("backToWorkByBus - Manager thread was interrupted.");
            }            
        }

        phoneCustomer = false;
        
    }

    public synchronized void phoneCustomer() {
        GenericIO.writelnString("------>>>>> (Lounge) phoneCustomer function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ALERTING_COSTUMER);
        phoneCustomer = true;
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
