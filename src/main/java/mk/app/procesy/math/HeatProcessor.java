package mk.app.procesy.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.decimal4j.util.DoubleRounder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeatProcessor {

	public static List<Double> compute(List<Pair<Integer, Double>> tmpAndCp) {
		List<Double> heat = new ArrayList<>(tmpAndCp.size());
		
		heat.add(0.0);
		double prevH = 0.0;
		for (int i = 1; i < tmpAndCp.size(); i++) {
			double average = (tmpAndCp.get(i - 1).getRight() + tmpAndCp.get(i).getRight()) / 2.0;
			int deltaTmp = tmpAndCp.get(i).getLeft() - tmpAndCp.get(i - 1).getLeft();
			
			double h = DoubleRounder.round(prevH + (average * deltaTmp), 3);
			prevH = h;
			
			heat.add(h);
			log.debug("H : {}, wyliczono dla prevH {}, Tmp {}, prevTmp {}, Cp {}, prevCp {}", h, prevH, tmpAndCp.get(i).getLeft(), 
					tmpAndCp.get(i - 1).getLeft(), tmpAndCp.get(i).getRight(), tmpAndCp.get(i - 1).getRight());
		}
		
		log.debug("Wyliczono H : {}", heat); 
		return heat;
	}
}
