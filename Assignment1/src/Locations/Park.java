/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Interfaces.CustomerPark;
import Interfaces.MechanicsPark;
import MainProgram.LoggerInterface;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import genclass.GenericIO;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class Park implements CustomerPark, MechanicsPark{
    
    /**
     * Logger class for debugging.
     */
    private LoggerInterface logger;
    
    
    Queue<Integer> replacementCars = new LinkedList<Integer>() { 
        { 
            add(-1); 
            add(-2); 
            add(-3); 
        } 
    };
    /**
     * The queue of cars parked.
     */
    private Queue<Integer> cars = new LinkedList<>();    
    /**
     * Queue of repaired cars.
     */
    private Queue<Integer> repairedCars = new LinkedList<>();  
    
    /**
     * The customer park the car in the park.
     * In this state you can get in, 
     * -> customers who will repair a car, 
     * -> customers who will get the car after repair and who has a replacement car.
     */
    public synchronized void goToRepairShop(String info) {        
        /**
         * id.carId.currentCar.wantsCar.paid
         */
        
        String[] inf = info.split(",");
        /**
         * If his current car is different from your car id, 
         * it means that the current car is a replacement car, he waits.
         */
        if ( inf[2].equals(inf[1]) == false ){
            replacementCars.add(Integer.parseInt(inf[2])); 
        }
        /**
         * If the car is repared and his current car is different from your car id, 
         * it means that the current car is a replacement car, he wait.
         */        
        if ( inf[2].equals(inf[1]) ){

            System.out.println("Carro adicionado : "+inf[1]);
            cars.add(Integer.parseInt(inf[1]));          
        }
        
    }

    /**
     * Method used by the customer to search for a replacement car
     */
    public synchronized int findCar() {
        
        /**
         * If the list of replacement car is not empty, he give a car to the customer.
        */
        if(replacementCars.isEmpty()){
            try {
                /**
                 * If the list of replacement car is empty, he's wait for a car.
                 */
                System.out.println("Waiting for a replacement car");
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("findCar - Customer thread was interrupted.");
                System.exit(1);
            }
        }
        return replacementCars.poll();
    }

    /**
     * Method for fetching the car after it has been repaired.
     */
    public synchronized void collectCar( int myCar ) {
        
        /**
         * If the list of repairedCards contains your car
         */
        if (repairedCars.contains(myCar)){
            /**
             * Removes your car from the repaired list
             */
            repairedCars.remove(myCar);            
        }

    }
    /**
     * 
     * @return 
     */
    public synchronized void returnVehicle(int car) {
        repairedCars.add(car);
        repairedCars.forEach((x) -> {System.out.println("Inside Repaired Cars : "+x);});
    }

    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
