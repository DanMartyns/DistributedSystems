/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.CustomerLounge;
import Interfaces.CustomerOutSideWorld;
import Interfaces.CustomerPark;

import java.util.UUID;

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
    private final Car car;
    
    
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
    public Customer(int id, CustomerOutSideWorld outsideWorld, CustomerPark park, CustomerLounge lounge) {
        this.id = id;
        this.state = State.NORMAL_LIFE_WITH_CAR;
        this.car = new Car();
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.lounge = lounge;
    }
    
    @Override
    public void run() {
        int keyForReplaceCar;
        
        while(outsideWorld.decideOnRepair()){
            park.goToRepairShop(); //Change State to PARK
            lounge.queueIn(); //Change State to RECEPTION
            lounge.talkWithManager();

            if(wantsReplaceCar){
                keyForReplaceCar = lounge.collectKey(); //Change State to WAITING_FOR_REPLACE_CAR
                park.findCar(keyForReplaceCar);
                outsideWorld.backToWorkByCar();
                /*after some time*/
                park.goToRepairShop();
            }

            else {
                outsideWorld.backToWorkByBus();
            }

            lounge.queueIn();
            lounge.payForTheService();
            park.collectCar(car);
            outsideWorld.backToWorkByCar();
        }
    }

    public Car getCar() {
        return car;
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
