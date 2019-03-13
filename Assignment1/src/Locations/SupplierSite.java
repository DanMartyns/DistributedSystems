/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Manager;
import Actors.Manager.State;

/**
 *
 * @author giselapinto
 */
public class SupplierSite {

    public synchronized void goToSupplier() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(State.GETTING_NEW_PARTS);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void storePart() {
        Manager manager = ((Manager)Thread.currentThread());
        manager.setManagerState(Manager.State.REPLENISH_STOCK);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
