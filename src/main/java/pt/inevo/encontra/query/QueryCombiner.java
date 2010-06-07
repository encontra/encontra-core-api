package pt.inevo.encontra.query;

import java.util.List;
import pt.inevo.encontra.index.ResultSet;

/**
 * A generic query combiner. Combines the Result's within all the ResultSet's.
 * Each Engine can have one or more combiners to join the results of one query.
 * The combination algorithm is specified by the objects that implement this interface.
 * @author ricardo
 */
public interface QueryCombiner {

    /**
     * Combines the set of ResultSet's passed as the argument accordingly to
     * the underlying algorithm.
     * @param results a list of ResultSet's to be combined
     * @return a ResulSet with the combined Result's
     */
    public ResultSet combine(List<ResultSet> results);
}