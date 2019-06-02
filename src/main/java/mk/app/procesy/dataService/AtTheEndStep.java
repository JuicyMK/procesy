package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtTheEndStep extends ModificationStep {

	public AtTheEndStep(int from, int to, double valueH) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
	}
	
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("AtTheEndStep: wyszykuje wartość do aktualizacji");
		Integer toValueIndex = lookForScope(tmpAndH).getRight();
		
		List<Double> results = new ArrayList<>(tmpAndH.size());
		
		for (int i = 0; i < tmpAndH.size(); i++) {
			if (i < toValueIndex) {
				results.add(0.0);
			} else {
				results.add(valueH);
			}
		}
		log.debug("AtTheEndStep: Zaktualizowano listę");
		return results;
	}

}
