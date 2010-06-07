package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Random Query - A query to retrieve random objects
 * @author ricardo
 */
public class RandomQuery implements Query {

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.RANDOM;
    }

}
