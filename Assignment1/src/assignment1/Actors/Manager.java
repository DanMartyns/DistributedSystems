/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.Actors;

import assignment1.Locations.Lounge;
import assignment1.Locations.RepairArea;
import assignment1.Locations.SupplierSite;
import static assignment1.ProblemInformation.Constants.ALERTING_CUSTOMER;
import static assignment1.ProblemInformation.Constants.ATENDING_CUSTOMER;
import static assignment1.ProblemInformation.Constants.GETTING_NEW_PARTS;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Manager extends Thread {
    private int id;
    
    private int stateOfService;
    
    private Lounge lounge;
    
    private SupplierSite supplierSite;
    
    private RepairArea repairArea;
    
    public Manager(int id, Lounge lounge, SupplierSite supplierSite, RepairArea repairArea) {
        this.id = id;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.repairArea = repairArea;
        
    }
    
    
    @Override
    public void run(){
        while(lounge.getNextTask()){
            switch(lounge.appraiseSit()){
                case ATENDING_CUSTOMER: 
                    lounge.talkToCustomer();
                    lounge.handCarKey();
                    stateOfService = repairArea.registerService();
                    break;
                case ALERTING_CUSTOMER:
                    lounge.phoneCustomer();
                    lounge.getNextTask();
                    lounge.talkToCustomer();
                    lounge.receivePayment();
                    break;
                case GETTING_NEW_PARTS:
                    supplierSite.goToSupplier();
                    supplierSite.storePart();
                    break;
            }
        }
    }
}
