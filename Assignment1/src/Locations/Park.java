/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Customer;
import Actors.Mechanic;

/**
 *
 * @author giselapinto
 */
public class Park {
    // numero dos carros de substituicao disponiveis;  nunca incremente acima do MAX_SUB_CARS
    private int substituionCar;
    
    // lista dos veiculos do clientes para reparacao
    // lista dos veiculos do clientes ja reparados
    //metodos para dar os veiculos

    public synchronized void goToRepairShop() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void findCar(int keyForReplaceCar) {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void collectCar() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void collectCar(int carID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void getVehicle() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void returnVehicle() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
