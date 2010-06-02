package pt.inevo.encontra.query;

import java.util.ArrayList;
import pt.inevo.encontra.index.ResultSet;

/**
 * Boolean Query - Can do Queries like "A and B" or "A or B"
 * @author ricardo
 */
public class BooleanQuery implements Query {

    public enum BooleanType {
        AND, OR
    }

    protected ArrayList<Query> clauses;
    protected BooleanType type;

    public BooleanQuery(){
        clauses = new ArrayList<Query>();
    }

    public void addClause(Query query){
        clauses.add(query);
    }

    /**
     * @return the type
     */
    public BooleanType getBooleanType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setBooleanType(BooleanType type) {
        this.type = type;
    }

    /**
     * @return the clauses
     */
    public ArrayList<Query> getClauses() {
        return clauses;
    }

    /**
     * @param clauses the clauses to set
     */
    public void setClauses(ArrayList<Query> clauses) {
        this.clauses = clauses;
    }

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.BOOLEAN;
    }
}
