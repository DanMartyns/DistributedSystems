package Actors;

import Interfaces.MechanicsLounge;
import Interfaces.MechanicsRepairArea;
import genclass.GenericIO;

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
    private String currentCar;
    private String currentPiece;
    
   
    /**
     * Instance of the mechanics interface Repair Area.
     */    
    private MechanicsRepairArea repairArea;
    
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
     */ 
    public Mechanic(int id, MechanicsLounge lounge, MechanicsRepairArea repairArea) {
        this.state = State.WAITING_FOR_WORK;
        this.id = id;
        this.lounge = lounge;
        this.repairArea = repairArea;
    
    }
    
    
    @Override
    public void run() {
        boolean partsInStock = true;
        int carfixed = 0;
        while(true){
            GenericIO.writelnString("Mechanics "+id+" is reading the paper");
            this.state = State.WAITING_FOR_WORK;
            repairArea.readThePaper();
            currentCar = repairArea.startRepairProcedure();
            repairArea.getVehicle();

            GenericIO.writelnString("Mechanics "+id+" is requiring new parts");
            currentPiece = repairArea.getRequiredPart();

            if (repairArea.partAvailable()){
                GenericIO.writelnString("Mechanics "+id+" is checking parts available");

                repairArea.resumeRepairProcedure();
            }

            else {
                GenericIO.writelnString("Mechanics "+id+" is notifing manager");

                lounge.letManagerKnow();
                repairArea.readThePaper();
                repairArea.startRepairProcedure();
                repairArea.getVehicle();
            }
            GenericIO.writelnString("Mechanics "+id+" is fixing the car");

            repairArea.fixIt();
            
            GenericIO.writelnString("Mechanics "+id+" is returning car");
            repairArea.returnVehicle(carfixed);
            
            GenericIO.writelnString("Mechanics "+id+" repairConcluded");
            lounge.repairConcluded(currentCar);

       }   
    }
    /**
     * Get the Mechanic ID
     * @return mechanic's id
     */
    public int getID() {
        return id;
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

    public String getCurrentCar() {
        return currentCar;
    }

    public String getCurrentPiece() {
        return currentPiece;
    }
        
} 

