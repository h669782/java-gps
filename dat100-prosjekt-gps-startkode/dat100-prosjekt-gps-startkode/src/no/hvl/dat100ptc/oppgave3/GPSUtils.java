package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] tabell = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			tabell[i] = gpspoints[i].getLatitude();
		}
		
		return tabell;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

double[] tabell = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++) {
			tabell[i] = gpspoints[i].getLongitude();
		}
		
		return tabell;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());

		double[] latitudeTabell = {gpspoint1.getLatitude(), gpspoint2.getLatitude()};
		double latitude = Math.toRadians(findMax(latitudeTabell)-findMin(latitudeTabell));
		
		double[] longitudeTabell = {gpspoint1.getLongitude(), gpspoint2.getLongitude()};
		double longitude = Math.toRadians(findMax(longitudeTabell)-findMin(longitudeTabell));
		
		double a = Math.pow(Math.sin(latitude/2), 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(longitude/2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		d = R * c;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		double distance = distance(gpspoint1, gpspoint2);
		
		double[] time = {gpspoint1.getTime(), gpspoint2.getTime()};
		
		secs = (int) (findMax(time)-findMin(time));
		
		double mps = distance/secs;
		
		speed = mps*3.6;
		
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int hr = (int) secs/3600;
		secs -= hr*3600;
		int min = (int) secs/60;
		secs -= min*60;
		
		String hrstr = String.valueOf(hr);
		String minstr = String.valueOf(min);
		String secsstr = String.valueOf(secs);
		
		if(hrstr.length() < 2) {
			hrstr = "0" + hrstr;
		}
		if(minstr.length() < 2) {
			minstr = "0" + minstr;
		}
		if(secsstr.length() < 2) {
			secsstr = "0" + secsstr;
		}
		
		timestr = "  " + hrstr + TIMESEP + minstr + TIMESEP + secsstr;
		
		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		
		str = String.format("%.2f", d);
		
		if(str.length() < TEXTWIDTH) {
			for(int i = 0; i < TEXTWIDTH-str.length(); i++) {
				str = " " + str;
			}
		}
		
		str = "   " + str;
		
		return str;
		
	}
}
