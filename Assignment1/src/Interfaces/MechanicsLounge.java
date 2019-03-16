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
    void letManagerKnow();
    
    /**
    * Notify the repair is concluded
    */
    void repairConcluded(int currentCar);
    
}
