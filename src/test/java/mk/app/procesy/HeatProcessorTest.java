package mk.app.procesy;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import mk.app.procesy.math.HeatProcessor;

public class HeatProcessorTest {

	@Test
	public void testHeatCompute() {
		List<Pair<Integer, Double>> inputData = new ArrayList<>();
		inputData.add(Pair.of(56, 0.44864));
		inputData.add(Pair.of(96, 0.48848));
		inputData.add(Pair.of(136, 0.50558));
		inputData.add(Pair.of(176, 0.52015));
		inputData.add(Pair.of(216, 0.53904));
		inputData.add(Pair.of(256, 0.54853));
		inputData.add(Pair.of(296, 0.56379));
		inputData.add(Pair.of(336, 0.57672));
		inputData.add(Pair.of(376, 0.59056));
		inputData.add(Pair.of(416, 0.60479));
		inputData.add(Pair.of(456, 0.62379));
		inputData.add(Pair.of(457, 0.62522));
		inputData.add(Pair.of(496, 0.64876));
		inputData.add(Pair.of(536, 0.67919));
		inputData.add(Pair.of(576, 0.69786));
		inputData.add(Pair.of(616, 0.73987));
		inputData.add(Pair.of(656, 0.77636));
		inputData.add(Pair.of(676, 0.79818));
		inputData.add(Pair.of(696, 0.83678));
		
		List<Double> expectedResults = new ArrayList<>();
		expectedResults.add(0.0);
		expectedResults.add(18.742);
		expectedResults.add(38.623);
		expectedResults.add(59.138);
		expectedResults.add(80.322);
		expectedResults.add(102.073);
		expectedResults.add(124.319);
		expectedResults.add(147.129);
		expectedResults.add(170.475);
		expectedResults.add(194.382);
		expectedResults.add(218.954);
		expectedResults.add(219.579);
		expectedResults.add(244.422);
		expectedResults.add(270.981);
		expectedResults.add(298.522);
		expectedResults.add(327.277);
		expectedResults.add(357.602);
		expectedResults.add(373.347);
		expectedResults.add(389.697);
		
		List<Double> results = HeatProcessor.compute(inputData);
		assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
	
	@Test
	public void testShortData() {
		List<Pair<Integer, Double>> inputData = new ArrayList<>();
		inputData.add(Pair.of(56, 0.44864));
		inputData.add(Pair.of(96, 0.48848));
		
		List<Double> expectedResults = new ArrayList<>();
		expectedResults.add(0.0);
		expectedResults.add(18.742);
		
		List<Double> results = HeatProcessor.compute(inputData);
		assertArrayEquals(expectedResults.toArray(), results.toArray());
		
	}
	
	@Test
	public void testSingleRow() {
		List<Pair<Integer, Double>> inputData = new ArrayList<>();
		inputData.add(Pair.of(56, 0.44864));
		
		List<Double> expectedResults = new ArrayList<>();
		expectedResults.add(0.0);
		
		List<Double> results = HeatProcessor.compute(inputData);
		assertArrayEquals(expectedResults.toArray(), results.toArray());
	}
	
}
