package mk.app.procesy.dataService;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtTheEndStep extends ModificationStep {

	public void modify(List<Pair<Integer, Double>> tmpAndH, double valueH) {
		log.debug("AtTheEndStep: wyszykuje wartość do aktualizacji");
		for (Pair<Integer, Double> row : tmpAndH) {
			if (row.getLeft().compareTo(to) == 0) {
				double h = row.getRight() + valueH;
				row = Pair.of(to, h);
				log.debug("AtTheEndStep: Dla tmp: {}, zwiększam H do {}", row.getLeft(), h);
				break;
			}
		}
	}

}
