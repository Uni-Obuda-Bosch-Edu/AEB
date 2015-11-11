package Implementation;
import java.util.Timer;
import java.util.TimerTask;

import FrameWork.busInterface.DriverInput_Out;
import FrameWork.busInterface.Public_In;
import Interfaces.IRadarBase;
import Interfaces.IWorldObject;
import Test.Container;
import Test.DriverInput;
public class AEB {
	
	public AEB(IRadarBase radar,Container bus,DriverInput control, long refreshInterval)
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
	Container bus;
	DriverInput control;
	public Refresher(IRadarBase radar,Container bus, DriverInput control)
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
			//control.pushBrakePedal(100);
			//for test
			bus.setBrakePedalPushPercent(100);
			bus.setMotionVectorXWithLengthAsSpeedInKm(bus.getMotionVectorXWithLengthAsSpeedInKm()-1);
			
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
