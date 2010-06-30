package pt.inevo.encontra.query;

/**
 * Query Weight - Used for specifing the weight of a query.
 * Useful for query combination (combination of the results provided by a query).
 * @author ricardo
 */
public class QueryWeight {

    /**
     * The weight of the query
     */
    protected double weight;

    public QueryWeight(){}

    public QueryWeight(double weight){
        this.weight = weight;
    }

    /**
     * Gets the value of the weight for the underlying query
     * @return
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * Sets the value of the weight for the underlying query
     * @param weight
     */
    public void setWeight(double weight){
        this.weight = weight;
    }
}