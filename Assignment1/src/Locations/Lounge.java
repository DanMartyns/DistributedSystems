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
import MainProgram.LoggerInterface;
import static ProblemInformation.Constants.ALERTING_CUSTOMER;
import static ProblemInformation.Constants.ATENDING_CUSTOMER;
import static ProblemInformation.Constants.GETTING_NEW_PARTS;
import genclass.GenericIO;
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
     * Logger class for debugging.
     */
    private LoggerInterface logger;
    
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
    Queue<Integer> getting_new_parts = new LinkedList<>();
    
    /**
     * Boolean variable "talkBetweenManCust" to ensure a conversation between manager and client.
     */
    private boolean talkBetweenManCust = false;
    
    /**
     * Variable that identifies the customer being serviced.
     */
    private int currentCustomer = 0;
    
    /**
     * Boolean variable "phoneCustomer" to wake the Customer from his normal life and go get the car.
     */
    private boolean phoneCustomer = false;

    /**
     * The costumer go into the Lounge and waits for his turn
     */
    public synchronized void queueIn(int id) {
        GenericIO.writelnString("------>>>>> (Lounge) queueIn function");

        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);
        atending_customer.add(id);
        notifyAll();
    }
    /**
     * 
     * @return 
     */
    public synchronized int collectKey() {
        GenericIO.writelnString("------>>>>> (Lounge) collectKey function");

        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.WAITING_FOR_REPLACE_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Para questões de simulação o pagamento é descrito por um tempo de espera entre 0 e 10 segundos
     */
    public synchronized void payForTheService() {
        GenericIO.writelnString("------>>>>> (Lounge) payForTheService function");

        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);        
    }

     /** 
      * Choose the next task
      * @return
      */
    public synchronized boolean getNextTask() {
        GenericIO.writelnString("------>>>>> (Lounge) getNextTask function");

        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);
        
        GenericIO.writelnString("------------>>>>> (Lounge) get next task  Atending Customer EMPTY ? "+atending_customer.isEmpty());
        GenericIO.writelnString(" ----------->>>>> (Lounge) get next task Alerting Customer EMPTY ? "+alerting_customer.isEmpty());
        GenericIO.writelnString("------------->>>>> (Lounge) get next task  Geting new parts EMPTY ? "+getting_new_parts.isEmpty());        
       
        
        if(atending_customer.isEmpty() || alerting_customer.isEmpty() || getting_new_parts.isEmpty()){
            
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("-------------->>>>> (Lounge) getNextTask - Manager thread was interrupted.");
                System.exit(1);
            }
        } 

        return true;
         
    }
    /**
     * Choose between 0 and 2 what task will choose 
     * 0 - ATENDING_CUSTOMER
     * 1 - ALERTING_CUSTOMER
     * 2 - GETTING_NEW_PARTS
     * @return 
     */
    public synchronized int appraiseSit() {
        assert(!atending_customer.isEmpty() || !alerting_customer.isEmpty() || !getting_new_parts.isEmpty());
        
        GenericIO.writelnString("------>>>>> (Lounge) appraiseSit function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);
        
        GenericIO.writelnString("------------>>>>> (Lounge)   Atending Customer EMPTY ? "+atending_customer.isEmpty());
        GenericIO.writelnString(" ----------->>>>> (Lounge)  Alerting Customer EMPTY ? "+alerting_customer.isEmpty());
        GenericIO.writelnString("------------->>>>> (Lounge)   Geting new parts EMPTY ? "+getting_new_parts.isEmpty());        
        return ( !atending_customer.isEmpty() ? ATENDING_CUSTOMER : ( !alerting_customer.isEmpty() ? ALERTING_CUSTOMER : GETTING_NEW_PARTS )) ;
//        int min = 0;
//        int max = 2;
//        int range = max - min + 1;
//        int result = 0;
//        
//        if (atending_customer.isEmpty()){
//            min = 1;
//            range = max - min + 1;
//            result = (int) (Math.random() * range) + 1;
//            GenericIO.writelnString("Result 1 :"+result);
//            return result;
//        }else if (alerting_customer.isEmpty()){
//           double val = Math.random();
//           if (val > 0.5){
//                GenericIO.writelnString("Result 2 :"+ 2);
//                return 2;
//           }else{
//                GenericIO.writelnString("Result 3 :"+ 0);
//                return 0;
//           }
//
//        }else if (getting_new_parts.isEmpty()){
//            max = 1;
//            range = max - min + 1; 
//            result = (int) (Math.random() * range) + 1;
//            GenericIO.writelnString("Result 4 :"+result);
//            return result;
//        }
//        result = (int) (Math.random() * range) + 1;
//        GenericIO.writelnString("Result 5 :"+result);
//        return result;

//          if(!atending_customer.isEmpty()){
//              return ATENDING_CUSTOMER;
//          }
//          else if(!alerting_customer.isEmpty()){
//              return ALERTING_CUSTOMER;
//          } 
//          else if(!getting_new_parts.isEmpty()){
//              return GETTING_NEW_PARTS;
//          }
//          return ATENDING_CUSTOMER;
    }
    /**
     * Spend some time talking with Customer
     */
    public synchronized int talkToCustomer() {
        GenericIO.writelnString("------>>>>> (Lounge) talkToCustomer function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);

        /**
         * The manager starts a conversation by putting the "talkBetweenManCust" variable to true
         */
        if(talkBetweenManCust == false){
            talkBetweenManCust = true;
            notifyAll();

        }
        else {
            /**
             * If not, wait for a customer response
             */
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("------------------------talkWithCustomer - Manager thread was interrupted.");
                System.exit(1);
            }
        }
        /**
         * Updates the current customer variable.
         */
        currentCustomer = atending_customer.poll();
        return currentCustomer;
    }
    
    /**
     * The customer spend some time talking with the Manager
     */
    public synchronized void talkWithManager() {
        GenericIO.writelnString("------>>>>> (Lounge) talkWithManager function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.RECEPTION);

        /**
         * If the manager has started a conversation and is the client to be answered, 
         * it sets the "talkBetweenManCust" variable to false, 
         * which means that it responded, and wakes up the other threads.
         */
        if(talkBetweenManCust == true && customer.getID() == currentCustomer){
            talkBetweenManCust = false;
            notifyAll();
        } else {
            /**
             * If it is not the customer being served or the manager has not yet started a conversation, then wait.
             */
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("--------------------talkWithManager - One Customer thread was interrupted.");
                System.exit(1);
            }
        }

    }
    
    public synchronized void handCarKey() { //Perguntar
        GenericIO.writelnString("------>>>>> (Lounge) handCarKey function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);        
        //TODO
    }
    
    public synchronized void phoneCustomer() {
        GenericIO.writelnString("------>>>>> (Lounge) phoneCustomer function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ALERTING_COSTUMER);
        if(phoneCustomer == false && !alerting_customer.isEmpty()){
            phoneCustomer = true;
            alerting_customer.poll();
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
    
    public synchronized void receivePayment() {
        GenericIO.writelnString("------>>>>> (Lounge) receivePayment function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.ATTENDING_CUSTOMER);
    }
    
    
    /*
    * Let manager know that the mechanics needs more pieces from supplier site
    */
    public synchronized void letManagerKnow() {
        GenericIO.writelnString("------>>>>> (Lounge) letManagerKnow function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.ALERTING_MANAGER);         
    }
    
    /*
    * Notify the repair is concluded
    */
    public synchronized void repairConcluded(int currentCar) {
        GenericIO.writelnString("------>>>>> (Lounge) repairConcluded function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.ALERTING_MANAGER);  
        
        alerting_customer.add(currentCar);
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
