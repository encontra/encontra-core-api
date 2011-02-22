package pt.inevo.encontra.common;

/**
 * Listener for the ResultSet
 * @author Ricardo
 */
public interface ResultSetListener<T> {

    public void handleEvent(ResultSetEvent<T> event);
}
