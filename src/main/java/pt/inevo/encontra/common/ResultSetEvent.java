package pt.inevo.encontra.common;

/**
 *
 * @author Ricardo
 */
public class ResultSetEvent<T> {

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return the result
     */
    public Result<T> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Result<T> result) {
        this.result = result;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

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
}
