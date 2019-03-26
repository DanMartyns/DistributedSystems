/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import Interfaces.CustomerLounge;
import Interfaces.CustomerOutSideWorld;
import Interfaces.CustomerPark;
import MainProgram.GeneralInformationRepo;
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
        NORMAL_LIFE_WITH_CAR ("NLC"),
        
        /**
         * Park
         */
        PARK ("PAR"),
        
        /**
         * Waiting for replace car
         */
        WAITING_FOR_REPLACE_CAR ("WFR"),
        
        /**
         * Reception
         */
        RECEPTION ("REC"),
        
        /**
         * Normal life without car.
         */
        NORMAL_LIFE_WITHOUT_CAR ("NLB");
        
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
    * Instance of the interface from logger.
    */ 
    
    private GeneralInformationRepo logger;

    /**
     * Variable to decide whether you want car or not
     */
    private boolean wantsReplacementCar = Math.random() > 0.5;
    
    
    /**
     * Mechanic constructor
     * 
     * @param id identifier of the customer
     * @param outsideWorld instance of the outside world
     * @param carID
     * @param park instance of the Park
     * @param lounge instance of the lounge
     * @param logger
     */     
    public Customer(int id, CustomerOutSideWorld outsideWorld,int carID, CustomerPark park, CustomerLounge lounge, GeneralInformationRepo logger) {
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
        this.logger = logger;
        
    }
    
    @Override
    public void run() {
        while ( outsideWorld.decideOnRepair() == false){
            System.out.println("Customer não quer consertar");
        }
            System.out.println("Customer quer consertar");
            setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);
            logger.setCustomerState(id, Customer.State.NORMAL_LIFE_WITH_CAR.toString()); 
            
            park.goToRepairShop(info);
            setCustomerState(Customer.State.PARK);
            logger.setCustomerState(id, Customer.State.PARK.toString());
            setCurrentCar(info,""+NUM_CUSTOMERS); // CurrentCar change to NUM_CUSTOMERS, that means without car
            System.out.println("Customer "+this.id+" after go to repair shop"); 
            
            lounge.queueIn(info);
            setCustomerState(Customer.State.RECEPTION);
            logger.setCustomerState(id, Customer.State.RECEPTION.toString());
            System.out.println("Customer "+this.id+" after queue in");             

            lounge.talkWithManager(this.id);
            System.out.println("Customer "+this.id+" after talk to manager"); 
            if(wantsReplacementCar){

                lounge.collectKey(this.id);
                setCustomerState(Customer.State.WAITING_FOR_REPLACE_CAR);
                logger.setCustomerState(id, Customer.State.WAITING_FOR_REPLACE_CAR.toString());
                System.out.println("Customer "+this.id+" after collect key"); 
                
                int replacementCar = park.findCar();
                //logger.setReplecementCar(this.id, replacementCar);
                setCurrentCar(info,""+replacementCar);
                setCustomerState(Customer.State.PARK);
                logger.setCustomerState(id, Customer.State.PARK.toString());
                System.out.println("Customer "+this.id+" after find car"); 
                
                outsideWorld.backToWorkByCar(this.id);
                setCustomerState(Customer.State.NORMAL_LIFE_WITH_CAR);
                logger.setCustomerState(id, Customer.State.NORMAL_LIFE_WITH_CAR.toString());
                System.out.println("Customer "+this.id+" after back to work by car"); 
                
                park.goToRepairShop(info);
                setCustomerState(Customer.State.PARK);
                logger.setCustomerState(id, Customer.State.PARK.toString());
                System.out.println("Customer "+this.id+" after go to repair shop");                 
            }
            else {
                outsideWorld.backToWorkByBus(this.id);
                setCustomerState(Customer.State.NORMAL_LIFE_WITHOUT_CAR);
                logger.setCustomerState(id, Customer.State.NORMAL_LIFE_WITHOUT_CAR.toString());
                System.out.println("Customer "+this.id+" after back to work by bus"); 
            }
            setPay(info,"1");
            lounge.queueIn(info);
            setCustomerState(Customer.State.RECEPTION);
            logger.setCustomerState(id, Customer.State.RECEPTION.toString());
            System.out.println("Customer "+this.id+" after queue in"); 
            
            lounge.payForTheService(this.id);
            System.out.println("Customer "+this.id+" after pay the service"); 
            
            park.collectCar(this.id);
            setCustomerState(Customer.State.PARK);
            logger.setCustomerState(id, Customer.State.PARK.toString());
            System.out.println("Customer "+this.id+" after collect the car"); 
            
            setCurrentCar(info,""+this.id);
            
            System.out.println("Customer "+this.id+" -------------------------------------------");             
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
