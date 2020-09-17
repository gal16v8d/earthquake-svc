/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.domain;

import java.io.Serializable;

/**
 *
 * @author JavaRevolutions
 */
public class DominioCount implements Serializable {
    private int count;
    private int maxAllowed;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the maxAllowed
     */
    public int getMaxAllowed() {
        return maxAllowed;
    }

    /**
     * @param maxAllowed the maxAllowed to set
     */
    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }
}
