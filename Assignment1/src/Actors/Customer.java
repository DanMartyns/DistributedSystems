/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.CustomerLounge;
import Interfaces.CustomerOutSideWorld;
import Interfaces.CustomerPark;
import genclass.GenericIO;

/**
 * @author giselapinto
 * @author danielmartins
 */
public class Customer extends Thread {

        public enum State { 
        /**
         * Normal life with car.
         */
        NORMAL_LIFE_WITH_CAR ("normal"),
        
        /**
         * Park
         */
        PARK ("park"),
        
        /**
         * Waiting for replace car
         */
        WAITING_FOR_REPLACE_CAR ("wait"),
        
        /**
         * Reception
         */
        RECEPTION ("reception"),
        
        /**
         * Normal life without car.
         */
        NORMAL_LIFE_WITHOUT_CAR ("normalWithout");
        
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
     * Identifier of the Customer.
     */       
    private int id;
    
    /**
     * State of the Customer.
     */    
    private State state;

    /**
     * Boolean to decide if will replace the car.
     */
    private boolean wantsReplaceCar;
    
    /**
    * Identifier the car.
    */
    private int carID;
    
    /**
     * Current Car
     * If currentCar == carID, it means that the car in your possession is your car.
     * else if currentCar == 0, it means he has no car in his possession.
     * else if currentCar == -1 or -2 or -3, it means he has a replacement car in his possession.
     */
    private int currentCarID;
    
    /**
    * interface Customer Park.
    */
    private CustomerPark park;
    
    /**
     * Instance of the interface from customer Lounge.
     */
    private CustomerLounge lounge;
    
    /**
    * Instance of the interface from customer Outside World.
    */ 
    private CustomerOutSideWorld outsideWorld;
    
    
   
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the customer
     * @param outsideWorld instance of the outside world
     * @param park instance of the Park
     * @param lounge instance of the lounge
     */     
    public Customer(int id, CustomerOutSideWorld outsideWorld,int carID, CustomerPark park, CustomerLounge lounge) {
        this.id = id;
        this.state = State.NORMAL_LIFE_WITH_CAR;
        this.carID = carID;
        this.currentCarID = carID;
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.lounge = lounge;
    }
    
    @Override
    public void run() {
        int keyForReplaceCar;
        
        if( !outsideWorld.decideOnRepair()){
            GenericIO.writelnString("Customer "+id+" decided not to fix the car.");
        } else {
            GenericIO.writelnString("Customer "+id+" decided to fix the car.");
            GenericIO.writelnString("Customer "+id+" go to repair shop");
            park.goToRepairShop(); //Change State to PARK
            
            GenericIO.writelnString("Customer "+id+" queueIn");
            lounge.queueIn(id); //Change State to RECEPTION
            
            GenericIO.writelnString("Customer "+id+" talk with manager");
            lounge.talkWithManager();

            if(wantsReplaceCar){
                GenericIO.writelnString("Customer "+id+" talk with manager");

                keyForReplaceCar = lounge.collectKey(); //Change State to WAITING_FOR_REPLACE_CAR
                park.findCar(keyForReplaceCar);
                GenericIO.writelnString("Customer "+id+" goes to work by car");

                outsideWorld.backToWorkByCar();
                park.goToRepairShop();
            }

            else {
                GenericIO.writelnString("Customer "+id+" goes to work by bus");

                outsideWorld.backToWorkByBus();
            }
            GenericIO.writelnString("Customer "+id+" back to lounge to get car");

            lounge.queueIn(id);
            lounge.payForTheService();
            park.collectCar();
            outsideWorld.backToWorkByCar();
        }
    }
    /**
     * 
     * @return id of the Customer 
     */
    public int getID(){
        return id;
    }
    /**
     * Get the Customer's car id
     * 
     * @return car id
     */    
    public int getCar() {
        return carID;
    }
    
    /**
     * Get the Customer's current car id
     * 
     * @return car id
     */     
    public int getCurrentCar(){
        return currentCarID;
    }

    /**
     * Set the current car ID
     * @param currentCarID 
     */
    public void setCurrentCarID(int currentCarID) {
        this.currentCarID = currentCarID;
    }    
    
    /**
     * Get the Customer's State
     * 
     * @return customer state
     */
    public State getCustomerState(){
        return this.state;
    }
    /**
     * Set the Customer's State
     * @param state customer state
     */
    public void setCustomerState(State state){
        this.state = state;
    }
    
    
}
