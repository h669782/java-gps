package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		
		for(int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		}
		
		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		
		for(int i = 0; i < gpspoints.length - 1; i++) {
			double en = gpspoints[i].getElevation();
			double to = gpspoints[i+1].getElevation();
			if(en < to) {
				elevation += to-en;
			}
		}
		
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int min = gpspoints[0].getTime();
		int max = gpspoints[gpspoints.length-1].getTime();
		
		return (int) max-min;

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] gjenomsnitt = new double[gpspoints.length-1];
		
		for(int i = 0; i < gpspoints.length - 1; i++) {
			gjenomsnitt[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		
		return gjenomsnitt;

	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		
		double[] list = speeds();
		
		for(int i = 0; i < list.length - 1; i++) {
			maxspeed = Math.max(list[i], list[i+1]);
		}
		
		return maxspeed;
		
	}

	public double averageSpeed() {

		double average = 0;
		
		average = totalDistance()/totalTime()*3600/1000;
		
		return average;
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		
		switch((int) Math.abs(speedmph)){
		case 1, 2, 3, 4, 5, 6, 7, 8, 9: 
			met = 4;
			break;
		case 10, 11:
			met = 6;
			break;
		case 12, 13:
			met = 8;
			break;
		case 14, 15:
			met = 10;
			break;
		case 16, 17, 18, 19:
			met = 12;
			break;
		default:
			met = 16;
			break;
		}
		
		kcal = (met*weight*secs)/3600;
		
		return kcal;
		
	}

	public double totalKcal(double weight) {

		int totaltime = totalTime();
		double averagespeed = averageSpeed();
		
		double totalkcal = kcal(weight, totaltime, averagespeed);
		
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.println(String.format("%-15s:", "Total time") + GPSUtils.formatTime(totalTime()));
		System.out.println(String.format("%-15s:", "Total distance") + GPSUtils.formatDouble(totalDistance()) + "km");
		System.out.println(String.format("%-15s:", "Total elevation") + GPSUtils.formatDouble(totalElevation()) + "m");
		
		System.out.println("==============================================");
		
	}

}
