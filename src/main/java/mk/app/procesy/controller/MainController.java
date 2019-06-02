package mk.app.procesy.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.dataService.DataSet;
import mk.app.procesy.dataService.ModificationStep;
import mk.app.procesy.io.Reader;
import mk.app.procesy.io.Writer;
import mk.app.procesy.math.HeatProcessor;
import mk.app.procesy.math.Interpolation;

@Slf4j
public class MainController implements Initializable{

	@FXML private AnchorPane mainPane;
	@FXML private JFXButton selectFileBtn;
	@FXML private JFXButton modifyBtn;
	@FXML private JFXButton saveFileBtn1;
    @FXML private AnchorPane rightAnchor;
    @FXML private ScrollPane stepsSP;
    
	private LineChart<Number, Number> chart;
    
	private DataSet data;
	
	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel("H");
		yAxis.setLabel("Temperatura");
		
		chart = new LineChart<Number, Number>(xAxis, yAxis);
		chart.setTitle("Ciepło");
		
		rightAnchor.getChildren().add(chart);
		rightAnchor.setTopAnchor(chart, 10.0);
		rightAnchor.setLeftAnchor(chart, 10.0);
		rightAnchor.setBottomAnchor(chart, 10.0);
		rightAnchor.setRightAnchor(chart, 10.0);
		
		updateSteps();
	}

	@FXML
	void readData(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wczytaj plik");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showOpenDialog(new Stage());
		Reader reader = Reader.getInstance();
		if (selectedFile != null) {
			reader.setFile(selectedFile);
			selectFileBtn.setText(selectedFile.getName());

			prepareAndSaveDatas(reader);
			updateMainChart();
			
			log.debug("Selected file {}", selectedFile.getName());
		} else {
			reader.setFile(null);
			data = null;
			updateMainChart();
			
			selectFileBtn.setText("Wczytaj dane");
			log.warn("File not found");
		}
	}

	@FXML
    void modify(ActionEvent event) {
		if (data == null) {
			showDialog(mainPane, "Brak danych", "Przed dodaniem kolejnych kroków, wczytaj dane");
			log.info("Brak danych: zablokowano możliwość próby dodania kolejnych korków.");
		} else {
			try {
	        	StepsController stepsController = new StepsController();
	        	stepsController.setDataSet(data);
	        	stepsController.setMainController(this);
	        	
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Steps.fxml"));
	            fxmlLoader.setController(stepsController);
	            
	            Parent root = (Parent) fxmlLoader.load();
	            Stage stage = new Stage();
	            stage.initModality(Modality.WINDOW_MODAL);
	            stage.setTitle("Dodaj krok");
	            stage.setScene(new Scene(root));
	            stage.show();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		}  
    }

	@FXML
    void saveFile(ActionEvent event) {
		if (data == null || CollectionUtils.isEmpty(data.getOrgTmpAndH())) {
			showDialog(mainPane, "Błąd", "Brak danych do zapisania");
			log.debug("Brak danych do zapisania");
			return ;
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Zapisz plik");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		
		File file = fileChooser.showSaveDialog(new Stage());
		
		if (file != null) {
			Writer writer = Writer.getInstance();
			writer.saveFile(file, data);
		}
		
    }

	private void prepareAndSaveDatas(Reader reader) {
		List<Pair<Integer, Double>> inputData = reader.parse();
		
		inputData = Interpolation.interpol(inputData);
		
		List<Integer> tmp = inputData.stream().map(e -> e.getLeft()).collect(Collectors.toList());
		List<Double> computeH = HeatProcessor.compute(inputData);
		
		try {
			data = new DataSet(tmp ,computeH);
			log.info("Dane do przetwarzania zostały poprawnie zapisane");
		} catch (Exception e) {
			data = null;
			log.error("Błąd łączenia danych: {}", e);
		}
	}
	
	void updateSteps() {
		VBox stepMenu = new VBox(3.0);
		Text stepsInfo = new Text("Lista dodanych kroków");
		stepsInfo.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
		stepsInfo.setTextAlignment(TextAlignment.CENTER);
		stepMenu.getChildren().add(stepsInfo);
		stepMenu.setAlignment(Pos.CENTER);
		stepsSP.setContent(stepMenu);
		
		if (data != null && !CollectionUtils.isEmpty(data.getSteps())) {
			for (ModificationStep step : data.getSteps()) {
				VBox stepObject = new VBox(3.0);
				stepObject.setMinWidth(275.0);
				stepObject.setAlignment(Pos.CENTER);
				Text stepName = new Text(step.getName());
				Text stepScope = new Text("Od: " + step.getFrom() + " do: " + step.getTo());
				Text valueH = new Text("Dodano: " + step.getValueH());
				Separator separator = new Separator();
				
				stepObject.getChildren().addAll(stepName, stepScope, valueH, separator);
				stepMenu.getChildren().add(stepObject);
			}
		}
	}
	
	void showDialog(AnchorPane paneToDisplay, String title, String message) {
		StackPane dialogPane = new StackPane();

		paneToDisplay.getChildren().add(dialogPane);
		paneToDisplay.setTopAnchor(dialogPane, 0.0);
		paneToDisplay.setLeftAnchor(dialogPane, 0.0);
		paneToDisplay.setBottomAnchor(dialogPane, 0.0);
		paneToDisplay.setRightAnchor(dialogPane, 0.0);

		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setHeading(new Text(title));
		dialogLayout.setBody(new Text(message));

		JFXDialog confirmDeleteDialog = new JFXDialog(dialogPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
		JFXButton buttonExit = new JFXButton("Ok");

		buttonExit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			confirmDeleteDialog.close();
			paneToDisplay.getChildren().remove(dialogPane);
		});

		confirmDeleteDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			confirmDeleteDialog.close();
			paneToDisplay.getChildren().remove(dialogPane);
		});

		dialogLayout.setActions(buttonExit);
		confirmDeleteDialog.show();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void updateMainChart() {
		log.debug("Aktualizuję wykres, tmp i h: ", data.getNewTmpAndH());
		if (data != null) {
			chart.getData().clear();
			
			XYChart.Series<Number, Number> orginalData = new XYChart.Series<Number, Number>();
			orginalData.setName("Oryginalne wartości");

			for (Pair<Integer, Double> row : data.getOrgTmpAndH()) {
				orginalData.getData().add(new XYChart.Data(row.getLeft(), row.getRight()));
			}

			chart.getData().add(orginalData);
			
			if (!CollectionUtils.isEmpty(data.getSteps())) {
				XYChart.Series<Number, Number> dataWithSteps = new XYChart.Series<Number, Number>();
				dataWithSteps.setName("Zaktualizowane wartości");

				for (Pair<Integer, Double> row : data.getNewTmpAndH()) {
					dataWithSteps.getData().add(new XYChart.Data(row.getLeft(), row.getRight()));
				}
				
				chart.getData().add(dataWithSteps);
			}
		} 
	}
}
