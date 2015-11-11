package Interfaces;
import java.util.List;

public interface IRadarBase
{
    IWorldObject GetNearestObject();
    double RelativeSpeedInKPH(IWorldObject object);
    double DistanceFromObject(IWorldObject object);
}
