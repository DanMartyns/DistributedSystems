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
public interface ManagerRepairArea {
    /**
     * Register a Service (RepairArea)
     */
    public void registerService(int customer);  
    
    /**
     * Replace the pieces (RepairArea)
     */
    public void storePart(String peca, int quantidade);
    
    /**
     * Role responsible for telling mechanics that work is over (RepairArea)
     */
    public void shutdownNow();
}
