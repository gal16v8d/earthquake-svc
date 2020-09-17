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
public class DominioGeometry implements Serializable {
    private double[] coordinates;

    /**
     * @return the coordinates
     */
    public double[] getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
