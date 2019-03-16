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
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import static ProblemInformation.Constants.NUM_MECHANICS;
import genclass.GenericIO;

import java.util.Random;
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
        
        //boolean replaceCar = false;
        int wantsReplaceCar = 0;
        
        /**
         * Problem Initialization.
         */
        Lounge lounge = new Lounge();
        Park park = new Park();
        OutsideWorld outsideWorld = new OutsideWorld();
        RepairArea repairArea = new RepairArea();
        SupplierSite supplierSite = new SupplierSite();
        
        Manager thread_manager;
        Mechanic[] thread_mechanic = new Mechanic[NUM_MECHANICS];
        Customer[] thread_customer = new Customer[NUM_CUSTOMERS];
        
        //random to choose if customer wants a replacement car or not
        Random rand_replace = new Random();
        //if 0-> NO else YES!!
        wantsReplaceCar = rand_replace.nextInt(1);
        
        
        /** 
         * Start of Simulation.
         */    
       
        for(int i = 0; i<NUM_CUSTOMERS; i++)
        {
            
            thread_customer[i] = new Customer(i, (CustomerOutSideWorld) outsideWorld, i, (CustomerPark) park, (CustomerLounge) lounge);
            //lunch run thread from customer
            thread_customer[i].start();
        }
        
        
        thread_manager = new Manager(1, (ManagerLounge) lounge, (ManagerSupplierSite) supplierSite, (ManagerRepairArea) repairArea);
        //lunch run thread from manager
        thread_manager.start();
        
        
        for(int i=0; i<NUM_MECHANICS; i++)
        {
            thread_mechanic[i] = new Mechanic(i, (MechanicsLounge) lounge, (MechanicsRepairArea) repairArea, (MechanicsPark) park);
            //lunch run thread from mechainc
            thread_mechanic[i].start();
        }
        
        /**
         * Wait for the end of simulation.
         */
        
        for (Customer customer : thread_customer) {
            try{
                customer.join();
            }
            catch(InterruptedException e){
                GenericIO.writeString("Main Program - One thread of Customer was interrupted.");
                System.exit(1);
            }
        }
        
        for (Mechanic mechanic : thread_mechanic) {
            try{
                mechanic.join();
            }
            catch(InterruptedException e){
                GenericIO.writeString("Main Program - One thread of Mechanic was interrupted.");
                System.exit(1);
            }
        }
        
        try{
            thread_manager.join();
        }
        catch(InterruptedException e){
            GenericIO.writeString("Main Program - One thread of Manager was interrupted.");
            System.exit(1);
        }
        
    }
    
}
