/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.index;

/**
 * Represents a element that can be stored (saved).
 * @author ricardo
 */
public interface Storable {

    public boolean load(String path);
    public boolean save(String path);
}
