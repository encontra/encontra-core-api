package pt.inevo.encontra.query;

import java.util.List;

/**
 * A generic query sorter.
 * Uses the information about the order of execution of
 * the queries to sort the list of queries.
 * @author ricardo
 */
public interface QuerySorter {

    /**
     * Sorts the queries in the list using a sort algorithm provided by the
     * subclasses.
     * @param queries
     * @return
     */
    public List<Query> sort(List<Query> queries);
}
