/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.domain;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author JavaRevolutions
 */
public class DominioProperties implements Serializable {
    DecimalFormat df = new DecimalFormat("#.#");
    private double mag;
    private String place;
    private long time;
    private String style = "";
    private String fecha;
    private String fechaLocal;
    private String url;
    private String mapa;
    private String googleMaps;

    /**
     * @return the mag
     */
    public double getMag() {
        mag = Double.valueOf(df.format(mag));
        return mag;
    }

    /**
     * @param mag the mag to set
     */
    public void setMag(double mag) {
        this.mag = mag;
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        int fontSize = 14;
        /*if(getMag() > 0 && getMag() < 4) {
            return "font-weight: bold; color: green; font-size: "+fontSize+"px;";
        }*/
        if(getMag() > 3.9 && getMag() < 6) {
            return "font-weight: bold; color: green; font-size: "+fontSize+"px;";
        }
        if(getMag() > 5.9 && getMag() < 7) {
            return "font-weight: bold; color: blue; font-size: "+(fontSize+4)+"px;";
        }
        if(getMag() > 6.9 && getMag() < 8) {
            return "font-weight: bold; color: red; font-size: "+(fontSize+8)+"px;";
        }
        if(getMag() > 7.9) {
            return "font-weight: bold; color: purple; font-size: "+(fontSize+16)+"px;";
        }
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the fechaLocal
     */
    public String getFechaLocal() {
        return fechaLocal;
    }

    /**
     * @param fechaLocal the fechaLocal to set
     */
    public void setFechaLocal(String fechaLocal) {
        this.fechaLocal = fechaLocal;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the mapa
     */
    public String getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    /**
     * @return the googleMaps
     */
    public String getGoogleMaps() {
        return googleMaps;
    }

    /**
     * @param googleMaps the googleMaps to set
     */
    public void setGoogleMaps(String googleMaps) {
        this.googleMaps = googleMaps;
    }

}
