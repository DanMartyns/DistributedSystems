/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;

/**
 *
 * @author danielmartins
 */
public interface LoggerInterface {
    /**
     * Cleans the log file and logs the description of the problem.
     */
    public void initStateLog(); 
    
    /**
     * Logs the header of the initial state of the entities.
     */
    public void printHeaderLog();    
}
