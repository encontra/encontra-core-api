/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;


/**
 * Criteria Query - Query for combining Criteria
 * @author ricardo
 */
public class CriteriaQuery extends Query {

    @Override
    public QueryType getType() {
        return QueryType.CRITERIA;
    }
}
