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
public interface CustomerOutSideWorld {
    
    /**
     * customer decide repair car
     * (Class OutsideWorld)
     * @return true if customer decide Repair the car, false if they still on outside world activity
     */
    boolean decideOnRepair(int customer, String customerState);
    
    /**
     * if customer decide on repair car and choose leave with replecement car, 
     * he goes back to outside world activitys by replecement car
     * (Class OutsideWorld)
     */
    void backToWorkByCar(String customer, String customerState);
    
    /**
     * if customer decide on repair car and choose not leaving with replecement car, 
     * he goes back to outside world activitys by replecement bus
     * (Class OutsideWorld)
     */
    void backToWorkByBus(int customer, String customerState);
    
    
    
    
    
}
