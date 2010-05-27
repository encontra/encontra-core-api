/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Random Query - Intent to retrieve a random object.
 * @author ricardo
 */
public class RandomQuery implements Query {

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.RANDOM;
    }

}
