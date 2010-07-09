package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.common.distance.DistanceMeasure;
import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.common.distance.HasWeights;
import pt.inevo.encontra.index.Vector;


public class WeightedDistanceMeasure<T extends Vector & HasDistance> implements DistanceMeasure<T>{

    @Override
    public double distance(T v1, T v2) {

        double result = 0.0;


        return result;
    }

    @Override
    public double distance(double centroidLengthSquare, Vector centroid, Vector v) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
