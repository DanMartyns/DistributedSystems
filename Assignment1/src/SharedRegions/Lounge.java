/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedRegions;

import java.util.*;
/**
 *
 * @author danielmartins
 */
public class Lounge {
    private Queue<Integer> lounge = new LinkedList<Integer>();
    
    // The customer go in to the lounge
    public synchronized void add(){
        
    }
    // The manager checks if there is someone in the lounge
    public synchronized boolean isEmpty(){
        return false;
    }
    //The Manager peeks the client from the lounge
    public synchronized int peek(){
        return 0;
    }
}
