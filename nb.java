/************************************************************************
 * Code for Naive Bayes using Weka's NB library
 */

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.classifiers.bayes.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

import java.io.*;

public class nb {

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

	public static void main(String[] args) throws Exception {

		Instances outputTrain;
		Instances outputTest;
		Discretize filter;

		String trainCsvFile = "trainNB.csv";
		String testCsvFile = "testNB.csv";
		String trainArffFile = "trainNB.arff";
		String testArffFile = "testNB.arff";

		convertCSV2Arff(trainCsvFile, trainArffFile);
		convertCSV2Arff(testCsvFile, testArffFile);

		BufferedReader reader1, reader2;
		Instances trainIns = null;
		Instances testIns = null;
		try {
			reader1 = new BufferedReader(new FileReader(trainArffFile));
			trainIns = new Instances(reader1);
			reader1.close();
			reader2 = new BufferedReader(new FileReader(testArffFile));
			testIns = new Instances(reader2);
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		trainIns.setClassIndex(trainIns.numAttributes() - 2);

		filter = new Discretize();
		filter.setInputFormat(trainIns);

		outputTrain = Filter.useFilter(trainIns, filter);
		outputTest = Filter.useFilter(testIns, filter);
		outputTrain.setClassIndex(outputTrain.numAttributes() - 1);
		outputTest.setClassIndex(outputTest.numAttributes() - 1);

		NaiveBayes nb = new NaiveBayes();
		System.out.println(nb.getUseKernelEstimator());
		System.out.println(nb.getUseSupervisedDiscretization());
		System.out.println(nb.getDisplayModelInOldFormat());
		
		double startTime = System.currentTimeMillis();
		try {
			nb.buildClassifier(outputTrain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double stopTime = System.currentTimeMillis();

		System.out.println("Time taken to build Classifier: "
				+ (stopTime - startTime) / 1000);
		for (int i = 0; i < filter.getCutPoints(8).length; i++) {
			System.out.println("Bin Ranges String: "
					+ filter.getCutPoints(6)[i]);
		}
		

		Instances op = new Instances(outputTest);
		System.out.println("Number of test instances: "
				+ outputTest.numInstances());
		for (int i = 0; i < outputTest.numInstances(); i++) {
			try {
				double res = nb.classifyInstance(outputTest.instance(i));
				op.instance(i).setClassValue(res);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter("resultNB.arff");
			bw = new BufferedWriter(fw);
			bw.write(op.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectOutputStream outPutModel, outPutFilter;
		try {
			outPutModel = new ObjectOutputStream(new FileOutputStream(
					"nb.model"));
			outPutModel.writeObject(nb);
			outPutModel.flush();
			outPutModel.close();
			outPutFilter = new ObjectOutputStream(new FileOutputStream(
					"Filter"));
			outPutFilter.writeObject(filter);
			outPutFilter.flush();
			outPutFilter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
