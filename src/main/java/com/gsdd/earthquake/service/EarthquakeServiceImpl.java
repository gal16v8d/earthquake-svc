package com.gsdd.earthquake.service;

import com.gsdd.earthquake.domain.EarthquakeCount;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class EarthquakeServiceImpl implements EarthquakeService {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private final WebClient usgsClient;

  @Override
  public EarthquakeResponse earthquakeQuery(EarthquakeRequest request) throws Exception {
    String startTime = setUpStartTime(request);
    String endTime = setUpEndTime(request);
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
                .queryParam("limit", request.getLength())
                .queryParam("offset", request.getStart())
                .build())
        .retrieve()
        .bodyToMono(EarthquakeResponse.class)
        .block(Duration.ofMinutes(1L));
  }

  @Override
  public Mono<EarthquakeCount> earthquakeCountQuery(EarthquakeRequest request) throws Exception {
    String startTime = setUpStartTime(request);
    String endTime = setUpEndTime(request);
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
        .bodyToMono(EarthquakeCount.class);
  }

  private String setUpStartTime(EarthquakeRequest request) {
    LocalDate date = LocalDate.now(ZoneOffset.UTC).plusDays(-request.getDays());
    if (request.isByDateRange()) {
      date = request.getIniDate().toInstant().atZone(ZoneOffset.UTC).toLocalDate();
    }
    return FORMATTER.format(date);
  }

  private String setUpEndTime(EarthquakeRequest request) {
    LocalDate date = LocalDate.now(ZoneOffset.UTC);
    if (request.isByDateRange()) {
      date = request.getEndDate().toInstant().atZone(ZoneOffset.UTC).toLocalDate();
    }
    return FORMATTER.format(date) + " 23:59:59.999";
  }

}
