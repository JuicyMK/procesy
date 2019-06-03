package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinearStep extends ModificationStep {

	private static final String STEP_NAME = "Równanie liniowe";
	
	private Double a;
	private Double b;
	
	public LinearStep(int from, int to, double valueH, double a, double b) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
		this.a = a;
		this.b = b;
	}
	
	@Override
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("LinearStep: wyszukuje wartości do aktualizacji");
		Pair<Integer, Integer> fromAndToIdxs = lookForScope(tmpAndH);
		int fromIdx = fromAndToIdxs.getLeft();
		int toIdx = fromAndToIdxs.getRight();
		
		List<Double> results = new ArrayList<>(tmpAndH.size());
		IntStream.range(0, tmpAndH.size()).boxed().forEach(e -> results.add(0.0));
		
		List<Double> equation = new ArrayList<>(tmpAndH.size());
		IntStream.range(0, tmpAndH.size()).boxed().forEach(e -> equation.add(0.0));
		
		double equationSum = 0.0;
		for (int i = fromIdx; i <= toIdx; i++) {
			double y = a * i + b;
			log.debug("Linear: {}", y);
			equationSum += Math.abs(y);
			equation.set(i, y);
		}

		if (equationSum != 0) {
			double aspectRatio = valueH / equationSum;
			log.debug("AspectRatio: H {}, sum {}, = {}", valueH, equationSum, aspectRatio);
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
		
		log.debug("LinearStep: Zaktualizowano listę");
		lastResult = new ArrayList<>(results); 
		return results;
	}

	@Override
	public String getName() {
		return STEP_NAME;
	}

	@Override
	public void stetExtraValue(Object first, Object second) {
		a = (Double) first;
		b = (Double) second;
	}

}
