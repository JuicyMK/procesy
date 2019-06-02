package mk.app.procesy;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import mk.app.procesy.dataService.EvenlyStep;

public class EvenlyStepTest {

	@Test
	public void testEvenlyStepModify() {
		List<Pair<Integer, Double>> inputList = new ArrayList<>();
		inputList.add(Pair.of(1, 0.0));
		inputList.add(Pair.of(2, 15.0));
		inputList.add(Pair.of(3, 30.0));
		inputList.add(Pair.of(4, 34.5));
		inputList.add(Pair.of(5, 39.0));
		inputList.add(Pair.of(6, 43.5));
		inputList.add(Pair.of(7, 48.0));
		
		List<Double> expectedResults = new ArrayList<>();
		expectedResults.add(0.0);
		expectedResults.add(2.0);
		expectedResults.add(4.0);
		expectedResults.add(6.0);
		expectedResults.add(8.0);
		expectedResults.add(10.0);
		expectedResults.add(10.0);

		EvenlyStep es = new EvenlyStep(2, 6, 10.0);
		List<Double> results = es.modify(inputList);
		assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
	
	@Test
	public void testBoundryValues() {
		List<Pair<Integer, Double>> inputList = new ArrayList<>();
		inputList.add(Pair.of(1, 0.0));
		inputList.add(Pair.of(2, 15.0));
		inputList.add(Pair.of(3, 30.0));
		inputList.add(Pair.of(4, 34.5));
		inputList.add(Pair.of(5, 39.0));
		inputList.add(Pair.of(6, 43.5));
		inputList.add(Pair.of(7, 48.0));
		
		List<Double> expectedResultsL = new ArrayList<>();
		//expectedResultsL.add(3.3333333333333335);
		//expectedResultsL.add(6.666666666666667);
		expectedResultsL.add(3.333);
		expectedResultsL.add(6.667);
		expectedResultsL.add(10.0);
		expectedResultsL.add(10.0);
		expectedResultsL.add(10.0);
		expectedResultsL.add(10.0);
		expectedResultsL.add(10.0);

		List<Double> expectedResultsR = new ArrayList<>();
		expectedResultsR.add(0.0);
		expectedResultsR.add(0.0);
		expectedResultsR.add(0.0);
		expectedResultsR.add(0.0);
		//expectedResultsR.add(3.6666666666666665);
		//expectedResultsR.add(7.333333333333333);
		expectedResultsR.add(3.667);
		expectedResultsR.add(7.333);
		expectedResultsR.add(11.0);

		//Left boundary values
		EvenlyStep es = new EvenlyStep(1, 3, 10.0);
		List<Double> results = es.modify(inputList);
		assertArrayEquals(expectedResultsL.toArray(), results.toArray());
		
		//right boundary values
		es = new EvenlyStep(5, 7, 11);
		results = es.modify(inputList);
		assertArrayEquals(expectedResultsR.toArray(), results.toArray());
	}
}
