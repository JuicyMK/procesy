package mk.app.procesy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.dataService.LinearStep;
import mk.app.procesy.dataService.NormalDistributionStep;

@Slf4j
public class RandomStepTest {

	@Test
	public void testRandomStep() {
		List<Pair<Integer, Double>> inputList = new ArrayList<>();
		inputList.add(Pair.of(1, 0.0));
		inputList.add(Pair.of(2, 15.0));
		inputList.add(Pair.of(3, 30.0));
		inputList.add(Pair.of(4, 34.5));
		inputList.add(Pair.of(5, 39.0));
		inputList.add(Pair.of(6, 43.5));
		inputList.add(Pair.of(7, 48.0));

		NormalDistributionStep nds = new NormalDistributionStep(1, 5, 10.0, 0, 1);
		List<Double> results = nds.modify(inputList);
		
		log.debug("Results: {}", results);
		//assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
	
	@Test
	public void testLinearStep() {
		List<Pair<Integer, Double>> inputList = new ArrayList<>();
		inputList.add(Pair.of(1, 0.0));
		inputList.add(Pair.of(2, 15.0));
		inputList.add(Pair.of(3, 30.0));
		inputList.add(Pair.of(4, 34.5));
		inputList.add(Pair.of(5, 39.0));
		inputList.add(Pair.of(6, 43.5));
		inputList.add(Pair.of(7, 48.0));

		LinearStep nds = new LinearStep(1, 5, 10.0, 3, 4);
		List<Double> results = nds.modify(inputList);
		
		log.debug("Results: {}", results);
		//assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
}
