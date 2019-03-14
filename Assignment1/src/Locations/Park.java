/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locations;

import Actors.Car;
import Actors.Customer;
import Actors.Mechanic;
import Interfaces.CustomerPark;
import Interfaces.MechanicsPark;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author giselapinto
 */
public class Park implements CustomerPark, MechanicsPark{
    // numero dos carros de substituicao disponiveis;  nunca incremente acima do MAX_SUB_CARS
    private int substituionCar;
    
    public Queue<Car> cars = new LinkedList<>();
    public Queue<Car> blockedCars = new LinkedList<>();
    
    // lista dos veiculos do clientes para reparacao
    // lista dos veiculos do clientes ja reparados
    //metodos para dar os veiculos

    @Override
    public synchronized void goToRepairShop() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void findCar(long keyForReplaceCar) {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void collectCar() {
        Customer customer = ((Customer)Thread.currentThread());
        customer.setManagerState(Customer.State.PARK);        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void collectCar(Car carID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized Car getVehicle() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        
        if (!blockedCars.isEmpty())
            return blockedCars.poll();
        
        return cars.poll();
        
    }

    @Override
    public synchronized void returnVehicle() {
        Mechanic mechanic = ((Mechanic)Thread.currentThread());
        mechanic.setManagerState(Mechanic.State.FIXING_CAR);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
