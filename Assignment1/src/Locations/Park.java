/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Interfaces.CustomerPark;
import MainProgram.LoggerInterface;
import genclass.GenericIO;
import java.util.LinkedList;
import java.util.Queue;
import ProblemInformation.Constants;
import java.util.Arrays;

/**
 * @author danielmartins
 * @author giselapinto
 */
public class Park implements CustomerPark {
    
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
        if ( !inf[2].equals(inf[1]) ){
            try {
                wait();
            } catch (InterruptedException ex) {
                GenericIO.writelnString("goToRepairShop - Customer thread was interrupted.");
                System.exit(1);
            }
            /**
             * After waiting, he returns the replacement car.
             */
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
        GenericIO.writelnString(">>>>> (Park) findCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.PARK);
        
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
                System.exit(1);;
            }
        }
        return replacementCars.poll();
    }

    /**
     * Method for fetching the car after it has been repaired.
     */
    public synchronized void collectCar() {
        GenericIO.writelnString(">>>>> (Park) collectCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.PARK);
        
//        /**
//         * If the list of repairedCards contains your car
//         */
//        if (repairedCars.contains(customer.getCar())){
//            /**
//             * Removes your car from the repaired list
//             */
//            repairedCars.remove(customer.getCar());
//            /**
//             * Upgrade your current car
//             */
//            customer.setCurrentCarID(customer.getCar());             
//        }

    }

    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
