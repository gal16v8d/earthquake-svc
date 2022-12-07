package com.gsdd.earthquake.service;

import com.gsdd.earthquake.domain.EarthquakeCount;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeServiceImpl implements EarthquakeService {

  private final WebClient usgsClient;

  @Override
  public EarthquakeResponse earthquakeQuery(EarthquakeRequest request) throws Exception {
    SimpleDateFormat dateFormat = setUpDateFormat();
    String startTime = setUpStartTime(request, dateFormat);
    String endTime = setUpEndTime(request, dateFormat);
    log.info(
        "About to call query service for request {} - starttime: {} - endtime: {}",
        request,
        startTime,
        endTime);
    return usgsClient.get()
        .uri(
            uriBuilder -> uriBuilder.path("query")
                .queryParam("format", "geojson")
                .queryParam("starttime", startTime)
                .queryParam("endtime", endTime)
                .queryParam("minmagnitude", request.getMinMagnitude())
                .queryParam("limit", request.getLenght())
                .queryParam("offset", request.getStart())
                .build())
        .retrieve()
        .bodyToMono(EarthquakeResponse.class)
        .block(Duration.ofMinutes(1L));
  }

  @Override
  public EarthquakeCount earthquakeCountQuery(EarthquakeRequest request) throws Exception {
    SimpleDateFormat dateFormat = setUpDateFormat();
    String startTime = setUpStartTime(request, dateFormat);
    String endTime = setUpEndTime(request, dateFormat);
    log.info(
        "About to call count service for request: {} - starttime: {} - endtime: {}",
        request,
        startTime,
        endTime);
    return usgsClient.get()
        .uri(
            uriBuilder -> uriBuilder.path("count")
                .queryParam("format", "geojson")
                .queryParam("starttime", startTime)
                .queryParam("endtime", endTime)
                .queryParam("minmagnitude", request.getMinMagnitude())
                .build())
        .retrieve()
        .bodyToMono(EarthquakeCount.class)
        .block(Duration.ofMinutes(1L));
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

}
