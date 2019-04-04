/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author danielmartins
 */
public interface CustomerPark {
    
    /**
     * Customer collects the car that is already done
     * (Class Park)
     */
    void collectCar(int myCar, String customerState);
    
    
    /**
     * customer go to repair shop
     * (Class Park)
     */
    void goToRepairShop(String info, String customerState);
    
    /**
     * customer find the replecment car on park
     * (Class Park)
     */
    int findCar(int id, String customerState);
}
