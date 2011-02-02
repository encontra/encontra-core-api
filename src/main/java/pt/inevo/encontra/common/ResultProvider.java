package pt.inevo.encontra.common;

/**
 * Interface for ResultProvider, a listener for the ResultSet.
 * @author Ricardo
 */
public interface ResultProvider extends ResultSetListener {

    public ResultSet getResultSet();
    public void setResultSet(ResultSet resultSet);
    public boolean registerListener(ResultSetListener listener);
}
