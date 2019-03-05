/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Locations.Lounge;
import Locations.Park;
import Locations.RepairArea;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Mechanic extends Thread {
    private int id;
    
    private int parts;
    
    private Lounge lounge;
    
    private RepairArea repairArea;
    
    private Park park;
    
    public Mechanic(int id, int parts, Lounge lounge, RepairArea repairArea, Park park) {
        this.id = id;
        this.parts = parts;
        this.lounge = lounge;
        this.repairArea = repairArea;
        this.park = park;
    
    }
    
    
    @Override
    public void run() {
        boolean partsInStock = true;
        while(!lounge.readThePaper()){
            repairArea.startRepairProcedure();
            park.getVehicle();

            if(partsInStock){
                repairArea.getRequiredPart();
                repairArea.partAvailable();

                if(parts>=1){
                    repairArea.resumeRepairProcedure();

                }

                else {
                    repairArea.fixIt();

                }

                park.returnVehicle();
                repairArea.repairConcluded();

            }   
        }
    } 
}
