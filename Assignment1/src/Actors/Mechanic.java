package Actors;

import Interfaces.MechanicsLounge;
import Interfaces.MechanicsPark;
import Interfaces.MechanicsRepairArea;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
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
    private int currentCar;
    private String currentPiece;
    private int carFixed = 0;
    
   
    /**
     * Instance of the mechanics interface Repair Area.
     */    
    private MechanicsRepairArea repairArea;
    
    /**
     * Instance of the mechanics interface lounge.
     */
    private MechanicsLounge lounge;
   
    private MechanicsPark park;
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the mechanic
     * @param lounge instance of the lounge
     * @param repairArea instance of the repair area
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

        while(carFixed < NUM_CUSTOMERS){
            //GenericIO.writelnString("Mechanics "+id+" is reading the paper");
            setMechanicState(Mechanic.State.WAITING_FOR_WORK);

            
            repairArea.readThePaper();
            //GenericIO.writelnString("Mechanic "+id+" read paper.");            
            repairArea.startRepairProcedure();
            //GenericIO.writelnString("Mechanic "+id+" start procedure.");            
            setMechanicState(Mechanic.State.FIXING_CAR);
            
            currentCar = repairArea.getVehicle();
            //GenericIO.writelnString("Mechanic "+id+" get vehicle.");            

            //GenericIO.writelnString("Mechanics "+id+" is requiring new parts");
            currentPiece = repairArea.getRequiredPart();
            setMechanicState(Mechanic.State.CHECKING_STOCK);
            //GenericIO.writelnString("Mechanic "+id+" get required part.");            

            if (repairArea.partAvailable(currentPiece, currentCar)){
                //GenericIO.writelnString("Mechanics "+id+" is checking parts available");

                repairArea.resumeRepairProcedure(currentPiece);
                setMechanicState(Mechanic.State.FIXING_CAR);
                //GenericIO.writelnString("Mechanic "+id+" resume repair procedure.");                
            }

            else {
                //GenericIO.writelnString("Mechanics "+id+" is notifing manager");

                lounge.letManagerKnow(currentPiece);
                setMechanicState(Mechanic.State.ALERTING_MANAGER);
                //GenericIO.writelnString("Mechanic "+id+" let manager know.");                
                
                repairArea.readThePaper();
                setMechanicState(Mechanic.State.WAITING_FOR_WORK);
                //GenericIO.writelnString("Mechanic "+id+" read paper.");
                
                repairArea.startRepairProcedure();
                setMechanicState(Mechanic.State.FIXING_CAR);
                //GenericIO.writelnString("Mechanic "+id+" start repair procedure.");
                
                currentCar = repairArea.getVehicle();
                //GenericIO.writelnString("Mechanic "+id+" get vehicle.");            
            }


            repairArea.fixIt();
            GenericIO.writelnString("Mechanics "+id+" is fixing the car");            
            carFixed++;

            park.returnVehicle(currentCar);
            GenericIO.writelnString("Mechanics "+id+" is returning car");
            

            lounge.repairConcluded(currentCar);
            setMechanicState(Mechanic.State.ALERTING_MANAGER);
            GenericIO.writelnString("Mechanics "+id+" repairConcluded");
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

    public int getCurrentCar() {
        return currentCar;
    }

    public String getCurrentPiece() {
        return currentPiece;
    }
        
} 

