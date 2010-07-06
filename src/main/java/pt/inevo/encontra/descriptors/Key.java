package pt.inevo.encontra.descriptors;

/**
 * Key -> element in the (Key, Descriptor) pair.
 * @author ricardo
 */
public class Key<T> {

    protected T keyValue;

    public Key(T key){
        this.keyValue = key;
    }

    /**
     * Gets the value of this key
     * @return the keyValue
     */
    public T getKeyValue() {
        return keyValue;
    }

    /**
     * @param keyValue the keyValue to set
     */
    public void setKeyValue(T keyValue) {
        this.keyValue = keyValue;
    }
    
}
