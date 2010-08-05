package pt.inevo.encontra.common.distance;

/** This interface is used for objects which can determine a distance metric between two points */
public interface DistanceMeasure<T extends HasDistance> {

    /**
     * Returns the distance metric applied to the arguments
     *
     * @param v1
     *          a Vector defining a multidimensional point in some feature space
     * @param v2
     *          a Vector defining a multidimensional point in some feature space
     * @return a scalar doubles of the distance
     */
    double distance(T v1, T v2);

    /**
     * Optimized version of distance metric for sparse vectors. This distance computation requires operations
     * proportional to the number of non-zero elements in the vector instead of the cardinality of the vector.
     *
     * @param centroidLengthSquare
     *          Square of the length of centroid
     * @param v
     * @param centroid
     *          Centroid vector
     * @return distance
     */
    double distance(double centroidLengthSquare, T centroid, T v);
}
