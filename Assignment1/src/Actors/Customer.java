/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;

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
    * Park.
    */
    private Park park;
    
    /**
     * Instance of the Lounge.
     */
    private Lounge lounge;
    
    /**
    * Instance of the Outside World.
    */ 
    private OutsideWorld outsideWorld;
    
   
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the customer
     * @param wantsReplaceCar boolean to decide if will replace the car
     * @param carID identifier of the car
     * @param outsideWorld instance of the outside world
     * @param lounge instance of the lounge
     */     
    public Customer(int id, boolean wantsReplaceCar, int carID, OutsideWorld outsideWorld, Park park, Lounge lounge) {
        this.id = id;
        this.state = State.NORMAL_LIFE_WITH_CAR;
        this.wantsReplaceCar = wantsReplaceCar;
        this.carID = carID;
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.lounge = lounge;
    }
    
    @Override
    public void run() {
        int keyForReplaceCar;
        
        while(outsideWorld.decideOnRepair()){
            park.goToRepairShop();
            lounge.queueIn();
            lounge.talkWithManager();

            if(wantsReplaceCar){
                keyForReplaceCar = lounge.collectKey();
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
            park.collectCar(carID);
            outsideWorld.backToWorkByCar();
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
