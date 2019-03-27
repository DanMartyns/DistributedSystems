/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author giselapinto
 */
public interface MechanicsLounge {
    
    /**
    * Let manager know that the mechanics needs more pieces from supplier site
    */
    void letManagerKnow(String currentPiece);
    
    /**
    * Notify the repair is concluded
    */
    void repairConcluded(int currentCar);
    
    /**
     * Check if a part order has already been ordered
     * @param peca
     * @return true or false
     */
    boolean checkRequest(String peca);
}
