package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntity;

import java.io.Serializable;


/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class AbstractObject<ID extends Serializable,O> implements IEntity<ID> {

    protected O object=null;
    protected ID id=null;

    public AbstractObject(){}

    public AbstractObject(ID identifier, O object){
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
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
       this.id=id;
    }


}
