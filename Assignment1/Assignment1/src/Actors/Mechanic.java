package Actors;

import Interfaces.MechanicsLounge;
import Interfaces.MechanicsPark;
import Interfaces.MechanicsRepairArea;
import MainProgram.GeneralInformationRepo;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import static ProblemInformation.Constants.TYPE_PARTS;
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
        while((currentService = repairArea.readThePaper()).equals("end") == false ){
            GenericIO.writelnString("Mechanic "+this.id+" :"+currentService);            
            GenericIO.writelnString("Mechanic "+this.id+" After readPaper");            
            setMechanicState(Mechanic.State.WAITING_FOR_WORK);
            logger.setMechanicState(id, Mechanic.State.WAITING_FOR_WORK.toString());
                       
            repairArea.startRepairProcedure();
            currentVehicle = Integer.parseInt(currentService.split(",")[0]);
            currentPiece = currentService.split(",")[1];
            GenericIO.writelnString("Mechanic "+this.id+" After starRepairProcedure "+currentVehicle+" , "+currentPiece+".");
            setMechanicState( Mechanic.State.FIXING_CAR);
            logger.setMechanicState(id,Mechanic.State.FIXING_CAR.toString());
            
            park.getVehicle(currentVehicle);           
            GenericIO.writelnString("Mechanic "+this.id+" After getVehicle");
            if (currentPiece.equals("-1")){
                currentPiece = repairArea.getRequiredPart();
                setMechanicState(Mechanic.State.CHECKING_STOCK);
                logger.setMechanicState(id, Mechanic.State.CHECKING_STOCK.toString());
                GenericIO.writelnString("Mechanic "+this.id+" After getRequiredPart "+currentPiece+".");                
            }        

            if (repairArea.partAvailable(currentPiece, currentVehicle)){
                GenericIO.writelnString("Mechanic "+this.id+" After part available");                
                repairArea.resumeRepairProcedure(currentPiece);
                GenericIO.writelnString("Mechanic "+this.id+" After resumeRepairProcedure");            
                setMechanicState(Mechanic.State.FIXING_CAR);
                logger.setMechanicState(id, Mechanic.State.FIXING_CAR.toString()); 
               
                repairArea.fixIt();           
                GenericIO.writelnString("Mechanic "+this.id+" After fixIt");            
                park.returnVehicle(currentVehicle);
                GenericIO.writelnString("Mechanic "+this.id+" After return vehicle");
                lounge.repairConcluded(currentVehicle);
                GenericIO.writelnString("Mechanic "+this.id+" After repairConcluded");            
                setMechanicState(Mechanic.State.ALERTING_MANAGER);
                logger.setMechanicState(id, Mechanic.State.ALERTING_MANAGER.toString());
                System.out.println("Mechanic "+this.id+" -------------------------------------------");                 
            }

            else {

                park.blockVehicle(currentVehicle);
                lounge.letManagerKnow(currentPiece);
                setMechanicState(Mechanic.State.ALERTING_MANAGER);
                logger.setMechanicState(id, Mechanic.State.ALERTING_MANAGER.toString());
          
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

