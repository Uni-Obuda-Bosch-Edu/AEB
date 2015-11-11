package Implementation;
import java.util.Timer;
import java.util.TimerTask;

import FrameWork.busInterface.DriverInput_Out;
import FrameWork.busInterface.Public_In;
import Interfaces.IRadarBase;
import Interfaces.IWorldObject;
public class AEB {
	
	public AEB(IRadarBase radar,Public_In bus,DriverInput_Out control, long refreshInterval)
	{
		this.refreshInterval=refreshInterval;
		task = new Refresher(radar,bus,control);
		timer = new Timer();
	}
	Refresher task;
	long refreshInterval;
	Timer timer;
	
	public void StartSystem()
	{
		timer.scheduleAtFixedRate(task, 0, refreshInterval);
	}
	
	public void StopSystem()
	{
		timer.cancel();
	}
	

}

class Refresher extends TimerTask
{
	IRadarBase radar;
	Public_In bus;
	DriverInput_Out control;
	public Refresher(IRadarBase radar,Public_In bus, DriverInput_Out control)
	{
		this.bus=bus;
		this.radar=radar;
		this.control=control;
	}
	
	@Override
	public void run() {
		IWorldObject o=radar.GetNearestObject();
		double speed=Math.sqrt(Math.pow(bus.getMotionVectorXWithLengthAsSpeedInKm(), 2)+Math.pow(bus.getMotionVectorYWithLengthAsSpeedInKm(), 2));
		if(EmergencyBreak(radar.RelativeSpeedInKPH(o),radar.DistanceFromObject(o),speed))
		{
			control.setBrakePedalPushPercent(100);
		}
			
		
	}
		
	private double BreakingDistance(double speed)
    {
        double mPERs = speed * 0.277777778;
        return Math.pow(mPERs,2)/(2*7.5);
    }
	
	private Boolean EmergencyBreak(double relativSpeed,double distance, double speed)
    {
        
        if (speed>0 && relativSpeed >= speed - 3 && BreakingDistance(speed) + 3 >= distance)
        {
            return true;
        }
        else return false;
    }
}
