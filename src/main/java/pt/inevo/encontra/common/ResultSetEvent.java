package pt.inevo.encontra.common;

/**
 *
 * @author Ricardo
 */
public class ResultSetEvent {

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
    public Result getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public enum Event {
        ADDED, REMOVED, CLEARED
    }

    private Event event;
    private Result result;

    public ResultSetEvent(){}

    public ResultSetEvent(Event event, Result result){
        this.event = event;
        this.result = result;
    }
}
