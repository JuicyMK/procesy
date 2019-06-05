package mk.app.procesy.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.dataService.DataSet;

@Slf4j
public class Writer {

	private static Writer instance;
	
	private static final String OUTPUT_FORMAT = "%-10s %-10s \n";
	
	public static synchronized Writer getInstance() {
		if (instance == null) {
			instance = new Writer();
		}
		
		return instance;
	}
	
	public boolean saveFile(File file, DataSet dataSet) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println("Wyniki działania programu:");
			writer.printf(OUTPUT_FORMAT, "Tmp [\u2103]", "H [kJ/kg]");
			
			if(dataSet != null && !CollectionUtils.isEmpty(dataSet.getNewTmpAndH())) {
				dataSet.getNewTmpAndH().stream().forEachOrdered(e -> {
					writer.printf(OUTPUT_FORMAT, e.getLeft(), e.getRight());
				});
			}
			
			writer.close();
			log.info("Dane pomyślnie zapisano do pliku");
			return true;
		} catch (IOException ex) {
			log.error("Błąd zapisu do pliku");
			return false;
		}
	}

}
