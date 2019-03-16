/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Interfaces.CustomerOutSideWorld;
import genclass.GenericIO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giselapinto
 */
public class OutsideWorld implements CustomerOutSideWorld {
     // lista com os costumers no outside

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Waits for the repairment of the car
     */
    public synchronized void backToWorkByBus() {
        GenericIO.writelnString("------>>>>> (OutsideWorld) backToWorkByBus function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
        
        //assert customer.withCar() == false;
        
        try {
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
