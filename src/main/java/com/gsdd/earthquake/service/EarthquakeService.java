package com.gsdd.earthquake.service;

import com.gsdd.earthquake.domain.EarthquakeCount;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;

public interface EarthquakeService {

  EarthquakeResponse earthquakeQuery(EarthquakeRequest request) throws Exception;

  EarthquakeCount earthquakeCountQuery(EarthquakeRequest request) throws Exception;

}
