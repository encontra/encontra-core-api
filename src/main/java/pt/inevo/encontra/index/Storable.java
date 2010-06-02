package pt.inevo.encontra.index;

/**
 * Represents a element that can be stored (saved).
 * @author ricardo
 */
public interface Storable {

    /**
     * Loads the element from the specified path
     * @param path the path from which to load the element
     * @return a boolean that represents the success of the operation
     */
    public boolean load(String path);

    /**
     * Stores the element to specified path
     * @param path where to save the element
     * @return a boolean that represents the success of the operation
     */
    public boolean save(String path);
}
