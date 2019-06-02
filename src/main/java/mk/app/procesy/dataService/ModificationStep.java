package mk.app.procesy.dataService;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public abstract class ModificationStep {
	
	protected int from;
	protected int to;
	
	public abstract void modify(List<Pair<Integer, Double>> tmpAndH, double valueH);
	
	public void setFrom(int from) {
		this.from = from;
	}

	public int getFrom() {
		return from;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getTo() {
		return to;
	}
}
