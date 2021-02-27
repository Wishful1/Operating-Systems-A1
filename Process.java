/*
Name: Scott Lonsdale
Course: COMP2240
Student Number: C3303788
*/


public class Process //each individual process inside the scheduling system
{
	private int ProcessID; //ID of the process
	private int ATime; //Arrival time of the process
	private int ExecSizeTime; //execution size of the process
	private int Priority; //priority of the process

	public Process() //empty constructor
	{
	    ATime = 0;
	    ExecSizeTime = 0;
	    ProcessID = 0;
	    Priority = 0;
	}

	public Process(int InitATime, int InitExecSizeTime, int InitProcessID, int InitPriority) //constructor with inputs
	{
	    ATime = InitATime;
	    ExecSizeTime = InitExecSizeTime;
	    ProcessID = InitProcessID;
	    Priority = InitPriority;
	}
    //basic setters/getters
	public void setATime(int ATime)
	{
		this.ATime = ATime;
	}

	public int getATime()
	{
		return ATime;
	}

    public void setExecSizeTime(int ExecSizeTime)
	{
		this.ExecSizeTime = ExecSizeTime;
	}

	public int getExecSizeTime()
	{
		return ExecSizeTime;
	}

	public void setProcessID(int ProcessID)
	{
		this.ProcessID = ProcessID;
	}

	public int getProcessID()
	{
		return ProcessID;
	}

	public void setPriority(int Priority)
	{
		this.Priority = Priority;
	}

	public int getPriority()
	{
		return Priority;
	}
	



}