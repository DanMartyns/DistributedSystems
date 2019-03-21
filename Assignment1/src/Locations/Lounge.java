/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.*;
import Interfaces.*;
import MainProgram.LoggerInterface;
import genclass.GenericIO;
import java.util.*;
import static ProblemInformation.Constants.ALERTING_CUSTOMER;
import static ProblemInformation.Constants.ATENDING_CUSTOMER;
import static ProblemInformation.Constants.GETTING_NEW_PARTS;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
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
     * Number of Repaired Cars
     */
    private int numCustomers;
    
    /**
     * Queue dedicated the service "ATENDING CUSTOMER".
     */
    Queue<String> atending_customer = new LinkedList<>();
    /**
     * Queue dedicated the service "ALERTING CUSTOMER".
     */    
    Queue<Integer> alerting_customer = new LinkedList<Integer>();
    /**
     * Queue dedicated the service "GETING NEW PARTS".
     */    
    Queue<String> getting_new_parts = new LinkedList<>();
    
    
    private String currentCustomer;
    
    /******************************************************************************************
     *                              Synchronization Variables
     *****************************************************************************************/
    /**
     * Boolean variable "talk" to ensure a conversation between manager and client.
     */
    private boolean talk = false;
    
    /**
     * Boolean variable "talk2" to ensure a conversation between manager and client, about the exchange of keys.
     */    
    private boolean key = false;
    
    /**
     * Boolean variable "pay" to guarantee the payment of the service.
     */
    private boolean pay = false;
    
    /******************************************************************************************
     *
     *****************************************************************************************/
     /** 
      * Choose the next task
      * In theoretical terms, it will confirm if there is a new task to be done.
      * In practical terms, the manager waits if the vectors of each service are empty. 
      * As soon as they are not, they perform a task.
      * @return true, when the vectores are not empty
      */
    public synchronized boolean getNextTask() {    
        System.out.println("NumCustomers :"+numCustomers);
        if(numCustomers < NUM_CUSTOMERS){
            
            while(atending_customer.isEmpty() && alerting_customer.isEmpty() && getting_new_parts.isEmpty()){ 
                try {
                    //GenericIO.writelnString("getNextTask - Waiting for new task."); 
                    wait();
                } catch (InterruptedException ex) {
                    GenericIO.writelnString("getNextTask - Manager thread was interrupted.");
                    System.exit(1);
                }
            } 

            return true;
        } else
            return false;
         
    }    

    /**
     * In theoretical terms, you will choose one of the services. 
     * In practical terms, it will choose one of the vectors, 
     * prioritizing to look for new parts, followed by alerting 
     * the customers and finally serving the customers. 
     * 0 - ATENDING_CUSTOMER
     * 1 - ALERTING_CUSTOMER
     * 2 - GETTING_NEW_PARTS
     * @return 0,1 or 2, which means the service number
     */
    public synchronized String appraiseSit() {
        assert(!atending_customer.isEmpty() || !alerting_customer.isEmpty() || !getting_new_parts.isEmpty());
        
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.CHECKING_WHAT_TO_DO);
        String choice;
        
        if(!atending_customer.isEmpty()){ 
            choice = ATENDING_CUSTOMER+"@"+atending_customer.poll();
            currentCustomer = choice.split("@")[1].toString();
        }
        else if (!alerting_customer.isEmpty())
            choice = ALERTING_CUSTOMER+"@"+alerting_customer.poll();
        else
            choice = GETTING_NEW_PARTS+"@"+getting_new_parts.poll();
        System.out.println("Choice "+choice);
        return choice;
    }
    
    /**
     * The costumer go into the Lounge and waits for his turn
     */
    public synchronized void queueIn(String id) {
        atending_customer.add(id);
        notifyAll();
    }
    /**
     * Synchronization point.
     * In theoretical terms, you will receive the key to the replacement car.
     * In practical terms, synchronization will only be done using the key variable.
     */
    public synchronized void collectKey(String info) {    
        String[] inf = info.split(",");
        
        if(!inf[2].equals(currentCustomer) && key == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("collectKey - One Customer thread was interrupted.");
                System.exit(1);
            }
        } else {
            key = false;
        }
        
    }
    
    /**
     * Synchronization point.
     * In theoretical terms, it will give the key to the replacement car if the customer wants to.
     * In practical terms, synchronization will only be done using the key variable.
     */
    public synchronized void handCarKey(String info) {

        String[] inf = info.split(",");

        if(inf[3].equals("1") && key == false){
            key = true;
            notifyAll();
        }
      
    }
    /**
     * In theoretical terms, spend some time talking to the customer.
     * In practical terms, it is a synchronization point, 
     * where the manager updates the "talkBetweenManCust" variable to true.
     * If the variable is already true, you expect the client to respond 
     * before talking to another client.
     * Remove this customer from the service queue.
     */
    public synchronized void talkToCustomer() {

        /**
         * The manager starts a conversation by putting the "talk" variable to true
         */
        if(talk == false){
            talk = true;
            notifyAll();
        }
        

    }
    
    /**
     * In theoretical terms, the client spends some time talking to the manager.
     * In practical terms, it is a synchronization point where the client updates the "talkBetweenManCust" 
     * variable to false if that client is the client that the manager initiated a conversation.
     * If the variable is already false, it means that the manager has not started a conversation 
     * yet and therefore expects the manager to respond.
     */
    public synchronized void talkWithManager(String info) {
        String[] inf = info.split(",");
        
        /**
         * If the manager has started a conversation and is the client to be answered, 
         * it sets the "talkBetweenManCust" variable to false, 
         * which means that it responded, and wakes up the other threads.
         */

        if (talk == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);
            }
        }
        
        if(inf[0].equals(currentCustomer)){
            talk = false;
            notifyAll();            
        } 

    }
    
    /**
     * In theoretical terms, the manager receives payment from the customer for the service rendered.
     * In practical terms, it is a synchronization point where the manager updates the variable "pay" to false, 
     * meaning the collection of the payment.
     */
    public synchronized void receivePayment() {
        
        while( pay == false ){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("receivePayment - Manager thread was interrupted.");
                System.exit(1);
            }
        }
        pay = false;
    }
    
    /**
     * In theoretical terms will effect the payment.
     * In practical terms, it is just a state of transition.
     */
    public synchronized void payForTheService(String info) {
        
        String[] inf = info.split(",");
        
        if ( inf[4].equals("1")){
            pay = true;
            notifyAll();
        }
    }    
    
    
    /*
    * Let manager know that the mechanics needs more pieces from supplier site
    */
    public synchronized void letManagerKnow(String peca) {
        
        getting_new_parts.add(peca);
        notifyAll();
        getting_new_parts.forEach((x) -> {System.out.println("Inside Getting New Parts : "+x);});
    }
    
    /*
    * Notify the repair is concluded
    */
    public synchronized void repairConcluded(int currentCar) {
        
        alerting_customer.add(currentCar);
        notifyAll();
        alerting_customer.forEach((x) -> {System.out.println("Inside Alerting_Customer : "+x);});
        numCustomers++;        
    }
    
    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }
    
}
