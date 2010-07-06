package pt.inevo.encontra.index;

/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class AbstractObject<T> {

    protected T object;
    protected String id;

    public AbstractObject(String identifier, T object){
        this.id = identifier;
        this.object = object;
    }

    /**
     * Gets the object of this object.
     * @return the object
     */
    public T getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(T id) {
        this.object = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
