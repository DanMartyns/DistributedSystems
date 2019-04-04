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
    public boolean getNextTask(String managerState);
    
    /**
     * Choose what is the new task (Lounge)
     * @return int 
     */
    public String appraiseSit();
    
    /**
     * Talk to Customer (Lounge)
     */
    public void talkToCustomer(String info, String managerState);
    
    /**
     * Receive Payment (Lounge)
     */
    public void receivePayment(String info);
    
    /**
     * Hand Car Key (Lounge)
     */
    public void handCarKey(String customer);
    
}
