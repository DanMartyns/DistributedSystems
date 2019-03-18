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
import Locations.Park;
import Locations.RepairArea;
import ProblemInformation.Constants;
import genclass.GenericIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author danielmartins
 */
public class Logger implements LoggerInterface{

    /**
     * Manager entity.
     */
    private final Manager manager;    

    /**
     * Mechanics entities.
     */
    private final Mechanic[] mechanic; 
    
    /**
     * Customer entities.
     */
    private final Customer[] customers;
    
    /**
     * Repair Area, where the repairs will be done.
     */
    private final RepairArea repairArea;
    
    /**
     * Lounge, where customers will have the car repaired.
     */
    private final Lounge lounge;
    
    /**
     * Park, where are the cars to arrange, the repaired and the substitution.
     */
    private final Park park;
    
    /**
     * Logger constructor
     * 
     * @param manager Manager entity
     * @param mechanic Array with all mechanics
     * @param customers Array with all customers
     * @param repairArea where the repairs will be done.
     * @param lounge where customers will have the car repaired.
     * @param park where are the cars to arrange, the repaired and the substitution.
     */
    public Logger(Manager manager, Mechanic[] mechanic, Customer[] customers, RepairArea repairArea, Lounge lounge, Park park){
        this.manager = manager;
        this.mechanic = mechanic;
        this.customers = customers;
        this.repairArea = repairArea;
        this.lounge = lounge;
        this.park = park;
    }
    
    /**
     * Cleans the logger file and prints the description of the problem.
     */
    @Override
    public synchronized void initStateLog(){
        
        try {
            FileWriter fw = new FileWriter(Constants.FILE_NAME);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("REPAIR SHOP ACTIVITIES - Description of the internal state of the problem");
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            GenericIO.writelnString("initStateLog error - Could not write to logger file.");
            System.exit(1);
        }
        
    }
    
    /**
     * Print the header of the states log.
     */
    @Override
    public synchronized void printHeaderLog(){
        try{
            FileWriter fw = new FileWriter(Constants.FILE_NAME, true);
        
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Stat");

            for (Mechanic mechanic : mechanic) {
                bw.write(" ST" + String.format("%01d", mechanic.getID()));
            }

            for (int i = 0; i < customers.length; i++) {
                bw.write(" S" + String.format("%02d", customers[i].getID()));
                bw.write(" C" + String.format("%02d", customers[i].getID()));
                bw.write(" P" + String.format("%02d", customers[i].getID()));   
                bw.write(" R" + String.format("%02d", customers[i].getID()));
                if((i+1)%10 == 0)
                    bw.newLine();
            }
                        
            bw.close();
            fw.close();
        }
        catch(IOException e){
            GenericIO.writelnString("printHeaderLog error - Could not write to logger file.");
            System.exit(1);
        }
    }
}
