package pt.inevo.encontra.query;

/**
 * Query order - used when combining queries
 * Specifies the order of combination of the queries
 * Higher order queries will be executed first than those with lower
 * @author ricardo
 */
public class QueryOrder {

    /**
     * The order of the query
     */
    protected int order;

    public QueryOrder(){}

    public QueryOrder(int order){
        this.order = order;
    }

    /**
     * Gets the value of the order property for the underlying query.
     * @return
     */
    public int getValue(){
        return this.order;
    }

    /**
     * Sets the value of the order property for the underlying query.
     * @param order
     */
    public void setValue(int order){
        this.order = order;
    }
}
