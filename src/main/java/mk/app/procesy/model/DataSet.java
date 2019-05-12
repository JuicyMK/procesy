package mk.app.procesy.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSet {
	
	private List<Pair<Double, Double>> tmpAndCp;

}
