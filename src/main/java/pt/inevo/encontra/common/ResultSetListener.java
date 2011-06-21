package pt.inevo.encontra.common;

/**
 * Listener for the ResultSet.
 * Interface for listeners that want to handle ResultSetEvents.
 * @author Ricardo
 */
public interface ResultSetListener<T> {

    /**
     * Handles the triggered ResultSetEvent
     * @param event the event to handle.
     */
    public void handleEvent(ResultSetEvent<T> event);
}
