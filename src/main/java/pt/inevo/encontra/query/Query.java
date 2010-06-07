package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Generic query interface
 * @author ricardo
 */
public interface Query {

    /**
     * QueryTypes
     */
    public enum QueryType{
        RANDOM, KNN, RANGE, BOOLEAN, TEXT, COMBINED
    }

    public ResultSet execute();

    /**
     * Obtains the query type.
     * @return the type that represents the query
     */
    public QueryType getType();
}
