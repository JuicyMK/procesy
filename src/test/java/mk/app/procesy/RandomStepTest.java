package mk.app.procesy;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import mk.app.procesy.dataService.EvenlyStep;
import mk.app.procesy.dataService.RandomStep;

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

		RandomStep es = new RandomStep(2, 6, 10.0);
		List<Double> results = es.modify(inputList);
		//assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
}
