package mk.app.procesy.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.util.CollectionUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.dataService.AtTheBeginingStep;
import mk.app.procesy.dataService.AtTheEndStep;
import mk.app.procesy.dataService.DataSet;
import mk.app.procesy.dataService.EvenlyStep;
import mk.app.procesy.dataService.LinearStep;
import mk.app.procesy.dataService.ModificationStep;
import mk.app.procesy.dataService.NormalDistributionStep;
import mk.app.procesy.dataService.RandomStep;

@Slf4j
public class StepsController implements Initializable {

	@FXML private AnchorPane stepsMainPane;
	
	@FXML private JFXRadioButton normalDist;
    @FXML private JFXRadioButton random;
    @FXML private JFXRadioButton linear;
    @FXML private JFXRadioButton evenly;
    @FXML private JFXRadioButton atTheEnd;
    @FXML private JFXRadioButton atTheBegining;
    
    @FXML private AnchorPane contextMenu;
    @FXML private AnchorPane rightAnchor;
    
    private List<JFXRadioButton> radioButtons = new ArrayList<>();
    
    private LineChart<Number, Number> chart;
    
    private VBox menu;
    private VBox upperMenu;
    private VBox lowerMenu;
    
    //Additional fields in contextMenu
    private JFXTextField firstInput;
    private JFXTextField secondInput;
    
    //Required fields in contextMenu
    private JFXTextField scopeFrom;
    private JFXTextField scopeTo;
    private JFXTextField value;
    private JFXButton preview;
    
    private DataSet dataSet;
    private MainController mainController;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		radioButtons.add(normalDist);
		radioButtons.add(random);
		radioButtons.add(linear);
		radioButtons.add(evenly);
		radioButtons.add(atTheEnd);
		radioButtons.add(atTheBegining);
		
		//Initialize Chart
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel("Temperatura");
		yAxis.setLabel("H");
		
		chart = new LineChart<Number, Number>(xAxis, yAxis);
		
		rightAnchor.getChildren().add(chart);
		rightAnchor.setTopAnchor(chart, 10.0);
		rightAnchor.setLeftAnchor(chart, 10.0);
		rightAnchor.setBottomAnchor(chart, 10.0);
		rightAnchor.setRightAnchor(chart, 10.0);
		
		
		//Initialize contextMenu pane
		menu = new VBox(3.0);
		upperMenu = new VBox(3.0);
		lowerMenu = new VBox(3.0);
		menu.getChildren().addAll(upperMenu, lowerMenu);
		
		contextMenu.getChildren().add(menu);
		contextMenu.setTopAnchor(menu, 0.0);
		contextMenu.setLeftAnchor(menu, 0.0);
		contextMenu.setBottomAnchor(menu, 0.0);
		contextMenu.setRightAnchor(menu, 0.0);
		
		initializeCommonFields();
		
