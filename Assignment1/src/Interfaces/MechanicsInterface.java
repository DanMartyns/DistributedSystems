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
public interface MechanicsInterface {
    
    /**
     * Mechanics waits for service, reading the paper
     * @return if mechanics is reading or not the paper
     * (Class Lounge)
     */
    boolean readThePaper();
    
    /**
     * when mechanics have a service to do, he starts the repair procedure
     * (Class RepairArea)
     */
    void startRepairProcedure();
    
    /**
     * Mechanics get the car to repair
     * (Class Park)
     */
    void getVehicle();
    
    /**
     * Mechanics Fix the car
     * (Class RepairArea)
     */
    void fixIt();
    
    /**
     * Mechanics finish de repair procedure and let the car in the park
     * (Class Park)
     */
    void returnVehicle();
    
    /**
     * If mechanics need a part, he goes to his supplier to get the required part
     * (Class RepairArea)
     */
    void getRequiredPart();
    
    /**
     * Mechanics check if the part is available on his supplier
     * (Class RepairArea)
     */
    void partAvailable();
    
    /**
     * if he has the part on his supplier he continues to finish repair procedure
     * (Class RepairArea)
     */
    void resumeRepairProcedure();
    
    /**
     * if mechanics hasn't the part in his supplier he let manager know that
     * (Class RepairArea)
     */
    void letManagerKnow();
    
    /**
     * Mechanics concluded de repair procedure
     * (Class RepairArea)
     */
    void repairConcluded();
   
    
}
