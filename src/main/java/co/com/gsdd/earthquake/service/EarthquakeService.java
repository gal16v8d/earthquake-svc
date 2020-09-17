/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.gsdd.earthquake.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.com.gsdd.earthquake.domain.DominioCount;
import co.com.gsdd.earthquake.domain.DominioRequest;
import co.com.gsdd.earthquake.domain.DominioSismos;

/**
 *
 * @author JavaRevolutions
 */
@Service
public class EarthquakeService implements Serializable {
	@Autowired
	RestTemplate restTemplate;
	private String URL_USGS_QUERY = "https://earthquake.usgs.gov/fdsnws/event/1/query";
	private String URL_USGS_COUNT = "https://earthquake.usgs.gov/fdsnws/event/1/count";
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity;

	public EarthquakeService() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		URL_USGS_QUERY += "?format={format}&starttime={starttime}&endtime={endtime}&minmagnitude={minmagnitude}";
		URL_USGS_QUERY += "&limit={limit}&offset={offset}";
		URL_USGS_COUNT += "?format={format}&starttime={starttime}&endtime={endtime}&minmagnitude={minmagnitude}";
		entity = new HttpEntity<>(headers);
	}

	public DominioSismos consultaSismosByQuery(DominioRequest request) throws Exception {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (request.isPorFechas()) {
			date = request.getFechaFin();
		}
		String today = dateFormat.format(date);
		today += " 23:59:59";
		System.out.println("UTC today: " + today);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -request.getDias());
		Date ayer = cal.getTime();
		if (request.isPorFechas()) {
			ayer = request.getFechaInicio();
		}
		String strAyer = dateFormat.format(ayer);
		System.out.println("# Días a buscar: " + request.getDias());
		System.out.println("UTC ayer: " + strAyer);

		Map<String, Object> params = new HashMap<>();
		params.put("format", "geojson");
		params.put("starttime", strAyer);
		params.put("endtime", today);
		params.put("minmagnitude", request.getMinMagnitud());
		params.put("limit", request.getLenght());
		params.put("offset", request.getStart());

		return restTemplate.exchange(URL_USGS_QUERY, HttpMethod.GET, entity, DominioSismos.class, params).getBody();
	}

	public DominioCount countSismosByQuery(DominioRequest request) throws Exception {
		System.out.println("#### Invoke Count. . .");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (request.isPorFechas()) {
			date = request.getFechaFin();
		}
		String today = dateFormat.format(date);
		today += " 23:59:59";

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -request.getDias());
		Date ayer = cal.getTime();
		if (request.isPorFechas()) {
			ayer = request.getFechaInicio();
		}
		String strAyer = dateFormat.format(ayer);
		Map<String, Object> params = new HashMap<>();
		params.put("format", "geojson");
		params.put("starttime", strAyer);
		params.put("endtime", today);
		params.put("minmagnitude", request.getMinMagnitud());

		return restTemplate.exchange(URL_USGS_COUNT, HttpMethod.GET, entity, DominioCount.class, params).getBody();
	}
}
