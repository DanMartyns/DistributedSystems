/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author danielmartins
 */
public interface ManagerLounge {
    /**
     * Get a new Task for the Manager (Lounge)
     * @return boolean 
     */
    public boolean getNextTask();
    
    /**
     * Choose what is the new task (Lounge)
     * @return int 
     */
    public int appraiseSit();
    
    /**
     * Talk to Customer (Lounge)
     */
    public int talkToCustomer();
    
    /**
     * Receive Payment (Lounge)
     */
    public void receivePayment();
    
    /**
     * Hand Car Key (Lounge)
     */
    public void handCarKey();
    
    /**
     * Call the Customer (Lounge)
     */
    public void phoneCustomer();    
}
