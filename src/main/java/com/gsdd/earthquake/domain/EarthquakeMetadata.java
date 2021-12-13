package com.gsdd.earthquake.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarthquakeMetadata {

  private int status;
  private long count = 0;

}
