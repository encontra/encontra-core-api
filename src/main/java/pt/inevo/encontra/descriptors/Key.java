package pt.inevo.encontra.descriptors;

import java.io.Serializable;

/**
 * Key -> element in the (Key, Descriptor) pair.
 * @author ricardo
 */
public class Key<K extends Serializable> implements Serializable {

    protected K keyValue;

    public Key(K key){
        this.keyValue = key;
    }

    /**
     * Gets the value of this key
     * @return the keyValue
     */
    public K getKeyValue() {
        return keyValue;
    }

    /**
     * @param keyValue the keyValue to set
     */
    public void setKeyValue(K keyValue) {
        this.keyValue = keyValue;
    }   
}