package mk.app.procesy.controller;

import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import mk.app.procesy.model.DataSet;

public class StepsController {

	@FXML private JFXRadioButton normalDist;
    @FXML private JFXRadioButton random;
    @FXML private JFXRadioButton linear;
    @FXML private JFXRadioButton evenly;
    @FXML private JFXRadioButton atTheEnd;
    @FXML private JFXRadioButton atTheBegining;
    @FXML private AnchorPane optionAnchor;
    @FXML private AnchorPane rightAnchor;
    
    private DataSet dataSet;
    
    @FXML void addAction(ActionEvent event) {
    	//Validate, add step to dataSet and close window if OK
    }
    
    public void setDataSet(DataSet dataSet) {
    	this.dataSet = dataSet;
    }
}