		normalDist.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		random.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		linear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		evenly.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		atTheEnd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		atTheBegining.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});
		
		normalDist.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleRadioButtonAction(event);
				buildContextMenu(event);
			}
		});

	}
    
    @FXML void addStep(ActionEvent event) {
    	ModificationStep step = readFieldsAndGetStep();
    	step.modify(dataSet.getNewTmpAndH());
    	if (step == null) {
    		mainController.showDialog(stepsMainPane, "Uwaga", "Nie utworzono żadnego kroku ");
    	} else {
    		dataSet.addStep(step);
    		mainController.updateSteps();
    		mainController.updateMainChart();
    	}
    }
    
    private void handleRadioButtonAction(ActionEvent event) {
    	radioButtons.stream().forEach(e -> e.setSelected(false));
		
		JFXRadioButton radioBtn = (JFXRadioButton) event.getSource();
		radioBtn.setSelected(true);
    }
    
    private void buildContextMenu(ActionEvent event) {
    	JFXRadioButton radioBtn = (JFXRadioButton) event.getSource();
    	upperMenu.getChildren().clear();
    	if (radioBtn.equals(normalDist)) {
    		initializeBothField("Wartości zostaną dodane zgodnie z", "rozkładem normalnym", "Podaj wartośc oczekiwaną",
    				"Podaj wariancję");
    		log.debug("Buduje menu kontekstowe dla rozkładu normalnego");
    	} else if (radioBtn.equals(random)) {
    		initializeOnlyDescription("Wartość ciepła zostanie rozlosowana", "we wskazanym zakresie");
    		log.debug("Buduje menu kontekstowe dla funkcji losującej");
    	} else if (radioBtn.equals(linear)) {
    		initializeBothField("Wskaż parametry funkcji liniowej.", "Wartość H jest zbędna", "Podaj współczynnik a", "Podaj współczynnik b");
    		log.debug("Buduje menu kontekstowe dla funkcji liniowej");
    	} else if (radioBtn.equals(evenly)) {
    		initializeOnlyDescription("Podana wartość zostanie równomiernie", "rozmieszczona na przedziale");
    		log.debug("Buduje menu kontekstowe dla rozkładu równomiernego");
    	} else if (radioBtn.equals(atTheEnd)) {
    		initializeOnlyDescription("Podana wartośc zostanie dodana", "na końcu przedziału");
    		log.debug("Buduje menu kontekstowe dla dodawania wartości na koniec przedziału");
    	} else if (radioBtn.equals(atTheBegining)) {
    		initializeOnlyDescription("Podana wartość zostanie dodane", "na początku przedziału");
    		log.debug("Buduje menu kontekstowe dla dodawania wartości na początek przedziału");
    	} else {
    		log.error("Nie znaleziono dopasowania dla wyboru menu contextowego");
    	}
    }
    
    private void initializeOnlyDescription(String descriptionFirstLine, String descriptionSecondLine) {
    	Label descFirstLine = new Label(descriptionFirstLine);
    	Label descSecondLine = new Label(descriptionSecondLine);
    	
    	upperMenu.getChildren().addAll(descFirstLine, descSecondLine);
    }
        
    private void initializeBothField(String descriptionFirstLine, String descriptionSecondLine, String firstPromptTextMessage, 
    		String secondPromptTextMessage) {
    	firstInput = new JFXTextField();
		secondInput = new JFXTextField();
		
		firstInput.setPromptText(firstPromptTextMessage);
		secondInput.setPromptText(secondPromptTextMessage);
		
		Label descFirstLine = new Label(descriptionFirstLine);
		Label descSecondLine = new Label(descriptionSecondLine);
		
		upperMenu.getChildren().addAll(descFirstLine, descSecondLine, firstInput, secondInput);
    }
    
    private void initializeCommonFields() {
    	initializeCommonFields(false);
    }
    
    private void initializeCommonFields(boolean disableValue) {
    	Separator separator = new Separator();

    	scopeFrom = new JFXTextField();
    	scopeTo = new JFXTextField();
    	value = new JFXTextField();
    	
    	scopeFrom.setPromptText("Początek zakresu (minimum: " + ((dataSet != null) ? dataSet.getFirstTmp() : 0) + ")");
    	scopeTo.setPromptText("Podaj koniec zakresu (maximum: " + ((dataSet != null) ? dataSet.getLastTmp() : 0)+ ")");
    	value.setPromptText("Ciepło");
    	value.setDisable(disableValue);
    	
    	Label tmpDescription = new Label("Podaj zakres temperatur");
    	Label valueDescription = new Label("Podaj ilość ciepła do rozlokowania");
    	
    	preview = new JFXButton("Pokaż podgląd");
    	preview.setStyle("-fx-background-color:#373A36; -fx-font-weight: bold;");
    	preview.setTextFill(Color.WHITE);
    	preview.setMinWidth(278.0);
    	
    	preview.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
    		tryReadFieldsAndShowChart();
    	});
    	
    	lowerMenu.getChildren().addAll(new Label(), separator, tmpDescription, scopeFrom, scopeTo,
    			valueDescription, value, preview);
    }
    
    private void tryReadFieldsAndShowChart() {
    	ModificationStep step = readFieldsAndGetStep();
    	if (step != null) {
    		updateChart(step);
    	}
    }
    
    private ModificationStep readFieldsAndGetStep() {
    	if (normalDist.isSelected()) {
    		log.debug("Parsuje pola rozkładu normalnego");
    		Pair<Double, Double> extraInputFields = readDoubleFromBothInputField();
    		Triple<Integer, Integer, Double> commonFields = readCommonFields();
    		if (extraInputFields != null && commonFields != null) {
    			return new NormalDistributionStep(commonFields.getLeft(), commonFields.getMiddle(),
    					commonFields.getRight(), extraInputFields.getLeft(), extraInputFields.getRight());
    		}
    		return null;
    		
    	} else if (random.isSelected()) {
    		log.debug("Parsuje pola  dla funkcji losującej");
    		Triple<Integer, Integer, Double> commonFields = readCommonFields();
    		if (commonFields != null) {
    			return new RandomStep(commonFields.getLeft(), commonFields.getMiddle(),
    					commonFields.getRight());
    		}
    		return null;
    		
    	} else if (linear.isSelected()) {
    		log.debug("Parsuje pola  dla funkcji liniowej");
    		Pair<Double, Double> extraInputFields = readDoubleFromBothInputField();
    		Triple<Integer, Integer, Double> commonFields = readCommonFields(true);
    		if (extraInputFields != null && commonFields != null) {
    			if (extraInputFields.getLeft() <= 0) {
    				firstInput.setText("Podaj wartość większą od 0");
    				return null;
    			}
    			return new LinearStep(commonFields.getLeft(), commonFields.getMiddle(), commonFields.getRight(), 
    					extraInputFields.getLeft(), extraInputFields.getRight());
    		}
    		return null;
    		
    	} else if (evenly.isSelected()) {
    		log.debug("Parsuje pola  dla rozkładu równomiernego");
    		Triple<Integer, Integer, Double> commonFields = readCommonFields();
    		if (commonFields != null) {
    			return new EvenlyStep(commonFields.getLeft(), commonFields.getMiddle(),
    					commonFields.getRight());
    		}
    		return null;
    		
    	} else if (atTheEnd.isSelected()) {
    		log.debug("Parsuje pola  dla dodawania wartości na koniec przedziału");
    		Triple<Integer, Integer, Double> commonFields = readCommonFields();
    		if (commonFields != null) {
    			return new AtTheEndStep(commonFields.getLeft(), commonFields.getMiddle(),
    					commonFields.getRight());
    		}
    		return null;
    		
    	} else if (atTheBegining.isSelected()) {
    		log.debug("Parsuje pola  dla dodawania wartości na początek przedziału");
    		Triple<Integer, Integer, Double> commonFields = readCommonFields();
    		if (commonFields != null) {
    			return new AtTheBeginingStep(commonFields.getLeft(), commonFields.getMiddle(),
    					commonFields.getRight());
    		}
    		return null;
    		
    	} else {
    		log.debug("Nie wybrano rodzaju kroku");
    		return null;
    	}
    }
    
    private Pair<Double, Double> readDoubleFromBothInputField() {
    	Double first = null;
    	Double second = null;
    	
    	boolean success = true;
    	
    	try {
    		first = Double.parseDouble(firstInput.getText());
    	} catch (NumberFormatException e) {
    		firstInput.setText("Błąd: Podaj wartość liczbową");
    		success = false;
    		log.debug("Parsowanie pola firstInput nie powiodło się");
    	}
    	
    	try {
    		second = Double.parseDouble(secondInput.getText());
    	} catch (NumberFormatException e) {
    		secondInput.setText("Błąd: Podaj wartość liczbową");
    		success = false;
    		log.debug("Parsowanie pola secondInput nie powiodło się");
    	}
    	
    	if (success) {
    		log.debug("Odczytano wartoći zmiennoprzecinkowe z dodatkowych pól firstInput {}, secondInput {}",
    				first, second);
    		return Pair.of(first, second);
    	}
    	log.debug("Nie odczytano poprawnie wartości zmiennoprzecinkowych z dodatkowych pól");
    	return null;
    }
    
    private Triple<Integer, Integer, Double> readCommonFields() {
    	return readCommonFields(false);
    }
    
    private Triple<Integer, Integer, Double> readCommonFields(boolean disapleValue) {
    	Integer from = null;
    	Integer to = null;
    	Double valueH = null;
    	
    	boolean success = true;
    	
    	try {
    		from = Integer.parseInt(scopeFrom.getText());
    	} catch (NumberFormatException e) {
    		scopeFrom.setText("Błąd: Podaj liczbę całkowitą");
    		success = false;
    		log.debug("Parsowanie pola scopeFrom nie powiodło się");
    	}
    	
    	try {
    		to = Integer.parseInt(scopeTo.getText());
    	} catch (NumberFormatException e) {
    		scopeTo.setText("Błąd: Podaj liczbę całkowitą");
    		success = false;
    		log.debug("Parsowanie pola scopeTo nie powiodło się");
    	}
    	
    	if (!disapleValue) {
    		try {
        		valueH = Double.parseDouble(value.getText());
        		if (valueH <= 0) {
        			success = false;
        			value.setText("Wartość powinna być większa od 0");
        			log.debug("Parsowanie pola value nie powiodło się: wybrano wartość 0 lub mniejszą");
        		}
        	} catch (NumberFormatException e) {
        		value.setText("Błąd: Podaj wartość liczbową");
        		success = false;
        		log.debug("Parsowanie pola value nie powiodło się ");
        	}
    	} else {
    		valueH = 0.0;
    	}
    	
    	if (from >= to) {
    		scopeFrom.setText("Nie może być mniejsze od końca");
    		success = false;
    	}
    	
    	if (from < dataSet.getFirstTmp()) {
    		scopeFrom.setText("Wartość mniejsza wczytany niż przedział");
    		success = false;
    	}
    	
    	if (to > dataSet.getLastTmp()) {
    		scopeTo.setText("Wartość większa niż wczytany przedział");
    		success = false;
    	}
    	
    	if (!CollectionUtils.isEmpty(dataSet.getSteps())) {
    		for (ModificationStep el : dataSet.getSteps()) {
    			if ((from >= el.getFrom()) && (from <= el.getTo())) {
    				success = false;
    				scopeFrom.setText("Obsadzone przez inny krok");
    				log.debug("Wybrany początek przedziału pokrywa się z innym przedziałem");
    			}
    			
    			if ((to >= el.getFrom()) && (to <= el.getTo())) {
    				success = false;
    				scopeTo.setText("Obsadzone przez inny krok");
    				log.debug("Wybrany koniec przedziału pokrywa się z innym przedziałem");
    			}
    		}
    	}
    	
    	if (success) {
    		log.debug("Odczytano wartości z pól wspólnych from {}, to {}, valueH {}",
    				from, to, valueH);
    		return Triple.of(from, to, valueH);
    	}
    	log.debug("Nie odczytano poprawnie wartości z pól wspólnych");
    	return null;
    }
    
    private void updateChart(ModificationStep step) {
    	log.debug("Aktualizuję wykres, tmp i h: ");
		if (step != null) {
			XYChart.Series<Number, Number> exampleDistribution = new XYChart.Series<Number, Number>();
			exampleDistribution.setName("Wizualizacja");

			List<Double> h = step.modify(dataSet.getOrgTmpAndH());
			List<Integer> tmpList = new ArrayList<>(h.size());
			IntStream.range(dataSet.getFirstTmp(), dataSet.getLastTmp() + 1).boxed().forEachOrdered(e -> tmpList.add(e));

			for (int i = 0; i < h.size(); i++) {
				exampleDistribution.getData().add(new XYChart.Data(tmpList.get(i), h.get(i)));
			}
			log.debug("Aktualizuję wizualizację danych na wykresie");

			chart.getData().clear();
			chart.getData().add(exampleDistribution);
		} 
    } 
    
    void setDataSet(DataSet dataSet) {
    	this.dataSet = dataSet;
    }
    
    void setMainController(MainController mainController) {
    	this.mainController = mainController;
    }
    
}
