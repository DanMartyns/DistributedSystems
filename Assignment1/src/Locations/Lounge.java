/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Manager;
import Actors.Mechanic;
import Interfaces.CustomerLounge;
import Interfaces.ManagerLounge;
import Interfaces.MechanicsLounge;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class Lounge implements CustomerLounge, ManagerLounge, MechanicsLounge {

    /**
     * Queue's Lounge
     */
    LinkedList<Customer> fifo = new LinkedList<>();
    
    /**
     * Queue dedicated the service "ATENDING CUSTOMER".
     */
    Queue<Integer> atending_customer = new LinkedList<>();
    /**
     * Queue dedicated the service "ALERTING CUSTOMER".
     */    
    Queue<Integer> alerting_customer = new LinkedList<>();
    /**
     * Queue dedicated the service "GETING NEW PARTS".
     */    
    Queue<String> getting_new_parts = new LinkedList<>();
    
    /**
     * Boolean variable "talkBetweenManCust" to ensure a conversation between manager and client.
     */
    private boolean talkBetweenManCust = false;
    
    /**
     * Boolean variable "phoneCustomer" to wake the Customer from his normal life and go get the car.
     */
    private boolean phoneCustomer = false;

    /**
     * The costumer go into the Lounge and waits for his turn
     */
    @Override
    public synchronized void queueIn() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);
        fifo.add(customer);
        notifyAll();
    }
    /**
     * 
     * @return 
     */
    @Override    
    public synchronized int collectKey() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.WAITING_FOR_REPLACE_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Para questões de simulação o pagamento é descrito por um tempo de espera entre 0 e 10 segundos
     */
    @Override    
    public synchronized void payForTheService() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);        
    }

     /** 
      * Choose the next task
      * @return
      */
    @Override    
    public synchronized boolean getNextTask() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Choose between 0 and 2 what task will choose 
     * 0 - ATENDING_CUSTOMER
     * 1 - ALERTING_CUSTOMER
     * 2 - GETTING_NEW_PARTS
     * @return 
     */
    @Override    
    public synchronized int appraiseSit() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);

        int min = 0;
        int max = 2;
        int range = max - min + 1;
        
        if (atending_customer.isEmpty()){
            min = 1;
            range = max - min + 1;
            return (int) (Math.random() * range) + 1;
        }else if (alerting_customer.isEmpty()){
           double val = Math.random();
           if (val > 0.5)
               return 2;
           else
               return 0;
        }else if (getting_new_parts.isEmpty()){
            max = 1;
            range = max - min + 1; 
            return (int) (Math.random() * range) + 1;
        }
        return (int) (Math.random() * range) + 1;
    }
    /**
     * Spend some time talking with Customer
     */
    @Override    
    public synchronized void talkToCustomer() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);
        
        if(talkBetweenManCust == false){
            talkBetweenManCust = true;
            notifyAll();
        }
        else {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Lounge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * The customer spend some time talking with the Manager
     */
    @Override   
    public synchronized void talkWithManager() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);
        
        if(talkBetweenManCust == true){
            talkBetweenManCust = false;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Lounge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override    
    public synchronized void handCarKey() { //Perguntar
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);        
        //TODO
    }
    @Override    
    public synchronized void phoneCustomer() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ALERTING_COSTUMER);
        if(phoneCustomer == false && !alerting_customer.isEmpty()){
            phoneCustomer = true;
            notifyAll();
        }
        else{
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Lounge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    @Override    
    public synchronized void receivePayment() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);
    }
    
    
    /*
    * Let manager know that the mechanics needs more pieces from supplier site
    */
    @Override
    public synchronized void letManagerKnow() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.ALERTING_MANAGER);         
    }
    
    /*
    * Notify the repair is concluded
    */
    @Override
    public synchronized void repairConcluded(int currentCar) {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.ALERTING_MANAGER);  
        
        alerting_customer.add(currentCar);
        notifyAll();
    }
    
}
