package pt.inevo.encontra.index;

/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class AbstractObject<T> {

    protected T id;

    public AbstractObject(T id){
        this.id = id;
    }

    /**
     * Gets the id of this object.
     * @return the id
     */
    public T getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(T id) {
        this.id = id;
    }
}
