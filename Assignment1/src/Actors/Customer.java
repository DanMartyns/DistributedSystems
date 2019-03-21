/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.CustomerLounge;
import Interfaces.CustomerOutSideWorld;
import Interfaces.CustomerPark;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
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
    
    private String info;
    /**
     * State of the Customer.
     */    
    private State state;
    
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
     * 
     */
    private boolean wantsReplacementCar = true;//Math.random() > 0.5;
    
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the customer
     * @param outsideWorld instance of the outside world
     * @param park instance of the Park
     * @param lounge instance of the lounge
     */     
    public Customer(int id, CustomerOutSideWorld outsideWorld,int carID, CustomerPark park, CustomerLounge lounge) {
        /**
         * The client id is a string consisting of
         * id = id.hisCar.currentCar.wantsCarOrNot.paidOrNot
         * Example : 
         * 1.1.1.0.0
         */
        /**
        * Current Car
        * If currentCar == carID, it means that the car in your possession is your car.
        * else if currentCar == 0, it means he has no car in his possession.
        * else if currentCar == -1 or -2 or -3, it means he has a replacement car in his possession.
        */
        
        this.id = id;
        this.info = id+","+carID+","+carID+","+(wantsReplacementCar ? 1 : 0)+","+"0";
        this.state = State.NORMAL_LIFE_WITH_CAR;
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.lounge = lounge;
    }
    
    @Override
    public void run() {     
        if( !outsideWorld.decideOnRepair()){
            GenericIO.writelnString("Customer "+id+" decided not to fix the car.");
        } else {
            GenericIO.writelnString("Customer "+id+" decided to fix the car.");
            park.goToRepairShop(info);
            setCustomerState(Customer.State.PARK);
            setCurrentCar(info,""+(NUM_CUSTOMERS + 1)); // CurrentCar change to NUM_CUSTOMERS + 1, that means without car
            
            lounge.queueIn(info);
            setCustomerState(Customer.State.RECEPTION);
            GenericIO.writelnString("Customer "+id+" queueIn.");
            lounge.talkWithManager(info);
            GenericIO.writelnString("Customer "+id+" talk to Manager.");
            if(wantsReplacementCar){

                lounge.collectKey(info);
                setCustomerState(Customer.State.WAITING_FOR_REPLACE_CAR);
                GenericIO.writelnString("Customer "+id+" collect key.");
                int replacementCar = park.findCar();
                setCurrentCar(info,""+replacementCar);
                setCustomerState(Customer.State.PARK);
                GenericIO.writelnString("Customer "+id+" find Car.");

                outsideWorld.backToWorkByCar(info);
                setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);
                setPay(info,"1");
                GenericIO.writelnString("Customer "+id+" back To work by car.");
                
                park.goToRepairShop(info);
                setCustomerState(Customer.State.PARK);
                GenericIO.writelnString("Customer "+id+" go to repaired shop.");
            }

            else {
                outsideWorld.backToWorkByBus(info);
                setCustomerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
                GenericIO.writelnString("Customer "+id+" back to work by bus.");
            }

            lounge.queueIn(info);
            setCustomerState(Customer.State.RECEPTION);
            GenericIO.writelnString("Customer "+id+" queue in.");
            
            lounge.payForTheService(info);
            GenericIO.writelnString("Customer "+id+" pay for the service.");
            setPay(info,"0");
            
            park.collectCar(this.id);
            setCustomerState(Customer.State.PARK);
            setCurrentCar(info,""+this.id);
            GenericIO.writelnString("Customer "+id+" collect Car.");
            
            
            outsideWorld.backToWorkByCar(info);
            setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);
            GenericIO.writelnString("Customer "+id+" back to work by car.");
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
    
    private void setCurrentCar(String id, String value){
        String[] temp = id.split(",");
        temp[2] = value;
        this.info = String.join(",",temp);
    }

    private void setPay(String id, String value){
        String[] temp = id.split(",");
        temp[4] = value;
        this.info = String.join(",",temp);
    }    
    
}
