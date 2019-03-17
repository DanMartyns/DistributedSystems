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
import genclass.GenericIO;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giselapinto
 */
public class RepairArea implements ManagerRepairArea, MechanicsRepairArea {
    
    /**
     *
     */
    private HashMap<Integer,Integer> CarsToBeRepaired = new LinkedHashMap<>();
    /**
     * number of A pieces
     */
    private int pieceA = 1000;
    /**
     * number of B pieces
     */
    private int pieceB = 1000;
    /**
     * number of C pieces
     */
    private int pieceC = 1000;
    
    /**
     * The mechanic remains in the "read paper" state, while the list is empty. If not, continue
     */
    public synchronized void readThePaper() {
        GenericIO.writelnString("------>>>>> (RepairArea) readThePaper function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.WAITING_FOR_WORK);         
        
        if ( CarsToBeRepaired.isEmpty() ){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RepairArea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * The manager records the repair of a car. 
     * @param customer 
     */
    public synchronized void registerService(int customer) {
        GenericIO.writelnString("------>>>>> (RepairArea) registerService function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.POSTING_JOB);
      
        /**
         * Register a service, means register the customer id with a random piece
         * 0 - piece A
         * 1 - piece B
         * 2 - piece C
         */
        int min = 0;
        int max = 2;
        int range = max - min + 1;
        CarsToBeRepaired.putIfAbsent(customer,(int) (Math.random() * range) + 1 );
        notifyAll();
        
        //return 1;
    }

    /**
     * In terms of simulation, it does no useful work. 
     * Transition state
     */
    public synchronized void startRepairProcedure() {
        GenericIO.writelnString("------>>>>> (RepairArea) startRepairProcedure function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
    }

    /*
    * Getting new pieces
    */
    public synchronized void getRequiredPart() {
        GenericIO.writelnString("------>>>>> (RepairArea) getRequiredPart function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.CHECKING_STOCK);   
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Checking the stock repetily
    * @return if mechanics has parts with him or not
    */
    public synchronized boolean partAvailable() {
        GenericIO.writelnString("------>>>>> (RepairArea) partAvailable function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.CHECKING_STOCK);          
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    * Back to fix
    */
    public synchronized void resumeRepairProcedure() {
        GenericIO.writelnString("------>>>>> (RepairArea) resumeRepairProcedure function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Process the fix the car from customer
    */
    public synchronized int fixIt() {
        GenericIO.writelnString("------>>>>> (RepairArea) fixIt function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
        
        int fix_car = mechanic.getCurrentCarToRepair();
        return fix_car;
    }
}
