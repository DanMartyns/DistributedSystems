/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Mechanic;

/**
 *
 * @author giselapinto
 */
public class OutsideWorld {
     // lista com os costumers no outside

    public synchronized boolean decideOnRepair() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITH_CAR);        
        //return true or false
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void backToWorkByCar() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITH_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void backToWorkByBus() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
