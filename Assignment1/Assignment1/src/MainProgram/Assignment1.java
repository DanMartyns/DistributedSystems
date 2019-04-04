/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import Actors.Customer;
import Actors.Manager;
import Actors.Mechanic;
import Interfaces.CustomerLounge;
import Interfaces.CustomerOutSideWorld;
import Interfaces.CustomerPark;
import Interfaces.ManagerLounge;
import Interfaces.ManagerOutsideWorld;
import Interfaces.ManagerRepairArea;
import Interfaces.ManagerSupplierSite;
import Interfaces.MechanicsLounge;
import Interfaces.MechanicsPark;
import Interfaces.MechanicsRepairArea;
import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;
import Locations.RepairArea;
import Locations.SupplierSite;
import static ProblemInformation.Constants.FILE_NAME;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import static ProblemInformation.Constants.NUM_MECHANICS;
import genclass.GenericIO;

/**
 * Main program class:
 * Main function launches all threads: a manager, mechanics and costumers.
 * It uses the Constants class to get the simulation parameters.
 * The shared regions are also initialized, as well the logger (not yet) and its parameters.
 * 
 * @author danielmartins and giselapinto
 */
public class Assignment1 {

    /**
     *  Main method.
     *
     *  @param args runtime arguments
    */
    public static void main(String[] args) {
        
        /**
         * Problem Initialization.
         */
        GeneralInformationRepo logger = new GeneralInformationRepo(FILE_NAME);
        logger.initStateLog();
        Lounge lounge = new Lounge(logger);
        Park park = new Park(logger);
        OutsideWorld outsideWorld = new OutsideWorld(logger);
        RepairArea repairArea = new RepairArea(logger);
        SupplierSite supplierSite = new SupplierSite(logger);
        
        Customer[] thread_customer = new Customer[NUM_CUSTOMERS];
        Mechanic[] thread_mechanic = new Mechanic[NUM_MECHANICS];
        
        
        logger.printHeaderLog();        
        
        /** 
         * Start of Simulation.
         */    
       
        for(int i = 0; i<NUM_CUSTOMERS; i++){
            thread_customer[i] = new Customer(i, (CustomerOutSideWorld) outsideWorld, i, (CustomerPark) park, (CustomerLounge) lounge, logger);
            thread_customer[i].start();
        }
               
        Manager thread_manager = new Manager(0, (ManagerLounge) lounge, (ManagerSupplierSite) supplierSite, (ManagerRepairArea) repairArea, (ManagerOutsideWorld) outsideWorld, logger);
        thread_manager.start();        
        for(int i=0; i<NUM_MECHANICS; i++){
            thread_mechanic[i] = new Mechanic(i, (MechanicsLounge) lounge, (MechanicsRepairArea) repairArea, (MechanicsPark)  park, logger);
            thread_mechanic[i].start();
        }
        
        /**
         * Wait for the end of simulation.
         */       
        
        for (int i = 0; i<NUM_CUSTOMERS; i++) {
            try{
                thread_customer[i].join();
                GenericIO.writelnString("Customer is dead!");

            }
            catch(InterruptedException e){
                GenericIO.writelnString("Main Program - One thread of Customer was interrupted.");
                System.exit(1);
            }
        }
        for (int i=0; i<NUM_MECHANICS; i++) {
            try{
                thread_mechanic[i].join();
                GenericIO.writelnString("Mechanic is dead!");

            }
            catch(InterruptedException e){
                GenericIO.writelnString("Main Program - One thread of Mechanic was interrupted.");
                System.exit(1);
            }
        }        
        try{
            thread_manager.join();
            GenericIO.writelnString("Manager is dead!");

        }
        catch(InterruptedException e){
            GenericIO.writelnString("Main Program - One thread of Manager was interrupted.");
            System.exit(1);
        }         
        
        
        
    }
    
}
