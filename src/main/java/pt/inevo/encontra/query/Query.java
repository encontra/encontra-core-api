/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.ResultSet;

/**
 * Generic query
 * @author ricardo
 */
public interface Query {

    public enum QueryType{
        RANDOM, KNN, RANGE, BOOLEAN, TEXT, COMBINED
    }

    public ResultSet execute();
    public QueryType getType();
}
