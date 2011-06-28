package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * Interface marks class which can be persisted.
 * The Id must be unique.
 *
 * @param <I> type of primary key, it must be serializable
 */
public interface IEntity<I extends Serializable> extends Serializable {

    /**
     * Get primary key.
     *
     * @return primary key
     */
    I getId();

    /**
     * Set primary key.
     *
     * @param id primary key
     */
    void setId(I id);
}