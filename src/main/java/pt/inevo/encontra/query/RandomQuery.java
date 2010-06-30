package pt.inevo.encontra.query;

/**
 * Random Query - A query to retrieve random objects
 * @author ricardo
 */
public class RandomQuery extends Query {

    @Override
    public QueryType getType() {
        return QueryType.RANDOM;
    }

}
