package pt.inevo.encontra.query.criteria;

/**
 * Represents a criteria to be passed to an EntityStorage.
 * The criteria is represented as a string that can be parsed by the storage.
 */
public class StorageCriteria {

    /**
     * The criteria in the form of a string.
     */
    public final String arg;

    public StorageCriteria(String criteria) {
        this.arg = criteria;
    }

    /**
     * Gets the criteria string.
     * @return a string representing the StorageCriteria
     */
    public String getCriteria(){
        return arg;
    }
}
