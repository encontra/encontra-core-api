package pt.inevo.encontra.common.distance;

import java.io.Serializable;
import pt.inevo.encontra.index.Vector;

/**
 * Like {@link EuclideanDistanceMeasure} but it does not take the square root.
 * <p/>
 * Thus, it is not actually the Euclidean Distance, but it is saves on computation when you only need the
 * distance for comparison and don't care about the actual value as a distance.
 */
public class SquaredEuclideanDistanceMeasure<T extends Vector & HasDistance> implements DistanceMeasure<T>, Serializable {

  @Override
  public double distance(T v1, T v2) {
    return v2.getDistanceSquared(v1);
  }

  @Override
  public double distance(double centroidLengthSquare, T centroid, T v) {
    return centroidLengthSquare - 2 * v.dot(centroid) + v.getLengthSquared();
  }
}