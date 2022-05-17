package com.gsdd.earthquake.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarthquakeResponse {

  private String type;
  private List<EarthquakeFeatures> features;
  private EarthquakeMetadata metadata;

  public EarthquakeResponse() {
    features = new ArrayList<>();
  }
}
