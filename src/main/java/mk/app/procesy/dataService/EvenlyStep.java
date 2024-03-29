package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.decimal4j.util.DoubleRounder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EvenlyStep extends ModificationStep{
	
	private static final String STEP_NAME = "Rozłóż równomiernie";
	
	public EvenlyStep(int from, int to, double valueH) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
	}
	
	@Override
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("EvenlyStep: wyszukuje wartości do aktualizacji");
		Pair<Integer, Integer> fromAndToIdxs = lookForScope(tmpAndH);
		int fromIdx = fromAndToIdxs.getLeft();
		int toIdx = fromAndToIdxs.getRight();
		
		double chunk = valueH / ((double) (to - from + 1));
		log.debug("EvenlyStep: rozkładam H {}, na porcję wielkości: {}", valueH, chunk);
		
		double accumulatedChunk = chunk;
		List<Double> results = new ArrayList<>(tmpAndH.size());
		for (int i = 0; i < tmpAndH.size(); i++) {
			if (i < fromIdx) {
				results.add(0.0);
			} else if ((i >= fromIdx) && (i < toIdx)) {
				results.add(DoubleRounder.round(accumulatedChunk, 3));
				accumulatedChunk += chunk;
			} else {
				results.add(accumulatedChunk);
			}
		}
		
		log.debug("EvenlyType: Zaktualizowano listę");
		lastResult = new ArrayList<>(results);
		return results;
	}

	@Override
	public String getName() {
		return STEP_NAME;
	}

	@Override
	public void stetExtraValue(Object first, Object second) {
	}

}
