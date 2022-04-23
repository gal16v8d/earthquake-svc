package com.gsdd.earthquake.util;

import com.gsdd.earthquake.domain.EarthquakeFeatures;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;
import com.gsdd.earthquake.service.EarthquakeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@Slf4j
@Getter
@Setter
public class EartquakeFeatureUtil extends LazyDataModel<EarthquakeFeatures> {

    private static final long serialVersionUID = 2797711935460952006L;
    private EarthquakeService earthquakeSvc;
    private EarthquakeRequest earthquakeRequest;

    @Override
    public List<EarthquakeFeatures> load(
            int first,
            int pageSize,
            String sortField,
            SortOrder sortOrder,
            Map<String, FilterMeta> filters) {
        List<EarthquakeFeatures> result = new ArrayList<>();
        try {
            log.info("Invoke pagination searchEarthquakes");
            EarthquakeRequest request = getEarthquakeRequest();
            request.setStart(first + 1);
            request.setLenght(pageSize);
            log.info("Start: {} | Lenght: {}", request.getStart(), request.getLenght());
            EarthquakeResponse earthquakeResponse = getEarthquakeSvc().earthquakeQuery(request);
            result = earthquakeResponse.getFeatures();
            log.info("Finish pagination searchEarthquakes");
        } catch (Exception e) {
            log.error("Error to load lazy data", e);
        }
        return result;
    }

    @Override
    public EarthquakeFeatures getRowData(String rowKey) {
        EarthquakeFeatures dom = new EarthquakeFeatures();
        dom.setId(rowKey);
        return dom;
    }

    @Override
    public Object getRowKey(EarthquakeFeatures object) {
        return object;
    }
}
