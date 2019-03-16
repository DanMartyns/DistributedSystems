/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.ManagerLounge;
import Interfaces.ManagerRepairArea;
import Interfaces.ManagerSupplierSite;
import static ProblemInformation.Constants.ALERTING_CUSTOMER;
import static ProblemInformation.Constants.ATENDING_CUSTOMER;
import static ProblemInformation.Constants.GETTING_NEW_PARTS;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Manager extends Thread {
    
    public enum State { 
        /**
         * Checking what to do.
         */
        CHECKING_WHAT_TO_DO ("Check"),
        
        /**
         * Attending customer.
         */
        ATTENDING_CUSTOMER ("Attend"),
        
        /**
         * Posting Job.
         */
        POSTING_JOB ("Post"),
        
        /**
         * Supervising the race.
         */
        ALERTING_COSTUMER ("Alert"),
        
        /**
         * Replenish Stock.
         */
        REPLENISH_STOCK ("Replen"),
        
        /**
         * Getting new parts.
         */
        GETTING_NEW_PARTS ("Get");
        
        private final String description;
        
        private State(String description){
            this.description = description;
        }
        
        @Override
        public String toString(){
            return this.description;
        }
    }
    /**
     * Identifier of the Manager.
     */
    private int id;
    
    /**
     * Current manager state.
     */
    private State state;
    
    /**
     * State of the Service.
     */    
    private int stateOfService;
    /**
     * Instance of the manager interface Lounge.
     */
    private ManagerLounge lounge;
    
    /**
     * Instance of the manager interface Supplier Site.
     */    
    private ManagerSupplierSite supplierSite;
    
    /**
     * Instance of the manager interface Repair Area.
     */
    private ManagerRepairArea repairArea;
    
    
    /**
     * Manager constructor
     * 
     * @param id identifier of the manager
     * @param lounge instance of the lounge
     * @param supplierSite instance of the supplier site
     * @param repairArea instance of the repair area
     */    
    public Manager(int id, ManagerLounge lounge, ManagerSupplierSite supplierSite, ManagerRepairArea repairArea) {
        this.state = State.CHECKING_WHAT_TO_DO;
        this.id = id;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.repairArea = repairArea;
        
    }
    /**
     * Implements the life cycle of the broker.
     */ 
    @Override
    public void run(){
        while(lounge.getNextTask()){
            this.state = State.CHECKING_WHAT_TO_DO;
            switch(lounge.appraiseSit()){
                case ATENDING_CUSTOMER:
                    this.state = State.ATTENDING_CUSTOMER;
                    int costumer = lounge.talkToCustomer();
                    lounge.handCarKey();
                    this.stateOfService = repairArea.registerService(costumer); //Updated Status : Posting Job
                    break;
                case ALERTING_CUSTOMER:
                    this.state = State.ALERTING_COSTUMER;
                    lounge.phoneCustomer();
                    lounge.getNextTask();
                    lounge.talkToCustomer();
                    lounge.receivePayment();
                    break;
                case GETTING_NEW_PARTS:
                    this.state = State.GETTING_NEW_PARTS;
                    supplierSite.goToSupplier();
                    supplierSite.storePart(); //Updated Status : Replenish Stock
                    break;
            }
        }
    }
    /**
     * Get the Manager state
     * 
     * @return manager state
     */
    public State getManagerState(){
        return this.state;
    }
    /**
     * Set the manager state
     * @param state manager state
     */
    public void setManagerState(State state){
        this.state = state;
    }
}
