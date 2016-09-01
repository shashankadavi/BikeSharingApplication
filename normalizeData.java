/******************************************************************************
 * Code for cleaning and normalizing the data for Multilayer Perceptron
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class normalizeData {
	public static ArrayList<Float> maxValues = new ArrayList<Float>();
	
	public static void convertCSV2Arff(String ipFilename, String opFilename) {
		CSVLoader csvLoader = new CSVLoader();
		Instances data;
		ArffSaver saver = new ArffSaver();

		try {
			csvLoader.setSource(new File(ipFilename));
			data = csvLoader.getDataSet();
			saver.setInstances(data);
			saver.setFile(new File(opFilename));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}


	public static void main(String[] arg) {
		try {
			String line;
			BufferedReader csvFile = new BufferedReader(new FileReader(
					"train.csv"));
			ArrayList<Float> season = new ArrayList<Float>();
			ArrayList<Float> workingDay = new ArrayList<Float>();
			ArrayList<Float> weather = new ArrayList<Float>();
			ArrayList<Float> temp = new ArrayList<Float>();
			ArrayList<Float> humidity = new ArrayList<Float>();
			ArrayList<Float> windspeed = new ArrayList<Float>();
			ArrayList<Float> holiday = new ArrayList<Float>();
			ArrayList<Float> time = new ArrayList<Float>();
			ArrayList<Float> count = new ArrayList<Float>();
			while ((line = csvFile.readLine()) != null) {
				String[] ins = line.split(",");
				season.add(Float.parseFloat(ins[0]));
				workingDay.add(Float.parseFloat(ins[1]));
				weather.add(Float.parseFloat(ins[2]));
				temp.add(Float.parseFloat(ins[3]));
				humidity.add(Float.parseFloat(ins[4]));
				windspeed.add(Float.parseFloat(ins[5]));
				holiday.add(Float.parseFloat(ins[6]));
				time.add(Float.parseFloat(ins[7]));
				count.add(Float.parseFloat(ins[8]));
			}
		
			csvFile.close();
		
			ArrayList<Float> nSeason = normalizedColumn(season);
			ArrayList<Float> nWorkingDay = normalizedColumn(workingDay);
			ArrayList<Float> nWeather = normalizedColumn(weather);
			ArrayList<Float> ntemp = normalizedColumn(temp);
			ArrayList<Float> nHumidity = normalizedColumn(humidity);
			ArrayList<Float> nWindspeed = normalizedColumn(windspeed);
			ArrayList<Float> nHoliday = normalizedColumn(holiday);
			ArrayList<Float> nTime = normalizedColumn(time);
			ArrayList<Float> nCount = normalizedColumn(count);

			System.out.println("nSeason: " + nSeason.toString());
			System.out.println("nWorkingDay: " + nWorkingDay.toString());
			System.out.println("nWeather: " + nWeather.toString());
			System.out.println("ntemp: " + ntemp.toString());
			System.out.println("nHumidity: " + nHumidity.toString());
			System.out.println("nWindspeed: " + nWindspeed.toString());
			System.out.println("nHoliday: " + nHoliday.toString());
			System.out.println("nTime: " + nTime.toString());
			System.out.println("nCount: " + nCount.toString());

			FileWriter cvsWriter = new FileWriter("cleanedTrain.csv");
			String names = "season,workingday,weather,temp,humidity,windspeed,holiday,time,count\n";
			cvsWriter.append(names);
			for (int i = 0; i < nSeason.size(); i++) {
				String row = nSeason.get(i).toString() + ","
						+ nWorkingDay.get(i).toString() + ","
						+ nWeather.get(i).toString() + ","
						+ ntemp.get(i).toString() + ","
						+ nHumidity.get(i).toString() + ","
						+ nWindspeed.get(i).toString() + ","
						+ nHoliday.get(i).toString() + ","
						+ nTime.get(i).toString() + ","
						+ nCount.get(i).toString() + "\n";

				cvsWriter.append(row);
			}
			cvsWriter.close();
			
			String trainCsvFile = "cleanedTrain.csv";
			String trainArffFile = "train.arff";

			convertCSV2Arff(trainCsvFile, trainArffFile);
			
			String line1;
			BufferedReader csvFile1 = new BufferedReader(new FileReader(
					"test.csv"));
			ArrayList<Float> season1 = new ArrayList<Float>();
			ArrayList<Float> workingDay1 = new ArrayList<Float>();
			ArrayList<Float> weather1 = new ArrayList<Float>();
			ArrayList<Float> temp1 = new ArrayList<Float>();
			ArrayList<Float> humidity1 = new ArrayList<Float>();
			ArrayList<Float> windspeed1 = new ArrayList<Float>();
			ArrayList<Float> holiday1 = new ArrayList<Float>();
			ArrayList<Float> time1 = new ArrayList<Float>();
			ArrayList<Float> count1 = new ArrayList<Float>();
			
			while ((line1 = csvFile1.readLine()) != null) {
				String[] ins1 = line1.split(",");
				season1.add(Float.parseFloat(ins1[0]));
				workingDay1.add(Float.parseFloat(ins1[1]));
				weather1.add(Float.parseFloat(ins1[2]));
				temp1.add(Float.parseFloat(ins1[3]));
				humidity1.add(Float.parseFloat(ins1[4]));
				windspeed1.add(Float.parseFloat(ins1[5]));
				holiday1.add(Float.parseFloat(ins1[6]));
				time1.add(Float.parseFloat(ins1[7]));
				count1.add(Float.parseFloat("0"));
			}
		
			csvFile1.close();
			System.out.println("season: " + season1.toString());
			System.out.println("workingDay: " + workingDay1.toString());
			System.out.println("weather: " + weather1.toString());
			System.out.println("temp: " + temp1.toString());
			System.out.println("humidity: " + humidity1.toString());
			System.out.println("windspeed: " + windspeed1.toString());
			System.out.println("holiday: " + holiday1.toString());
			System.out.println("time: " + time1.toString());
			System.out.println("count: " + count1.toString());
			
			ArrayList<Float> nSeason1 = normalizedColumn(season1);
			ArrayList<Float> nWorkingDay1 = normalizedColumn(workingDay1);
			ArrayList<Float> nWeather1 = normalizedColumn(weather1);
			ArrayList<Float> ntemp1 = normalizedColumn(temp1);
			ArrayList<Float> nHumidity1 = normalizedColumn(humidity1);
			ArrayList<Float> nWindspeed1 = normalizedColumn(windspeed1);
			ArrayList<Float> nHoliday1 = normalizedColumn(holiday1);
			ArrayList<Float> nTime1 = normalizedColumn(time1);
			ArrayList<Float> nCount1 = normalizedColumn(count1);
			
			System.out.println("nSeason: " + nSeason1.toString());
			System.out.println("nWorkingDay: " + nWorkingDay1.toString());
			System.out.println("nWeather: " + nWeather1.toString());
			System.out.println("ntemp: " + ntemp1.toString());
			System.out.println("nHumidity: " + nHumidity1.toString());
			System.out.println("nWindspeed: " + nWindspeed1.toString());
			System.out.println("nHoliday: " + nHoliday1.toString());
			System.out.println("nTime: " + nTime1.toString());
			System.out.println("nCount: " + nCount1.toString());
			
			FileWriter cvsWriter1 = new FileWriter("cleanedTest.csv");
			String names1 = "season,workingday,weather,temp,humidity,windspeed,holiday,time,count\n";
			cvsWriter1.append(names1);
			for (int i = 0; i < nSeason1.size(); i++) {
				String row = nSeason1.get(i).toString() + ","
						+ nWorkingDay1.get(i).toString() + ","
						+ nWeather1.get(i).toString() + ","
						+ ntemp1.get(i).toString() + ","
						+ nHumidity1.get(i).toString() + ","
						+ nWindspeed1.get(i).toString() + ","
						+ nHoliday1.get(i).toString() + ","
						+ nTime1.get(i).toString() + ","
						+ nCount1.get(i).toString() + "\n";

				cvsWriter1.append(row);
			}
			cvsWriter1.close();
			
			String trainCsvFile1 = "cleanedTest.csv";
			String trainArffFile1 = "test.arff";

			convertCSV2Arff(trainCsvFile1, trainArffFile1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ObjectOutputStream outPutMaxvalues;
		try {
			outPutMaxvalues = new ObjectOutputStream(new FileOutputStream(
					"maxValues"));
			outPutMaxvalues.writeObject(maxValues);
			outPutMaxvalues.flush();
			outPutMaxvalues.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Float> normalizedColumn(ArrayList<Float> column) {
		Float maxElement = Collections.max(column);
		maxValues.add(maxElement);
		ArrayList<Float> nColumn = new ArrayList<Float>();
		for (Float f : column) {
			Float x = (f / maxElement) * 100;
			nColumn.add(x);
		}
		return nColumn;
	}
}
