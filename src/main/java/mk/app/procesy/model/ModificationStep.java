package mk.app.procesy.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ModificationStep {

	public void modify(List<Pair<Integer, Double>> tmpAndH, int from, int to);
}
