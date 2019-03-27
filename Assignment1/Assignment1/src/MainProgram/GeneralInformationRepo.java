/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;


import ProblemInformation.Constants;
import static ProblemInformation.Constants.NUM_CUSTOMERS;
import static ProblemInformation.Constants.NUM_MECHANICS;
import genclass.GenericIO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



/**
 *
 * @author danielmartins and giselapinto
 */
public class GeneralInformationRepo{
    
    private String fileName;
       

    private String managerState = "CWTD";
    private String[] customerState = new String[NUM_CUSTOMERS];
    private String[] mechanicState = new String[NUM_MECHANICS];
    
    private String[] ownCar = new String[NUM_CUSTOMERS];
    private String[] replecementCar = new String[NUM_CUSTOMERS];
    private String[] repairedCar = new String[NUM_CUSTOMERS];
    
    private String valueQueueIn;
    
    private String numberWaitingReplece;
    
    private String numberRepair;
    
    private String numberParkCars;
    
    private String numberReplacementPark;
    
    
    private String numberServiceRequest;
    
    private String pieces0Stored;
    
    private String pieces1Stored;

    private String pieces2Stored;

    private String piecesAAvabal;
    private String piecesBAvabal;
    private String piecesCAvabal;

    private String flagAPieces;
    private String flagBPieces;
    private String flagCPieces;
    
    private String pieces0Manager;
    
    private String pieces1Manager;

    private String pieces2Manager;

    
    
    /**
     * GeneralInformationRepo constructor
     * 
     * @param fileName name of the logger file
     */
   
    GeneralInformationRepo(String fileName) {
        this.fileName = fileName;
        
        for(int i = 0; i<customerState.length; i++){
            
            customerState[i] = "NLC";
            ownCar[i] = "--";
            replecementCar[i] = "F";
            valueQueueIn = "00";
            repairedCar[i]="F";
            
            
        }

        for(int i = 0; i<mechanicState.length; i++){
            mechanicState[i] = "WFW";
        }
        
        numberRepair = "00";
        numberParkCars = "00";
        numberReplacementPark="00";
        numberWaitingReplece = "00";
        numberServiceRequest = "00";
        
        pieces0Stored=""+Constants.pieceA;
        pieces1Stored=""+Constants.pieceB;
        pieces2Stored=""+Constants.pieceC;
        
        piecesAAvabal = "00";
        piecesBAvabal = "00";
        piecesCAvabal = "00";
        
        flagAPieces = "F";
        flagBPieces = "F";
        flagCPieces = "F";
        
        pieces0Manager ="00";
        pieces1Manager ="00";
        pieces2Manager ="00";



    }

    /**
     * This function initiate de header log from the logger file, without deleting anything important
     */
    public synchronized void initStateLog(){
        
        try {
            FileWriter fw = new FileWriter(Constants.FILE_NAME);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("\t\t\t\t REPAIR SHOP ACTIVITIES - Description of the internal state of the problem\n");
            bw.newLine();
            bw.write("MAN   MECHANIC \t\t\t\t\t\t\t\t\t\t Customers");
            bw.newLine();
            bw.write("Stat  St0 St1  S00 C00 P00 R00 S01 C01 P01 R01 S02 C02 P02 R02 S03 C03 P03 R03 S04 C04 P04 R04 S05 C05 P05 R05 S06 C06 P06 R06 S07 C07 P07 R07 S08 C08 P08 R08 S09 C09 P09 R09");
            bw.newLine();
            bw.write("               S10 C10 P10 R10 S11 C11 P11 R11 S12 C12 P12 R12 S13 C13 P13 R13 S14 C14 P14 R14 S15 C15 P15 R15 S16 C16 P16 R16 S17 C17 P17 R17 S18 C18 P18 R18 S19 C19 P19 R19");
            bw.newLine();
            bw.write("               S20 C20 P20 R20 S21 C21 P21 R21 S22 C22 P22 R22 S23 C23 P23 R23 S24 C24 P24 R24 S25 C25 P25 R25 S26 C26 P26 R26 S27 C27 P27 R27 S28 C28 P28 R28 S29 C29 P29 R29");
            bw.newLine();
            bw.write("               \t LOUNGE\t\tPARK\t\t\t\t\tREPAIR AREA\t\t\t\t\tSUPPLIERES SITE");
            bw.newLine();
            bw.write("               InQ Wtk NRV     NCV NPV          NSRQ    Prt0  NV0  S0  Prt1  NV1  S1  Prt2  NV2  S2                     PP0  PP1  PP2                                          ");
            bw.newLine();

            
            bw.close();
            fw.close();
        } catch (IOException ex) {
            GenericIO.writelnString("initStateLog error - Could not write to logger file.");
            System.exit(1);
        }
        
    }
    
