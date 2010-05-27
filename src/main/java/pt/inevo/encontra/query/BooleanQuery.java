/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Boolean Query - Can do Queries like "A and B" or "A or B"
 * @author ricardo
 */
public class BooleanQuery implements Query {

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.BOOLEAN;
    }
}
