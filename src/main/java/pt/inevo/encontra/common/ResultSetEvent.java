package pt.inevo.encontra.common;

/**
 * Event created by the a ResultSet instance.
 * The available ResultSetEvents are: ADDED, REMOVED, CLEARED
 * @author Ricardo
 */
public class ResultSetEvent<T> {

    /**
     * Possible ResultSet Events
     */
    public enum Event {
        ADDED, REMOVED, CLEARED
    }

    private Event event;
    private Result<T> result;
    private Object sender;

    public ResultSetEvent(){}

    public ResultSetEvent(Event event, Result<T> result, Object sender){
        this.event = event;
        this.result = result;
        this.sender = sender;
    }

    /**
     * Gets the event triggered by the ResultSet.
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event triggered.
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Gets the Result ADDED or REMOVED.
     * @return the result
     */
    public Result<T> getResult() {
        return result;
    }

    /**
     * Sets the Result when an ADDED or REMOVED event is triggered.
     * @param result the result to set
     */
    public void setResult(Result<T> result) {
        this.result = result;
    }

    /**
     * Gets the sender of the event, the object that triggered the event.
     * @return the sender object
     */
    public Object getSender() {
        return sender;
    }

    /**
     * Sets the sender of the event, the object that triggered the event.
     * @param sender
     */
    public void setSender(Object sender) {
        this.sender = sender;
    }
}
