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

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class DataSet {
	
	private List<Pair<Integer, Double>> orgTmpAndH;
	private List<Pair<Integer, Double>> newTmpAndH;
	
	private List<ModificationStep> steps; 

	public DataSet(List<Integer> tmp, List<Double> h) throws Exception {
		orgTmpAndH = new ArrayList<>();
		newTmpAndH = new ArrayList<>();
		steps = new ArrayList<>();
		
		if (tmp.size() != h.size()) {
			log.error("Lista tmp nie pokrywa się z listą H");
			throw new Exception("Niewłaściwe rozmiary list tmp i h");
		}
		
		orgTmpAndH = IntStream.range(0, tmp.size() - 1).boxed()
				.map(e -> Pair.of(tmp.get(e), h.get(e)))
				.collect(Collectors.toList());
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
