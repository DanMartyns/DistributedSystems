/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import Actors.Customer;
import Actors.Manager;
import Actors.Mechanic;
import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;
import Locations.RepairArea;
import Locations.SupplierSite;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import static ProblemInformation.Constants.NUM_MECHANICS;

import java.util.Random;
/**
 *
 * @author danielmartins
 */
public class Assignment1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        boolean replaceCar = false;
        int wantsReplaceCar = 0;
        
        // initialize actors threads and locations
        Customer[] thread_customer = new Customer[NUM_CUSTOMERS];
        Manager thread_manager;
        Mechanic[] thread_mechanic = new Mechanic[NUM_MECHANICS];
        
    
        Lounge lounge = new Lounge();
        Park park = new Park();
        OutsideWorld outsideWorld = new OutsideWorld();
        RepairArea repairArea = new RepairArea();
        SupplierSite supplierSite = new SupplierSite();
        
        //random to choose if customer wants a replacement car or not
        Random rand_replace = new Random();
        //if 0-> NO else YES!!
        wantsReplaceCar = rand_replace.nextInt(1);
        if(wantsReplaceCar==0)
        {
            replaceCar = false;
        }
        else {
            replaceCar = true;
        }
            
       
        for(int i = 0; i<NUM_CUSTOMERS; i++)
        {
            
            thread_customer[i] = new Customer(i, replaceCar, i, outsideWorld, park, lounge);
            //lunch run thread from customer
            thread_customer[i].start();
        }
        
        
        thread_manager = new Manager(1, lounge, supplierSite, repairArea);
        //lunch run thread from manager
        thread_manager.start();
        
        
        for(int i=0; i<NUM_MECHANICS; i++)
        {
            thread_mechanic[i] = new Mechanic(i, lounge, repairArea, park);
            //lunch run thread from mechainc
            thread_mechanic[i].start();
        }
        
    }
    
}
