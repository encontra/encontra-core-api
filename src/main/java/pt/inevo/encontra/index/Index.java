package pt.inevo.encontra.index;

import java.util.List;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.Query.QueryType;

/**
 * Generic index structure.
 * Defines the shared methods by all the index structures that implement this
 * interface, for insert, remove, list, and search through it;
 * @author ricardo
 */
public interface Index<O extends AbstractObject> {

    /**
     * Inserts an object into the index
     * @param obj the object to be inserted
     * @return the success of the operation
     */
    public boolean insert(O obj);

    /**
     * Removes an object from the index
     * @param obj the object to be removed
     * @return the success of the operation
     */
    public boolean remove(O obj);

    public int size();
    
    public IndexEntry get(int idx);
    
    /**
     * Checks if an object already exists in the index.
     * @param object the object to be checked
     * @return true if already exists, or false otherwise
     */
    public boolean contains(AbstractObject object);

    /**
     * Obtains all the objects that are within the index
     * @return a list with all the objects
     */
    public List<O> getAll();

    /**
     * Obtains all the supported QueryTypes by the index
     * @return a list of the supported query types
     */
    public QueryType [] getSupportedQueryTypes();

    /**
     * Checks if a specific QueryType is supported by the index
     * @param type the QueryType to be checked
     * @return true if type is supported, otherwise, retrieved false
     */
    public boolean supportsQueryType(QueryType type);

    /**
     * Searches the index by a specific query
     * @param query the interrogation to be performed to the index
     * @return a ResultSet with all the Result's that respect the query
     */
    public ResultSet search(Query query);

}