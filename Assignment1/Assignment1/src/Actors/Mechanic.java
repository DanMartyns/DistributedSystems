package Actors;

import Interfaces.MechanicsLounge;
import Interfaces.MechanicsPark;
import Interfaces.MechanicsRepairArea;
import MainProgram.GeneralInformationRepo;
import static ProblemInformation.Constants.TYPE_PARTS;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Mechanic extends Thread {


   
    public enum State { 
        /**
         * WAITING FOR WORK.
         */
        WAITING_FOR_WORK ("WFW"),
        
        /**
         * FIXING THE CAR.
         */
        FIXING_CAR ("FIX"),
        
        /**
         * CHECKING THE STOCK.
         */
        CHECKING_STOCK ("CHS"),
        
        /**
         * ALEARTING THE STOCK.
         */
        ALERTING_MANAGER ("ALM");
        
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
    private String currentService = "";
    private int currentVehicle = 0;
    
    /** 
     * Piece currently being serviced 
     */
    private String currentPiece =  ""+TYPE_PARTS;
    
    
    private final GeneralInformationRepo logger;

   
    /**
     * Instance of the mechanics interface Repair Area.
     */    
    private MechanicsRepairArea repairArea;
    
    /**
     * Instance of the mechanics interface lounge.
     */
    private MechanicsLounge lounge;

    /**
     * Instance of the mechanics interface park.
     */    
    private MechanicsPark park;
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the mechanic
     * @param lounge instance of the lounge
     * @param repairArea instance of the repair area
     */ 
    public Mechanic(int id, MechanicsLounge lounge, MechanicsRepairArea repairArea, MechanicsPark park, GeneralInformationRepo logger) {
        this.state = State.WAITING_FOR_WORK;
        this.id = id;
        this.lounge = lounge;
        this.repairArea = repairArea;
        this.park = park;
        this.logger = logger;
    
    }
    
    
    @Override
    public void run() {
        setMechanicState(Mechanic.State.WAITING_FOR_WORK);
        logger.setMechanicState(id, Mechanic.State.WAITING_FOR_WORK.toString());
        
        while((currentService = repairArea.readThePaper(id, Mechanic.State.WAITING_FOR_WORK.toString())).equals("end") == false ){
           
            setMechanicState(Mechanic.State.WAITING_FOR_WORK);
                       
            repairArea.startRepairProcedure(id, Mechanic.State.FIXING_CAR.toString());
            currentVehicle = Integer.parseInt(currentService.split(",")[0]);
            currentPiece = currentService.split(",")[1];
            
            park.getVehicle(currentVehicle);           

            if (currentPiece.equals("-1")){
                currentPiece = repairArea.getRequiredPart(id,Mechanic.State.CHECKING_STOCK.toString());  
            }        

            if (repairArea.partAvailable(currentPiece, currentVehicle)){
              
                repairArea.resumeRepairProcedure(currentPiece, id, Mechanic.State.FIXING_CAR.toString());  
                repairArea.fixIt();                      
                park.returnVehicle(currentVehicle);
                lounge.repairConcluded(currentVehicle, id, Mechanic.State.ALERTING_MANAGER.toString());               
            }
            else {
                park.blockVehicle(currentVehicle);
                if (lounge.checkRequest(currentPiece))
                    lounge.letManagerKnow(currentPiece, id, Mechanic.State.ALERTING_MANAGER.toString());                          
            }
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

    public String getCurrentService() {
        return currentService;
    }

    public String getCurrentPiece() {
        return currentPiece;
    }
        
} 

