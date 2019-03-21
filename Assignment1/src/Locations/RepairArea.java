/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Mechanic;
import Actors.Manager;
import Interfaces.ManagerRepairArea;
import Interfaces.MechanicsRepairArea;
import MainProgram.LoggerInterface;
import ProblemInformation.Constants;
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
     *
     */
    private Queue<Integer> cars = new LinkedList<>();

    /** 
     * Row of carss blocked for lack of parts.
     */ 
    private Queue<Integer> blockedCars = new LinkedList<>();
    
    /**
     * Queue of repaired cars.
     */
    private Queue<Integer> repairedCars = new LinkedList<>();    
    /**
     * The mechanic remains in the "read paper" state, while the list is empty. If not, continue
     */
    
    private int numCarsRepair = 0;
    
    public synchronized void readThePaper() {
        
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        GenericIO.writelnString(">>>>> (RepairArea) Mechanic "+mechanic.getID()+" - readThePaper function");
        mechanic.setMechanicState(Mechanic.State.WAITING_FOR_WORK);         
        
//        if ( cars.isEmpty() ){
//            try {
//                wait();
//            } catch (InterruptedException ex) {
//                GenericIO.writelnString("readThePaper - Manager thread was interrupted.");
//                System.exit(1);
//            }
//        }
        //return numCarsRepair;
    }
    
    /**
     * The manager records the repair of a car. 
     * @param customer 
     */
    public synchronized void registerService(int customer) {
        GenericIO.writelnString(">>>>> (RepairArea) registerService function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.POSTING_JOB);
      
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
    public synchronized String startRepairProcedure() {
        GenericIO.writelnString(">>>>> (RepairArea) startRepairProcedure function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
//        GenericIO.writelnString("Serviços começados : ");
//        cars.forEach((x) -> { GenericIO.writelnString("Customer : "+x.toString()); });         
//        return ""+cars.poll();
        return "";
    }

    /*
    * Getting new pieces
    */
    public synchronized String getRequiredPart() {
        GenericIO.writelnString(">>>>> (RepairArea) getRequiredPart function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.CHECKING_STOCK);
        
//        int min = 0;
//        int max = 2;
//        int range = max - min + 1;
//        return ""+(Math.random() * range);
        return "";
    }

    /*
    * Checking the stock repetily
    * @return if mechanics has parts with him or not
    */
    public synchronized boolean partAvailable() {
        GenericIO.writelnString(">>>>> (RepairArea) partAvailable function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.CHECKING_STOCK);
        
//        return (mechanic.getCurrentPiece() == "0" && pieceA > 0 || mechanic.getCurrentPiece() == "1" && pieceB > 0 || mechanic.getCurrentPiece() == "2" && pieceC > 0);
        return true;
    }
    /*
    * Back to fix
    */
    public synchronized void resumeRepairProcedure() {
        GenericIO.writelnString(">>>>> (RepairArea) resumeRepairProcedure function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
        
//        if (mechanic.getCurrentPiece() == "0" )
//            pieceA--;
//        if (mechanic.getCurrentPiece() == "1" )
//            pieceB--;
//        if (mechanic.getCurrentPiece() == "2" )
//            pieceC--;
    }

    /*
    * Process the fix the car from customer
    */
    public synchronized void fixIt() {
        GenericIO.writelnString(">>>>> (RepairArea) fixIt function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);

    }
    
    public synchronized void storePart(String peca, int quantidade) {
        GenericIO.writelnString(">>>>> (Supplier Site) storePart function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.REPLENISH_STOCK);        
        
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
    public synchronized void getVehicle() {
        GenericIO.writelnString(">>>>> (Park) getVehicle function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
        
//        GenericIO.writelnString("Lista de carros estacionados : ");
//        cars.forEach((x) -> { GenericIO.writelnString(x.toString()); });
//        GenericIO.writelnString("Lista de carros bloqueados : ");
//        blockedCars.forEach((x) -> { GenericIO.writelnString(x.toString()); });          
//        /**
//         * In terms of simulation, the mechanic pulls the vehicle out of the "blocksCars" queue if it is not empty or the car's queue.
//         */
//        if (!blockedCars.isEmpty()){
//            blockedCars.poll();
//        } 
//        if (!cars.isEmpty()) {
//            cars.poll();
//        }
        
    }
    /**
     * 
     * @return 
     */
    public synchronized int returnVehicle(int carId) {
        GenericIO.writelnString(">>>>> (Park) returnVehicle function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
//        int vehicle = mechanic.getCurrentCarToRepair();
//        mechanic.setCurrentCarToRepair(Constants.NUM_CUSTOMERS+1);
//        return vehicle;
        return 1;
    }    
    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
