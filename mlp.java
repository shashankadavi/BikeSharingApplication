/************************************************************************
 * Code for Multilayer perceptron using Weka's Mlp library
 */


import weka.core.Instances;
import weka.classifiers.functions.MultilayerPerceptron;

import java.io.*;

public class mlp {

	public static void main(String[] arg) {
		String trainArffFile = "train.arff";
		String testArffFile = "test.arff";

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
		trainIns.setClassIndex(trainIns.numAttributes() - 1);
		testIns.setClassIndex(testIns.numAttributes() - 1);

		MultilayerPerceptron nn = new MultilayerPerceptron();
		System.out.println(nn.getCapabilities().toString());
		System.out.println(nn.getHiddenLayers());
		System.out.println(nn.getLearningRate());
		System.out.println(nn.getNormalizeAttributes());
		System.out.println(nn.getValidationThreshold());
		System.out.println(nn.getGUI());

		nn.setValidationSetSize(10);
		nn.setGUI(true);
		System.out.println(nn.getValidationSetSize());

		System.out.println("GUI: " + nn.GUITipText());
		System.out.println("Hidden Layers: " + nn.hiddenLayersTipText());
		System.out.println("AutoBuild" + nn.autoBuildTipText());

		double startTime = System.currentTimeMillis();
		try {
			nn.buildClassifier(trainIns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double stopTime = System.currentTimeMillis();

		System.out.println("Time taken to build Classifier: "
				+ (stopTime - startTime) / 1000);

		System.out.println(nn.getHiddenLayers());

		Instances op = new Instances(testIns);
		System.out.println("Number of test instances: "
				+ testIns.numInstances());
		for (int i = 0; i < testIns.numInstances(); i++) {
			try {
				double res = nn.classifyInstance(testIns.instance(i));
				double res1 = (res < 0) ? 0 : res;
				op.instance(i).setClassValue(res1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter("result.arff");
			bw = new BufferedWriter(fw);
			bw.write(op.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectOutputStream outPutModel;
		try {
			outPutModel = new ObjectOutputStream(new FileOutputStream(
					"mlp.model"));
			outPutModel.writeObject(nn);
			outPutModel.flush();
			outPutModel.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
