/*
Name: Scott Lonsdale
Course: COMP2240
Student Number: C3303788
*/


import java.util.*;
import java.io.*;  


public class A1
{
	
	public static void main (String args[]) throws FileNotFoundException
	{
        ArrayList<Process> ProcList = new ArrayList<Process>(); //the main array that contains all the processes
        Scanner input = new Scanner(new FileReader(args[0])); //passes the desired txt file
        int id = 1;
        Integer disp = null;

        while (input.hasNext())
        { 
            if(input.hasNextInt() == true)
            {
                if (disp == null) {disp = input.nextInt();} //takes the first int of the txt file as the dispatch
                //finds the next int (so the arrival time) and then generates the id
                //this assumes that the passed processes from the txt file will be in order
                else 
                {
                    Process newProc = new Process();
                    newProc.setProcessID(id);
                    newProc.setATime(input.nextInt());
                    input.next();
                    newProc.setExecSizeTime(input.nextInt());
                    input.next();
                    newProc.setPriority(input.nextInt());
                    ProcList.add(newProc);
                    id++;
                    //finds the necessary data and incrememts the id
                }
            }
            else {input.next();}
        }

        //calls the schedule functions and outputs the resuls of each with formatting
        Output[] Output1 = FCFS(ProcList, disp);
        OutputSchedule(Output1);
        Output[] Output2 = SPN(ProcList, disp);
        OutputSchedule(Output2);
        Output[] Output3 = PP(ProcList, disp);
        OutputSchedule(Output3);
        Output[] Output4 = PRR(ProcList, disp);
        OutputSchedule(Output4);
        System.out.println("Summary");
        System.out.println("Algorithm       Average Turnaround Time  Average Waiting Time");
        //uses the average function to calc averages for the final summary
        Average(Output1, "FCFS");
        Average(Output2, "SPN  ");
        Average(Output3, "PP ");
        Average(Output4, "PRR ");

         

	
	}

	public static Output[] FCFS(ArrayList<Process> ProcList, int dispatch) //FirstComeFirstServed scheduling algorithm
	{
        //initialises the overallTime, the output array and txt output formatting
        Output[] OutputArray = new Output[ProcList.size()];
        int overallTime = 0; 
        System.out.println("FCFS:");
        while (true)
        {    
            int currentProcess = Integer.MAX_VALUE; //Integer.MAX_VALUE is used to represent no next process being picked
            //the main for loop that determines the next process to be used
            for (int i = 0; i < ProcList.size(); i++) //iterates through all processes
            {
                //checks if the process has arrived and if that process has been completed yet
                if ((ProcList.get(i).getATime() <= overallTime) && (OutputArray[i] == null))  
                {
                    //uses FCFS to determine next process (smallestATime then smallestID)
                    if (currentProcess == Integer.MAX_VALUE) {currentProcess = i;}
                    else if (ProcList.get(i).getATime() < ProcList.get(currentProcess).getATime()) {currentProcess = i;}
                    else if (ProcList.get(i).getATime() == ProcList.get(currentProcess).getATime())
                    {
                        if(i < currentProcess) {currentProcess = i;}
                    }   
                }  
            }
            if (currentProcess == Integer.MAX_VALUE) {break;} //if no nextProcess is found the while loop is broken
            //adds the dispatch value to the overall time and determines the startTime of the process
            overallTime = overallTime + dispatch;
            int startTime = overallTime;
            //outputs the schedule event as txt
            System.out.println("T" + overallTime + ": p" + ProcList.get(currentProcess).getProcessID() + "(" + ProcList.get(currentProcess).getPriority() + ")");	
         	//increments the overallTime by the size of the currentProcess
            overallTime += ProcList.get(currentProcess).getExecSizeTime();         	
         	//adds the finished process to the output array
            OutputArray[currentProcess] = new Output();
         	OutputArray[currentProcess].setOutputID(ProcList.get(currentProcess).getProcessID());
         	OutputArray[currentProcess].setWaitTime(startTime - ProcList.get(currentProcess).getATime());
         	OutputArray[currentProcess].setTurnTime(overallTime - ProcList.get(currentProcess).getATime());
        }
        System.out.println();
        return OutputArray; //return the output array
	}

