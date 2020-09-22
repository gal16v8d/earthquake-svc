package co.com.gsdd.earthquake.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarthquakeRequest {

	private double minMagnitude;
	private int days;
	private boolean byDateRange;
	private Date iniDate;
	private Date endDate;
	private int start = 1;
	private int lenght = 10;

}
