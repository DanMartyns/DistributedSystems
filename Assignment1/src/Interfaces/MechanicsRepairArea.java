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
public interface MechanicsRepairArea {
    
    /**
     * when mechanics have a service to do, he starts the repair procedure
     * (Class RepairArea)
     * @return car id to procedue repair 
    */
    String startRepairProcedure();
    
 
    /**
     * Mechanics Fix the car
     * (Class RepairArea)
     */
    void fixIt();
 
    /**
     * If mechanics need a part, he goes to his supplier to get the required part
     * (Class RepairArea)
     */
    String getRequiredPart();
    
    /**
     * Mechanics check if the part is available on his supplier
     * (Class RepairArea)
     * @return if mechanics has parts with him or not
     */
    boolean partAvailable();
    
    /**
     * if he has the part on his supplier he continues to finish repair procedure
     * (Class RepairArea)
     */
    void resumeRepairProcedure();
    
    /**
     * Mechanics waits for service, reading the paper
     * (Class RepairArea)
     */
    void readThePaper();   
    
     /**
    * Mechanics get the car to repair
    * (Class RepairArea)
    */
    void getVehicle();
    
    
    /**
    * Mechanics finish de repair procedure and let the car in the park
    * (Class RepairArea)
    */
    int returnVehicle(int carId);
}
