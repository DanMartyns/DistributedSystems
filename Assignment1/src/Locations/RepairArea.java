/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.ManagerRepairArea;
import Interfaces.MechanicsRepairArea;
import MainProgram.LoggerInterface;
import ProblemInformation.Constants;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import genclass.GenericIO;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author giselapinto
 */
public class RepairArea implements ManagerRepairArea, MechanicsRepairArea {

    /**
     * Logger class for debugging.
     */
    private LoggerInterface logger;

    /**
     * The queue of cars parked.
     */
    private Queue<Integer> cars = new LinkedList<>();    

    
    /** 
     * Row of carss blocked for lack of parts.
     */ 
    private Queue<Integer> blockedCars = new LinkedList<>();
    
    
    private int numCustomers = 0;

    /**
     * The mechanic remains in the "read paper" state, while the list is empty. If not, continue
     */    
    public synchronized int readThePaper() {        
        
        if ( cars.isEmpty() && blockedCars.isEmpty() ){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("readThePaper - Mechanic thread was interrupted.");
                System.exit(1);
            }
        }
        return numCustomers;
    }
    
    /**
     * The manager records the repair of a car. 
     * @param customer 
     */
    public synchronized void registerService(int customer) {
      
        /**
         * Register a service, means register the customer id with a random piece
         * 0 - piece A
         * 1 - piece B
         * 2 - piece C
         */
        cars.add( customer );
        notifyAll();
        
        GenericIO.writelnString("Serviços registados : ");
        cars.forEach((x) -> { GenericIO.writelnString("Customer : "+x.toString()); }); 
    }

    /**
     * In terms of simulation, it does no useful work. 
     * Transition state
     */
    public synchronized void startRepairProcedure() {
        GenericIO.writelnString("Serviços começados");
//        cars.forEach((x) -> { GenericIO.writelnString("Customer : "+x.toString()); });         
//        return ""+cars.poll();
    }

    /*
    * Getting new pieces
    */
    public synchronized String getRequiredPart() {       
        
        int min = 0;
        int max = 2;
        return ""+(int)(Math.random() * ((max) + 1));
    }

    /*
    * Checking the stock repetily
    * @return if mechanics has parts with him or not
    */
    public synchronized boolean partAvailable(String piece, int car) {       
        
        if ((piece.equals("0") && Constants.pieceA > 0 || piece.equals("1") && Constants.pieceB > 0 || piece.equals("2") && Constants.pieceC > 0) == false ){
            blockedCars.add(car);
            return false;
        } else
            return true;
    }
    /*
    * Back to fix
    */
    public synchronized void resumeRepairProcedure(String piece) {
        
        if (piece.equals("0") )
            Constants.pieceA--;
        if (piece.equals("1") )
            Constants.pieceB--;
        if (piece.equals("2") )
            Constants.pieceC--;      
    }

    /*
    * Process the fix the car from customer
    */
    public synchronized void fixIt() {;        

    }
    
    public synchronized void storePart(String peca, int quantidade) {  
        
        if ( peca.equals("0") )
            Constants.pieceA = Constants.pieceA + quantidade;
        if ( peca.equals("1") )
            Constants.pieceB = Constants.pieceB + quantidade;
        if ( peca.equals("2") )
            Constants.pieceC = Constants.pieceC + quantidade;
    }

    /**
     * Method used by the mechanic to get the car to be repaired
     */
    public synchronized int getVehicle() {
        
        GenericIO.writelnString("Lista de carros estacionados : ");
        cars.forEach((x) -> { GenericIO.writelnString(x.toString()); });
        GenericIO.writelnString("Lista de carros bloqueados : ");
        blockedCars.forEach((x) -> { GenericIO.writelnString(x.toString()); });          
        assert !blockedCars.isEmpty() || !cars.isEmpty();
        
        int carToBeRepaired = 0;
        /**
         * In terms of simulation, the mechanic pulls the vehicle out of the "blocksCars" queue if it is not empty or the car's queue.
         */
        if (!blockedCars.isEmpty() ){
            carToBeRepaired = blockedCars.poll();
        } 
        if (!cars.isEmpty()) {
            carToBeRepaired = cars.poll();
        }
        return carToBeRepaired;
    }
    
    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
