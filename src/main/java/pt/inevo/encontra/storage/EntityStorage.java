package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * Entry storage interface
 * @param <I> entry's key
 * @param <O> entry to be saved
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
    public O get(I id);

     /**
     * Save all changes made to an object.
     *
     * @param object object
     */
    public O save(O object);

    /**
     * Save all changes made to several object.
     *
     * @param objects the objects to be saved
     */
    public void save(final O... objects);
    
    /**
     * Remove an object by given id. Check if object is not default one.
     *
     *
     * @param object object
     */
    public void delete(O object);
}
