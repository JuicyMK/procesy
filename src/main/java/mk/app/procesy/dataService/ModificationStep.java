package mk.app.procesy.dataService;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public abstract class ModificationStep {

	public Integer form;
	public Integer to;
	
	public abstract void modify(List<Pair<Integer, Double>> tmpAndH, int from, int to);
}
