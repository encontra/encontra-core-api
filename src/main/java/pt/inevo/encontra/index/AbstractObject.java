package pt.inevo.encontra.index;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class AbstractObject<I,O> {

    protected O object=null;
    protected I id=null;

    public AbstractObject(){}

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
    public void setObject(O object) {
        this.object = object;
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
