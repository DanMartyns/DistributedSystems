/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Manager;
import Actors.Mechanic;

/**
 *
 * @author giselapinto
 */
public class Lounge {

    public synchronized void queueIn() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.RECEPTION);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // lista de clientes para falarem com o manager
    
    // lista de clientes para pagar

    public synchronized void talkWithManager() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.RECEPTION);
        // wait()??
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized int collectKey() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.WAITING_FOR_REPLACE_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void payForTheService() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.RECEPTION);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized boolean getNextTask() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized int appraiseSit() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void talkToCustomer() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);        
        // wait()??
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void handCarKey() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void phoneCustomer() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ALERTING_COSTUMER);        
        //notifyAll()
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void receivePayment() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    public synchronized boolean readThePaper() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.WAITING_FOR_WORK);         
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
