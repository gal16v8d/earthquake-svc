/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JavaRevolutions
 */
public class DominioSismos implements Serializable {
    private String type;
    private List<DominioFeatures> features;
    private DominioMetadata metadata;
    
    public DominioSismos() {
        features = new ArrayList<>();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the features
     */
    public List<DominioFeatures> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<DominioFeatures> features) {
        this.features = features;
    }

    /**
     * @return the metadata
     */
    public DominioMetadata getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(DominioMetadata metadata) {
        this.metadata = metadata;
    }
}