    public static Output[] SPN(ArrayList<Process> ProcList, int dispatch) //the ShortestProcessNext scheduling algorithm
    {
        //initialises the overallTime, the output array and txt output formatting
        Output[] OutputArray = new Output[ProcList.size()];
        int overallTime = 0;
        System.out.println("SPN:");
        while (true)
        {    
            int currentProcess = Integer.MAX_VALUE; //Integer.MAX_VALUE is used to represent no next process being picked
            //the main for loop that determines the next process to be used
            for (int i = 0; i < ProcList.size(); i++)
            {
                //checks if the process has arrived and if that process has been completed yet
                if ((ProcList.get(i).getATime() <= overallTime) && (OutputArray[i] == null))
                {
                    //uses SPN to determine next process (smallestExecSizeTime then smallestID)
                    if (currentProcess == Integer.MAX_VALUE) {currentProcess = i;}
                    else if (ProcList.get(i).getExecSizeTime() < ProcList.get(currentProcess).getExecSizeTime()) {currentProcess = i;}
                    else if (ProcList.get(i).getExecSizeTime() == ProcList.get(currentProcess).getExecSizeTime())
                    {
                        if(i < currentProcess) {currentProcess = i;}
                    }   
                }  
            }
            if (currentProcess == Integer.MAX_VALUE) {break;} //if no nextProcess is found the while loop is broken
            //adds the dispatch value to the overall time and determines the startTime of the process
            overallTime = overallTime + dispatch;
            int startTime = overallTime;
            //outputs the schedule event as txt
            System.out.println("T" + overallTime + ": p" + ProcList.get(currentProcess).getProcessID() + "(" + ProcList.get(currentProcess).getPriority() + ")");    
            //increments the overallTime by the size of the currentProcess
            overallTime += ProcList.get(currentProcess).getExecSizeTime();          
            //adds the finished process to the output array
            OutputArray[currentProcess] = new Output();
            OutputArray[currentProcess].setOutputID(ProcList.get(currentProcess).getProcessID());
            OutputArray[currentProcess].setWaitTime(startTime - ProcList.get(currentProcess).getATime());
            OutputArray[currentProcess].setTurnTime(overallTime - ProcList.get(currentProcess).getATime());
        }
        System.out.println();
        return OutputArray; //return the output array
    }

