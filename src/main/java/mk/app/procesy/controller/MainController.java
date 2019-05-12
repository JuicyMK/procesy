package mk.app.procesy.controller;

import java.io.File;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.io.Reader;

@Slf4j
public class MainController {

	@FXML private JFXButton selectFileBtn;
	@FXML private JFXButton computeBtn;
	@FXML private JFXButton saveFileBtn1;

	@FXML
	void readData(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showOpenDialog(new Stage());
		Reader reader = Reader.getInstance();
		if (selectedFile != null) {
			reader.setFile(selectedFile);
			selectFileBtn.setText(selectedFile.getName());
			//TODO: handle parsed data
			reader.parse();
			log.debug("Selected file {}", selectedFile.getName());
		} else {
			reader.setFile(null);
			selectFileBtn.setText("Wczytaj dane");
			log.warn("File not found");
		}
	}

	@FXML
    void compute(ActionEvent event) {

    }

	@FXML
    void saveFile(ActionEvent event) {

    }
}
