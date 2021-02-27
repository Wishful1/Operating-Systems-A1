/*
Name: Scott Lonsdale
Course: COMP2240
Student Number: C3303788
*/


public class Output //the stats of each finished process
{
	private int WaitTime; //the wait time of the finished process
	private int TurnTime; //the turnaround time of the finished process
	private int OutputID; //the ID of the finished process

	public Output() //empty contructor
	{
	    WaitTime = 0;
	    TurnTime = 0;
	    OutputID = 0;
	}

	public Output(int InitWaitTime, int InitTurnTime, int InitOutputID) //contructor with inputs
	{
	    WaitTime = InitWaitTime;
	    TurnTime = InitTurnTime;
	    OutputID = InitOutputID;
	}
    //basic setters/getters
	public void setWaitTime(int WaitTime)
	{
		this.WaitTime = WaitTime;
	}

	public int getWaitTime()
	{
		return WaitTime;
	}

    public void setTurnTime(int TurnTime)
	{
		this.TurnTime = TurnTime;
	}

	public int getTurnTime()
	{
		return TurnTime;
	}

	public void setOutputID(int OutputID)
	{
		this.OutputID = OutputID;
	}

	public int getOutputID()
	{
		return OutputID;
	}
	



}