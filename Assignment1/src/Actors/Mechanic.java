package Actors;

import Locations.Lounge;
import Locations.Park;
import Locations.RepairArea;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Mechanic extends Thread {
    
    public enum State { 
        /**
         * WAITING FOR WORK.
         */
        WAITING_FOR_WORK ("wait"),
        
        /**
         * FIXING THE CAR.
         */
        FIXING_CAR ("fix"),
        
        /**
         * CHECKING THE STOCK.
         */
        CHECKING_STOCK ("check"),
        
        /**
         * ALEARTING THE STOCK.
         */
        ALERTING_MANAGER ("Alert");
        
        private final String description;
        
        private State(String description){
            this.description = description;
        }
        
        @Override
        public String toString(){
            return this.description;
        }
    }
    /**
     * Identifier of the Manager.
     */
    private int id;
    
    /**
     * The State of the Mechanic
     */
    private State state;
    
    /**
     * Instance of the Lounge.
     */
    private Lounge lounge;
    
    /**
     * Instance of the Repair Area.
     */    
    private RepairArea repairArea;

    /**
     * Instance of the Park.
     */
    private Park park;
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the mechanic
     * @param lounge instance of the lounge
     * @param repairArea instance of the repair area
     * @param park instance of the park
     */ 
    public Mechanic(int id, Lounge lounge, RepairArea repairArea, Park park) {
        this.state = State.WAITING_FOR_WORK;
        this.id = id;
        this.lounge = lounge;
        this.repairArea = repairArea;
        this.park = park;
    
    }
    
    
    @Override
    public void run() {
        boolean partsInStock = true;
        while(!repairArea.readThePaper()){
            this.state = State.WAITING_FOR_WORK;
            repairArea.startRepairProcedure();
            park.getVehicle();

            if(!partsInStock){
                repairArea.getRequiredPart();
                repairArea.partAvailable();
            
                if (repairArea.pieces()){
                    repairArea.resumeRepairProcedure();
                }

                else {
                    repairArea.fixIt();

                }
            }
            park.returnVehicle();
            repairArea.repairConcluded();

        }   
    }
    /**
     * Get the Mechanic state
     * 
     * @return mechanic state
     */
    public State getManagerState(){
        return this.state;
    }
    /**
     * Set the manager state
     * @param state mechanic state
     */
    public void setManagerState(State state){
        this.state = state;
    }
} 

