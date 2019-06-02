package mk.app.procesy.dataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class DataSet {
	
	@Getter
	@Setter
	private List<Pair<Integer, Double>> orgTmpAndH;
	
	@Getter
	@Setter
	private List<Pair<Integer, Double>> newTmpAndH;
	
	@Getter
	private List<ModificationStep> steps; 

	public DataSet(List<Integer> tmp, List<Double> h) throws Exception {
		orgTmpAndH = new ArrayList<>();
		newTmpAndH = new ArrayList<>();
		steps = new ArrayList<>();
		
		if (tmp.size() != h.size()) {
			log.error("Lista tmp nie pokrywa się z listą H");
			throw new Exception("Niewłaściwe rozmiary list tmp i h");
		}
		
		orgTmpAndH = IntStream.range(0, tmp.size()).boxed()
				.map(e -> Pair.of(tmp.get(e), h.get(e)))
				.collect(Collectors.toList());
		
		newTmpAndH = IntStream.range(0, tmp.size()).boxed()
				.map(e -> Pair.of(tmp.get(e), h.get(e)))
				.collect(Collectors.toList());
	}
	
	public void addStep(ModificationStep step) {
		List<Double> stepResults = step.getLastResult();
		if (newTmpAndH.size() != stepResults.size()) {
			log.error("Błąd modyfikacji wartości H. Listy wartości mają różne długości");
			return;
		}
		
		IntStream.range(0, newTmpAndH.size()).boxed().forEachOrdered(e -> {
			Pair<Integer, Double> orgValue = newTmpAndH.get(e);
			newTmpAndH.set(e, Pair.of(orgValue.getLeft(), orgValue.getRight() + stepResults.get(e)));
		});
		
		steps.add(step);
		log.debug("Zaktualizowano dane wynikowe");
	}
	
	public int getFirstTmp() {
		if (!CollectionUtils.isEmpty(orgTmpAndH)) {
			return orgTmpAndH.get(0).getLeft();
		}
		return 0;
	}
	
	public int getLastTmp() {
		if (!CollectionUtils.isEmpty(orgTmpAndH)) {
			return orgTmpAndH.get(orgTmpAndH.size() -1).getLeft();
		}
		return 0;
	}
}
