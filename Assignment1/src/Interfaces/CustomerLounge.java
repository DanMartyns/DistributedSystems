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
    void queueIn(int id);
    
    /**
     * customer talks with manager
     * (Class Lounge)
     */
    void talkWithManager();
    
    /**
     * customer wants a replecement car so he need the key
     * @return id from replecement car
     * (Class Lounge)
     */
    int collectKey();
    
    /**
     * customer pay for the service to Manager
     * (Class Lounge)
     */
    void payForTheService();
    
    
}
