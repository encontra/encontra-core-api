/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Range Query.
 * @author ricardo
 */
public class RangeQuery implements Query {

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.RANGE;
    }

}
