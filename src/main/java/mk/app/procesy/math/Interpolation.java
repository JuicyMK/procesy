package mk.app.procesy.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Interpolation {
	
	public static List<Pair<Integer, Double>> interpol(List<Pair<Integer, Double>> rawData) {
		List<Pair<Integer , Double>> interpolated = new ArrayList<>();
		
		if (rawData.size() < 2) {
			log.warn("Wczytane dane sa za krótkie");
			return Collections.emptyList();
		}
		
		//IntStream.range(0, rawData.size() -2).boxed().flatMap(e -> interpolBeetween(rawData.get(e), rawData.get(e + 1))).collect(Collectors.toList());
		//forEachOrdered(e -> interpolBeetween(rawData.get(e), rawData.get(e +1)));
		
		log.debug("Interpolacja zestawu danych: {}", rawData);
		for (int i = 0; i < rawData.size() - 1; i++) {
			interpolated.addAll(interpolBeetween(rawData.get(i), rawData.get(i+1)));	
		}
		interpolated.add(rawData.get(rawData.size() -1));
		
		log.debug("Interpolacja wyniki: {}", interpolated);
		return interpolated;
	}
	
	private static List<Pair<Integer, Double>> interpolBeetween(Pair<Integer , Double> current, 
			Pair<Integer , Double> next) {
		List<Pair<Integer, Double>> results = new ArrayList<>();
		
		int tmpDiff = next.getLeft() - current.getLeft();
		double cpDiff = next.getRight() - current.getRight();
		double delta = cpDiff / tmpDiff;
		
		Integer tmp = current.getLeft();
		Double cp = current.getRight();
		
		results.add(current);
		for (int i = 1; i < tmpDiff; i++) {
			tmp++;
			cp += delta;
			results.add(Pair.of(tmp, cp));
		}
		
		log.debug("Interpolacja cząstkowa od: {}, do {}: wynik: {}", current, next, results);
		return results;
	}
}
