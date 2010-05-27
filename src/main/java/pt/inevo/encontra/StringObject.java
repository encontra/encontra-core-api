/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra;

import pt.inevo.encontra.index.AbstractObject;

/**
 * String object just for testing
 * @author ricardo
 */
public class StringObject extends AbstractObject {

    protected String str;

    public StringObject(String s){
        this.str = s;
    }

    /**
     * @return the str
     */
    public String getStr() {
        return str;
    }

    /**
     * @param str the str to set
     */
    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString(){
        return str;
    }

}
