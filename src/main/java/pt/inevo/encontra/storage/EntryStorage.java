package pt.inevo.encontra.storage;


import java.io.Serializable;
import java.util.List;


/**
 * Entry storage interface
 * @param <I>
 * @param <O>
 */
public interface EntryStorage<I extends Serializable,O extends IEntry<I, ?>> {


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
     * Retrieve objects using the given ids as primary keys.
     *
     * @param ids objects's ids
     * @return list of objects
     */
    List<O> get(I... ids);

    /**
     * Retrieve all persisted objects.
     *
     * @return list of objects
     */
    List<O> getAll();

     /**
     * Save all changes made to an object.
     *
     * @param object object
     */
    void save(O object);

    /**
     * Save all changes made to objects.
     *
     * @param objects objects
     */
    void save(O... objects);

    /**
     * Remove an object by given id. Check if object is not default one.
     *
     *
     * @param id object's pk
     */
    void delete(I id);
}