    public static Output[] PP(ArrayList<Process> ProcList, int dispatch) //the preemptive priority scheduling algorithm
    {
        //initialises the overallTime, the output array, the previousProcess (as Integer.MIN_VALUE), the workLeft and startTimes arrays and txt output formatting
        Output[] OutputArray = new Output[ProcList.size()];
        int overallTime = 0;
        int previousProcess = Integer.MIN_VALUE;
        int[] startTimes = new int[ProcList.size()];
        int[] workLeft = new int[ProcList.size()];
        System.out.println("PP:");
        //populates the workLeft arrays with ExecSize times or each process
        for (int i = 0; i < ProcList.size(); i++) {workLeft[i] = ProcList.get(i).getExecSizeTime();}
        
        while (true)
        {    
            int currentProcess = Integer.MAX_VALUE; //Integer.MAX_VALUE is used to represent no next process being picked
            //the main for loop that determines the next process to be used
            for (int i = 0; i < ProcList.size(); i++)
            {
                //checks if the process has arrived and if that process has been completed yet
                if ((ProcList.get(i).getATime() <= overallTime) && (OutputArray[i] == null))
                {
                    //uses PP to determine next process (highestPriority then smallestID)
                    if (currentProcess == Integer.MAX_VALUE) {currentProcess = i;}
                    else if (ProcList.get(i).getPriority() < ProcList.get(currentProcess).getPriority()) {currentProcess = i;}
                    else if (ProcList.get(i).getPriority() == ProcList.get(currentProcess).getPriority())
                    {
                        if(i < currentProcess) {currentProcess = i;}
                    }   
                }  
            }
            if (currentProcess == Integer.MAX_VALUE) {break;} //if no nextProcess is found the while loop is broken
            if (previousProcess != currentProcess) //if the same process is chosen twice in a row there is no need to add the dispatch or output the event txt again
            {
                //adds the dispatch value to the overall time and outputs the schedule event as txt
                overallTime = overallTime + dispatch;
                System.out.println("T" + overallTime + ": p" + ProcList.get(currentProcess).getProcessID() + "(" + ProcList.get(currentProcess).getPriority() + ")");
            }
            //if this is the first work done on this specific process the startTime of the process is recorded in startTimes array
            if (ProcList.get(currentProcess).getExecSizeTime() == workLeft[currentProcess]) {startTimes[currentProcess] = overallTime;}    
            //adds 1 to the overall time and removes 1 section of the work of the chosen process
            overallTime++;
            workLeft[currentProcess]--;
            //if the process is finished it is added to the output array
            if (workLeft[currentProcess] == 0)
            {     
                OutputArray[currentProcess] = new Output();
                OutputArray[currentProcess].setOutputID(ProcList.get(currentProcess).getProcessID());
                OutputArray[currentProcess].setWaitTime(startTimes[currentProcess] - ProcList.get(currentProcess).getATime() + ((overallTime - startTimes[currentProcess]) - ProcList.get(currentProcess).getExecSizeTime()));
                OutputArray[currentProcess].setTurnTime(overallTime - ProcList.get(currentProcess).getATime());
            }
            previousProcess = currentProcess;
        }
        System.out.println();
        return OutputArray;
    }

