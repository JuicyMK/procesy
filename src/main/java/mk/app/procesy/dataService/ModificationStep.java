package mk.app.procesy.dataService;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ModificationStep {
	
	@Getter
	protected int from;
	@Getter
	protected int to;
	@Getter
	protected double valueH;
	@Getter
	protected List<Double> lastResult;
	
	public abstract List<Double> modify(List<Pair<Integer, Double>> tmpAndH);
	
	protected Pair<Integer, Integer> lookForScope(List<Pair<Integer, Double>> tmpAndH) {
		Integer firstIdx = null;
		Integer lastIdx = null; 
		
		for (int i = 0; i <tmpAndH.size(); i++) {
			if (tmpAndH.get(i).getLeft().compareTo(from) == 0) {
				firstIdx = i;
				log.trace("EvenlyStep:lookForScope firstIdx : {}", firstIdx);
			} else if (tmpAndH.get(i).getLeft().compareTo(to) == 0) {
				lastIdx = i;
				log.trace("EvenlyStep:lookForScope lastIdx : {}", lastIdx);
				break;
			}
		}
		return Pair.of(firstIdx, lastIdx);
	}
	
	public abstract String getName();
	
	public abstract void stetExtraValue(Object first, Object second);
	
}
