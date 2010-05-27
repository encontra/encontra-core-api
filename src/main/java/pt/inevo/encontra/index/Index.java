/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.index;

import java.util.List;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.Query.QueryType;

/**
 * Generic index structure.
 * @author ricardo
 */
public interface Index {

    public boolean insertObject(AbstractObject obj);
    public boolean removeObject(AbstractObject obj);
    public List<AbstractObject> getAllObjects();

    public QueryType [] getSupportedQueryTypes();
    public boolean supportsQueryType(QueryType type);
    public ResultSet search(Query query);
}