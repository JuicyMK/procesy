package mk.app.procesy;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import mk.app.procesy.math.Interpolation;

public class InterpolationTest {

	@Test
	public void testSimpleList() {
		List<Pair<Integer, Double>> simpleSet = new ArrayList<>();
		List<Pair<Integer, Double>> simpleSetExpected = new ArrayList<>();
		
		simpleSet.add(Pair.of(1, 0.0));
		simpleSet.add(Pair.of(3, 30.0));
		simpleSet.add(Pair.of(7, 48.0));
		simpleSet.add(Pair.of(8, 100.0));
		simpleSet.add(Pair.of(11, 121.0));
		
		simpleSetExpected.add(Pair.of(1, 0.0));
		simpleSetExpected.add(Pair.of(2, 15.0));
		simpleSetExpected.add(Pair.of(3, 30.0));
		simpleSetExpected.add(Pair.of(4, 34.5));
		simpleSetExpected.add(Pair.of(5, 39.0));
		simpleSetExpected.add(Pair.of(6, 43.5));
		simpleSetExpected.add(Pair.of(7, 48.0));
		simpleSetExpected.add(Pair.of(8, 100.0));
		simpleSetExpected.add(Pair.of(9, 107.0));
		simpleSetExpected.add(Pair.of(10, 114.0));
		simpleSetExpected.add(Pair.of(11, 121.0));
		
		List<Pair<Integer, Double>> results = Interpolation.interpol(simpleSet);
		assertArrayEquals(results.toArray(), simpleSetExpected.toArray());
	}
	
	@Test
	public void testShortSet() {
		List<Pair<Integer, Double>> shortSet = new ArrayList<>();
		List<Pair<Integer, Double>> shortSetExpected = new ArrayList<>();
		
		shortSet.add(Pair.of(3, 15.0));
		shortSet.add(Pair.of(7, 35.0));
		
		shortSetExpected.add(Pair.of(3, 15.0));
		shortSetExpected.add(Pair.of(4, 20.0));
		shortSetExpected.add(Pair.of(5, 25.0));
		shortSetExpected.add(Pair.of(6, 30.0));
		shortSetExpected.add(Pair.of(7, 35.0));
		
		List<Pair<Integer, Double>> results = Interpolation.interpol(shortSet);
		assertArrayEquals(results.toArray(), shortSetExpected.toArray());
	}
	
	@Test
	public void testTwoElements() {
		List<Pair<Integer, Double>> twoElementsSet = new ArrayList<>();
		List<Pair<Integer, Double>> twoElementsSetExpected = new ArrayList<>();
		
		twoElementsSet.add(Pair.of(3, 3.0));
		twoElementsSet.add(Pair.of(4, 4.0));
		
		twoElementsSetExpected.add(Pair.of(3, 3.0));
		twoElementsSetExpected.add(Pair.of(4, 4.0));
		
		List<Pair<Integer, Double>> results = Interpolation.interpol(twoElementsSet);
		assertArrayEquals(results.toArray(), twoElementsSetExpected.toArray());
	}
}
