/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import java.util.List;
import pt.inevo.encontra.index.ResultSet;

/**
 * A generic query combiner.
 * @author ricardo
 */
public interface QueryCombiner {

    public ResultSet combine(List<ResultSet> results);
}