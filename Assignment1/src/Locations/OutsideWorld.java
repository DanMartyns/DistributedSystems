/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Interfaces.CustomerOutSideWorld;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giselapinto
 */
public class OutsideWorld implements CustomerOutSideWorld {
     // lista com os costumers no outside

    @Override
    public synchronized boolean decideOnRepair() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITH_CAR);        
        return Math.random() > 0.5;
    }

    @Override
    public synchronized void backToWorkByCar() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITH_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Waits for the repairment of the car
     */
    @Override
    public synchronized void backToWorkByBus() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
        
        assert customer.withCar() == false;
        
        try {
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(OutsideWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
