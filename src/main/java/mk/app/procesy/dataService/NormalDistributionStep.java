package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NormalDistributionStep extends ModificationStep {

	private static final String STEP_NAME = "Rozkład normalny";
	
	private Double mean;
	private Double standardDeviation;
	
	public NormalDistributionStep(int from, int to, double valueH, double mean, double standardDeviation) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}
	
	@Override
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("NormalDistribution: wyszukuje wartości do aktualizacji");
		Pair<Integer, Integer> fromAndToIdxs = lookForScope(tmpAndH);
		int fromIdx = fromAndToIdxs.getLeft();
		int toIdx = fromAndToIdxs.getRight();
		
		List<Double> results = new ArrayList<>(tmpAndH.size());
		IntStream.range(0, tmpAndH.size()).boxed().forEach(e -> results.add(0.0));
		
		List<Double> equation = new ArrayList<>(tmpAndH.size());
		IntStream.range(0, tmpAndH.size()).boxed().forEach(e -> equation.add(0.0));
		
		double equationSum = 0.0;
		
		int middle = (toIdx - fromIdx) / 2;
		middle = (middle == 0) ? 1 : middle;

		double partOne = 1.0 / (standardDeviation * Math.sqrt(3.141));
		double denominator = 2.0 * Math.pow(standardDeviation, 2);
		
		int x = 0;
		for (int i = (fromIdx + middle); i <= toIdx; i++) {
			x++;
			
			double y = partOne * Math.exp((- Math.pow((x - mean), 2)) / denominator);
			equationSum += Math.abs(y);
			equation.set(i, y);
		}
		
		x = - middle;
		for (int i = fromIdx; i < (fromIdx + middle); i++) {
			x++;
			double y = partOne * Math.exp((- Math.pow((x - mean), 2)) / denominator);
			equationSum += Math.abs(y);
			equation.set(i, y);
		}
		
		log.debug("NormalDistribution: {}", equation);
		equation.stream().forEach(e -> System.out.println(e));
		
		if (equationSum != 0) {
			double aspectRatio = valueH / equationSum;
			for (int i = fromIdx; i <= toIdx; i++) {
				equation.set(i, equation.get(i) * aspectRatio); 
			}
			
			double sum = 0.0;
			for (int i = fromIdx; i < tmpAndH.size(); i++) {
				if (i <= toIdx) {
					sum += equation.get(i);
				}
				results.set(i, sum);
			}
		}
		
		log.debug("NormalDistribution: Zaktualizowano listę");
		lastResult = new ArrayList<>(results); 
		return results;
	}

	@Override
	public String getName() {
		return STEP_NAME;
	}

	@Override
	public void stetExtraValue(Object first, Object second) {
		mean = (Double) first;
		standardDeviation = (Double) second;
	}

}
