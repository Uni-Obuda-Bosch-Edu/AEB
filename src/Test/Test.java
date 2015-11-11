package Test;

import Implementation.AEB;

public class Test {
	public static void main(String[] args)
	{
		Radar radar=new Radar();
		DriverInput control=new DriverInput();
		Container bus=new Container();
		bus.setMotionVectorXWithLengthAsSpeedInKm(50);
		bus.setMotionVectorYWithLengthAsSpeedInKm(0);
		AEB testmodul=new AEB(radar, bus, control, 100);
		testmodul.StartSystem();
		while(bus.getMotionVectorXWithLengthAsSpeedInKm()>0)
		{
			double speed=Math.sqrt(Math.pow(bus.getMotionVectorXWithLengthAsSpeedInKm(), 2)+Math.pow(bus.getMotionVectorYWithLengthAsSpeedInKm(), 2));
			System.out.println("Az autó sebessége: "+speed);
			System.out.println("A fékpedál állása: "+bus.getBrakePedalPushPercent());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double speed=Math.sqrt(Math.pow(bus.getMotionVectorXWithLengthAsSpeedInKm(), 2)+Math.pow(bus.getMotionVectorYWithLengthAsSpeedInKm(), 2));
		System.out.println("Az autó sebessége: "+speed);
		System.out.println("A fékpedál állása: "+bus.getBrakePedalPushPercent());
	}
}
