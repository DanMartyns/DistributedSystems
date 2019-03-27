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
import MainProgram.GeneralInformationRepo;
import static ProblemInformation.Constants.ALERTING_CUSTOMER;
import static ProblemInformation.Constants.ATENDING_CUSTOMER;
import static ProblemInformation.Constants.GETTING_NEW_PARTS;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import genclass.GenericIO;
import java.util.Arrays;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Manager extends Thread {

    public enum State { 
        /**
         * Checking what to do.
         */
        CHECKING_WHAT_TO_DO ("CWTD"),
        
        /**
         * Attending customer.
         */
        ATTENDING_CUSTOMER ("ATCU"),
        
        /**
         * Posting Job.
         */
        POSTING_JOB ("POJB"),
        
        /**
         * Supervising the race.
         */
        ALERTING_CUSTOMER ("ALCU"),
        
        /**
         * Replenish Stock.
         */
        REPLENISH_STOCK ("REST"),
        
        /**
         * Getting new parts.
         */
        GETTING_NEW_PARTS ("GENP");
        
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
    
    private GeneralInformationRepo logger;
    
    private int numRepairedClients = 0;

    
    
    
    /**
     * Manager constructor
     * 
     * @param id identifier of the manager
     * @param lounge instance of the lounge
     * @param supplierSite instance of the supplier site
     * @param repairArea instance of the repair area
     * @param outsideWorld instance of the outside world
     * @param logger instance of the logger
     */
    
    public Manager(int id, ManagerLounge lounge, ManagerSupplierSite supplierSite, ManagerRepairArea repairArea, ManagerOutsideWorld outsideWorld, GeneralInformationRepo logger) {
        this.state = State.CHECKING_WHAT_TO_DO;
        this.id = id;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.repairArea = repairArea;
        this.outsideWorld = outsideWorld;
        this.logger = logger;
        
    }
    /**
     * Implements the life cycle of the broker.
     */ 
    @Override
    public void run(){
        
        setManagerState(State.CHECKING_WHAT_TO_DO);
        logger.setManagerState(State.CHECKING_WHAT_TO_DO.toString());
        
        while(lounge.getNextTask()){            
            setManagerState(State.CHECKING_WHAT_TO_DO);
            logger.setManagerState(State.CHECKING_WHAT_TO_DO.toString());
            System.out.println("Manager After getNextTask");

            String[] choice = lounge.appraiseSit().split("@");
            System.out.println("choice "+Arrays.toString(choice));
            System.out.println("Manager "+choice[1]+" After apraiseSit");            
             

            if(choice[0].equals(ATENDING_CUSTOMER)){
                String[] customer = choice[1].split(",");
                lounge.talkToCustomer(choice[1]);
                System.out.println("Manager "+choice[1]+" After talk");                
                setManagerState(State.ATTENDING_CUSTOMER);
                logger.setManagerState(State.ATTENDING_CUSTOMER.toString());
                 System.out.println(Arrays.toString(customer));
              
                if(customer[3].equals("0") && customer[4].equals("0")){
                    repairArea.registerService( Integer.parseInt( customer[1] ) ); //Updated Status : Posting Job
                    System.out.println("Manager "+choice[1]+" After register service");                    
                    setManagerState(State.POSTING_JOB);
                    logger.setManagerState(State.POSTING_JOB.toString());
                    System.out.println("Manager ----------------------------------");                    
                
                }
                else if (customer[3].equals("1") && customer[4].equals("0")){               
                    lounge.handCarKey(choice[1]);
                    System.out.println("Manager After hand car key");
                    repairArea.registerService( Integer.parseInt( customer[1] ) ); //Updated Status : Posting Job
                    setManagerState(State.POSTING_JOB);
                    //logger.setManagerState(State.POSTING_JOB.toString());
                    System.out.println("Manager "+choice[1]+" After register service");
                    System.out.println("Manager -----------------------------------");                    
                }               
                else if (customer[4].equals("1")) {                   
                    lounge.receivePayment(choice[1]);    
                    System.out.println("Manager "+choice[1]+" After receive payment");
                    System.out.println("Manager -----------------------------------");                     
                    
                    numRepairedClients++;
                    System.out.println("Manager => Número de Clientes "+numRepairedClients);
                    if( numRepairedClients == NUM_CUSTOMERS){
                        System.out.println("Mandei o mecânico embora");
                        repairArea.shutdownNow();
                        break;
                    }                    
                }
            
            }  
            else if(choice[0].equals(ALERTING_CUSTOMER)){
                outsideWorld.phoneCustomer(choice[1]);
                System.out.println("Manager After phoneCustomer");                
                setManagerState(State.ALERTING_CUSTOMER);
                logger.setManagerState(State.ALERTING_CUSTOMER.toString());
                System.out.println("Manager -----------------------------------");                    
            
            } 
            
            else if (choice[0].equals(GETTING_NEW_PARTS)){    

                int quantidade = supplierSite.goToSupplier(choice[1]);
                setManagerState(State.GETTING_NEW_PARTS);
                logger.setManagerState(State.GETTING_NEW_PARTS.toString());             
                System.out.println("Manager After go to supplier");
                System.out.println("Manager repos a peca "+choice[1]+" e quantidade "+quantidade);
                repairArea.storePart(choice[1], quantidade); //Updated Status : Replenish Stock
                setManagerState(State.REPLENISH_STOCK);
                logger.setManagerState(State.REPLENISH_STOCK.toString());
                System.out.println("Manager After store part");                
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
