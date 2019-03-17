package Actors;

import Interfaces.MechanicsLounge;
import Interfaces.MechanicsPark;
import Interfaces.MechanicsRepairArea;
import genclass.GenericIO;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Car currently being serviced
     */
    private int currentCarToRepair = 0;
    
   
    /**
     * Instance of the mechanics interface Repair Area.
     */    
    private MechanicsRepairArea repairArea;

    /**
     * Instance of the mechanics interface Park.
     */
    private MechanicsPark park;
    
    
    /**
     * Instance of the mechanics interface lounge.
     */
    private MechanicsLounge lounge;
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the mechanic
     * @param lounge instance of the lounge
     * @param repairArea instance of the repair area
     * @param park instance of the park
     */ 
    public Mechanic(int id, MechanicsLounge lounge, MechanicsRepairArea repairArea, MechanicsPark park) {
        this.state = State.WAITING_FOR_WORK;
        this.id = id;
        this.lounge = lounge;
        this.repairArea = repairArea;
        this.park = park;
    
    }
    
    
    @Override
    public void run() {
        boolean partsInStock = true;
        //while(true){
            GenericIO.writelnString("Mechanics "+id+" is reading the paper");
            this.state = State.WAITING_FOR_WORK;
            repairArea.readThePaper();
            repairArea.startRepairProcedure();
            park.getVehicle();

            if(!partsInStock){
                GenericIO.writelnString("Mechanics "+id+" is requiring new parts");

                repairArea.getRequiredPart();
                
                if (repairArea.partAvailable()){
                    GenericIO.writelnString("Mechanics "+id+" is checking parts available");

                    repairArea.resumeRepairProcedure();
                }

                else {
                    GenericIO.writelnString("Mechanics "+id+" is notifing manager");

                    lounge.letManagerKnow();
                    repairArea.readThePaper();
                    repairArea.startRepairProcedure();
                    park.getVehicle();
                    

                }
            }
            GenericIO.writelnString("Mechanics "+id+" is fixing the car");

            int carfixed = repairArea.fixIt();
            
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mechanic.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentCarToRepair =  park.returnVehicle(carfixed);
            lounge.repairConcluded(currentCarToRepair);

       // }   
    }
    /**
     * Get the Mechanic state
     * 
     * @return mechanic state
     */
    public State getMechanicState(){
        return this.state;
    }
    /**
     * Set the manager state
     * @param state mechanic state
     */
    public void setMechanicState(State state){
        this.state = state;
    }
    /**
     * Get the car that is in the hands of the mechanic 
     * @return the car id
     */
    public int getCurrentCarToRepair() {
        return currentCarToRepair;
    }
    /**
     * Set the car that is in the hands of the mechanic 
     * @param currentCarToRepair 
     */
    public void setCurrentCarToRepair(int currentCarToRepair) {
        this.currentCarToRepair = currentCarToRepair;
    }
        
} 

