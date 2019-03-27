/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.*;
import MainProgram.GeneralInformationRepo;
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
    private GeneralInformationRepo logger;
    
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
    
    public Lounge(GeneralInformationRepo logger) {
        this.logger = logger;
    }
    /******************************************************************************************
     *                              Synchronization Array
     *****************************************************************************************/

     private boolean[] clients = new boolean[NUM_CUSTOMERS];   
    
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
        while(atending_customer.isEmpty() && alerting_customer.isEmpty() && getting_new_parts.isEmpty()){ 
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("getNextTask - Manager thread was interrupted.");
                System.exit(1);
            }
        }
        
        return true;

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
        GenericIO.writelnString("Manager Fila de Atendimento : "+atending_customer);
        GenericIO.writelnString("Manager Fila de Alertas : "+alerting_customer);
        GenericIO.writelnString("Manager Fila de pedidos de peças : "+getting_new_parts);
        
        return (!getting_new_parts.isEmpty() ? GETTING_NEW_PARTS+"@"+getting_new_parts.poll() : ( !alerting_customer.isEmpty() ? ALERTING_CUSTOMER+"@"+alerting_customer.poll() : ATENDING_CUSTOMER+"@"+atending_customer.poll() ));
        
        
    }
    
    /**
     * The costumer go into the Lounge and waits for his turn
     */
    public synchronized void queueIn(String id) {
        
        atending_customer.add(id);       
        logger.setValueQueueIn(atending_customer.size());
        GenericIO.writelnString("fila de atendimento : "+atending_customer);
        notifyAll();
    }
    /**
     * Synchronization point.
     * In theoretical terms, you will receive the key to the replacement car.
     * In practical terms, synchronization will only be done using the key variable.
     */
    public synchronized void collectKey(int customer) {    

        while(clients [customer] == false){
            try {
                GenericIO.writelnString("collect key - Customer "+customer+" está em espera");
                System.out.println("collect key - Eu customer "+customer+" à espera das chaves do manager. array de booleanos "+Arrays.toString(clients));
                wait();

            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        System.out.println("collect key - Respondi! Sou o "+customer);
        GenericIO.writelnString("Rasto - Antes de colocar o collect key a false "+customer);        
        clients [customer] = false;
        GenericIO.writelnString("Rasto - Depois de colocar o collect key a false "+customer);        
        logger.setNumberWaitingReplece(clients.length);
        notifyAll();
    }
    
    /**
     * Synchronization point.
     * In theoretical terms, it will give the key to the replacement car if the customer wants to.
     * In practical terms, synchronization will only be done using the key variable.
     */
    public synchronized void handCarKey(String info) {
        int id = Integer.parseInt(info.split(",")[0]);
        while(clients [id] == true){
            try {
                wait();

            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }        
        System.out.println("collect key - INFORMATION "+info);
        System.out.println("collect key - Vou dar a chaves ao "+info.split(",")[0]);
        GenericIO.writelnString("Rasto - Antes de colocar o handCarKey a true "+info);        
        clients [Integer.parseInt(info.split(",")[0])] = true; 
        System.out.println("collect key - array de booleanos "+Arrays.toString(clients));
        GenericIO.writelnString("Rasto - Depois de colocar o handCarKey a true "+info);        
        notifyAll();
        
    }
    /**
     * In theoretical terms, spend some time talking to the customer.
     * In practical terms, it is a synchronization point, 
     * where the manager updates the "talkBetweenManCust" variable to true.
     * If the variable is already true, you expect the client to respond 
     * before talking to another client.
     * Remove this customer from the service queue.
     */
    public synchronized void talkToCustomer(String customer) {

        /**
         * The manager starts a conversation by putting the "talk" variable to true
         */
        int id = Integer.parseInt(customer.split(",")[0]);
        while(clients [id] == true){
            try {
                wait();

            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        
        GenericIO.writelnString("Rasto - Antes de colocar o talk a true "+customer);
        clients [Integer.parseInt(customer.split(",")[0])] = true;
        notifyAll();
        GenericIO.writelnString("Rasto - Depois de colocar o talk a true "+customer);        



    }
    
    /**
     * In theoretical terms, the client spends some time talking to the manager.
     * In practical terms, it is a synchronization point where the client updates the "talkBetweenManCust" 
     * variable to false if that client is the client that the manager initiated a conversation.
     * If the variable is already false, it means that the manager has not started a conversation 
     * yet and therefore expects the manager to respond.
     */
    public synchronized void talkWithManager(int customer) {

        /**
         * If the manager has started a conversation and is the client to be answered, 
         * it sets the "talkBetweenManCust" variable to false, 
         * which means that it responded, and wakes up the other threads.
         */
        
        while(clients [customer] == false){
            try {
                wait();

            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        System.out.println("Respondi! Sou o "+customer);
        GenericIO.writelnString("Rasto - Antes de colocar o talk a false "+customer);        
        clients [customer] = false;
        GenericIO.writelnString("Rasto - Depois de colocar o talk a false "+customer);
        notifyAll();
    }
    
    /**
     * In theoretical terms, the manager receives payment from the customer for the service rendered.
     * In practical terms, it is a synchronization point where the manager updates the variable "pay" to false, 
     * meaning the collection of the payment.
     */
    public synchronized void receivePayment(String info) {
        int id = Integer.parseInt(info.split(",")[0]);
        while(clients [id] == true){
            try {
                wait();

            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }        
        System.out.println("Manager vai receber o pagamento do "+info);        
        clients [Integer.parseInt(info.split(",")[0])] = true;
        notifyAll();

    }
    
    /**
     * In theoretical terms will effect the payment.
     * In practical terms, it is just a state of transition.
     */
    public synchronized void payForTheService(int customer) {
        while(clients [customer] == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("talkWithManager - One Customer thread was interrupted.");
                System.exit(1);                                        
            }
        }
        System.out.println("Respondi! Sou o "+customer);
        clients [customer] = false;
        notifyAll();
        
    }    
    
    
    /*
    * Let manager know that the mechanics needs more pieces from supplier site
    */
    public synchronized void letManagerKnow(String peca) {

        getting_new_parts.add(peca);
        logger.setFlagAPieces(peca);
        logger.setFlagBPieces(peca);
        logger.setFlagCPieces(peca);
        GenericIO.writelnString("Mechanic getting new parts => "+peca);
        notifyAll();
    }
    
    /*
    * Notify the repair is concluded
    */
    public synchronized void repairConcluded(int currentCar) {

        alerting_customer.add(currentCar);

        notifyAll();
        System.out.println("Mechanic Alerting repaired car "+currentCar);
    }
    public synchronized boolean checkRequest(String peca){
        return getting_new_parts.contains(peca) ? false : true;
    }
}
