package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomStep extends ModificationStep{
	
	public RandomStep(int from, int to, double valueH) {
		this.from = from;
		this.to = to;
		this.valueH = valueH;
	}
	
	@Override
	public List<Double> modify(List<Pair<Integer, Double>> tmpAndH) {
		log.debug("RandomStep: wyszukuje wartości do aktualizacji");
		Pair<Integer, Integer> fromAndToIdxs = lookForScope(tmpAndH);
		int fromIdx = fromAndToIdxs.getLeft();
		int toIdx = fromAndToIdxs.getRight();
		
		List<Double> results = new ArrayList<>(tmpAndH.size());
		IntStream.range(0, tmpAndH.size()).boxed().forEach(e -> results.add(0.0));
		
		Random randomizer = new Random();
		randomizer.ints((int) valueH, fromIdx, toIdx).forEach(e -> {
			results.set(e, results.get(e) + 1.0);
		});;
			
		for (int i = 1; i < tmpAndH.size(); i++) {
			results.set(i, results.get(i) + results.get(i - 1));
		}
		
		return results;
	}
}
