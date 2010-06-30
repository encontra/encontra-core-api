package pt.inevo.encontra.query;

/**
 * Generic query interface
 * @author ricardo
 */
public abstract class Query {

    /**
     * QueryTypes
     */
    public enum QueryType{
        RANDOM, KNN, APROXIMATE_KNN, RANGE, BOOLEAN, TEXT, COMBINED, CRITERIA
    }

    /**
     * The weight of the query - used for combination
     */
    protected QueryWeight weight;

    /**
     * The order of the query - used for combination (specifying which query mas
     * be made before the others, the sequence of execution)
     */
    protected QueryOrder order;

    /**
     * Gets the Weight of this Query.
     * @return
     */
    public QueryWeight getWeight(){
        return this.weight;
    }

    /**
     * Sets the weight of this query.
     * @param w
     */
    public void setWeight(QueryWeight w){
        this.weight = w;
    }

    /**
     * Gets the Order of this Query.
     * @return
     */
    public QueryOrder getOrder(){
        return this.order;
    }

    /**
     * Sets the order of this Query.
     * @param o
     */
    public void setOrder(QueryOrder o){
        this.order = o;
    }

    /**
     * Obtains the query type.
     * @return the type that represents the query
     */
    public abstract QueryType getType();
}
