package pt.inevo.encontra.common.distance;

import java.io.Serializable;
import pt.inevo.encontra.index.Vector;


/**
 * This class implements a Euclidean distance metric by summing the square root of the squared differences
 * between each coordinate.
 * <p/>
 * If you don't care about the true distance and only need the values for comparison, then the base class,
 * {@link SquaredEuclideanDistanceMeasure}, will be faster since it doesn't do the actual square root of the
 * squared differences.
 */
public class EuclideanDistanceMeasure<T extends Vector & HasDistance> extends SquaredEuclideanDistanceMeasure<T> {

  @Override
  public double distance(T v1, T v2) {
    return Math.sqrt(super.distance(v1, v2));
  }

  @Override
  public double distance(double centroidLengthSquare, T centroid, T v) {
    return Math.sqrt(super.distance(centroidLengthSquare, centroid, v));
  }
}
