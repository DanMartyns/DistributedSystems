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
public interface MechanicsPark {
    
    /**
    * Mechanics get the car to repair
    * (Class Park)
    */
    void getVehicle();
    
    
    /**
    * Mechanics finish de repair procedure and let the car in the park
    * (Class Park)
    */
    void returnVehicle();
    
}