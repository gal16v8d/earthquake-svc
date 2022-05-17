package com.gsdd.earthquake.service;

import com.gsdd.earthquake.domain.EarthquakeCount;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeService {

  private static final String USGS_BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
  private static final String USGS_COMMON_PARAMS =
      "format={format}&starttime={startTime}" + "&endtime={endTime}&minmagnitude={minMagnitude}";
  private static final String URL_USGS_QUERY =
      USGS_BASE_URL + "query?" + USGS_COMMON_PARAMS + "&limit={limit}&offset={offset}";
  private static final String URL_USGS_COUNT = USGS_BASE_URL + "count?" + USGS_COMMON_PARAMS;
  private final RestTemplate restTemplate;

  private HttpEntity<String> initHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(headers);
  }

  public EarthquakeResponse earthquakeQuery(EarthquakeRequest request) throws Exception {
    SimpleDateFormat dateFormat = setUpDateFormat();
    String endTime = setUpEndTime(request, dateFormat);
    String startTime = setUpStartTime(request, dateFormat);
    Map<String, Object> params = defineQueryParams(startTime, endTime, request.getMinMagnitude());
    params.put("limit", request.getLenght());
    params.put("offset", request.getStart());
    log.info("About to call {} service using params {}", URL_USGS_QUERY, params);
    return restTemplate
        .exchange(URL_USGS_QUERY, HttpMethod.GET, initHeaders(), EarthquakeResponse.class, params)
        .getBody();
  }

  public EarthquakeCount earthquakeCountQuery(EarthquakeRequest request) throws Exception {
    SimpleDateFormat dateFormat = setUpDateFormat();
    String endTime = setUpEndTime(request, dateFormat);
    String startTime = setUpStartTime(request, dateFormat);
    Map<String, Object> params = defineQueryParams(startTime, endTime, request.getMinMagnitude());
    log.info("About to call {} service using params {}", URL_USGS_COUNT, params);
    return restTemplate
        .exchange(URL_USGS_COUNT, HttpMethod.GET, initHeaders(), EarthquakeCount.class, params)
        .getBody();
  }

  private SimpleDateFormat setUpDateFormat() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat;
  }

  private String setUpStartTime(EarthquakeRequest request, SimpleDateFormat dateFormat) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -request.getDays());
    Date startTime = cal.getTime();
    if (request.isByDateRange()) {
      startTime = request.getIniDate();
    }
    return dateFormat.format(startTime);
  }

  private String setUpEndTime(EarthquakeRequest request, SimpleDateFormat dateFormat) {
    Date date = new Date();
    if (request.isByDateRange()) {
      date = request.getEndDate();
    }
    String endTime = dateFormat.format(date);
    endTime += " 23:59:59.999";
    return endTime;
  }

  private Map<String, Object> defineQueryParams(
      String startTime, String endTime, double minMagnitude) {
    Map<String, Object> params = new HashMap<>();
    params.put("format", "geojson");
    params.put("startTime", startTime);
    params.put("endTime", endTime);
    params.put("minMagnitude", minMagnitude);
    return params;
  }
}
