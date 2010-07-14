package pt.inevo.encontra.storage;


import java.io.Serializable;
import java.util.List;


/**
 * Entry storage interface
 * @param <I>
 * @param <O>
 */
public interface EntityStorage<I extends Serializable,O extends IEntity<I>> {


    /**
     * Retrieve an persisted object using the given id as primary key.
     *
     * Returns null if not found.
     *
     * @param id object's primary key
     * @return object
     */
    O get(I id);


     /**
     * Save all changes made to an object.
     *
     * @param object object
     */
    O save(O object);


    public void save(final O... objects);
    
    /**
     * Remove an object by given id. Check if object is not default one.
     *
     *
     * @param object object
     */
    void delete(O object);
}
