package mk.app.procesy.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import mk.app.procesy.dataService.DataSet;

@Slf4j
public class StepsController implements Initializable {

	@FXML private JFXRadioButton normalDist;
    @FXML private JFXRadioButton random;
    @FXML private JFXRadioButton linear;
    @FXML private JFXRadioButton evenly;
    @FXML private JFXRadioButton atTheEnd;
    @FXML private JFXRadioButton atTheBegining;
    @FXML private AnchorPane contextMenu;
    @FXML private AnchorPane rightAnchor;
    
    private List<JFXRadioButton> radioButtons = new ArrayList<>();
    
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
    
    private DataSet dataSet;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		radioButtons.add(normalDist);
		radioButtons.add(random);
		radioButtons.add(linear);
		radioButtons.add(evenly);
		radioButtons.add(atTheEnd);
		radioButtons.add(atTheBegining);
		
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
    
    @FXML void addAction(ActionEvent event) {
    	//Validate, add step to dataSet and close window if OK
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
    		initializeBothField("Wskaż parametry funkcji liniowej", "", "Podaj współczynnik a", "Podaj współczynnik b");
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
    	Separator separator = new Separator();

    	scopeFrom = new JFXTextField();
    	scopeTo = new JFXTextField();
    	value = new JFXTextField();
    	
    	scopeFrom.setPromptText("Początek zakresu (minimum: " + ((dataSet != null) ? dataSet.getFirstTmp() : 0) + ")");
    	scopeTo.setPromptText("Podaj koniec zakresu (maximum: " + ((dataSet != null) ? dataSet.getLastTmp() : 0)+ ")");
    	value.setPromptText("Ciepło");
    	
    	Label tmpDescription = new Label("Podaj zakres temperatur");
    	Label valueDescription = new Label("Podaj ilość ciepła do rozlokowania");
    	
    	lowerMenu.getChildren().addAll(new Label(), separator, tmpDescription, scopeFrom, scopeTo,
    			valueDescription, value);
    }
    
    public void setDataSet(DataSet dataSet) {
    	this.dataSet = dataSet;
    }
}
