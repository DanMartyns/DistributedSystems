/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Manager;
import Actors.Manager.State;
import Interfaces.ManagerSupplierSite;
import MainProgram.LoggerInterface;
import genclass.GenericIO;

/**
 *
 * @author giselapinto
 */
public class SupplierSite implements ManagerSupplierSite {

    /**
     * Logger class for debugging.
     */
    private LoggerInterface logger;
    
    public synchronized int goToSupplier(String peca) {
        GenericIO.writelnString(">>>>> (Supplier Site) goToSupplier function");
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(State.GETTING_NEW_PARTS);
        
        int min = 1;
        int max = 9;
        int range = max - min + 1;
        return (int) (Math.random() * range);
    }
    

    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
