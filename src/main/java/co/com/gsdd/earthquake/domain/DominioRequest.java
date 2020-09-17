/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JavaRevolutions
 */
public class DominioRequest implements Serializable {
    private double minMagnitud;
    private int dias;
    private boolean porFechas;
    private Date fechaInicio;
    private Date fechaFin;
    private int start = 1;
    private int lenght = 10;

    /**
     * @return the minMagnitud
     */
    public double getMinMagnitud() {
        return minMagnitud;
    }

    /**
     * @param minMagnitud the minMagnitud to set
     */
    public void setMinMagnitud(double minMagnitud) {
        this.minMagnitud = minMagnitud;
    }

    /**
     * @return the dias
     */
    public int getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * @return the porFechas
     */
    public boolean isPorFechas() {
        return porFechas;
    }

    /**
     * @param porFechas the porFechas to set
     */
    public void setPorFechas(boolean porFechas) {
        this.porFechas = porFechas;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the lenght
     */
    public int getLenght() {
        return lenght;
    }

    /**
     * @param lenght the lenght to set
     */
    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

}
