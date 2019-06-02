package mk.app.procesy.dataService;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface ModificationStep {
	
	public abstract void modify(List<Pair<Integer, Double>> tmpAndH);
	
	public void setFrom(int from);
	
	public int getFrom();
	
	public void setTo(int to);
	
	public int getTo();
}
