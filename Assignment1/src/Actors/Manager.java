/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.ManagerLounge;
import Interfaces.ManagerOutsideWorld;
import Interfaces.ManagerRepairArea;
import Interfaces.ManagerSupplierSite;
import static ProblemInformation.Constants.ALERTING_CUSTOMER;
import static ProblemInformation.Constants.ATENDING_CUSTOMER;
import static ProblemInformation.Constants.GETTING_NEW_PARTS;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import genclass.GenericIO;

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
        ALERTING_CUSTOMER ("Alert"),
        
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
    
    private ManagerOutsideWorld outsideWorld;
    
    
    /**
     * Manager constructor
     * 
     * @param id identifier of the manager
     * @param lounge instance of the lounge
     * @param supplierSite instance of the supplier site
     * @param repairArea instance of the repair area
     */
    
    public Manager(int id, ManagerLounge lounge, ManagerSupplierSite supplierSite, ManagerRepairArea repairArea, ManagerOutsideWorld outsideWorld) {
        this.state = State.CHECKING_WHAT_TO_DO;
        this.id = id;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.repairArea = repairArea;
        this.outsideWorld = outsideWorld;
        
    }
    /**
     * Implements the life cycle of the broker.
     */ 
    @Override
    public void run(){
        GenericIO.writelnString("Manager is alive!");

        while(lounge.getNextTask()){
            //GenericIO.writelnString("Manager is checking what to do");
            setManagerState(Manager.State.ATTENDING_CUSTOMER);
            
            String[] choice = lounge.appraiseSit().split("@");

            if(choice[0].equals(ATENDING_CUSTOMER)){
                
                GenericIO.writelnString("Manager is atending customer");
                
                lounge.talkToCustomer();
                setManagerState(Manager.State.ATTENDING_CUSTOMER);
                GenericIO.writelnString("Manager talk to customer.");
                
                lounge.handCarKey(choice[1]);
                GenericIO.writelnString("Manager hand car key.");
                
                repairArea.registerService(Integer.parseInt(choice[1].split(",")[1])); //Updated Status : Posting Job
                setManagerState(Manager.State.POSTING_JOB);
                GenericIO.writelnString("Manager register service.");
            } else if(choice[0].equals(ALERTING_CUSTOMER)){
                
                GenericIO.writelnString("Manager is alerting customer");
                
                outsideWorld.phoneCustomer(choice[1]);
                setManagerState(Manager.State.ALERTING_CUSTOMER);
                GenericIO.writelnString("Manager phone Customer");
                
                lounge.getNextTask();
                setManagerState(Manager.State.ATTENDING_CUSTOMER);
                GenericIO.writelnString("Manager get next task.");                
                
                lounge.talkToCustomer();
                setManagerState(Manager.State.ATTENDING_CUSTOMER);
                GenericIO.writelnString("Manager talk to customer.");                
                
                System.out.println("receivePayment info :"+choice[1]);
                
                lounge.receivePayment();
                GenericIO.writelnString("Manager receive payment."); 
            
            } else if (choice[0].equals(GETTING_NEW_PARTS)){    
                
                GenericIO.writelnString("Manager is getting new parts");

                int quantidade = supplierSite.goToSupplier(choice[1]);
                setManagerState(Manager.State.GETTING_NEW_PARTS);
                GenericIO.writelnString("Manager go to supplier.");                
                
                repairArea.storePart(choice[1], quantidade); //Updated Status : Replenish Stock
                setManagerState(Manager.State.REPLENISH_STOCK);
                GenericIO.writelnString("Manager store part.");  
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
