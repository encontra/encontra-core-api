package pt.inevo.encontra.storage;

import pt.inevo.encontra.query.criteria.StorageCriteria;

import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

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
     * Checks if the entity with the I id respects the criteria
     * @param id
     * @param criteria
     * @return
     */
    public boolean validate(I id, StorageCriteria criteria);

    /**
     * Retrieves a list of valid ids for the provided criteria
     * @param criteria
     * @return
     */
    public List<I> getValidIds(StorageCriteria criteria);

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
