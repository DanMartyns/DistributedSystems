/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actors;

import java.util.UUID;

/**
 *
 * @author danielmartins
 */
public class Car {
    private final long id;

    public Car() {
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

    public long getId() {
        return id;
    }
    
}
