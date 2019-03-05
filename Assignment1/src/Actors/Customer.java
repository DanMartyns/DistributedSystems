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
    private int id;
    private boolean wantsReplaceCar;
    private int carID;
   
    private Park park;
    
    private Lounge lounge;
    
    private OutsideWorld outsideWorld;
    
   
    public Customer(int id, boolean wantsReplaceCar, int carID, OutsideWorld outsideWorld, Park park, Lounge lounge) {
        this.id = id;
        this.wantsReplaceCar = wantsReplaceCar;
        this.carID = carID;
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.lounge = lounge;
    }
    
    @Override
    public void run() {
        int keyForReplaceCar;
        
        outsideWorld.decideOnRepair();
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