    /**
     * print all the relevante information from logger file, States, and all updateVariables
     */
    public synchronized void printHeaderLog(){
        try{
            FileWriter fw = new FileWriter(Constants.FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(""+ managerState);
            
            for(int i = 0; i<mechanicState.length; i++){
                bw.write(" "+ mechanicState[i]);
            }
            
            for(int i = 0; i<customerState.length; i++){
                bw.write("   "+customerState[i]+"  "+ownCar[i]+"  "+ replecementCar[i]+"   "+repairedCar[i]);
                if((i+1)%10 == 0){
                    bw.newLine();
                    bw.write("            ");
                }
                
            }
            bw.write("    "+valueQueueIn+"  "+numberWaitingReplece+"   "+numberRepair+"      "+numberParkCars+"   "+numberReplacementPark+"           "+numberServiceRequest+"      "+pieces0Stored+"    "+piecesAAvabal
                    +"   "+flagAPieces+"    "+pieces1Stored+"   "+piecesBAvabal+"   "+flagBPieces+"    "+pieces2Stored+"   "+piecesCAvabal+"    "+flagCPieces+"                    "
                    +pieces0Manager+"    "+pieces1Manager+"    "+pieces2Manager);
            bw.newLine();
            bw.newLine();
            bw.close();
            fw.close();
        }
        catch(IOException e){
            GenericIO.writelnString("printHeaderLog error - Could not write to logger file.");
            System.exit(1);
        }
    }
 
    /**
     * Get the state from manager
     * @param manager_state
     */
    public synchronized void setManagerState(String manager_state) {
        this.managerState = manager_state;
        printHeaderLog();
    }

    /**
     * get the respective customer state for all customers alive
     * @param id from customer
     * @param customerState state of customers
     */
    public synchronized void setCustomerState(int id, String customerState) {
        this.customerState[id] = customerState;
        printHeaderLog();
    }

    /**
     * get the respective mechanics state for all mechaincs alive
     * @param id of mechanic
     * @param mechanicState state
     */
    public synchronized void setMechanicState(int id, String mechanicState) {
        this.mechanicState[id] = mechanicState;
        printHeaderLog();
    }
    
    /**
     * vehicle driven by customer: 
     * own car – customer id; 
     * replacement car – R0, R1, R2 ; 
     * none - ‘-’
     * @param id customer Id
     * @param ownCar it own car or replecment car
     */
    public synchronized void setOwnCar(int id, String ownCar) {
        String[] inf = ownCar.split(",");
        
        if(inf[1].equals(inf[2]) && id<=9)
        {
            this.ownCar[id] = ("0"+inf[2]);
        }
        
        else if(inf[1].equals(inf[2])){
            this.ownCar[id] = inf[2];
        }
        else{
            if(Integer.parseInt(inf[2]) == -1)
            {
                this.ownCar[id] = "R0";
            }
            else if(Integer.parseInt(inf[2]) == -2){
                this.ownCar[id] = "R1";
            }
            else {
                 this.ownCar[id] = "R2";
            }
        }

        printHeaderLog();
    }

    /**
     * customer requires replacement vehicle: T or F 
     * @param id customer id
     * @param replecementCar if requeires replecment car or not
     */
    public synchronized void setReplecementCar(int id, String replecementCar) {
        String[] inf = replecementCar.split(",");
        if(inf[3].equals("1")){
            this.replecementCar[id] = "T";
        }
        else{
            this.replecementCar[id] = "F";
        }
        
        printHeaderLog();

    }
    
    /*TODO customer vehicle has already been repaired: T or F (# - 0 .. 29)*/
    public synchronized void setAlreadyRepaired(int id, int repairedCar[]) {
        //String[] inf = repairedCar.split(",");
        if(repairedCar[id]==1){
            this.repairedCar[id] = "T";
        }
        else{
            this.repairedCar[id] = "F";
        }

        printHeaderLog();

    }
    
    /**
     * number of customers presently queueing to talk to the manager
     * @param valueQueueIn value of customer on queue
     */
    public synchronized void setValueQueueIn(int valueQueueIn) {
        if(valueQueueIn<=9)
        {
            this.valueQueueIn = "0"+valueQueueIn;
        }
        else{
            this.valueQueueIn = ""+valueQueueIn;
        }
       
        printHeaderLog();
    }
       
    /**
     *number of customer waiting for a replacement vehicle
     * @param numberWaitingReplece number of replacement car
     */
    public synchronized void setNumberWaitingReplece(int numberWaitingReplece) {
        if(numberWaitingReplece<=9)
        {
            this.numberWaitingReplece = "0"+numberWaitingReplece;
        }
        else{
           this.numberWaitingReplece = ""+numberWaitingReplece;
        }
        printHeaderLog();
        
    }
    
    /**
     * number of cars that have already been repaired
     * @param numberRepair number of cars repaired
     */
    public synchronized void setNumberRepair(int numberRepair) {
        if(numberRepair<=9){
            this.numberRepair = "0"+numberRepair;
        }
        else{
            this.numberRepair = ""+numberRepair;
        }
        printHeaderLog();
    }

    /**
     * number of customer vehicles presently parked at the repair shop park
     * @param numberParkCars
     */
    public synchronized void setNumberParkCars(int numberParkCars) {
        if(numberParkCars<=9){
            this.numberParkCars = "0"+numberParkCars;
        }
        else{
            this.numberParkCars = ""+numberParkCars;
        }
        
        printHeaderLog();
    }

    /**
     * number of replacement vehicles presently parked at the repair shop park
     * @param numberReplacementPark
     */
    public synchronized void setNumberReplacementPark(int numberReplacementPark) {
        if(numberReplacementPark<=9){
            this.numberReplacementPark = "0"+numberReplacementPark;
        }
        else{
            this.numberReplacementPark = ""+numberReplacementPark;
        }
        
        printHeaderLog();
    }

    /**
     *number of service requests made by the manager to the repair area
     * @param numberServiceRequest
     */
    public synchronized void setNumberServiceRequest(int numberServiceRequest) {
        if(numberServiceRequest<=9){
            this.numberServiceRequest = "0"+numberServiceRequest;
        }
        else{
            this.numberServiceRequest = ""+numberServiceRequest;
        }
        printHeaderLog();
    }

    /**
     * number of parts of type 0 presently in storage at the repair area
     * @param pieces0Stored
     */
    public synchronized void setPieces0Stored(int pieces0Stored) {
        if(pieces0Stored<=9){
            this.pieces0Stored = "0"+pieces0Stored;
        }
        else{
            this.pieces0Stored = ""+pieces0Stored;
        }
        printHeaderLog();
    }

    /**
     * number of parts of type 1 presently in storage at the repair area
     * @param pieces1Stored
     */
    public synchronized void setPieces1Stored(int pieces1Stored) {
        if(pieces1Stored<=9){
            this.pieces1Stored = "0"+pieces1Stored;
        }
        else{
            this.pieces1Stored = ""+pieces1Stored;
        }
        printHeaderLog();
    }

    /**
     * number of parts of type 2 presently in storage at the repair area
     * @param pieces2Stored
     */
    public synchronized void setPieces2Stored(int pieces2Stored) {
        if(pieces2Stored<=9){
            this.pieces2Stored = "0"+pieces2Stored;
        }
        else{
            this.pieces2Stored = ""+pieces2Stored;
        }
        printHeaderLog();
    }

    /**
     * number of customer vehicles waiting for part # to be available so that the repair may resume 
     * @param piecesAvabal
     */
    public synchronized void setPiecesAvabal(String piecesAvabal) {
        if(piecesAvabal.equals("0")){
            if(Integer.parseInt(piecesAvabal)<=9){
                this.piecesAAvabal = "0"+piecesAvabal;
            }
            else{
                this.piecesAAvabal = ""+piecesAvabal;
            }
        }
        else if(piecesAvabal.equals("1")){
            if(Integer.parseInt(piecesAvabal)<=9){
                this.piecesBAvabal = "0"+piecesAvabal;
            }
            else{
                this.piecesBAvabal = ""+piecesAvabal;
            }
        }
        else if(piecesAvabal.equals("2")){
            if(Integer.parseInt(piecesAvabal)<=9){
                this.piecesCAvabal = "0"+piecesAvabal;
            }
            else{
                this.piecesCAvabal = ""+piecesAvabal;
            }
        }
        
        printHeaderLog();
    }
    
    /**
     * flag signaling the manager has been adviced that part # is missing at the repair area: T or F
     * @param flagPieces
     */
    public synchronized void setFlagAPieces(String flagPieces) {
        if(flagPieces.equals("0")){
            this.flagAPieces = "T";
        }
        
        else{
            this.flagAPieces = "F";
           
        }
        
        printHeaderLog();
    }
    
    public synchronized void setFlagBPieces(String flagPieces) {
        if(flagPieces.equals("1")){
            this.flagBPieces = "T";
        }
        
        else{
            this.flagBPieces = "F";
           
        }
        
        printHeaderLog();
    }
    
    public synchronized void setFlagCPieces(String flagPieces) {
        if(flagPieces.equals("2")){
            this.flagCPieces = "T";
        }
        else{
            
            this.flagCPieces = "F";
        }
        
        printHeaderLog();
    }
   
    /**
     * number of parts of type 0 which have been purchased so far by the manager
     * @param pieces0Manager
     */
    public synchronized void setPieces0Manager(int pieces0Manager) {
        if(pieces0Manager<=9){
            this.pieces0Manager = "0"+pieces0Manager;

        }
        else{
            this.pieces0Manager = ""+pieces0Manager;
        }
        
        printHeaderLog();
    }

    /**
     *number of parts of type 1 which have been purchased so far by the manager
     * @param pieces1Manager
     */
    public synchronized void setPieces1Manager(int pieces1Manager) {
        if(pieces1Manager<=9){
            this.pieces1Manager = "0"+pieces1Manager;
        }
        else{
            this.pieces1Manager = ""+pieces1Manager;
        }
        printHeaderLog();
    }

    /**
     * number of parts of type 2 which have been purchased so far by the manager
     * @param pieces2Manager
     */
    public synchronized void setPieces2Manager(int pieces2Manager) {
        if(pieces2Manager<=9){
            this.pieces2Manager = "0"+pieces2Manager;
        }
        else{
            this.pieces2Manager = ""+pieces2Manager;
        }
        printHeaderLog();
    }    
}
