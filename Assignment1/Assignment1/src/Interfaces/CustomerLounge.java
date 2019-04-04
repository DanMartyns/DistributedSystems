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
public interface CustomerLounge {
    /**
     * customer is waiting for manager to talk
     * (Class Lounge)
     * @param id from customer
     */
    void queueIn(String id, String customerState);
    
    /**
     * customer talks with manager
     * @param customer 
     */
    void talkWithManager(int customer);
    
    /**
     * customer wants a replecement car so he need the key
     * (Class Lounge)
     */
    void collectKey(int customer, String customerState);
    
    /**
     * customer pay for the service to Manager
     * (Class Lounge)
     */
    void payForTheService(int customer);
    
    
}
