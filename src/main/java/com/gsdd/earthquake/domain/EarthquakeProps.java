package com.gsdd.earthquake.domain;

import java.text.DecimalFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarthquakeProps {

  DecimalFormat df = new DecimalFormat("#.#");
  private double mag;
  private String place;
  private long time;
  private String style = "";
  private String date;
  private String url;
  private String map;
  private String googleMaps;

  public double getMag() {
    mag = Double.valueOf(df.format(mag));
    return mag;
  }

  public String getStyle() {
    int fontSize = 14;
    if (getMag() > 3.5 && getMag() <= 5) {
      style = "font-weight: bold; color: green; font-size: " + fontSize + "px;";
    }
    if (getMag() > 5 && getMag() <= 6) {
      style = "font-weight: bold; color: blue; font-size: " + (fontSize + 4) + "px;";
    }
    if (getMag() > 6 && getMag() <= 7) {
      style = "font-weight: bold; color: red; font-size: " + (fontSize + 8) + "px;";
    }
    if (getMag() > 7) {
      style = "font-weight: bold; color: purple; font-size: " + (fontSize + 16) + "px;";
    }
    return style;
  }

}
