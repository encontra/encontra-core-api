package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.index.IndexEntry;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.storage.IEntry;

import java.io.Serializable;


public interface Descriptor extends HasDistance<Descriptor>, IEntry {
    
    String getName();

    /**
     * Gets a String representation of the Descriptor.
     * @return
     */
    //String getStringRepresentation();
    //Descriptor setStringRepresentation(String d);

}
