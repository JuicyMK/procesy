package mk.app.procesy.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Reader {
	
	private static Reader instance;
	
	private File file;
	
	public static synchronized Reader getInstance() {
		if (instance == null) {
			instance = new Reader();
		}
		return instance;
	}

	public List<Pair<Integer, Double>> parse() {
		log.info("Parsing  file");
		if (file == null) {
			log.warn("File to parse is null");
			return Collections.emptyList();
		}

		List<Pair<Integer, Double>> loadedData = new ArrayList<>();
		BufferedReader br = null;
		try {	
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			do {
				String[] part = line.split(" ", 2);
				if (part.length == 2) {
					try {
						Double tmp = Double.parseDouble(part[0]);
						Double cp = Double.parseDouble(part[1]);

						loadedData.add(Pair.of(tmp.intValue(), cp));
					} catch (NumberFormatException | NullPointerException singleDataException) {
						log.warn("Single data parsing error in line {}, {}", line, singleDataException);
					}
				} else {
					log.warn("Incorrect data, skipping line: {}", line);
				}

				line = br.readLine();
			} while (line != null && !line.isEmpty());

			br.close();
			log.info("Loaded data: {}", loadedData);
			return loadedData;
		} catch (IOException fileException) {
			log.error("Parsing file error : {}", fileException);
			return Collections.emptyList();
		}
	}

}
