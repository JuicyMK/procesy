package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.decimal4j.util.DoubleRounder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EvenlyStep extends ModificationStep{
	
	public EvenlyStep(int from, int to, double valueH) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
	}
	
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("EvenlyStep: wyszukuje wartości do aktualizacji");
		Pair<Integer, Integer> fromAndToInxs = lookForScope(tmpAndH);
		int fromIdx = fromAndToInxs.getLeft();
		int toIdx = fromAndToInxs.getRight();
		
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
		return results;
	}

	
}
