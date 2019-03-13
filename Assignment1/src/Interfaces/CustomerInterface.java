/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author giselapinto and  daniel  
 */
public interface CustomerInterface {
    
    /**
     * customer decide repair car
     * (Class OutsideWorld)
     * @return true if customer decide Repair the car, false if they still on outside world activity
     */
    boolean decideOnRepair();

    /**
     * customer go to repair shop
     * (Class Park)
     */
    void goToRepairShop();
    
    /**
     * if customer decide on repair car and choose leave with replecement car, 
     * he goes back to outside world activitys by replecement car
     * (Class OutsideWorld)
     */
    void backToWorkByCar();
    
    /**
     * if customer decide on repair car and choose not leaving with replecement car, 
     * he goes back to outside world activitys by replecement bus
     * (Class OutsideWorld)
     */
    void backToWorkByBus();
    
    /**
     * customer is waiting for manager to talk
     * (Class Lounge)
     */
    void queueIn();
    
    /**
     * customer talks with manager
     * (Class Park)
     */
    void talkToCustomer();
    
    
    /**
     * customer wants a replecement car so he need the key
     * @return id from replecement car
     * (Class Park)
     */
    int collectKey();
    
    /**
     * customer find the replecment car on park
     * @param keyForReplaceCar id from car
     * (Class Lounge)
     */
    void findCar(int keyForReplaceCar);
    
    /**
     * customer pay for the service to Manager
     * (Class Lounge)
     */
    void payForTheService();
    
    /**
     * Customer collects the car that is already done
     * (Class Park)
     */
    void collectCar();
    
    
    
    
}
