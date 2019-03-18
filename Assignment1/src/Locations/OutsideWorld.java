/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Interfaces.CustomerOutSideWorld;
import MainProgram.LoggerInterface;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class OutsideWorld implements CustomerOutSideWorld {
    /**
    * Logger class for debugging.
    */
    private LoggerInterface logger;

    public synchronized boolean decideOnRepair() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) decideOnRepair function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);        
        //return Math.random() > 0.5;
        return true;

    }

    public synchronized void backToWorkByCar() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) backToWorkByCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);
        try {
            //assert customer.withCar() == false;

            sleep(10);
//        try {
//            wait();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (InterruptedException ex) {
            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Waits for the repairment of the car
     */
    public synchronized void backToWorkByBus() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) backToWorkByBus function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
        
        try {
            //assert customer.withCar() == false;

            sleep(10);
//        try {
//            wait();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (InterruptedException ex) {
            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
