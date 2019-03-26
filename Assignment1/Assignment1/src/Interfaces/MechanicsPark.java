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
public interface MechanicsPark {
    
    /**
    * Mechanics finish de repair procedure and let the car in the park
    * (Class Park)
    */
    void returnVehicle(int car);
    
    /**
    * Mechanics get the car to repair
    * (Class Park)
    */
    void getVehicle(int car);  
    
    void blockVehicle(int car);
}
