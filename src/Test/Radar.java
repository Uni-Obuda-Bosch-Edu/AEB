package Test;

import Interfaces.IRadarBase;
import Interfaces.IWorldObject;
import World.WorldObj;
import World.WorldObjectTypes;

public class Radar implements IRadarBase {

	@Override
	public IWorldObject GetNearestObject() {
		double[] pos={80,0};
		double[][] trans={{1,0},{0,1}};
		return new WorldObj(0,"men", WorldObjectTypes.Type.Misc, WorldObjectTypes.Sign.None, WorldObjectTypes.Lane.None, WorldObjectTypes.Misc.Man, pos ,trans);
	}

	@Override
	public double RelativeSpeedInKPH(IWorldObject object) {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public double DistanceFromObject(IWorldObject object) {
		// TODO Auto-generated method stub
		return 2;
	}

}
