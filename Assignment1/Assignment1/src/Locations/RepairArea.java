/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.ManagerRepairArea;
import Interfaces.MechanicsRepairArea;
import MainProgram.GeneralInformationRepo;
import ProblemInformation.Constants;
import genclass.GenericIO;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
/**
 * @author danielmartins
 * @author giselapinto
 */
public class RepairArea implements ManagerRepairArea, MechanicsRepairArea {

    /**
     * Logger class for debugging.
     */
    private GeneralInformationRepo logger;

    /**
     * The queue of services registered by the manager
     */
    private Queue<Integer> services = new LinkedList<>();
    
    private Map<Integer,String> blockedServices = new HashMap<>();
    
    /**
     * Count completed jobs
     */
    private boolean shutdown = false;

    
    public RepairArea(GeneralInformationRepo logger) {
        this.logger = logger;
    }

    /**
     * The mechanic remains in the "read paper" state, while the lists is empty. If not, continue
     */    
    public synchronized String readThePaper() {
        
        GenericIO.writelnString("Mechanic Lista de serviços : "+services);
        GenericIO.writelnString("Mechanic Lista de serviços bloqueados : "+blockedServices);        
        while ( blockedServices.isEmpty() && services.isEmpty() && shutdown == false ){
            try {
                System.out.println("À espera de mais trabalho");
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("readThePaper - Mechanic thread was interrupted.");
                System.exit(1);
            }
        }
        String service;        
        if (!blockedServices.isEmpty()){
            int key = Integer.parseInt( blockedServices.get(blockedServices.keySet().toArray()[0]) ) ;
            String value = (String) blockedServices.values().toArray()[0];
            service = key+","+value;
            blockedServices.remove(key,value);
        } else {
            service = services.poll()+",-1";
        }
        if(shutdown){
            service = "end";
        }
       notifyAll();
       System.out.println("read paper "+service);
       return service;
    }
    
    /**
     * The manager records the repair of a car. 
     * @param customer 
     */
    public synchronized void registerService(int customer) {
      
        /**
         * Register a service, means register the customer id
         * */
        services.add( customer );
        logger.setNumberServiceRequest(services.size());
        System.out.println("registos : "+services);         
        notifyAll();
    }

    /**
     * In terms of simulation, indicates the service to be done
     * Transition state
     */
    public synchronized void startRepairProcedure() {
    }

    /*
    * Theoretically the mechanic will find out which part is missing from the car
    * A random value is generated to indicate the part missing from the car
    */
    public synchronized String getRequiredPart() {       
        /**
          * 0 - piece A
          * 1 - piece B
          * 2 - piece C
          */
        int min = 0;
        int max = 2;
        return ""+(int)(Math.random() * ((max) + 1));
    }

    /*
    * Checking the stock 
    * If there are no parts, the car is locked and is passed to a next service
    * @return true or false, if mechanics has parts with him or not
    */
    public synchronized boolean partAvailable(String piece, int car) {       
        
        if (piece.equals("0") && Constants.pieceA <= 1 || piece.equals("1") && Constants.pieceB <= 1 || piece.equals("2") && Constants.pieceC <= 1){
            blockedServices.put(car, piece);
            return false;
        } else
            return true;
    }
    /*
    * Decreases the number of pieces after verifying that they have
    */
    public synchronized void resumeRepairProcedure(String piece) {
        logger.setPieces0Stored(Constants.pieceA);
        logger.setPieces1Stored(Constants.pieceB);
        logger.setPieces2Stored(Constants.pieceC);
        
        if (piece.equals("0") )
            Constants.pieceA--;
        if (piece.equals("1") )
            Constants.pieceB--;
        if (piece.equals("2") )
            Constants.pieceC--;    
       
    }

    /*
    * Process the fix the car from customer
    * Transitional State
    */
    public synchronized void fixIt() {
        GenericIO.writelnString("Fixing the car");
    }
    
    /**
     * Function called by the manager to replace parts, according to a part and quantity.
     * @param peca
     * @param quantidade 
     */
    public synchronized void storePart(String peca, int quantidade) {  
        if ( peca.equals("0") ){
            Constants.pieceA = Constants.pieceA + quantidade;
            logger.setPieces0Manager(Constants.pieceA);
        }
        if ( peca.equals("1") ) {
            Constants.pieceB = Constants.pieceB + quantidade;
            logger.setPieces1Manager(Constants.pieceB);
        }

        if ( peca.equals("2") ){
            Constants.pieceC = Constants.pieceC + quantidade;
            logger.setPieces2Manager(Constants.pieceC);
        }
    }
    public synchronized void shutdownNow(){
        this.shutdown = true;
        notifyAll();
    }
     
}