    public static Output[] PRR(ArrayList<Process> ProcList, int dispatch) //the priority round robin scheduling algorithm
    {
        //initialises the overallTime, the output array, the previousProcess, txt output formatting as well as the time quotient
        Output[] OutputArray = new Output[ProcList.size()];
        int overallTime = 0;
        int timeQ;
        int previousProcess = Integer.MIN_VALUE;
        int[] startTimes = new int[ProcList.size()];
        int[] workLeft = new int[ProcList.size()];
        System.out.println("PRR:");
        //the array and number of processQueueExceptions is also initialised for same arrival ready queue changes
        int numOfExceptions = 0;
        int[][] processQueueExceptions = new int[ProcList.size()][2];
        //populates the workLeft arrays with ExecSize times or each process
        for (int i = 0; i < ProcList.size(); i++) {workLeft[i] = ProcList.get(i).getExecSizeTime();}

        while (true)
        {    
            int currentProcess = Integer.MAX_VALUE; //Integer.MAX_VALUE is used to represent no next process being picked
            //the main for loop that determines the next process to be used
            for (int i = 0; i < ProcList.size(); i++)
            {
                //checks if the process has arrived, if that process has been completed yet and if the previousProcess matches the process
                if ((ProcList.get(i).getATime() <= overallTime) && (OutputArray[i] == null) && i != previousProcess)
                {
                    //uses FCFS to determine next process (smallestATime then smallestID)
                    if (currentProcess == Integer.MAX_VALUE) {currentProcess = i;}
                    else if (ProcList.get(i).getATime() < ProcList.get(currentProcess).getATime()) {currentProcess = i;} 
                    else if (ProcList.get(i).getATime() == ProcList.get(currentProcess).getATime())
                    {
                        if (i < currentProcess) {currentProcess = i;}
                    }  
                } 
            } 

            //if no next process is found, the previous process is checked. If it is not able to be used the while loop is broken
            if (currentProcess == Integer.MAX_VALUE) 
            {
                if ((ProcList.get(previousProcess).getATime() <= overallTime) && (OutputArray[previousProcess] == null)) {currentProcess = previousProcess;}
                else {break;}
            }

            //if a processQueueException is found for the currentProcess it is switched with the other process and the excception is then removed
            for (int j = 0; j < ProcList.size(); j++)
            {
                if (processQueueExceptions[j][0] == currentProcess && (processQueueExceptions[j][0] != processQueueExceptions[j][1]))
                {
                    currentProcess = processQueueExceptions[j][1];
                    processQueueExceptions[j][0] = Integer.MAX_VALUE;
                    processQueueExceptions[j][1] = Integer.MAX_VALUE;
                }
            } 
                       

            if (previousProcess != currentProcess) //if the same process is chosen twice in a row there is no need to add the dispatch or output the event txt again
            {
                //adds the dispatch value to the overall time and outputs the schedule event as txt
                overallTime = overallTime + dispatch;
                System.out.println("T" + overallTime + ": p" + ProcList.get(currentProcess).getProcessID() + "(" + ProcList.get(currentProcess).getPriority() + ")");
            }
            
            //if this is the first work done on this specific process the startTime of the process is recorded in startTimes array
            if (ProcList.get(currentProcess).getExecSizeTime() == workLeft[currentProcess]) {startTimes[currentProcess] = overallTime;}    

            //determines the time quotient for the currentProcess
            if (ProcList.get(currentProcess).getPriority() < 3) {timeQ = 4;}
            else {timeQ = 2;}

            //loops through the allowed number of time units from the time quotient unless the process finished if which case the process is added to the output array and the for loop breaks
            for (int i = 0; i < timeQ; i++) 
            {
                overallTime++;
                workLeft[currentProcess]--;
                if (workLeft[currentProcess] == 0)
                {     
                    OutputArray[currentProcess] = new Output();
                    OutputArray[currentProcess].setOutputID(ProcList.get(currentProcess).getProcessID());
                    OutputArray[currentProcess].setWaitTime(startTimes[currentProcess] - ProcList.get(currentProcess).getATime() + ((overallTime - startTimes[currentProcess]) - ProcList.get(currentProcess).getExecSizeTime()));
                    OutputArray[currentProcess].setTurnTime(overallTime - ProcList.get(currentProcess).getATime());
                    break;
                } 
            }

            //if another process arrives at the same time another one has just been interrupted, a processQueueException is made making the newly arrived Process come before the interrupted process
            for (int i = 0; i < ProcList.size(); i++)
            {
                if (overallTime == ProcList.get(i).getATime() && overallTime != 0)
                {
                    processQueueExceptions[numOfExceptions][0] = currentProcess;
                    processQueueExceptions[numOfExceptions][1] = i;
                    numOfExceptions++;
                }
            }
            previousProcess = currentProcess; 

        }
        System.out.println();
        return OutputArray;
    }
    
    public static void OutputSchedule(Output OArray[]) //formats the OutputArray for text output
	{
		System.out.println("Process Turnaround Time Waiting Time");
        for (int i = 0; i < OArray.length; i++)
        {
            System.out.println("p" + OArray[i].getOutputID() + "      " + OArray[i].getTurnTime() + "              " + OArray[i].getWaitTime());  
        }
        System.out.println();
	}

	public static void Average(Output OArray[], String AlgorithmName) //finds the average of both TurnTime and WaitTime and formats them for text output
	{
		float AverageTurnTime = 0;
		float AverageWaitTime = 0;
		for (int i = 0; i < OArray.length; i++)
		{
            AverageTurnTime = AverageTurnTime + OArray[i].getTurnTime();
            AverageWaitTime = AverageWaitTime + OArray[i].getWaitTime();
		}
		AverageTurnTime = AverageTurnTime / OArray.length;
		AverageWaitTime = AverageWaitTime / OArray.length;
		System.out.println(AlgorithmName + "            " + AverageTurnTime + "                     " +   AverageWaitTime);



	}


}


