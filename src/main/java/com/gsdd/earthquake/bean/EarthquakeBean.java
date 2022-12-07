package com.gsdd.earthquake.bean;

import com.gsdd.earthquake.domain.EarthquakeCoordinates;
import com.gsdd.earthquake.domain.EarthquakeCount;
import com.gsdd.earthquake.domain.EarthquakeProps;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.service.EarthquakeService;
import com.gsdd.earthquake.util.EartquakeFeatureUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
@Named(value = "earthquakeBean")
@SessionScoped
public class EarthquakeBean implements Serializable {

  private static final long serialVersionUID = 3775215271079611932L;
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
  private static final String GOOGLE_MAPS_QUERY = "https://maps.google.com/?q=%s&ll=%s&z=4";
  private static final String INDEX_FACES = "index.faces";
  private static final String UI_ICON_CLOSE = "ui-icon-close";

  private final EarthquakeService earthquakeSvc;
  private EartquakeFeatureUtil features;
  private EarthquakeProps selectedEarthquake;
  private boolean byDateRange;
  private boolean autoUpdate;
  private double minMagnitude;
  private int days;
  private Date iniDate;
  private Date endDate;
  private String iconUpdate;

  @PostConstruct
  public void initBean() {
    initRangeDates();
    setByDateRange(false);
    setAutoUpdate(false);
    setIconUpdate(UI_ICON_CLOSE);
    setDays(1);
    setMinMagnitude(2.5);
    features = new EartquakeFeatureUtil();
    features.setEarthquakeRequest(initRequest());
    features.setEarthquakeSvc(getEarthquakeSvc());
  }

  private EarthquakeRequest initRequest() {
    EarthquakeRequest request = new EarthquakeRequest();
    request.setMinMagnitude(getMinMagnitude());
    request.setDays(getDays());
    request.setByDateRange(isByDateRange());
    request.setIniDate(getIniDate());
    request.setEndDate(getEndDate());
    return request;
  }

  private void initRangeDates() {
    Date date = new Date();
    setIniDate(date);
    setEndDate(date);
  }

  public String searchEarthquakes() {
    log.debug("Invoke searchEarthquakes...");
    if (isByDateRange()) {
      if (getIniDate() == null || getEndDate() == null) {
        log.info("Init date or end date is not defined, can not perform any search");
        features.setRowCount(0);
        initRangeDates();
        return INDEX_FACES;
      }
      int calcDays = (int) ((getEndDate().getTime() - getIniDate().getTime()) / 86400000);
      log.info("Difference in days is {}", calcDays);
      if (calcDays > 30 || calcDays < 0) {
        log.info(
            "Can not perform search if days are not in range [0,30], current value: {}",
            calcDays);
        features.setRowCount(0);
        return INDEX_FACES;
      }
    }
    EarthquakeRequest request = initRequest();
    features.setEarthquakeRequest(request);
    getEarthquakeCount(request);
    features.setPageSize(10);
    return INDEX_FACES;
  }

  private void getEarthquakeCount(EarthquakeRequest request) {
    try {
      EarthquakeCount count = getEarthquakeSvc().earthquakeCountQuery(request);
      features.setRowCount(count.getCount());
      log.info("Row Count: {}", features.getRowCount());
    } catch (Exception e) {
      log.error("Couldn't get count data", e);
    }
  }

  public void showDetail(EarthquakeProps props, String rowKey, EarthquakeCoordinates geometry) {
    log.debug("Row Key Sel: {}", rowKey);
    log.debug("Magnitude: {}", props.getMag());
    log.debug("Place: {}", props.getPlace());
    log.debug("Date: {}", props.getTime());
    setSelectedEarthquake(props);
    Date date = new Date(props.getTime());
    getSelectedEarthquake()
        .setDate(FORMATTER.format(LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC)));
    getSelectedEarthquake().setMap(props.getUrl() + "/map");
    String coordinates = geometry.getCoordinates()[1] + "," + geometry.getCoordinates()[0];
    getSelectedEarthquake()
        .setGoogleMaps(String.format(GOOGLE_MAPS_QUERY, coordinates, coordinates));
    log.debug("Google Maps URL: {}", getSelectedEarthquake().getGoogleMaps());
  }

  public String doAutoUpdate() {
    setAutoUpdate(!isAutoUpdate());
    log.info("doAutoUpdate Auto Update: {}", isAutoUpdate());
    if (isAutoUpdate()) {
      setIconUpdate("ui-icon-check");
    } else {
      setIconUpdate(UI_ICON_CLOSE);
    }
    return INDEX_FACES;
  }
}
