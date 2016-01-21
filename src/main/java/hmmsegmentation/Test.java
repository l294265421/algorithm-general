package hmmsegmentation;

import java.util.ArrayList;
import java.io.IOException;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;
import be.ac.ulg.montefiore.run.jahmm.OpdfIntegerFactory;
import be.ac.ulg.montefiore.run.jahmm.OpdfInteger;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchScaledLearner;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;

public class Test {

	public static void main(String[] args) {

		// generate origin HMM model 生成初始的HMM模型，以小红与小明为例子
		int nbHiddenStates = 3;
		int nbObservedStates = 3;
		Hmm<ObservationInteger> originHmm = new 
				Hmm<ObservationInteger>(nbHiddenStates, 
						new OpdfIntegerFactory(nbObservedStates));

		// set initial state vector -Pi
		originHmm.setPi(0, 0.36);
		originHmm.setPi(1, 0.51);
		originHmm.setPi(2, 0.31);

		// set transition matrix - Aij
		originHmm.setAij(0, 0, 0.365);
		originHmm.setAij(0, 1, 0.500);
		originHmm.setAij(0, 2, 0.135);
		originHmm.setAij(1, 0, 0.250);
		originHmm.setAij(1, 1, 0.125);
		originHmm.setAij(1, 2, 0.625);
		originHmm.setAij(2, 0, 0.365);
		originHmm.setAij(2, 1, 0.265);
		originHmm.setAij(2, 2, 0.370);
		// set emission matrix - Opdf
		originHmm.setOpdf(0, new OpdfInteger(new double[] { 0.1, 0.2, 0.7 }));
		originHmm.setOpdf(1, new OpdfInteger(new double[] { 0.5, 0.25, 0.25 }));
		originHmm.setOpdf(2, new OpdfInteger(new double[] { 0.8, 0.1, 0.1 }));

		String originHmmStr = originHmm.toString();
		System.out.print("Origin HMM *************** \n");
		System.out.print(originHmmStr);

		// task One: learn use BaumWelch Algorithm 学习，生成合适的HMM模型
		ArrayList<ArrayList<ObservationInteger>> observSequence = 
				new ArrayList<ArrayList<ObservationInteger>>();

		int[] array = { 0, 1, 2, 2, 2, 1, 1, 0, 0, 2, 1, 2, 1, 1, 1, 1, 2, 2,
				2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2,
				2, 2, 2, 2, 0, 2, 1, 2, 0, 2, 1, 2, 1, 1, 0, 0, 0, 1, 0, 1, 0,
				2, 1, 2, 1, 2, 1, 2, 1, 1, 0, 0, 2, 2, 0, 2, 1, 1, 0 };

		ArrayList<ObservationInteger> oneSequence = new 
				ArrayList<ObservationInteger>();
		for (int i = 0; i < array.length; i++) {
			oneSequence.add(new ObservationInteger(array[i]));
		}
		observSequence.add(oneSequence);

		BaumWelchScaledLearner bw = new BaumWelchScaledLearner();
		bw.setNbIterations(10);

		Hmm<ObservationInteger> learnedHmm_bw = bw.learn(originHmm,
				observSequence);

		String learnedHmmStr_bw = learnedHmm_bw.toString();
		System.out
				.print("\nTask 1:Learned HMM use BaumWelch *************** \n");
		System.out.print(learnedHmmStr_bw);

		// task two: get the sequence the probability 评估，获得指定观测序列的概率
		int[] arraySeq = { 1, 2, 0, 0, 0, 0, 1, 2, 0 };
		ArrayList<ObservationInteger> sequenceTo = new ArrayList<ObservationInteger>();
		for (int i = 0; i < arraySeq.length; i++) {
			sequenceTo.add(new ObservationInteger(arraySeq[i]));
		}

		// HMM's Probability use ForwardBackward Algorithm
		double seq_prob = learnedHmm_bw.probability(sequenceTo);
		System.out.printf("\nTask 2: %s 's probability is %f \n",
				sequenceTo.toString(), seq_prob);

		// task three: get the hidden states sequence of the observer states
		// sequence解码，获得指定观测序列对应的最有可能的隐藏序列
		int[] arraySeq2 = { 2, 2, 0, 0, 1, 2, 1, 2, 2, 1 };
		ArrayList<ObservationInteger> sequenceThree = new ArrayList<ObservationInteger>();
		for (int i = 0; i < arraySeq2.length; i++) {
			sequenceThree.add(new ObservationInteger(arraySeq2[i]));
		}

		// use the Viterbi Algorithm
		int[] hiddenStatesSeq = learnedHmm_bw
				.mostLikelyStateSequence(sequenceThree);
		ArrayList<ObservationInteger> Sequence_hidden = new ArrayList<ObservationInteger>();
		for (int i = 0; i < hiddenStatesSeq.length; i++) {
			Sequence_hidden.add(new ObservationInteger(hiddenStatesSeq[i]));
		}
		System.out
				.printf("\nTask 3: observer hidden states sequence %s 's hidden states sequence is %s \n",

						sequenceThree.toString(), Sequence_hidden.toString());

		// HmmDrawerDot Hmm模型可视化
		// you can install graphviz and use GVEdit to view the HMM model
		GenericHmmDrawerDot hmmDrawer = new GenericHmmDrawerDot();

		try {
			hmmDrawer.write(originHmm, "hmm-origin.dot");
		} catch (IOException e) {
			System.err.print("Writing file triggered an exception: " + e);
		}

	}

}
