/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.ManagerSupplierSite;
import MainProgram.GeneralInformationRepo;

/**
 *
 * @author giselapinto
 */
public class SupplierSite implements ManagerSupplierSite {

    /**
     * Logger class for debugging.
     */
    private GeneralInformationRepo logger;
    
    private int[] sendingPieces = new int[3];

    public SupplierSite(GeneralInformationRepo logger) {
        this.logger = logger;
    }

    /**
     * The choice of quantity to be replenished in the stock of the repair area is random. 
     * Since this quantity is added to a sales record, where each index of the array 
     * corresponds to the id of the part, and its value is the quantity sold so far.
     * @param peca
     * @return random number of parts of a part type.
     */
    public synchronized int goToSupplier(String peca, String managerState) {
        logger.setManagerState(managerState);
        int min = 1;
        int max = 9;
        int range = max - min + 1;
        int result = (int) (Math.random() * range);
        this.sendingPieces[Integer.parseInt(peca)] = this.sendingPieces[Integer.parseInt(peca)] + result;
        
        return result;
    }
        
}
