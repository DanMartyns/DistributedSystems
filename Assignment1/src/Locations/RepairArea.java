/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Car;
import Actors.Mechanic;
import Actors.Manager;
import Interfaces.ManagerRepairArea;
import Interfaces.MechanicsRepairArea;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giselapinto
 */
public class RepairArea implements ManagerRepairArea, MechanicsRepairArea {

    List<Car> repairCars = new ArrayList<>();
    /**
     *
     * @return
     */
    @Override
    public synchronized boolean readThePaper() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.WAITING_FOR_WORK);         
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public synchronized int registerService() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.POSTING_JOB);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void startRepairProcedure() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Getting new pieces
    */
    @Override
    public synchronized void getRequiredPart() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.CHECKING_STOCK);   
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Checking the stock repetily
    * @return if mechanics has parts with him or not
    */
    @Override
    public synchronized boolean partAvailable() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.CHECKING_STOCK);          
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
    * Back to fix
    */
    @Override
    public synchronized void resumeRepairProcedure() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    * Process the fix
    */
    @Override
    public synchronized void fixIt() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        try {
            Thread.sleep((int) (Math.random() * 10 * 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(RepairArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
