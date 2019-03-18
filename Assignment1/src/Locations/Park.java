/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Mechanic;
import Interfaces.CustomerPark;
import Interfaces.MechanicsPark;
import MainProgram.LoggerInterface;
import genclass.GenericIO;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Row of cars blocked for lack of parts.
     */ 
    private Queue<Integer> blockedCars = new LinkedList<>();
    
    /**
     * Queue of repaired cars.
     */
    private Queue<Integer> repairedCars = new LinkedList<>();

    /**
     * The customer park the car in the park.
     */
    public synchronized void goToRepairShop() {
        GenericIO.writelnString("------>>>>> (Park) goToRepairShop function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.PARK);
        
        /**
         * If the car is repared and his current car is different from your car id, it means that the current car is a replacement car, he wait.
         */
        if (!repairedCars.contains(customer.getCar()) && customer.getCurrentCar() != customer.getCar()){
            try {
                wait();
                /**
                 * He give it back the replacement car.
                 */
                replacementCars.add(customer.getCurrentCar());
            } catch (InterruptedException ex) {
                Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            /**
             * He park his car.
             */
            cars.add(customer.getCar());
        }
        
        /**
         * Places a variable "current car" to 0.
         */
        customer.setCurrentCarID(0);
    }

    /**
     * Method used by the customer to search for a replacement car
     * @param keyForReplaceCar 
     */
    public synchronized void findCar(long keyForReplaceCar) {
        GenericIO.writelnString("------>>>>> (Park) findCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.PARK);
        
        /**
         * If the list of replacement car is not empty, he give a car to the customer.
        */
        if(!replacementCars.isEmpty()){
            customer.setCurrentCarID(replacementCars.poll());
        } else {
            try {
                /**
                 * If the list of replacement car is empty, he's wait for a car.
                 */
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Park.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method for fetching the car after it has been repaired.
     */
    public synchronized void collectCar() {
        GenericIO.writelnString("------>>>>> (Park) collectCar function");
        Customer customer = ((Customer)Thread.currentThread());
        customer.setCustomerState(Customer.State.PARK);
        
        /**
         * If the list of repairedCards contains your car
         */
        if (repairedCars.contains(customer.getCar())){
            /**
             * Removes your car from the repaired list
             */
            repairedCars.remove(customer.getCar());
            /**
             * Upgrade your current car
             */
            customer.setCurrentCarID(customer.getCar());             
        }

    }
    /**
     * Method used by the mechanic to get the car to be repaired
     * @return the car id
     */
    public synchronized void getVehicle() {
        GenericIO.writelnString("------>>>>> (Park) getVehicle function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
        
        /**
         * In terms of simulation, the mechanic pulls the vehicle out of the "blocksCars" queue if it is not empty or the car's queue.
         */
        if (!blockedCars.isEmpty()){
            mechanic.setCurrentCarToRepair(blockedCars.poll());
        } else {
            mechanic.setCurrentCarToRepair(cars.poll());
        }
        
    }
    /**
     * 
     * @return 
     */
    public synchronized int returnVehicle(int carId) {
        GenericIO.writelnString("------>>>>> (Park) returnVehicle function");
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setMechanicState(Mechanic.State.FIXING_CAR);
        int vehicle = mechanic.getCurrentCarToRepair();
        mechanic.setCurrentCarToRepair(0);
        return vehicle;
    }

    /**
     * Set the current logger
     * @param logger Logger to be used for the entity
     */
    public synchronized void setLogger(LoggerInterface logger){
        this.logger = logger;
    }    
}
