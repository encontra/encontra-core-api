package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.index.IndexEntry;


public interface Descriptor<T> extends HasDistance<Descriptor<T>> { // IndexEntry, 
    
    String getId();

    /**
     * Gets a String representation of the Descriptor.
     * @return
     */
    String getStringRepresentation();
    Descriptor<T> setStringRepresentation(String d);
}
