/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.gsdd.earthquake.domain.DominioCount;
import co.com.gsdd.earthquake.domain.DominioFeatures;
import co.com.gsdd.earthquake.domain.DominioGeometry;
import co.com.gsdd.earthquake.domain.DominioProperties;
import co.com.gsdd.earthquake.domain.DominioRequest;
import co.com.gsdd.earthquake.domain.DominioSismos;
import co.com.gsdd.earthquake.service.EarthquakeService;

@Named(value = "beanSismos")
@SessionScoped
public class BeanSismos implements Serializable {
    @Autowired
    private EarthquakeService serviceSismos;
    private DominioSismos sismos;
    private LazyDataModel<DominioFeatures> features;
    private double minMagnitud = 2.5;
    private int dias = 1;
    private DominioProperties sismoSel;
    private boolean porFechas = false;
    private boolean autoUpdate = false;
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private String iconUpdate = "ui-icon-close";

    /**
     * @return the serviceSismos
     */
    public EarthquakeService getServiceSismos() {
        return serviceSismos;
    }

    /**
     * @param serviceSismos the serviceSismos to set
     */
    public void setServiceSismos(EarthquakeService serviceSismos) {
        this.serviceSismos = serviceSismos;
    }

    /**
     * @return the sismos
     */
    public DominioSismos getSismos() {
        return sismos;
    }

    /**
     * @param sismos the sismos to set
     */
    public void setSismos(DominioSismos sismos) {
        this.sismos = sismos;
    }

    /**
     * @return the features
     */
    public LazyDataModel<DominioFeatures> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(LazyDataModel<DominioFeatures> features) {
        this.features = features;
    }

    /**
     * @return the minMagnitud
     */
    public double getMinMagnitud() {
        return minMagnitud;
    }

    /**
     * @param minMagnitud the minMagnitud to set
     */
    public void setMinMagnitud(double minMagnitud) {
        this.minMagnitud = minMagnitud;
    }

    /**
     * @return the dias
     */
    public int getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * @return the sismoSel
     */
    public DominioProperties getSismoSel() {
        return sismoSel;
    }

    /**
     * @param sismoSel the sismoSel to set
     */
    public void setSismoSel(DominioProperties sismoSel) {
        this.sismoSel = sismoSel;
    }

    /**
     * @return the porFechas
     */
    public boolean isPorFechas() {
        return porFechas;
    }

    /**
     * @param porFechas the porFechas to set
     */
    public void setPorFechas(boolean porFechas) {
        this.porFechas = porFechas;
    }

    /**
     * @return the autoUpdate
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * @param autoUpdate the autoUpdate to set
     */
    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    /**
     * @return the iconUpdate
     */
    public String getIconUpdate() {
        return iconUpdate;
    }

    /**
     * @param iconUpdate the iconUpdate to set
     */
    public void setIconUpdate(String iconUpdate) {
        this.iconUpdate = iconUpdate;
    }

    public String consultarSismos() {
        System.out.println("######### Invoke consultarSismos. . .");
        if (isPorFechas()) {
            if (getFechaInicio() == null || getFechaFin() == null) {
                System.out.println("######### La consulta no se realizo por fechas vacias. . .");
                if (features != null) {
                    features.setRowCount(0);
                }
                Date fecha = new Date();
                setFechaInicio(fecha);
                setFechaFin(fecha);
                return "index.faces";
            }
            int dias = (int) ((getFechaFin().getTime() - getFechaInicio().getTime()) / 86400000);
            System.out.println("######### Dias por fecha: " + dias);
            if (dias > 30 || dias < 0) {
                System.out.println("######### La consulta no se realizo por fechas incorrectas. . .");
                features.setRowCount(0);
                return "index.faces";
            }
        }
        features = new LazyDataModel<DominioFeatures>() {
            @Override
            public List<DominioFeatures> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {
                List<DominioFeatures> result = new ArrayList();
                try {
                    System.out.println("######### Invoke pagination consultarSismos. . .");
                    DominioRequest request = new DominioRequest();
                    request.setMinMagnitud(getMinMagnitud());
                    request.setDias(getDias());
                    request.setPorFechas(isPorFechas());
                    request.setFechaInicio(getFechaInicio());
                    request.setFechaFin(getFechaFin());
                    request.setStart(first + 1);
                    request.setLenght(pageSize);
                    System.out.println("#### Start: " + request.getStart());
                    System.out.println("#### Lenght: " + request.getLenght());
                    sismos = getServiceSismos().consultaSismosByQuery(request);
                    result = sismos.getFeatures();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            public DominioFeatures getRowData(String rowKey) {
                DominioFeatures dom = new DominioFeatures();
                dom.setId(rowKey);
                return dom;
            }

            @Override
            public Object getRowKey(DominioFeatures object) {
                return object;
            }
        };
        try {
            DominioRequest request = new DominioRequest();
            request.setMinMagnitud(getMinMagnitud());
            request.setDias(getDias());
            request.setPorFechas(isPorFechas());
            request.setFechaInicio(getFechaInicio());
            request.setFechaFin(getFechaFin());
            DominioCount count = getServiceSismos().countSismosByQuery(request);
            features.setRowCount(count.getCount());
            System.out.println("######### Row Count: " + features.getRowCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        features.setPageSize(10);
        return "index.faces";
    }

    public void showDetail(DominioProperties props, String rowKey, DominioGeometry geometry) {
        System.out.println("########## Row Key Sel: " + rowKey);
        System.out.println("### Magnitud: " + props.getMag());
        System.out.println("### Ubicación: " + props.getPlace());
        System.out.println("### Fecha: " + props.getTime());
        setSismoSel(props);
        Date fecha = new Date(props.getTime());
        SimpleDateFormat formatoEsMX = new SimpleDateFormat(
                "EEEE d 'de' MMMM 'de' yyyy 'a las' HH:mm:ss", new Locale("ES", "MX"));
        getSismoSel().setFecha(formatoEsMX.format(fecha));
        getSismoSel().setMapa(props.getUrl() + "/map");
        String coordenadas = geometry.getCoordinates()[1] + "," + geometry.getCoordinates()[0];
        getSismoSel().setGoogleMaps("https://maps.google.com/?q=" + coordenadas
                + "&ll=" + coordenadas + "&z=4");
        System.out.println("####URL Google Maps: " + getSismoSel().getGoogleMaps());
    }

    public String doAutoUpdate() {
        setAutoUpdate(!isAutoUpdate());
        System.out.println("#### doAutoUpdate Auto Update: "+isAutoUpdate());
        if(isAutoUpdate()) {
            setIconUpdate("ui-icon-check");
        } else {
            setIconUpdate("ui-icon-close");
        }
        return "index.faces";
    }
}
