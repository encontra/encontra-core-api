package pt.inevo.encontra.index;

/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class AbstractObject<I,O> {

    protected O object;
    protected I id;

    public AbstractObject(I identifier, O object){
        this.id = identifier;
        this.object = object;
    }

    /**
     * Gets the object of this object.
     * @return the object
     */
    public O getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(O obj) {
        this.object = obj;
    }

    /**
     * @return the id
     */
    public I getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(I id) {
        this.id = id;
    }
}
