/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Mechanic;
import Actors.Manager;

/**
 *
 * @author giselapinto
 */
public class RepairArea {

    public synchronized int registerService() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.POSTING_JOB);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void startRepairProcedure() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Getting new pieces
    */
    public synchronized void getRequiredPart() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.CHECKING_STOCK);   
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Checking the stock repetily
    */
    public synchronized void partAvailable() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.CHECKING_STOCK);          
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    * Back to fix
    */
    public synchronized void resumeRepairProcedure() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Process the fix
    */
    public synchronized void fixIt() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Notify the repair is concluded
    */
    public synchronized void repairConcluded() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.ALERTING_MANAGER);  
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public synchronized void letManagerKnow() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.ALERTING_MANAGER);         
    }
    
    /*
    * Checking if have enough pieces
    */
    public boolean pieces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
