package com.gsdd.earthquake.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarthquakeFeatures {

    private String type;
    private EarthquakeProps properties;
    private EarthquakeCoordinates geometry;
    private String id;
}
